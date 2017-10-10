package com.dreamer.view.pmall.dto;

/**
 * Created by huangfei on 13/07/2017.
 */
public class MallGoodsDto{

    private Integer goodsId;

    private String goodsName;

    private String  imgPath;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}