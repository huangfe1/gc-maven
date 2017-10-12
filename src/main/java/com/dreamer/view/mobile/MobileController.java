package com.dreamer.view.mobile;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsType;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.dto.UserDto;
import com.dreamer.domain.user.enums.AccountsTransferStatus;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.service.mobile.*;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.CommonUtil;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        if (findAgent != null) {//系统存在这个用户
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
        return "mobile/register";
    }

    //注册并且绑定上级
    @RequestMapping("/mobile/register.json")
    @ResponseBody
    public Message register(Agent agent, HttpServletRequest request, HttpServletResponse response, String refCode) {

//        String refCode = (String) request.getSession().getAttribute("refCode");
        if (refCode == null) return Message.createFailedMessage("没有推荐人，请联系管理员");
        if (agent.getWxUnionID() == null) return Message.createSuccessMessage("没有WxUnionID，联系管理员");
        try {
            Agent newAgent = agentHandler.selfRegister(agent, refCode);
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

        if (refCode != null) {
            request.getSession().setAttribute("refCode", refCode);
//            Agent refAgent = agentDAO.findByAgentCode(refCode);
        }

        //首页幻灯片
        model.addAttribute("imagses", systemParameterHandler.get("code", "16").getParamValue());
        //所有大类
        model.addAttribute("categorys", categoryHandler.getList("type", 0));
        //查询所有产品
        Map<String, Object> map = new HashedMap();
        map.put("goodsType", GoodsType.MALL);
        if (cid != null) {
            map.put("category.id", cid);
        }
        map.put("ASC", "order");//排序
        List<Goods> goodss = goodsHandler.getList(map);
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
        if (isVip) {
            List<AddressMy> addressMies = addressMyHandler.findAddressByAgent(agent);
            model.addAttribute("addresses", addressMies);
        }
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
        List agents = agentHandler.getList("parent.id", user.getId());
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
    public Message withdraw(Double amount, Integer cid, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            accountsTransferHandler.withDraw(user.getId(), amount, cid);//提现
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
    public String addCard(){
        return "/mobile/addCard";
    }

    @RequestMapping("/mobile/addCard.json")
    @ResponseBody
    public Message addCard(Card card,HttpServletRequest request) {
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
        //今日代金券进账
        List<AccountsRecord> records = accountsRecordHandler.findAccountsRecords(user.getId(), null, null, AccountsType.VOUCHER.getState());
        Map<Integer, Double> map = accountsRecordHandler.countRecordsAmount(records);
        model.addAttribute("todayVoucher", map.get(AccountsRecord.ADD));//进账
        return "/mobile/wallet";
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

//    @Autowired
//    private WxConfigFactory wxConfigFactory;


}
