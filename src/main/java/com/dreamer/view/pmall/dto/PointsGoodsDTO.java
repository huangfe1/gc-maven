package com.dreamer.view.pmall.dto;


import com.dreamer.domain.pmall.goods.GoodsStandard;

import java.util.List;
import java.util.Set;

public class PointsGoodsDTO {

	private Integer id;
	private String name;
	private String shareName;
	private String shareDetail;
	private String spec;
	private Double voucher;
	private String vouchers;//返利模式
	private Double price;
	private Double retailPrice;//实际价格
	private Boolean shelf;
	private String imgUrl;
	private String wallImgUrl;
	private Integer sel;//选择数量
	private Integer stockQuantity;
	private Set<GoodsStandard> goodsStandard;
	private List<String> detailImgUrls;//详情页

	public Integer getId() {
		return id;
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

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public String getShareDetail() {
		return shareDetail;
	}

	public void setShareDetail(String shareDetail) {
		this.shareDetail = shareDetail;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}

	public String getVouchers() {
		return vouchers;
	}

	public void setVouchers(String vouchers) {
		this.vouchers = vouchers;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Boolean getShelf() {
		return shelf;
	}

	public void setShelf(Boolean shelf) {
		this.shelf = shelf;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getWallImgUrl() {
		return wallImgUrl;
	}

	public void setWallImgUrl(String wallImgUrl) {
		this.wallImgUrl = wallImgUrl;
	}

	public Integer getSel() {
		return sel;
	}

	public void setSel(Integer sel) {
		this.sel = sel;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Set<GoodsStandard> getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(Set<GoodsStandard> goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public List<String> getDetailImgUrls() {
		return detailImgUrls;
	}

	public void setDetailImgUrls(List<String> detailImgUrls) {
		this.detailImgUrls = detailImgUrls;
	}
}