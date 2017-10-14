package com.dreamer.service.mobile.impl;

import com.dreamer.repository.mobile.BaseDao;
import com.dreamer.service.mobile.BaseHandler;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class BaseHandlerImpl<T> implements BaseHandler<T> {


    private BaseDao<T> baseDao;

    @Override
    @Transactional
    public T merge(T object) {
        return baseDao.merge(object);
    }

    @Override
    public T save(T object) {
        return baseDao.merge(object);
    }

    @Override
    public T get(Integer id) {
        return baseDao.get(id);
    }

    @Override
    public T get(String name, Object value) {
        return baseDao.get(name, value);
    }

    @Override
    public T get(Map<String, Object> map) {
        return baseDao.get(map);
    }

    @Override
    public List<T> getList(Map<String, Object> map) {
        return baseDao.getList(map);
    }

    @Override
    public List<T> getList(String name, Object value) {
        return baseDao.getList(name,value);
    }


    @Override
    public List<T> getListIn(String name, List<Object> value) {
        return baseDao.getListIn(name,value);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> getOr(Map<String, Object> map) {
        return baseDao.getOr(map);
    }

    @Override
    public T like(String name, Object value) {
        return baseDao.like(name,value);
    }

    @Override
    public List<T> likeList(String name, Object value) {
        return baseDao.likeList(name,value);
    }

    @Override
    public void addRestraction(DetachedCriteria dc, String name, Object o) {
         baseDao.addRestraction(dc,name,o);
    }

    public BaseDao<T> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }
}
