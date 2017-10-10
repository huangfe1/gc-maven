package com.dreamer.service.mobile.impl;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import com.dreamer.repository.mobile.GoodsAccountDao;
import com.dreamer.repository.mobile.GoodsDao;
import com.dreamer.service.mobile.AgentLevelHandler;
import com.dreamer.service.mobile.GoodsAccountHandler;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.mx.otter.exception.ApplicationException;

import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 03/07/2017.
 */
@Service
public class GoodsAccountHandlerImpl extends BaseHandlerImpl<GoodsAccount> implements GoodsAccountHandler{

    /**
     * 给某人新增主打产品授权
     *
     * @param agent
     */
    public GoodsAccount generateMainGoodsAccount(Agent agent) {
        //找出主打产品
        Goods mg = goodsDao.get("benchmark", true);
        //找出最低级别
        AgentLevel lowestLevel = agentLevelHandler.findLowestLevel();
        //新建账户
        GoodsAccount mainGoodsAccount = new GoodsAccount(agent, mg, lowestLevel);
        mainGoodsAccount = goodsAccountDao.merge(mainGoodsAccount);//新增主打产品授权
        return mainGoodsAccount;
    }





    /**
     * 获取用户的主打产品级别
     * 没有就新增
     *
     * @param agent
     * @return
     */
    public GoodsAccount getMainGoodsAccount(Agent agent) {
        GoodsAccount goodsAccounts = goodsAccountDao.getMainGoodsAccount(agent);
        if (goodsAccounts == null ) {//没有主打产品新增
            return generateMainGoodsAccount(agent);//生成主打产品级别
        }
        return goodsAccounts;
    }


    /**
     * 生成某个产品的库存
     *
     * @param agent
     * @param mainGoodsAccount
     * @param goods
     * @return
     */
    public GoodsAccount generateGoodsAccount(Agent agent, GoodsAccount mainGoodsAccount, Goods goods) {
        //主打产品级别
        AgentLevel agentLevel = mainGoodsAccount.getAgentLevel();
        GoodsAccount goodsAccount = new GoodsAccount(agent, goods, agentLevel);
        goodsAccount = goodsAccountDao.merge(goodsAccount);//新增当前产品授权
        return goodsAccount;
    }




    /**
     * 验证账户金额
     *
     * @param goodsAccount
     * @param quantity
     */
    private void validateQuantity(GoodsAccount goodsAccount, Integer quantity) {
        if (goodsAccount == null) {
            throw new ApplicationException("账户为空!请联系管理员");
        }
        if (quantity <= 0) {
            throw new ApplicationException("数量必须大于0!");
        }
    }

    /**
     * 减少库存
     *
     * @param goodsAccount
     * @param quantity
     */
    public void deductGoodsAccount(GoodsAccount goodsAccount, Integer quantity) {
        validateQuantity(goodsAccount, quantity);//验证金额
        Integer result = validateGoodsAccountBalance(goodsAccount, quantity);//验证库存
        goodsAccount.setCurrentBalance(result);
    }

    /**
     * 增加库存
     *
     * @param goodsAccount
     * @param quantity
     */
    public void increaseGoodsAccount(GoodsAccount goodsAccount, Integer quantity) {
        validateQuantity(goodsAccount, quantity);
        Integer balance = goodsAccount.getCurrentBalance();//当前余额
        Integer result = balance + quantity;
        goodsAccount.setCurrentBalance(result);
    }


    /**
     * 验证某人库存  返回扣减后的库存
     *
     * @param agent
     * @param goods
     */
    private Integer validateGoodsAccountBalance(GoodsAccount goodsAccount, Integer quantity) {
        validateQuantity(goodsAccount, quantity);//验证数量
        Integer result = goodsAccount.getCurrentBalance() - quantity;
        //后期可以修改此处的验证  此时只验证当前库存
        if (result < 0) {
            throw new ApplicationException(goodsAccount.getGoods().getName() + "库存不足!");
        }
        return result;
    }


    /**
     * 获取某人某个产品的账户
     * 没有就新增
     *
     * @param goods
     * @return
     */
    public GoodsAccount getGoodsAccount(Agent agent, Goods goods, GoodsAccount mainGoodsAccount) {
        Map map = new HashedMap();
        map.put("user", agent);
        map.put("goods", goods);
        GoodsAccount goodsAccount = goodsAccountDao.get(map);
        if (goodsAccount == null) {
            return generateGoodsAccount(agent, mainGoodsAccount, goods);
        }
        return goodsAccount;
    }


    //获取账户
    @Override
    public GoodsAccount getGoodsAccount(Agent agent, Goods goods) {
        Map map = new HashedMap();
        map.put("user", agent);
        map.put("goods", goods);
        GoodsAccount goodsAccount = goodsAccountDao.get(map);
        return goodsAccount;
    }

    //获取所有账户
    @Override
    public List<GoodsAccount> getGoodsAccounts(Agent agent) {
        Map map = new HashedMap();
        map.put("user", agent);
        return  goodsAccountDao.getList(map);
    }

    @Autowired
    private GoodsAccountDao goodsAccountDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private AgentLevelHandler agentLevelHandler;

}
