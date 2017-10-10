package com.dreamer.service.mobile.impl;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.domain.user.Admin;
import com.dreamer.repository.mobile.MallGoodsDao;
import com.dreamer.service.mobile.GoodsStandardHandler;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.PmallStockBlotterHandler;
import com.dreamer.service.mobile.SystemParameterHandler;
import com.dreamer.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.SearchParameter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by huangfei on 12/07/2017.
 */
@Service
public class MallGoodsHandlerImpl extends BaseHandlerImpl<PmallGoods> implements MallGoodsHandler {


    @Override
    public List<PmallGoods> findMallGoods(SearchParameter<PmallGoods> p) {
        return mallGoodsDao.findMallGoods(p);
    }


    @Transactional
    public void addMallGoods(PmallGoods pmallGoods, String imgType, String wallType, byte[] imgBytes, byte[] wallBytes, Integer[] ids, String[] names, Double[] prices, Integer[] stocks) {
        String pathStr = systemParameterHandler.getGoodsImgPath();
        if (Objects.nonNull(imgType)) {
            String fileName = CommonUtil.generateFileName(imgType);
            CommonUtil.writeImgFile(imgBytes, fileName, pathStr);
            pmallGoods.setImgFile(fileName);
        }
        if (Objects.nonNull(wallType)) {
            String fileName = CommonUtil.generateFileName(wallType);
            CommonUtil.writeImgFile(wallBytes, fileName, pathStr);
            pmallGoods.setWallFile(fileName);
        }
        if (pmallGoods.getActivity()) {//活动
            if (Objects.isNull(pmallGoods.getStartTime())) {//
                //设置当前时间为活动时间
                pmallGoods.setStartTime(new Timestamp(System.currentTimeMillis()));
            }
        } else {//下架
            if (Objects.nonNull(pmallGoods.getStartTime())) {
                pmallGoods.setStartTime(null);
            }
        }
        assembleStandard(pmallGoods, ids, names, prices, stocks);//创建assemble
        merge(pmallGoods);
    }


    //减少产品库存
    @Override
    @Transactional
    public void deductMallGoodsAccount(PmallGoods pmallGoods, Integer qunatity) {
        Integer balance = pmallGoods.getStockQuantity();//当前库存
        if (qunatity <= 0) {
            throw new ApplicationException("数量参数异常");
        }
        if (balance - qunatity < 0) {
            throw new ApplicationException(pmallGoods.getName() + "产品库存不足");
        }
        pmallGoods.setStockQuantity(balance - qunatity);
        merge(pmallGoods);
    }

    //增加产品库存
    @Override
    @Transactional
    public void increaseMallGoodsAccount(PmallGoods pmallGoods, Integer qunatity) {
        Integer balance = pmallGoods.getStockQuantity();//当前库存
        if (qunatity <= 0) {
            throw new ApplicationException("数量参数异常");
        }
        pmallGoods.setStockQuantity(balance + qunatity);
        merge(pmallGoods);
    }

    //更改库存
    @Override
    @Transactional
    public PmallGoodsStockBlotter changeStock(Admin admin, PmallGoods pmallGoods, Integer quantity, String remark) {
        if (quantity == 0) {
            throw new ApplicationException("数量不能为0");
        }
        if (quantity > 0) {
            increaseMallGoodsAccount(pmallGoods, quantity);
        }
        if (quantity < 0) {
            deductMallGoodsAccount(pmallGoods, quantity);
        }
        //生成流水
        PmallGoodsStockBlotter pmallGoodsStockBlotter = new PmallGoodsStockBlotter(admin, pmallGoods, quantity, new Date(), pmallGoods.getStockQuantity(), remark);
        stockBlotterHandler.merge(pmallGoodsStockBlotter);//保存流水
        return pmallGoodsStockBlotter;
    }


    /**
     * 添加规格
     */
    private PmallGoods assembleStandard(PmallGoods goods, Integer[] ids, String[] names, Double[] prices, Integer[] stocks) {
        goods.setGoodsStandards(new HashSet<>());//清空
        if (!Objects.isNull(names)) {
            for (int i = 0; i < names.length; i++) {
                GoodsStandard goodsStandard = new GoodsStandard();
                if (!ids[i].equals(0)) {//不是0
                    GoodsStandard temp = goodsStandardHandler.get(ids[i]);
                    if (!Objects.isNull(temp)) {
                        goodsStandard = temp;
                    } else {
                        throw new ApplicationException("修改失败，没查询到对应的standard");
                    }
                }
                goodsStandard.setName(names[i]);
                goodsStandard.setPrice(prices[i]);
                goodsStandard.setStock(stocks[i]);
                goods.addGoodsStandards(goodsStandard);
            }
        }

        return goods;
    }


    @Autowired
    private SystemParameterHandler systemParameterHandler;

    @Autowired
    private GoodsStandardHandler goodsStandardHandler;

    @Autowired
    private PmallStockBlotterHandler stockBlotterHandler;

    private MallGoodsDao mallGoodsDao;

    public MallGoodsDao getMallGoodsDao() {
        return mallGoodsDao;
    }

    @Autowired
    public void setMallGoodsDao(MallGoodsDao mallGoodsDao) {
        this.mallGoodsDao = mallGoodsDao;
        super.setBaseDao(mallGoodsDao);
    }
}
