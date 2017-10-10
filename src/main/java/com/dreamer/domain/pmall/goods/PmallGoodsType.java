package com.dreamer.domain.pmall.goods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangfei on 2017/1/15.
 */
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PmallGoodsType implements Serializable{

    private Integer id;//id

    private String name;//类别名字

    private Date   updateTime;//上传时间

    private Integer varStatus;//类别标签  一个类是一个

    private Integer type;//0为1级标签 1为2级标签

    private Integer orderIndex;

    private PmallGoodsType parentType;//上级类型

    private String img;//图片地址

    public Integer getId() {
        return id;
    }

    public Integer getVarStatus() {
        return varStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PmallGoodsType getParentType() {
        return parentType;
    }

    public void setParentType(PmallGoodsType parentType) {
        this.parentType = parentType;
    }

    public void setVarStatus(Integer varStatus) {
        this.varStatus = varStatus;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImg() {
        return img;
    }



    public void setImg(String img) {
        this.img = img;
    }
}
