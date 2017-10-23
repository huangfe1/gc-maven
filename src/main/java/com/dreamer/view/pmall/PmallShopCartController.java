package com.dreamer.view.pmall;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.shopcart.ShopCart;
import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.AddressCloneHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.OrderHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.exception.DataNotFoundException;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = {"/pmall/shopcart", "/dmz/pmall/shopcart"})
@SessionAttributes({"ref"})
public class PmallShopCartController {

    private static final String PMCART = "pmshopcart";

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public Message addGoodsToShopcart(@RequestParam("goodsId") Integer goodsId, @RequestParam(value = "standardIds[]", required = false) Integer[] standardIds, @RequestParam(value = "standardPrices[]", required = false) Double[] standardPrices, @RequestParam(value = "standardNames[]", required = false) String[] standardNames, @RequestParam(value = "quantity", required = false) Integer quantity, HttpServletRequest request) {
        try {
            PmallGoods goods = mallGoodsHandler.get(goodsId);
            if (Objects.isNull(goods)) {
                throw new DataNotFoundException("商品不存在");
            }
            Object ob = WebUtil.getSessionAttribute(request, PMCART);
            ShopCart cart;
            if (Objects.nonNull(ob)) {
                cart = (ShopCart) ob;
            } else {
                cart = new ShopCart();
            }
            Integer addQuantity, result;
            Set<GoodsStandard> goodsStandards = new HashSet<>();
            String no = String.valueOf(goodsId);
            if (standardIds == null) {//没有选择分类
//                if (goods.getSel() == 1) {
//                    GoodsStandard goodsStandard = goods.getGoodsStandards().iterator().next();
//                    if(goodsStandard!=null){
//                        goodsStandards.add(goodsStandard);
//                    }
//                }
            } else {//组装分类
                for (int i = 0; i < standardIds.length; i++) {
                    no += "_" + standardIds[i];
                    GoodsStandard goodsStandard = new GoodsStandard();
                    goodsStandard.setId(standardIds[i]);
                    goodsStandard.setPrice(standardPrices[i]);
                    goodsStandard.setName(standardNames[i]);
                    goodsStandards.add(goodsStandard);
                }
            }
            if (Objects.isNull(quantity)) {
                addQuantity = 1;
            } else {
                addQuantity = quantity;
                cart.removeItems(no);
            }
//            //如果是登录用户验证活动数量
//            if (WebUtil.isLogin(request)) {
//                User user = (User) WebUtil.getCurrentUser(request);
//                //如果已经超出活动限制
//                if (mallGoodsHandler.isActivityLitmit(user.getId(), goods, addQuantity + cart.getGoodsQuantitu(goods))) {
//                    throw new ApplicationException("购买过多" + goods.getName() + "限购" + goods.getLimitNumer());
//                }
//            }

            cart.addGoods(no, goods, addQuantity, goodsStandards);
            result = cart.getQuantity();
            WebUtil.addSessionAttribute(request, PMCART, cart);
            return Message.createSuccessMessage("OK", String.valueOf(result));
        } catch (Exception exp) {
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    @RequestMapping(value = "/remove.json", method = RequestMethod.POST)
    public Message removeGoodsToShopcart(
            @RequestParam("goodsId") Integer goodsId, @RequestParam(value = "standardIds[]", required = false) Integer[] standardIds, HttpServletRequest request) {
        try {
            PmallGoods goods = mallGoodsHandler.get(goodsId);
            if (Objects.isNull(goods)) {
                throw new DataNotFoundException("商品不存在");
            }
            Object ob = WebUtil.getSessionAttribute(request, PMCART);
            if (Objects.nonNull(ob)) {
                ShopCart cart = (ShopCart) ob;
                String no = goods.getId() + "";
                if (standardIds != null) {
                    for (Integer i : standardIds) {
                        no += "_" + i;
                    }
                }
                cart.removeItems(no);
                WebUtil.addSessionAttribute(request, PMCART, cart);
            }

            return Message.createSuccessMessage();
        } catch (Exception exp) {
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    @RequestMapping("/commit.json")
    @ResponseBody
    public Message commitOrder(AddressMy addressMy, String remark, HttpServletRequest request) {
        ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request, PMCART);
        if (Objects.isNull(cart)) {
            throw new ApplicationException("登陆超时,请重新加入购物车!!");
        }
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent agent = agentHandler.get(user.getId());
            addressMy.setAgent(agent);
            AddressClone address = addressCloneHandler.getFromAddressMy(addressMy);

            Order order = orderHandler.commitOrder(agent, address, cart, remark);
            Message message = Message.createSuccessMessage();
            message.setData(String.valueOf(order.getId()));
            WebUtil.removeSessionAttribute(request, PMCART);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    //微信支付完成后  回调支付方法
    @RequestMapping("/wxPay.json")
    @ResponseBody
    public String wxPay(@RequestBody(required = false) String body) {
        try {
            String result = orderHandler.payOrderByWx(body);
            return result;
        } catch (Exception e) {
            logger.error("支付失败",e.getMessage());
            e.printStackTrace();
        }
        return "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
    }

    //奖金支付完成后  回调支付方法
    @RequestMapping("/accountsPay.json")
    @ResponseBody
    public Message accountsPay(Integer id, Integer accountsType, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Order order = orderHandler.get(id);
            if (!order.getUser().getId().equals(user.getId())) {
                throw new ApplicationException("此订单不是你的！！");
            }
            orderHandler.payOrderByAccounts(id, accountsType);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
        return Message.createSuccessMessage();
    }

    @RequestMapping("/adminPay.json")
    public Message adminPay(HttpServletRequest request, Integer id) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (!user.isAdmin()) {
            return Message.createFailedMessage("权限不够");
        }
        try {
            orderHandler.payOrderByAdmin(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
        return Message.createSuccessMessage();
    }


    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MallGoodsHandler mallGoodsHandler;

    @Autowired
    private OrderHandler orderHandler;

    @Autowired
    private AddressCloneHandler addressCloneHandler;

    Logger logger = LoggerFactory.getLogger(this.getClass());

}
