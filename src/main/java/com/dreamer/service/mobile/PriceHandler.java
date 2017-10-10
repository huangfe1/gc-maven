package com.dreamer.service.mobile;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.user.Agent;

/**
 * Created by huangfei on 05/07/2017.
 */
public interface PriceHandler extends BaseHandler<Price>{



    Price getPrice(Agent agent, Goods goods);

}
