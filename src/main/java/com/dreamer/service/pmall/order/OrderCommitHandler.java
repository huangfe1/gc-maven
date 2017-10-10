package com.dreamer.service.pmall.order;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderItem;
import com.dreamer.domain.user.Agent;
import com.dreamer.repository.mall.goods.MallGoodsDAO;
import com.dreamer.repository.pmall.goods.StandardDao;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.service.mall.goods.MallGoodsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderCommitHandler {

    @Transactional
    public Order commitOrder(Order order, Agent agent, List<OrderItem> items) {
//        LOG.debug("进入优惠商城订单提交");
//        try {
//            items.stream().forEach(item -> {
//                //判断库存是否充足
//                PmallGoods mg = mallGoodsDAO.findById(item.getGoodsId());//找出当前货物
//                if (mg.getStockQuantity() < item.getQuantity()) {//库存不够
//                    throw new ApplicationException(mg.getName() + "库存不足");
//                }
//                //有单品的话
//                if(item.getStandards()!=null&&!item.getStandards().isEmpty()){
//                    //如果单品库存不够
//                    item.getStandards().keySet().forEach(p->{
//                        GoodsStandard goodsStandard = standardDao.findById(p);
//                        if (Objects.isNull(goodsStandard)) {
//                            throw new ApplicationException("分类不存在");
//                        }
//                        if (goodsStandard.getStock() < item.getQuantity()) {
//                            throw new ApplicationException(mg.getName() + goodsStandard.getName() + "库存不足");
//                        }
//                    });
//                }
//
////                mg.getGoodsStandards().forEach(standard -> {
////                    GoodsStandard goodsStandard = standardDao.findById(standard.getId());
////                    if (Objects.isNull(goodsStandard)) {
////                        throw new ApplicationException("分类不存在");
////                    }
////                    if (goodsStandard.getStock() < item.getQuantity()) {
////                        throw new ApplicationException(mg.getName() + goodsStandard.getName() + "库存不足");
////                    }
////                });
//                //如果已经下架
//                if (!mg.getShelf()) {
//                    throw new ApplicationException(mg.getName() + "库存不足");
//                }
//                //如果已经超出活动限制
//                if (mallGoodsHandler.isActivityLitmit(agent.getId(), mg, item.getQuantity())) {
//                    throw new ApplicationException("购买过多" + mg.getName() + "限购" + mg.getLimitNumer());
//                }
//                item.setOrder(order);
//                order.addItem(item);
//            });
//            //释放内存
//            order.commit(agent);
//            orderDAO.save(order);
////			order.getItems().clear();//释放空间
////			order=null;
//        } catch (ApplicationException aep) {
//            LOG.error("优惠商城订单提交失败", aep);
//            aep.printStackTrace();
//            throw aep;
//        } catch (Exception exp) {
//            LOG.error("优惠商城订单提交异常", exp);
//            exp.printStackTrace();
//            throw new ApplicationException(exp);
//        }

        return order;
    }


    @Autowired
    private StandardDao standardDao;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private MallGoodsDAO mallGoodsDAO;

    @Autowired
    private MallGoodsHandler mallGoodsHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
}
