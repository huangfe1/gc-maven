package com.dreamer.service.mobile;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.user.Agent;

import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
public interface GoodsAccountHandler extends BaseHandler<GoodsAccount> {

    GoodsAccount generateMainGoodsAccount(Agent agent);

    GoodsAccount getMainGoodsAccount(Agent agent);

    GoodsAccount generateGoodsAccount(Agent agent, GoodsAccount mainGoodsAccount, Goods goods);

    void deductGoodsAccount(GoodsAccount goodsAccount, Integer quantity);

    void increaseGoodsAccount(GoodsAccount goodsAccount, Integer quantity);

    GoodsAccount getGoodsAccount(Agent agent, Goods goods, GoodsAccount mainGoodsAccount);

    GoodsAccount getGoodsAccount(Agent agent, Goods goods);

    List<GoodsAccount> getGoodsAccounts(Agent agent);


}
