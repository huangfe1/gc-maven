package com.dreamer.service.mobile.impl;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import com.dreamer.repository.mobile.PriceDao;
import com.dreamer.service.mobile.GoodsAccountHandler;
import com.dreamer.service.mobile.PriceHandler;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public class PriceHandlerImpl extends BaseHandlerImpl<Price> implements PriceHandler {

    /**
     * 调用之前要验证是否有相关账户
     * 获取某人某个产品的主打产品级别的价格
     *
     * @param agent
     * @param goods
     * @return
     */
    public Price getPrice(Agent agent, Goods goods) {
        //获取某个人主打产品级别
        GoodsAccount main = goodsAccountHandler.getMainGoodsAccount(agent);
        AgentLevel mainLevel = main.getAgentLevel();//主打产品级别
        Map map = new HashedMap();
        map.put("goods", goods);
        map.put("agentLevel", mainLevel);
//        DetachedCriteria dc = DetachedCriteria.forClass(Price.class);
//        dc.add(Restrictions.eq("goods", goods));//对应的产品
//        dc.add(Restrictions.eq("agentLevel", mainLevel));
        return priceDao.get(map);
    }

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private PriceDao priceDao;
}
