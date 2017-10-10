package com.dreamer.service.mobile;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.shopcart.ShopCart;
import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.Agent;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 11/07/2017.
 */
public interface OrderHandler extends BaseHandler<Order>{

    Order commitOrder(Agent agent, AddressClone addressClone, ShopCart shopCart, String remark);//提交订单


    String payOrderByWx(String body);//接受微信支付回调 并支付  返回支付结果

    void payOrderByAdmin(Integer id);//接受微信支付回调 并支付  返回支付结果

    List<Order> findOrdersWithChildren(SearchParameter<Order> p);


    String resetOpenIdToPay(String code, String orderId);


    void payOrderByAccounts(Integer id, Integer accountsType);//支付订单 通过系统的券

    void  delivery(Integer id,String logistics,String logisticsCode,String operate);//发货

    List<Order> findOrders(SearchParameter<Order> parameter);

    List<Order> findAllOrders(SearchParameter<Order> parameter);

    List<Order> findNewOrders(Integer limit);//所有的新订单

    List<Object[]> getOrdersItemCount(Integer limit);//所有新订单的总数

}
