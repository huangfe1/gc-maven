package com.dreamer.service.mall.goods;

import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.repository.mall.goods.MallGoodsDAO;
import com.dreamer.repository.mall.goods.MallGoodsStockBlotterDAO;
import com.dreamer.repository.mall.goods.MallGoodsTypeDAO;
import com.dreamer.repository.pmall.goods.StandardDao;
import com.dreamer.repository.pmall.order.OrderItemDAO;
import com.dreamer.repository.system.SystemParameterDAOInf;
import com.dreamer.repository.user.MutedUserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
public class MallGoodsHandler {

    @Transactional
    public void addMallGoods(PmallGoods pmallGoods, String imgType, String wallType, byte[] imgBytes, byte[] wallBytes, Integer[] ids, String[] names, Double[] prices, Integer[] stocks) {
        LOG.debug("新增优惠商城商品 name:{}", pmallGoods.getName());
        if (Objects.nonNull(imgType)) {
            String fileName = generateFileName(imgType);
            writeImgFile(imgBytes, fileName);
            pmallGoods.setImgFile(fileName);
        }
        if (Objects.nonNull(wallType)) {
            String fileName = generateFileName(wallType);
            writeImgFile(wallBytes, fileName);
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
        assembleStandard(pmallGoods,ids,names,prices,stocks);//创建assemble
        if(Objects.isNull(pmallGoods.getVersion())) {
            mallGoodsDAO.merge(pmallGoods);
        }else {
            mallGoodsDAO.merge(pmallGoods);
        }
    }

    /**
     *  添加规格
     */
    private PmallGoods assembleStandard(PmallGoods goods, Integer[] ids, String[] names, Double[] prices, Integer[] stocks){
        goods.setGoodsStandards(new HashSet<>());//清空
        if(!Objects.isNull(names)){
            for(int i=0;i<names.length;i++){
                GoodsStandard goodsStandard = new GoodsStandard();
                if(!ids[i].equals(0)){//不是0
                    GoodsStandard temp  = standardDao.findById(ids[i]);
                    if(!Objects.isNull(temp)){
                        goodsStandard = temp;
                    }else {
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

//    @Transactional
//    public void modifyMallGoods(PmallGoods pmallGoods, String imgType, byte[] imgBytes) {
//        LOG.debug("修改优惠商城商品 id:{}", pmallGoods.getId());
//        if (Objects.nonNull(imgType)) {
//            String fileName = pmallGoods.getImgFile();
//            if (Objects.isNull(fileName) || fileName.isEmpty()) {
//                fileName = generateFileName(imgType);
//                pmallGoods.setImgFile(fileName);
//            }
//            writeImgFile(imgBytes, fileName);
//        }
//        if (pmallGoods.getActivity() == 1) {//活动
//            if (Objects.isNull(pmallGoods.getStartTime())) {//
//                //设置当前时间为活动时间
//                pmallGoods.setStartTime(new Timestamp(System.currentTimeMillis()));
//            }
//        } else {//下架
//            if (Objects.nonNull(pmallGoods.getStartTime())) {
//                pmallGoods.setStartTime(null);
//            }
//        }
//        mallGoodsDAO.merge(pmallGoods);
//    }

    /**
     * 增加产品类型
     * @param mallGoods
     * @param imgType
     * @param imgBytes
     */
    @Transactional
    public void addMallGoodsType(PmallGoodsType mallGoods, String imgType, byte[] imgBytes) {
        LOG.debug("新增积分商城商品类型 name:{}", mallGoods.getName());
        if (Objects.nonNull(imgType)) {
            String fileName = generateFileName(imgType);
            writeImgFile(imgBytes, fileName);
            mallGoods.setImg(fileName);
        }
        if(Objects.isNull(mallGoods.getId())) {
            mallGoodsTypeDAO.save(mallGoods);
        }else {
            mallGoodsTypeDAO.merge(mallGoods);
        }
        if(mallGoods.getType()==0){//设置为自己的id
            mallGoods.setVarStatus(mallGoods.getId());
        }else {//上级的id
            mallGoods.setVarStatus(mallGoods.getParentType().getId());
        }
        mallGoodsTypeDAO.merge(mallGoods);
    }

    @Transactional
    public void removeMallGoodsType(PmallGoodsType mallGoods) {
        LOG.debug("删除积分商城商品类型 id:{}", mallGoods.getId());
        mallGoodsTypeDAO.delete(mallGoods);
    }

    /**
     * 购物车的产品已经大于或者等于限制值了
     *
     * @param uid
     * @param goods
     * @return
     */
    public boolean isActivityLitmit(Integer uid, PmallGoods goods, Integer quantity) {
        if (!goods.getActivity()) {//非活动期间
            return false;
        }
        return (orderItemDao.getOrdersDateBefore(uid, goods).size() + quantity) > goods.getLimitNumer();
    }

    @Transactional
    public void removeMallGoods(PmallGoods pmallGoods) {
        LOG.debug("删除优惠商城商品 id:{}", pmallGoods.getId());
        mallGoodsDAO.delete(pmallGoods);
    }

    @Transactional
    public void addStock(PmallGoodsStockBlotter stock) {
        PmallGoods goods = mallGoodsDAO.findById(stock.getGoods().getId());
//        goods.addStockBlotter(stock); TODO
        mallGoodsStockBlotterDAO.save(stock);
        LOG.debug("新增库存成功");
    }

    private String generateFileName(String imgType) {
        String s = UUID.randomUUID().toString();
        StringBuilder sbd = new StringBuilder();
        sbd.append(s.substring(0, 8)).append(s.substring(9, 13)).append(s.substring(14, 18)).append(s.substring(19, 23)).append(s.substring(24)).append(imgType);
        return sbd.toString();
    }


    private void writeImgFile(byte[] imgBytes, String fileName) {
        try {
            Path path = Paths.get(systemParameterDAO.getMallGoodsImgPath());
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path imgFile = path.resolve(fileName);
            if (!Files.exists(imgFile)) {
                Files.createFile(imgFile);
            }
            Files.write(imgFile, imgBytes, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException iop) {
            LOG.error("写图片文件失败", iop);
            throw new ApplicationException(iop);
        }
    }

    @Autowired
    private OrderItemDAO orderItemDao;
    @Autowired
    private MallGoodsDAO mallGoodsDAO;
    @Autowired
    private SystemParameterDAOInf systemParameterDAO;
    @Autowired
    private MutedUserDAO mutedUserDAO;
    @Autowired
    private MallGoodsStockBlotterDAO mallGoodsStockBlotterDAO;
    @Autowired
    private StandardDao standardDao;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private MallGoodsTypeDAO mallGoodsTypeDAO;

}
