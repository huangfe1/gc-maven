package com.dreamer.view.mobile;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.delivery.DeliveryItem;
import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.pmall.order.PaymentStatus;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.dto.UserDto;
import com.dreamer.domain.user.enums.AccountsTransferStatus;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.service.mobile.*;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.CommonUtil;
import com.dreamer.util.PreciseComputeUtil;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangfei on 02/07/2017.
 */
@Controller
public class MobileController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //获取用户信息
    @RequestMapping("/mobile/wxLogin.html")
    public String wxLogin(@RequestParam(required = false) String code, Model model, HttpServletRequest request) {
        if (code == null) {
            model.addAttribute("message", "wxlogin失败，没有获取code，请截图发给管理员");
            return "mobile/error";
        }
        SdkResult result = JSAPI.getTokenAndOpenId(wxConfigFactory.getBaseConfig().getAppid(), wxConfigFactory.getBaseConfig().getSecret(), code);
        if (!result.isSuccess()) {//失败
            model.addAttribute("message", result.getError() + ",请截图发给管理员");
            return "mobile/error";
        }
        JSONObject json = JSONObject.fromObject(result.getData());
        String openid = json.getString("openid");
        String access_token = json.getString("access_token");
        //调用授权接口获取用户基本信息
        result = JSAPI.getSnsUserInfo(access_token, openid);
        if (!result.isSuccess()) {
            model.addAttribute("message", result.getError() + ",请截图发给管理员");
            return "mobile/error";
        }
        json = JSONObject.fromObject(result.getData());
        String nickname = json.getString("nickname");
        String headimgurl = json.getString("headimgurl");
        String unionid = json.getString("unionid");
        //获取的信息加入session中存储
        request.getSession().setAttribute("s_openid", openid);
        request.getSession().setAttribute("s_nickname", nickname);
        request.getSession().setAttribute("s_headimgurl", headimgurl);
        request.getSession().setAttribute("s_unionid", unionid);
        //以统一Id为标准登陆
        Agent findAgent = agentHandler.get("wxUnionID", unionid);
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
        if (findAgent != null && findAgent.getAgentStatus().equals(AgentStatus.ACTIVE)) {//系统存在这个用户
            //登录
            WebUtil.addCurrentUser(request, findAgent);
            WebUtil.login(request);
            //跳转到要跳转的页面
            return "redirect:" + redirectUrl;
        } else {//新用户或者没绑定的用户  去登陆页面一键登录 或者登陆绑定
            if (redirectUrl.indexOf("pmall") > -1) {
                model.addAttribute("isPmall", true);//可以一键授权登录
            }
            return "mobile/login";
        }
    }

    //手机端登陆 一定要绑定openId，unionId
    @RequestMapping(value = "/mobile/login.json", method = RequestMethod.POST)
    @ResponseBody
    public Message mobileLogin(HttpServletRequest request, @RequestParam("accountName") String name, @RequestParam("password") String pwd, HttpServletResponse response) {
        //获取登陆的跳转地址
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
        response.setHeader("location", redirectUrl);
        try {
            Agent agent = agentHandler.login(name, pwd);
            String s_unionid = (String) request.getSession().getAttribute("s_unionid");
            String s_openid = (String) request.getSession().getAttribute("s_openid");
            String s_headimgurl = (String) request.getSession().getAttribute("s_headimgurl");
            String s_nickname = (String) request.getSession().getAttribute("s_nickname");
            if (s_unionid != null && !s_unionid.equals("")) {
                agent.setWxOpenid(s_openid);
                agent.setWxUnionID(s_unionid);//绑定
                agent.setHeadimgurl(s_headimgurl);
                agent.setNickName(s_nickname);
            }
            agentHandler.merge(agent);//保存Agent
            //登陆
            WebUtil.addCurrentUser(request, agent);
            WebUtil.login(request);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //积分商城过来的用户可以一键授权登陆  还有一个任务就是绑定上级
    @RequestMapping("/dmz/mobile/oAuthLogin.html")
    public String oAuthLogin(HttpServletRequest request) {
        Integer refCode = (Integer) request.getSession().getAttribute("refCode");
        String unionId = (String) request.getSession().getAttribute("s_unionid");
        String openId = (String) request.getSession().getAttribute("s_openid");
        String headerImg = (String) request.getSession().getAttribute("s_headimgurl");
        String nickName = (String) request.getSession().getAttribute("s_nickname");
        Agent agent = agentHandler.createVisitor(unionId, openId, nickName, headerImg, String.valueOf(refCode));
        WebUtil.addCurrentUser(request, agent);
        WebUtil.login(request);
        String redirect = (String) request.getSession().getAttribute("redirectUrl");
        return "redirect:" + redirect;
    }


    //注册
    @RequestMapping("/mobile/register.html")
    public String toRegister(@RequestParam(required = false) String s_unionid, @RequestParam(required = false) String s_openid, @RequestParam(required = false) String refCode, Model model) {
        //TODO 这里有问题
        if (s_openid != null) {
            model.addAttribute("s_unionid", s_unionid);
            model.addAttribute("s_openid", s_openid);
            model.addAttribute("refCode", refCode);//推荐过来的
        }
        Agent agent = agentHandler.findByAgentCodeOrId(refCode);
        String levelName = agentHandler.getLevelName(agent);
        if (levelName.contains(AgentLevelName.分公司.toString())) {
            model.addAttribute("isF", true);
        }
        if (levelName.contains(AgentLevelName.业务员.toString())) {
            model.addAttribute("isY", true);
        }

        if (levelName.contains(AgentLevelName.大区.toString())) {
            model.addAttribute("isD", true);
        }

        if (levelName.contains(AgentLevelName.省代.toString())) {
            model.addAttribute("isS", true);
        }


        return "mobile/register";
    }

    //注册并且绑定上级
    @RequestMapping("/mobile/register.json")
    @ResponseBody
    public Message register(Agent agent, HttpServletRequest request, HttpServletResponse response, String refCode, @RequestParam(required = false) MultipartFile img) {

//        String refCode = (String) request.getSession().getAttribute("refCode");
        if (refCode == null) return Message.createFailedMessage("没有推荐人，请联系管理员");
        if (agent.getWxUnionID() == null) return Message.createSuccessMessage("没有WxUnionID，联系管理员");
        try {
            Agent newAgent = agentHandler.selfRegister(agent, refCode, img);
            String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
            if (redirectUrl == null) {
                redirectUrl = ServletUriComponentsBuilder.fromContextPath(request).path("/mobile/my.html").build().toString();
            }
            WebUtil.addCurrentUser(request, newAgent);
            WebUtil.login(request);
            response.setHeader("Location", redirectUrl);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //超找用户
    @RequestMapping("/mobile/findAgent.json")
    @ResponseBody
    public UserDto findAgents(String info, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent;
        if (info.indexOf("zmz") > -1) {//编号
            agent = agentHandler.get("agentCode", info);
        } else {//手机号
            agent = agentHandler.get("mobile", info);
        }
        if (agent.getId().equals(user.getId())) {
            return new UserDto();
        }
        return new UserDto(agent);
    }


    //分销商城首页
    @RequestMapping({"/dmz/mobile/index.html", "/dmz/mobile/{refCode}/index.html"})
    public String mallIndex(Model model, HttpServletRequest request, @PathVariable(name = "refCode", required = false) String refCode, @RequestParam(required = false) Integer cid) {

        //只有登陆的用户才能访问
        if (!WebUtil.isLogin(request)) {
            return "redirect:login.html";
        }

        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        Agent fAgent = agentHandler.findVip(agent);//找出分公司

        if (refCode != null) {
            request.getSession().setAttribute("refCode", refCode);
//            Agent refAgent = agentDAO.findByAgentCode(refCode);
        }

        //首页幻灯片
        model.addAttribute("imagses", systemParameterHandler.get("code", "16").getParamValue());
        //所有大类
        model.addAttribute("categorys", categoryHandler.getList("type", 0));
        //查询所有产品
//        Map<String, Object> map = new HashedMap();
//        map.put("goodsType", GoodsType.MALL);
//        if (cid != null) {
//            map.put("category.id", cid);
//        }
//        map.put("ASC", "order");//排序
//        List<Goods> goodss = goodsHandler.getList(map);
        //查询所有产品  显示当前分公司的价格
        List<Goods> tems = new ArrayList<>();
        //找出当前用户的分公司
        List<Goods> goodss = new ArrayList<>();
        for (GoodsAccount g : fAgent.getGoodsAccounts()) {
            if (g.getCurrentBalance() > 0) {
                Price price = priceHandler.getPrice(agent, g.getGoods());
                g.getGoods().setRetailPrice(price.getPrice());
                goodss.add(g.getGoods());
            }

        }

        model.addAttribute("goodss", goodss);

        return "mobile/index";
    }


    @RequestMapping("/dmz/goods/list.html")
    public String list(@RequestParam(required = false) Integer cid, @RequestParam(required = false) Integer orderType, Model model) {
        List<Goods> goodss = new ArrayList<>();
        List<Goods> temp = goodsHandler.getList("category.id", cid);
        if (cid != null) {
            for (Goods goods : temp) {
                if (goods.getCategory().getId().equals(cid)) {
                    goodss.add(goods);
                }
            }
        } else {
            goodss = temp;
        }
        model.addAttribute("goodss", goodss);
        return "mobile/list";
    }

    @RequestMapping("/dmz/mobile/{gid}/detail.html")
    public String detail(@PathVariable Integer gid, Model model) {
        Goods goods = goodsHandler.get(gid);
        model.addAttribute("goods", goods);
        return "mobile/detail";
    }

    //购物车
    @RequestMapping("/mobile/shopcart/index.html")
    public String shopCartIndex(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("agent", agent);
        Boolean isVip = agentHandler.isVip(agent);
        model.addAttribute("isVip", isVip);
//        if (isVip) {
        List<AddressMy> addressMies = addressMyHandler.findAddressByAgent(agent);
        model.addAttribute("addresses", addressMies);
//        }
        return "/mobile/shopcart/index";
    }


    //转货记录
    @RequestMapping("/mobile/transfer/records.html")
    public String transferRecord(HttpServletRequest request, Model model, @RequestParam(required = false) Integer tid) {
        User user = (User) WebUtil.getCurrentUser(request);
        List<Transfer> items = transferHandler.findTransfers(user.getId(), tid);
        model.addAttribute("items", items);
        return "mobile/transfer/records";
    }

    //发货记录
    @RequestMapping("/mobile/delivery/records.html")
    public String deliveryRecord(HttpServletRequest request, Model model, @RequestParam(required = false) Integer noteId) {
        User user = (User) WebUtil.getCurrentUser(request);
        List items = deliveryNoteHandler.findDeliveryNotes(user.getId(), noteId);
        List<DeliveryNote> notes = deliveryNoteHandler.findDeliveryByParent(user.getId(), noteId);
        items.addAll(notes);

        model.addAttribute("items", items);
        return "mobile/delivery/records";
    }

    //用户中心
    @RequestMapping("/mobile/my.html")
    public String me(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        //生成二维码
        SdkResult<JSONObject> sdkResult = JSAPI.getQrcode(TokenInfo.getAccessToken(wxConfigFactory.getBaseConfig().getAppid()), "QR_SCENE", agent.getId() + "");
        if (sdkResult.isSuccess()) {
            model.addAttribute("qrcodeUrl", sdkResult.getData().get("ticket"));
        } else {
            logger.error("获取ticket失败:{}", sdkResult.getError());
        }

        model.addAttribute("levelName", agentHandler.getLevelName(agent));
        model.addAttribute("agent", agent);
        model.addAttribute("isVip", agentHandler.isVip(agent));
        return "/mobile/my";
    }

    @RequestMapping("/mobile/changePaw.html")
    public String changePaw() {
        return "/mobile/paw";
    }


    @RequestMapping("/mobile/set.html")
    public String mall_set() {
        return "/mobile/set";
    }

    @RequestMapping("/mobile/changePaw.json")
    @ResponseBody
    public Message changePaw(HttpServletRequest request, String oldP, String newP, String conP) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            agentHandler.changePaw(user.getId(), oldP, newP, conP);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //分享
    @RequestMapping("/mobile/find.html")
    public String find() {
        return "/mobile/find";
    }

    //联系人
    @RequestMapping("/mobile/contacts.html")
    public String contacts(HttpServletRequest request, Model model) {
        //所有的联系人
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        //找出自己的下级
        List<Agent> agents = agentHandler.getList("parent.id", user.getId());
        //找出自己的二级代理 药店
        List<Object> ids = new ArrayList<>();
        for (Agent a : agents) {
            ids.add(a.getId());
        }
        if (!ids.isEmpty()) {
            List<Agent> yAgent = agentHandler.getListIn("parent.id", ids);
            agents.addAll(yAgent);
        }

//        List<Agent> agents = agentHandler.findByParentHasUnionId(user.getId());
        MutedUser mutedUser = muteUserHandler.getMuteUser();
        model.addAttribute("mutedUserId", mutedUser.getId());
        model.addAttribute("isVip", agentHandler.isVip(agent));
        model.addAttribute("agents", agents);
        return "/mobile/contacts";
    }

    //登录出去
    @RequestMapping("/mobile/out.html")
    public String out(HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent agent = agentHandler.get(user.getId());
            //清除绑定
            agent.setWxOpenid(null);
            agent.setWxUnionID(null);
            agent.setPayOpenid(null);
            agentHandler.merge(agent);
            WebUtil.logout(request);//退出登陆
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "mobile/error";
        }
        return "redirect:/login.html";
    }

//    //代理详细信息
//    @RequestMapping("/mobile/profile.html")
//    public String profile(Integer uid, Model model) {
//        Agent agent = agentHandler.get(uid);
//        model.addAttribute("agent", agent);
//        model.addAttribute("aTypes", AccountsType.values());
//        return "mobile/profile";
//    }

    //代理自己的详细信息
    @RequestMapping("/mobile/profile.html")
    public String profile(Integer uid, Model model) {
        MutedUser mutedUser = muteUserHandler.getMuteUser();
        if (mutedUser.getId().equals(uid)) {
            model.addAttribute("levelName", "公司账户");
            model.addAttribute("agent", mutedUser);
            return "mobile/profile";
        }
        Agent agent = agentHandler.get(uid);
        model.addAttribute("agent", agent);
        model.addAttribute("aTypes", AccountsType.values());
        String levelName;
        if (agent.getAgentCode() != null && !agent.getAgentCode().equals("")) {
            //账户
            Set<GoodsAccount> goodsAccount = agent.getGoodsAccounts();
            goodsAccount = goodsAccount.stream().filter(p -> p.getCurrentBalance() > 0).collect(Collectors.toSet());
            model.addAttribute("goodsAccount", goodsAccount);
            //级别
            levelName = goodsAccountHandler.getMainGoodsAccount(agent).getAgentLevel().getName();
        } else {
            levelName = "游客";
        }
        model.addAttribute("levelName", levelName);
        return "mobile/profile";
    }


    //去转账页面
    @RequestMapping("/mobile/accounts/transfer.html")
    public String transferAccount(Integer uid, Integer state, Model model) {
        AccountsType accountsType = AccountsType.stateOf(state);
        Agent agent = agentHandler.get(uid);
        model.addAttribute("toAgent", agent);
        model.addAttribute("type", accountsType);
        return "mobile/accounts/transfer";
    }

    //手机端转账业务
    @RequestMapping("/mobile/accounts/transfer.json")
    @ResponseBody
    public Message transferAccount(Integer uid, int state, Double amount, String remark, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        try {
            accountsTransferHandler.transferAccounts(user.getId(), uid, state, amount, remark);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }

        return Message.createSuccessMessage();
    }


    //去充值页面
    @RequestMapping("/mobile/accounts/recharge.html")
    public String to_recharge(Model model, HttpServletRequest request) {

        //如果启动单独支付  先去获取openid
        WxConfig payConfig = wxConfigFactory.getPayConfig();
        if (payConfig.getOpen()) {//如果支付配置是打开的，就启用支付配置
            String redirectUrl = ServletUriComponentsBuilder.fromContextPath(request).path("/mobile/accounts/callBack/recharge.html").build().toString();
            String url = JSAPI.createGetCodeUrl(payConfig.getAppid(), redirectUrl, "snsapi_base", "");
            return "redirect:" + url;
        }
        //没有启动单独支付  直接去支付页面
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("toAgent", agent);
        model.addAttribute("balance", agent.getAccounts().getAccount(AccountsType.ADVANCE));
        return "mobile/accounts/recharge";
    }


    //去提现页面
    @RequestMapping("/mobile/withdraw.html")
    public String withdraw(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        List<Card> cards = cardHandler.getList("agent.id", user.getId());
        if (cards != null && !cards.isEmpty()) {
            model.addAttribute("card", cards.get(0));
        }
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("balance", agent.getAccounts().getVoucherBalance());
        return "/mobile/withdraw";
    }

    //提现业务
    @RequestMapping("/mobile/withdraw.json")
    @ResponseBody
    public Message withdraw(Double amount, Integer cid, String remark, HttpServletRequest request, Boolean isAdvance) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            accountsTransferHandler.withDraw(user.getId(), amount, cid, remark, isAdvance);//提现
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //拒绝提现业务
    @RequestMapping("/mobile/withdraw/refuse.json")
    @ResponseBody
    public Message refuseWithdraw(Integer aid) {
        try {
            accountsTransferHandler.refuseWithdraw(aid);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //拒绝提现业务
    @RequestMapping("/mobile/withdraw/confirm.json")
    @ResponseBody
    public Message confirmWithdraw(Integer aid) {
        try {
            accountsTransferHandler.confirmWithdraw(aid);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //银行卡列表
    @RequestMapping("/mobile/cards.html")
    public String cardList(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        List<Card> cards = cardHandler.getList("agent.id", user.getId());
        model.addAttribute("cards", cards);
        return "/mobile/cards";
    }


    @RequestMapping("/mobile/addCard.html")
    public String addCard() {
        return "/mobile/addCard";
    }

    @RequestMapping("/mobile/addCard.json")
    @ResponseBody
    public Message addCard(Card card, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent agent = agentHandler.get(user.getId());
            card.setName(user.getRealName());
            card.setAgent(agent);
            cardHandler.merge(card);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }

    }


    //手机端支付发货订单页面
    @RequestMapping("/mobile/dmz/note/pay.html")
    public String payOrder(Integer nid, HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent agent = agentHandler.get(user.getId());
            DeliveryNote note = deliveryNoteHandler.get(nid);
            WxConfig wxConfig = wxConfigFactory.getBaseConfig();
            String jsapiparam = payHandler.toWxPay(wxConfig, agent.getWxOpenid(), note.getOrderNo(), note.getAmount(), WxConfig.NOTE_NOTICE_URL);
            model.addAttribute("jsApiParam", jsapiparam);
            model.addAttribute("note", note);
            return "/mobile/delivery/pay";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "/mobile/error";
        }
    }

    @RequestMapping("/dmz/mobile/note/wxPay.json")
    @ResponseBody
    public String wxConfirmOrder(@RequestBody(required = false)String body) {
        try {
            String result = deliveryNoteHandler.confirmPayByWx(body);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
    }


    //管理员确认支付
    @RequestMapping("/mobile/note/confirmPay.json")
    @ResponseBody
    public Message adminConfirmOrder(Integer id, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            if (user.isAdmin()) {//管理员才可以
                deliveryNoteHandler.confirmPay(id);
            } else {
                throw new ApplicationException("非管理员操作!");
            }
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //代金券确认支付
    @RequestMapping("/mobile/note/accountsPay.json")
    @ResponseBody
    public Message accountsConfirmOrder(Integer nid, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            deliveryNoteHandler.confirmPayByAccounts(nid, user.getId());
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //开启了单独支付去获取支付openid然后去充值页面
    @RequestMapping("/mobile/accounts/callBack/recharge.html")
    public String callBack_recharge(Model model, String code, HttpServletRequest request) {
        //通过code获取openid
        WxConfig payConfig = wxConfigFactory.getPayConfig();
        SdkResult sdkResult = JSAPI.getTokenAndOpenId(payConfig.getAppid(), payConfig.getSecret(), code);
        if (!sdkResult.isSuccess()) {
            model.addAttribute("message", sdkResult.getError());
            return "mobile/error";
        }
        JSONObject jsonObject = (JSONObject) sdkResult.getData();
        String openid = jsonObject.getString("openid");
        model.addAttribute("openid", openid);
        //没有启动单独支付  直接去支付页面
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("toAgent", agent);
        model.addAttribute("balance", agent.getAccounts().getAccount(AccountsType.VOUCHER));
        return "mobile/accounts/recharge";
    }


    //手机端充值业务统一下单 提交订单
    @RequestMapping("/mobile/accounts/commitRecharge.json")
    @ResponseBody
    public Message commit_recharge(Double amount, HttpServletRequest request, @RequestParam(required = false) String openid) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        //单号
//        String orderNo = CommonUtil.createNoByTime()+"_"+agent.getAgentCode();
        String orderNo = CommonUtil.createNo();
        try {
            //生成订单
            Agent fromAgent = muteUserHandler.getMuteUser();
            AccountsTransfer accountsTransfer = new AccountsTransfer(agent, fromAgent, "预存款充值", amount, AccountsType.ADVANCE, new Date());
            accountsTransfer.setOut_trade_no(orderNo);//订单
            accountsTransfer.setStatus(AccountsTransferStatus.NEW);
            accountsTransferHandler.merge(accountsTransfer);//存储订单
            //统一下单
            WxConfig wxConfig = wxConfigFactory.getBaseConfig();
            String oid = agent.getWxOpenid();
            //如果payConfig存在 则用payConfig收款
            WxConfig payConfig = wxConfigFactory.getPayConfig();
            if (payConfig.getOpen() && openid != null && !openid.equals("")) {
                wxConfig = payConfig;
                oid = openid;
            }
            //改变通知地址
            String jsapiparam = payHandler.toWxPay(wxConfig, oid, orderNo, amount, WxConfig.RECHARGE_NOTICE_URL);
            Message message = Message.createSuccessMessage();
            message.setData(jsapiparam);//返回支付需要参数
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }

//        return Message.createSuccessMessage();
    }


    //手机端充值支付成功调用
    @RequestMapping("/dmz/mobile/accounts/recharge.json")
    @ResponseBody
    public String recharge(@RequestBody(required = false) String body) {
        try {
            String result = accountsTransferHandler.recharge(body);
            return result;
        } catch (Exception e) {
            logger.error("已收钱,支付失败" + e.getMessage());
            e.printStackTrace();
        }

        return "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
//        return Message.createSuccessMessage();
    }


    //去拨货界面
    @RequestMapping("/mobile/goods/transfer.html")
    public String transferGoods(Integer toUid, HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("goodsAccounts", agent.getGoodsAccounts());
        Agent toAgent = agentHandler.get(toUid);
        model.addAttribute("toAgent", toAgent);
        //找出与收货人相关的地址
        List<AddressMy> addressList = addressMyHandler.findAddressByAgent(agent, toAgent);
        model.addAttribute("addressList", addressList);
        return "mobile/goods/transfer";
    }


    //去退货界面
    @RequestMapping("/mobile/goods/backTransfer.html")
    public String applyBackTransferGoods(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("goodsAccounts", agent.getGoodsAccounts());
        Agent toAgent = muteUserHandler.getMuteUser();
        model.addAttribute("toAgent", toAgent);
        return "mobile/goods/transfer";
    }

    //主动转货
    @RequestMapping("/mobile/goods/transfer.json")
    @ResponseBody
    public Message transferGoods(Integer goodsIds[], Integer[] amounts, String remark, Integer toUid, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        try {
            transferHandler.transfer(agent.getId(), toUid, goodsIds, amounts, remark);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //申请退货
    @RequestMapping("/mobile/goods/backTransfer.json")
    @ResponseBody
    public Message backransferGoods(Integer goodsIds[], Integer[] amounts, String remark, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        try {
            transferHandler.applyBackTransfer(agent.getId(), goodsIds, amounts, remark);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //确认退货 管理员操作
    @RequestMapping("/mobile/goods/confirmBackTransfer.json")
    @ResponseBody
    public Message backransferGoods(Integer tid, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (!user.isAdmin()) {
            throw new ApplicationException("权限不够！");
        }
        Transfer transfer = transferHandler.get(tid);
        try {
            transferHandler.backTransferConfirm(transfer);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }

    //拒绝退货 管理员操作
    @RequestMapping("/mobile/goods/refuseBackTransfer.json")
    @ResponseBody
    public Message refsuseBackTransferGoods(Integer tid, HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (!user.isAdmin()) {
            throw new ApplicationException("权限不够！");
        }
        Transfer transfer = transferHandler.get(tid);
        try {
            transferHandler.backTransferRefuse(transfer);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //发货
    @RequestMapping("/mobile/goods/delivery.json")
    @ResponseBody
    public Message deliveryGoods(AddressMy address, Integer goodsIds[], Integer[] amounts, String remark, Integer toUid, HttpServletRequest request) throws CloneNotSupportedException {
        User user = (User) WebUtil.getCurrentUser(request);
        try {
            deliveryNoteHandler.deliveryGoods(address, user.getId(), toUid, goodsIds, amounts, remark);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }


    /**
     * 账户记录
     *
     * @param date
     * @param stateType
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/mobile/accounts/records.html")
    public String accountsRecords(@RequestParam(required = false) String date, Integer stateType, HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (date == null || date.equals("")) {
            date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        }
        List<AccountsRecord> accountsRecords = accountsRecordHandler.findAccountsRecords(user.getId(), date, date, stateType);
        model.addAttribute("records", accountsRecords);
        //统计
        Map map = accountsRecordHandler.countRecordsAmount(accountsRecords);
        model.addAttribute("countAdd", map.get(AccountsRecord.ADD));
        model.addAttribute("countSub", map.get(AccountsRecord.SUB));
        model.addAttribute("date", date);
        model.addAttribute("accountType", AccountsType.stateOf(stateType));
        return "/mobile/accounts/records";
    }


    @RequestMapping("/mobile/wallet.html")
    public String wallet(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("agent", agent);
        model.addAttribute("voucher", agent.getAccounts().getAccount(AccountsType.VOUCHER));
        model.addAttribute("advance", agent.getAccounts().getAccount(AccountsType.ADVANCE));
        model.addAttribute("benefit", agent.getAccounts().getAccount(AccountsType.BENEFIT));
        //今日奖金进账
        List<AccountsRecord> records = accountsRecordHandler.findAccountsRecords(user.getId(), null, null, AccountsType.VOUCHER.getState());
        Map<Integer, Double> map = accountsRecordHandler.countRecordsAmount(records);
        model.addAttribute("todayVoucher", map.get(AccountsRecord.ADD));//进账
        //直属代理总业绩
        List<Agent> cs = agentHandler.getList("parent.id", user.getId());
        Double sumB = 0.0;
        for (Agent a : cs) {
            sumB += a.getAccounts().getAccount(AccountsType.BENEFIT);
        }
        sumB = PreciseComputeUtil.round(sumB);
        model.addAttribute("sumB", sumB);
        return "/mobile/wallet";
    }

    @RequestMapping("/dmz/count.html")
    public String count(@RequestParam(required = false) String agentCode, @RequestParam(required = false) Integer gid, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Model model) {
        Agent fA = null;
        Goods fG = null;
        if (agentCode != null) {
            fA = agentHandler.get("agentCode", agentCode);
            model.addAttribute("agent", fA);
        }
        if (gid != null && gid > 0) {
            fG = goodsHandler.get(gid);
            model.addAttribute("goods", fG);
        }
        //查询出所有的分公司
        List<Agent> vips = agentHandler.getList("parent.id", 3);
        //所有产品
        List<Goods> goods = goodsHandler.findAll();//所有产品
        List<Agent> childrens = new ArrayList<>();
        for (Agent agent : vips) {
            childrens.addAll(agentHandler.getAllChildrens(agent.getAgentCode()));
        }
        if (startTime == null) {
            startTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
            endTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        }
        //根据时间、分公司、产品筛选 以金钱显示
        List<DeliveryNote> notes = deliveryNoteHandler.findByChlidrens(childrens, startTime, endTime);
        //金额总数
        Double sum = 0.0;
        //产品金额总数
        Map<String, Double> sMap = new HashMap<>();
        //分公司，分产品统计
        Map<Integer, Map<String, Double>> aMap = new HashMap<>();
        Map<String, Double> gMap;
        for (DeliveryNote note : notes) {
            Integer ak = note.getApplyAgent().getId();
            sum += note.getAmount();
            if (fA != null) {//有查找的
                Agent t = note.getApplyAgent();//获取申请人
                if (agentHandler.findVip(t).getId() != fA.getId()) {
                    continue;//调试不是这个分公司
                }
            }
            for (DeliveryItem item : note.getDeliveryItems()) {

                if (fG != null) {//跳过不是当前产品的
                    if (fG.getId() != item.getGoods().getId()) {
                        continue;
                    }
                }

                String gk = item.getGoods().getName();
                //总数统计
                if (sMap.containsKey(gk)) {
                    Double tem = sMap.get(gk);
                    tem += item.getAmount();
                    sMap.put(gk, tem);
                } else {
                    sMap.put(gk, item.getAmount());
                }

                //获取当前用户的统计产品map
                if (aMap.containsKey(ak)) {//如果这个代理已经有了
                    gMap = aMap.get(ak);
                } else {
                    gMap = new HashMap<>();
                }
                //产品map添加数量
                if (gMap.containsKey(gk)) {
                    Double tem = gMap.get(gk);
                    tem += item.getAmount();
                    gMap.put(gk, tem);
                } else {//不包含
                    gMap.put(gk, item.getAmount());
                }
                aMap.put(ak, gMap);
            }
        }
        HashMap<String, Map<String, Double>> gg = new HashMap<>();
        aMap.keySet().stream().forEach(m -> {
            Agent agent = agentHandler.get(m);
            gg.put(agent.getRealName() + agent.getAgentCode(), aMap.get(m));
        });
        model.addAttribute("sum", sum);
        model.addAttribute("sMap", sMap);
        model.addAttribute("aMap", gg);
        model.addAttribute("vips", childrens);
        model.addAttribute("goodses", goods);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "/mobile/count";
    }


    //大区-省-市-县-业务员 联动查询
    @RequestMapping("/mobile/select/children.json")
    @ResponseBody
    public JSONArray getChildrens(Integer uid) {
        try {
            List<Agent> agents = agentHandler.getList("parent.id", uid);
            JSONArray jsonArray = new JSONArray();
            agents.forEach(a -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", a.getId());
                jsonObject.put("name", a.getRealName());
                jsonArray.add(jsonObject);
            });
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //用户统计页面
    @RequestMapping("/mobile/agent/count.html")
    public String countAgent(HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            HashMap<Long, List<Agent>> map = getSelectAgent(user);
            model.addAttribute("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/mobile/agent_count";
    }

    @RequestMapping("/mobile/agent/count.json")
    @ResponseBody
    public JSONArray countAgent(Integer uid, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Integer days) {
//      List<Agent> agents = agentHandler.findAgentByTimeAndPid(uid,startTime,endTime);
        try {
            if (startTime == null || startTime.equals("")) {
                startTime = "1990-01-01";
            }
            if (endTime == null || endTime.equals("")) {
                endTime = "2990-01-01";
            }
            Date st = DateUtil.formatStartTime(startTime);
            Date et = DateUtil.formatEndTime(endTime);
            JSONObject sum = new JSONObject();
            sum.put("name", "合计");
            sum.put("number", 0);
            List<Agent> agents = agentHandler.getList("parent.id", uid);//先获取直接下级
            JSONArray jsonArray = new JSONArray();
            Agent agent = agentHandler.get(uid);
            String ln = agentHandler.getLevelName(agent);
            if (ln.equals(AgentLevelName.业务员.toString())) {//业务员就是查明细
                agents.forEach(a -> {
                    String key = a.getAgentCode() + a.getRealName();
                    if (a.getJoinDate().after(st) && a.getJoinDate().before(et)) {//当前查询的时间内
                        //满足购买天数的
                        Date buyDate = a.getBuyTime();
                        if (buyDate == null) buyDate = a.getJoinDate();
                        //规定购物时间超过规定天数没购物的
                        if (DateUtil.longOfTwoDate(new Date(), buyDate) > days) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", key);
                            jsonObject.put("number", 1);
                            jsonArray.add(jsonObject);
                            //合计
                            Integer s = sum.getInt("number");
                            s++;
                            sum.put("number", s);
                        }
                    }
                });
            } else {//其它就是查总数需要汇总
                agents.forEach(a -> {
                    JSONObject jsonObject = new JSONObject();
                    String key = a.getAgentCode() + a.getRealName();
                    Integer amount = 0;
                    List<Agent> temA = agentHandler.getAllChildrens(a.getAgentCode());
                    for (Agent t : temA) {
                        String lv = agentHandler.getLevelName(t);
                        if (!lv.contains("药房")) continue;
                        if (t.getJoinDate().after(st) && t.getJoinDate().before(et)) {//当前查询的时间内
                            //满足购买天数的
                            Date buyDate = t.getBuyTime();
                            if (buyDate == null) buyDate = t.getJoinDate();
                            //规定购物时间超过规定天数没购物的
                            if (DateUtil.longOfTwoDate(new Date(), buyDate) > days) {
                                amount++;
                            }
                        }
                    }
                    jsonObject.put("name", key);
                    jsonObject.put("number", amount);
                    jsonArray.add(jsonObject);
                    //合计
                    Integer s = sum.getInt("number");
                    s += amount;
                    sum.put("number", s);
                });
            }
            jsonArray.add(sum);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //销售统计页面
    @RequestMapping("/mobile/note/count.html")
    public String countNote(HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            HashMap<Long, List<Agent>> map = getSelectAgent(user);
            model.addAttribute("map", map);
            List<Goods> goods = goodsHandler.findAll();
            model.addAttribute("goods", goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/mobile/note_count";
    }


    //统计订单销售数 未测试
    @RequestMapping("/mobile/note/count.json")
    @ResponseBody
    public JSONArray countNote(Integer uid, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer gid) {
//      List<Agent> agents = agentHandler.findAgentByTimeAndPid(uid,startTime,endTime);
        try {
            if (startTime == null || startTime.equals("")) {
                startTime = "1990-01-01";
            }
            if (endTime == null || endTime.equals("")) {
                endTime = "2990-01-01";
            }
            Goods goods = goodsHandler.get(gid);
            Date st = DateUtil.formatStartTime(startTime);
            Date et = DateUtil.formatEndTime(endTime);
            JSONArray jsonArray = new JSONArray();
            JSONObject sum = new JSONObject();
            sum.put("param1", "合计");
            sum.put("param2", 0);
            sum.put("param3", 0);
            //获取uid下面所有的代理
            List<Agent> agents = agentHandler.getList("parent.id", uid);//先获取直接下级
            Agent agent = agentHandler.get(uid);
            String ln = agentHandler.getLevelName(agent);
            if (ln.equals(AgentLevelName.业务员.toString())) {
                List<DeliveryNote> notes = deliveryNoteHandler.getList("toAgent.id", uid);
                for (DeliveryNote note : notes) {
                    Integer quantity = 0;
                    Double amount = 0.0;
                    //没有支付
                    if (note.getPaymentStatus() == PaymentStatus.UNPAID) {
                        continue;
                    }
                    //时间不对
                    if (st.after(note.getUpdateTime()) || et.before(note.getUpdateTime())) continue;
                    Set<DeliveryItem> items = note.getDeliveryItems();
                    for (DeliveryItem item : items) {
                        if (!item.getGoods().getId().equals(goods.getId())) continue;//产品不对
                        amount += item.getAmount();
                        quantity += item.getQuantity();
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("param1", DateUtil.formatDate(note.getUpdateTime()));
                    jsonObject.put("param2", quantity);
                    jsonObject.put("param1", amount);
                    jsonArray.add(jsonObject);
                    //合计
                    Integer sumQ = sum.getInt("param2");
                    Double sumA = sum.getDouble("param3");
                    sumQ += quantity;
                    sumA += amount;
                    sum.put("param2", sumQ);
                    sum.put("param3", sumA);
                }
                jsonArray.add(sum);
                return jsonArray;
            }
            //非业务员
            for (Agent a : agents) {
                List<Agent> tems = agentHandler.getAllChildrens(a.getAgentCode());
                List<Object> ids = new ArrayList<>();
                for (Agent t : tems) {
                    ids.add(t.getId());
                }
                if (ids.isEmpty()) {
                    continue;
                }
                //查找tems这些人所有成功的订单
                List<DeliveryNote> notes = deliveryNoteHandler.getListIn("toAgent.id", ids);
                Integer quantity = 0;
                Double amount = 0.0;
                //统计
                for (DeliveryNote note : notes) {
                    //没有支付
                    if (note.getPaymentStatus() == PaymentStatus.UNPAID) {
                        continue;
                    }
                    //时间不对
                    if (st.after(note.getUpdateTime()) || et.before(note.getUpdateTime())) continue;
                    Set<DeliveryItem> items = note.getDeliveryItems();
                    for (DeliveryItem item : items) {
                        if (!item.getGoods().getId().equals(goods.getId())) continue;
                        quantity += item.getQuantity();
                        amount += item.getAmount();
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("param1", a.getAgentCode() + a.getRealName());
                jsonObject.put("param2", quantity);
                jsonObject.put("param3", amount);
                jsonArray.add(jsonObject);
                //合计
                Integer sumQ = sum.getInt("param2");
                Double sumA = sum.getDouble("param3");
                sumQ += quantity;
                sumA += amount;
                sum.put("param2", sumQ);
                sum.put("param3", sumA);
            }
            jsonArray.add(sum);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //销售统计页面
    @RequestMapping("/mobile/accounts/count.html")
    public String countAccounts(HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            HashMap<Long, List<Agent>> map = getSelectAgent(user);
            model.addAttribute("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/mobile/accounts_count";
    }


    //统计订单销售数 未测试
    @RequestMapping("/mobile/accounts/count.json")
    @ResponseBody
    public JSONArray countAccounts(Integer uid, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer addSub) {
//      List<Agent> agents = agentHandler.findAgentByTimeAndPid(uid,startTime,endTime);
        try {
            if (startTime == null || startTime.equals("")) {
                startTime = "1990-01-01";
            }
            if (endTime == null || endTime.equals("")) {
                endTime = "2990-01-01";
            }
            Date st = DateUtil.formatStartTime(startTime);
            Date et = DateUtil.formatEndTime(endTime);
            JSONArray jsonArray = new JSONArray();
            JSONObject sum = new JSONObject();
            sum.put("param1", "合计");
            sum.put("param2", 0);
            //获取uid下面所有的代理
            List<Agent> agents = agentHandler.getList("parent.id", uid);//先获取直接下级
            Agent agent = agentHandler.get(uid);
            String ln = agentHandler.getLevelName(agent);
            if (ln.equals(AgentLevelName.业务员.toString())) {
                List<AccountsRecord> notes = accountsRecordHandler.getList("agent.id", uid);
                for (AccountsRecord note : notes) {
                    //时间不对
                    if (st.after(note.getUpdateTime()) || et.before(note.getUpdateTime())) continue;
                    //进账出账不对的
                    if (note.getAddSub() != addSub) continue;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("param1", DateUtil.formatDate(note.getUpdateTime()));
                    jsonObject.put("param2", note.getAmount());
                    jsonArray.add(jsonObject);
                    //合计
                    Double sumA = sum.getDouble("param2");
                    sumA += note.getAmount();
                    sum.put("param2", sumA);
                }
                jsonArray.add(sum);
                return jsonArray;
            }
            //非药房
            for (Agent a : agents) {
                List<Agent> tems = agentHandler.getAllChildrens(a.getAgentCode());
                List<Object> ids = new ArrayList<>();
                for (Agent t : tems) {
                    ids.add(t.getId());
                }
                if (ids.isEmpty()) {
                    continue;
                }
                //查找tems这些人所有成功的订单
                List<AccountsRecord> notes = accountsRecordHandler.getListIn("agent.id", ids);
                Double amount = 0.0;
                //统计
                for (AccountsRecord note : notes) {
                    //进账出账不对
                    if (note.getAddSub() != addSub) {
                        continue;
                    }
                    //时间不对
                    if (st.after(note.getUpdateTime()) || et.before(note.getUpdateTime())) continue;
                    amount += note.getAmount();
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("param1", a.getAgentCode() + a.getRealName());
                jsonObject.put("param2", amount);
                jsonArray.add(jsonObject);
                //合计
                Double sumA = sum.getDouble("param2");
                sumA += amount;
                sum.put("param2", sumA);
            }
            jsonArray.add(sum);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private HashMap<Long, List<Agent>> getSelectAgent(User user) {
        HashMap<Long, List<Agent>> hashMap = new HashMap<>();//注意 这里是int类型的话 前台取不到值  因为el表达式取map是Long类型
        if (user.isAdmin()) {//显示大区
            AgentLevel level = levelHandler.get("name", AgentLevelName.大区.toString());
            Agent agent = muteUserHandler.getMuteUser();
            hashMap.put(0L, agentHandler.findAgentByLvAndParent(level, agent.getId()));
        } else {//普通用户
            Agent agent = agentHandler.get(user.getId());
            if (agent.getAgentCode() != null) {
                AgentLevel tlv = agentHandler.getLevel(agent);
                AgentLevel lv = levelHandler.get("level", tlv.getLevel() + 1);//获取下一个级别的等级
                hashMap.put(lv.getLevel().longValue(), agentHandler.findAgentByLvAndParent(lv, agent.getId()));
            }
        }
        return hashMap;
    }

    @RequestMapping("/mobile/parent/active/agent.json")
    @ResponseBody
    public Message activeAgentAndChangePayWay(Integer uid, String payWay, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent parent = agentHandler.get(user.getId());
            Agent agent = agentHandler.get(uid);
            if (!parent.getId().equals(agent.getParent().getId())) throw new ApplicationException("没有权限");
            agent.setAgentStatus(AgentStatus.WAITING);
            agent.setPayWay(payWay);
            agentHandler.merge(agent);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            return Message.createFailedMessage(e.getMessage());
        }
    }


    @Autowired
    private WxConfigFactory wxConfigFactory;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private SystemParameterHandler systemParameterHandler;

    @Autowired
    private CategoryHandler categoryHandler;

    @Autowired
    private GoodsHandler goodsHandler;

    @Autowired
    private AccountsRecordHandler accountsRecordHandler;

    @Autowired
    private TransferHandler transferHandler;

    @Autowired
    private AddressMyHandler addressMyHandler;

    @Autowired
    private DeliveryNoteHandler deliveryNoteHandler;

    @Autowired
    private AccountsTransferHandler accountsTransferHandler;

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    @Autowired
    private PayHandler payHandler;

    @Autowired
    private CardHandler cardHandler;

    @Autowired
    private PriceHandler priceHandler;

    @Autowired
    private AgentLevelHandler levelHandler;

//    @Autowired
//    private WxConfigFactory wxConfigFactory;


}
