package com.dreamer.repository.pmall.goods;

import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.repository.pmall.order.OrderDAO;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.dao.hibernate.HibernateBaseDAO;

/**
 * Created by huangfei on 2017/3/14.
 */
@Repository
public class StandardDao extends HibernateBaseDAO<GoodsStandard>{

    private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);

    private Session getCurrentSession(){
      return   getSessionFactory().getCurrentSession();
    }

    /**
     * 通过Id获取
     * @param id
     * @return
     */
    public GoodsStandard findById(Integer id){
        try {
            Session session = getCurrentSession();
            GoodsStandard goodsStandard = session.get(GoodsStandard.class,id);
            return goodsStandard;
        }catch (Exception e){
            log.error("查询Standard失败",e);
            throw  e;
        }
    }

    public void deleteById(Integer id){
        try {
            Session session = getCurrentSession();
            session.delete(id);
        }catch (Exception e){
            log.error("删除Standard失败",e);
            throw  e;
        }
    }

}
