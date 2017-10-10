package com.dreamer.view.goods;

import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.mall.shopcart.CartItem;
import com.dreamer.domain.mall.shopcart.ShopCart;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.MutedUser;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.DeliveryNoteHandler;
import com.dreamer.service.mobile.MuteUserHandler;
import com.dreamer.service.mobile.TransferHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Map.Entry;
import java.util.Optional;

@RestController
@RequestMapping("/transfer")
public class TransferController {
//    /**
//     * 后台申请发货
//     *
//     * @param transfer
//     * @param goodsIds
//     * @param quantitys
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/applyFrom.json", method = RequestMethod.POST)
//    public Message applyTransfer(
//            @ModelAttribute("parameter") Transfer transfer, Integer[] goodsIds,
//            Integer[] quantitys, Model model, HttpServletRequest request) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            Agent agent = agentDAO.findById(user.getId());
//            if (user.isTopAgent()) {
//                MutedUser mutedUser = (MutedUser) WebUtil.getSessionAttribute(
//                        request, Constant.MUTED_USER_KEY);
//                transfer.setFromAgent(mutedUserDAO.findById(mutedUser
//                        .getId()));
//            } else {
//                transfer.setFromAgent((Agent) agent.getParent());//由上级处理
//            }
//            transfer.setToAgent(agent);
//            if (goodsIds == null || goodsIds.length == 0) {
//                throw new ApplicationException("未指定申请转货的货物");
//            }
//            transferHandler.applyTransfer(transfer, goodsIds, quantitys);
//            return Message.createSuccessMessage("ok", "转货申请提交成功");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("转货申请失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }


    /**
     //     * 后台结算
     //     *
     //     * @param transfer
     //     * @param goodsIds
     //     * @param quantitys
     //     * @param model
     //     * @param request
     //     * @return
     //     */
//    @RequestMapping(value = "/calculate.json", method = RequestMethod.POST)
//    public Message calculate(
//            @ModelAttribute("parameter") Transfer transfer, Integer[] goodsIds,
//            Integer[] quantitys, Model model, HttpServletRequest request) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            Agent agent = agentDAO.findById(user.getId());
//            if (user.isTopAgent()) {
//                MutedUser mutedUser = (MutedUser) WebUtil.getSessionAttribute(
//                        request, Constant.MUTED_USER_KEY);
//                transfer.setFromAgent(mutedUserDAO.findById(mutedUser
//                        .getId()));
//            } else {
//                transfer.setFromAgent((Agent) agent.getParent());//改变上家
//            }
//            transfer.setToAgent(agent);
//            if (goodsIds == null || goodsIds.length == 0) {
//                throw new ApplicationException("未指定申请转货的货物");
//            }
//            Transfer result = transferHandler.calculate(transfer, goodsIds, quantitys);
//            WebUtil.addSessionAttribute(request, "transfer_calculate", result);
//            return Message.createSuccessMessage("ok", "calculate");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("转货申请价格核算失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

    /**
     * 主动转货
     *
     * @param transfer
     * @param goodsIds
     * @param quantitys
     * @param request
     * @return
     */
    @RequestMapping(value = "/to.json", method = RequestMethod.POST)
    @ResponseBody
    public Message transferTo(String fromName, String fromCode, String toName, String toCode, Integer[] goodsIds, Integer[] quantitys, String remark, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent fromAgent;
            if (user.isAdmin()) {
                fromAgent = muteUserHandler.getMuteUser();
                if (!fromAgent.getAgentCode().equals(fromCode)) {//不是管理员，修改了账号
                    fromAgent = agentHandler.findByAgentCodeOrId(fromCode);
                    if (!fromAgent.getRealName().equals(fromName)) {
                        throw new ApplicationException("输入的转货人账号与名字不匹配");
                    }
                }
            } else {//不是管理员 转出人就是自己
                fromAgent = agentHandler.get(user.getId());
            }
            Agent toAgent = agentHandler.findByAgentCodeOrId(toCode);
            if (!toAgent.getRealName().equals(toName)) {
                throw new ApplicationException("输入的收货人编号与名字不匹配");
            }
            //转货
            transferHandler.transfer(fromAgent.getId(), toAgent.getId(), goodsIds, quantitys, remark);
            return Message.createSuccessMessage();
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("转出货物失败", exp);
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    /**
     * 代金券+进货券自动购买
     *
     * @return
     */
    @RequestMapping("/autoConfirm.json")
    @ResponseBody
    public Message transferAutoConfirm(HttpServletRequest request, String remark) {
        try {
            ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request, TCART);
            //初始化
            if (cart.getItems().isEmpty()) {
                return Message.createFailedMessage("购物车为空！");
            }
            Integer[] goodsIds = new Integer[cart.getItems().size()];
            Integer[] quantitys = new Integer[cart.getItems().size()];
            //封装赋值
            int i = 0;
            for (Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                goodsIds[i] = entry.getKey();
                quantitys[i] = entry.getValue().getQuantity();
                i++;
            }
            if (goodsIds == null || goodsIds.length == 0) {
                throw new ApplicationException("未指定申请转货的货物");
            }
            User user = (User) WebUtil.getCurrentUser(request);
            Agent fromAgent = muteUserHandler.getMuteUser();

            //扣钱转货操作
            transferHandler.transferAutoConfirm(fromAgent.getId(), user.getId(), goodsIds, quantitys, remark);
            WebUtil.removeSessionAttribute(request, TCART);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


    /**
     * 代金券+进货券自动购买
     *
     * @return
     */
    @RequestMapping("/delivery.json")
    @ResponseBody
    public Message delivery(AddressMy address, HttpServletRequest request, String remark) {
        try {
            ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request, TCART);
            //初始化
            if (cart.getItems().isEmpty()) {
                return Message.createFailedMessage("购物车为空！");
            }
            Integer[] goodsIds = new Integer[cart.getItems().size()];
            Integer[] quantitys = new Integer[cart.getItems().size()];
            //封装赋值
            int i = 0;
            for (Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                goodsIds[i] = entry.getKey();
                quantitys[i] = entry.getValue().getQuantity();
                i++;
            }
            if (goodsIds == null || goodsIds.length == 0) {
                throw new ApplicationException("未指定申请转货的货物");
            }
            User user = (User) WebUtil.getCurrentUser(request);
            Agent fromAgent = muteUserHandler.getMuteUser();

            //扣钱转货操作 发货
            transferHandler.transferAutoConfirmAndDelivery(address, fromAgent.getId(), user.getId(), goodsIds, quantitys, remark);
            WebUtil.removeSessionAttribute(request, TCART);
            return Message.createSuccessMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }


//    /**
//     * 确认转货
//     *
//     * @param transfer
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/confirm.json", method = RequestMethod.POST)
//    public Message confirm(@ModelAttribute("parameter") Transfer transfer,
//                           HttpServletRequest request) {
//        //设置操作员
//        transfer.setOperator(((User) WebUtil.getCurrentUser(request)).getRealName());
//        try {
////            if (transfer.isBackedTransfer()) {//退货
////                transferHandler.confirmBackTransfer(transfer);
////                return Message.createSuccessMessage("货物退还确认成功");
////            } else {
////                transferHandler.confirmTransfer(transfer);
////                return Message.createSuccessMessage("货物转出确认成功");
////            }
//
//        } catch (ApplicationException exp) {
//            exp.printStackTrace();
//            LOG.error("确认失败" + exp.getMessage(), exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//        return null;
//    }

//    @RequestMapping(value = "/refuse.json", method = RequestMethod.POST)
//    public Message refuse(@ModelAttribute("parameter") Transfer transfer,
//                          HttpServletRequest request) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            transfer.setOperator(user.getRealName());//设置操作员
//            transferHandler.refuseTransfer(transfer);
//            return Message.createSuccessMessage("拒绝货物转出成功");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("拒绝转出货物失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/remove.json", method = {RequestMethod.POST, RequestMethod.DELETE})
//    public Message remove(@ModelAttribute("parameter") Transfer transfer,
//                          HttpServletRequest request) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            Integer operaterId = transfer.getToAgent().getId();
////            if (transfer.isBackedTransfer()) {//如果是退货,操作人就是转出方
////                operaterId = transfer.getFromAgent().getId();
////            }
//            if (Objects.equals(operaterId, user.getId())) {//撤销只能撤销自己的
//                transferHandler.removeTransfer(transfer);
//            } else {
//                throw new ApplicationException("只能撤销本人申请的转货");
//            }
//            return Message.createSuccessMessage("撤销转货申请成功");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("拒绝转出货物失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

    @ModelAttribute("parameter")
    public Transfer preprocess(@RequestParam("id") Optional<Integer> id) {
        Transfer parameter;
        if (id.isPresent()) {
            parameter = transferHandler.get(id.get());
        } else {
            parameter = new Transfer();
        }
        return parameter;
    }


//    /**
//     * add by hxf----------------------------------------------------------------------------------------
//     */
//
//
//    @RequestMapping(value = "/hf_confirm.json", method = RequestMethod.POST)
//    public Message hf_confirm(@ModelAttribute("parameter") Transfer transfer,
//                              HttpServletRequest request) {
//        try {
//            transferHandler.confirmTransfer(transfer);
//            return Message.createSuccessMessage("货物转出确认成功");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("撤销转货申请失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }


//    /**
//     * mall新增从购物车转货！！
//     */
//
//    private static final String CART = "shopcart";
//
//    @RequestMapping(value = "/hf_applyFrom.json", method = RequestMethod.POST)
//    public Message hf_applyTransfer(
//            @ModelAttribute("parameter") Transfer transfer, Model model, HttpServletRequest request) {
//        ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request,
//                CART);
//
//        Integer[] goodsIds = null;
//
//        Integer[] quantitys = null;
//        //初始化
//        if (!cart.getItems().isEmpty()) {
//            goodsIds = new Integer[cart.getItems().size()];
//            quantitys = new Integer[cart.getItems().size()];
//        }
//        //封装赋值
//        int i = 0;
//        for (Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
//            goodsIds[i] = entry.getKey();
//            quantitys[i] = entry.getValue().getQuantity();
//            i++;
//        }
//
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            Agent agent = agentDAO.findById(user.getId());
//            if (user.isTopAgent()) {
//                MutedUser mutedUser = (MutedUser) WebUtil.getSessionAttribute(
//                        request, Constant.MUTED_USER_KEY);
//                transfer.setFromAgent(mutedUserDAO.findById(mutedUser
//                        .getId()));
//            } else {
//                //transfer.setFromAgent((Agent) agent.getParent());//改变上家
//                GoodsAccount gac = agent.loadAccountForGoodsId(16);//主打产品
//                String lv = gac.getAgentLevel().getName();
////                if (lv.contains(AgentLevelName.官方.toString()) || lv.contains(AgentLevelName.联盟单位.toString()) || lv.contains(AgentLevelName.发起者.toString())) {//订单由官方处理
////                    transfer.setFromAgent(agentDAO.findById(3));
////                } else {
////                    transfer.setFromAgent(agent.getParent());//改变上家
////                }
//            }
//            transfer.setToAgent(agent);
////            transfer.setApplyOrigin(TransferApplyOrigin.MALL);
//            if (goodsIds == null || goodsIds.length == 0) {
//                throw new ApplicationException("未指定申请转货的货物");
//            }
//            transferHandler.applyTransfer(transfer, goodsIds, quantitys);
//            //清空购物车
//            cart.getItems().clear();
//
//            return Message.createSuccessMessage("ok", "转货申请提交成功");
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("转货申请失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }


    /**
     * tmall新增从购物车转货！！
     */
    private static final String TCART = "shopcart";

    @RequestMapping(value = "/hf_tmallApplyFrom.json", method = RequestMethod.POST)
    public Message hf_mallApplyTransfer(
            @ModelAttribute("parameter") Transfer transfer, HttpServletRequest request, @RequestParam(required = false) Boolean usePurchase, Double d) {
        ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request, TCART);
        //初始化
        if (cart.getItems().isEmpty()) {
            return Message.createFailedMessage("购物车为空！");
        }
        Integer[] goodsIds = new Integer[cart.getItems().size()];
        Integer[] quantitys = new Integer[cart.getItems().size()];
        //封装赋值
        int i = 0;
        for (Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            goodsIds[i] = entry.getKey();
            quantitys[i] = entry.getValue().getQuantity();
            i++;
        }
        if (goodsIds == null || goodsIds.length == 0) {
            throw new ApplicationException("未指定申请转货的货物");
        }

        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent agent = agentHandler.get(user.getId());
            MutedUser mutedUser = muteUserHandler.getMuteUser();
            if (user.isTopAgent()) {
                transfer.setFromAgent(mutedUser);
            } else {
                if (agent.isTeqVip()) {//如果是筑美的特权商城VIP
                    transfer.setFromAgent(agentHandler.get(mutedUser.getId()));
                } else {
                    transfer.setFromAgent(agent.getParent());//设置为自己的特权上级
                }
            }
//            transfer.setApplyOrigin(TransferApplyOrigin.TMALL);//设置为特权商城的申请
            transfer.setToAgent(agent);
            if (agent.isTeqVip()) {//收钱转货
//                transferHandler.confirmByPurchase(transfer, goodsIds, quantitys);
            } else {//提交申请
//                transferHandler.applyTransfer(transfer, goodsIds, quantitys);
            }
            //清空购物车
            WebUtil.removeSessionAttribute(request, TCART);
            return Message.createSuccessMessage("ok", "转货申请提交成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    @Autowired
    private TransferHandler transferHandler;

    @Autowired
    private DeliveryNoteHandler deliveryNoteHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());


}
