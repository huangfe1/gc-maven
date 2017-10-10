package com.dreamer.domain.pmall.goods;

import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PmallGoods implements java.io.Serializable {

    private Integer id;

    private Integer version;

    @JsonView(PmallGoodsStockBlotter.MallGoodsStockBlotterView.class)
    private String name;

    private String shareName;

    private PmallGoodsType goodsType;

    private String shareDetail;

    private Double profit;//利润

    private String spec;

    private Boolean shelf;

    private Date updateTime;

    private Integer sequence;

    private Double price;//上架价格

    private Double retailPrice;//实际价格

    private Boolean canAdvance;//是否能置换


    private String imgFile;

    private String wallFile;

    private Integer stockQuantity;

    private String vouchers;

    private String detailImg;//详情页名字

    private Boolean activity;//是否活动,0代表不在活动,1代表活动

    private Timestamp startTime;//活动开始时间

    private Integer limitNumer;//活动限购数量

    private Integer sel; //种类数量

    public Boolean getCanAdvance() {
        return canAdvance;
    }

    public void setCanAdvance(Boolean canAdvance) {
        this.canAdvance = canAdvance;
    }

    private Set<GoodsStandard> goodsStandards = new HashSet<>();//产品种类


    public void addGoodsStandards(GoodsStandard goodsStandard){
        goodsStandards.add(goodsStandard);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public PmallGoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(PmallGoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getShareDetail() {
        return shareDetail;
    }

    public void setShareDetail(String shareDetail) {
        this.shareDetail = shareDetail;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Boolean getShelf() {
        return shelf;
    }

    public void setShelf(Boolean shelf) {
        this.shelf = shelf;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getWallFile() {
        return wallFile;
    }

    public void setWallFile(String wallFile) {
        this.wallFile = wallFile;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getVouchers() {
        return vouchers;
    }

    public void setVouchers(String vouchers) {
        this.vouchers = vouchers;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Integer getLimitNumer() {
        return limitNumer;
    }

    public void setLimitNumer(Integer limitNumer) {
        this.limitNumer = limitNumer;
    }

    public Integer getSel() {
        return sel;
    }

    public void setSel(Integer sel) {
        this.sel = sel;
    }

    public Set<GoodsStandard> getGoodsStandards() {
        return goodsStandards;
    }

    public void setGoodsStandards(Set<GoodsStandard> goodsStandards) {
        this.goodsStandards = goodsStandards;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}