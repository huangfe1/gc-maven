package com.dreamer.domain.mall.goods;

import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.user.AgentLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.json.BaseView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Goods implements java.io.Serializable {

	private static final long serialVersionUID = -4183715107638006860L;
	private Integer id;
	@JsonView(BaseView.class)
	private String name;
	private Integer order;
	private Integer currentBalance;
	private Integer currentStock;
	private Integer alertStock;
	private Timestamp updateTime;
    private Boolean shelf;//上架
	private Integer version;
	private Double pointFactor;
	private Double currentPoint;
	private Boolean benchmark;//是否主打产品
	private GoodsType goodsType;//产品的类型
	private String voucher;//返利模式
	private Double weight;//货物重量
	private Integer boxamount;//一箱多少盒
	private String imgFile;
	private String spec;
	private Double retailPrice;
	private String detailImg;//详情页名字

    @JsonIgnore
    private GoodsCategory category;
	private Boolean activity;//0不活动  1活动
	private String actImg;//活动图片
	private String startTime;//活动开始时间
	private String endTime;//活动结束时间
	@JsonIgnore
	private AuthorizationType authorizationType;
	@JsonIgnore
	private Set<Price> prices = new HashSet<Price>(0);
	@JsonIgnore
	public Price getLowestPrice(){
		Price price=null;
		Integer lowestLevel=-1;
		Iterator<Price> ite=prices.iterator();
		while(ite.hasNext()){
			Price temp=ite.next();
			if(temp.getAgentLevel().getGoodsType()==this.getGoodsType()){
				int level=temp.getAgentLevel().getLevel();
				if(level>lowestLevel){
					lowestLevel=level;
					price=temp;
				}
				}
			}
		
		return price;
	}
	/**
	 * 获取本商品指定等级的等级价格对象
	 * @param level AgentLevel 等级
	 * @return {@link Price}
	 */
	@JsonIgnore
	public Price getPrice(AgentLevel level){
		Iterator<Price> ite=prices.iterator();
		while(ite.hasNext()){
			Price temp=ite.next();
			if(Objects.equals(temp.getAgentLevel().getId(),level.getId())){
				return temp;
			}
		}
		return null;
	}
	
	@JsonIgnore
	public Integer getThreshold(AgentLevel level){
		Iterator<Price> ite=prices.iterator();
		while(ite.hasNext()){
			Price temp=ite.next();
			if(Objects.equals(temp.getAgentLevel(),level)){
				return temp.getThreshold();
			}
		}
		return 0;
	}
	
	
	
	public boolean isMainGoods(){
		return benchmark;
	}
	
	public boolean stockEnough(Integer quantity){
		return this.currentStock>=quantity;
	}
	
	public void addPrice(Price price){
		price.setGoods(this);
		prices.add(price);
	}
	
	public void clearPrices(){
		Iterator<Price> ips=prices.iterator();
		while(ips.hasNext()){
			ips.next().setGoods(null);
		}
		prices.clear();
	}

    public GoodsCategory getCategory() {
        return category;
    }

    public void setCategory(GoodsCategory category) {
        this.category = category;
    }

    public void removePrice(Price price){
		prices.remove(price);
		price.setGoods(null);
	}
	
	public void addStockBlotter(StockBlotter stock){
		
		stock.setGoods(this);
		Double point=caculatePoints(stock.getChange());
		stock.setPoint(point);
		stockValidate(stock);
		increaseCurrentStock(stock.getChange());
		//increaseCurrentPoint(point);
		increaseCurrentBalance(stock.getChange());
		stock.recordChange(this);
	}
	
	public Integer increaseCurrentStock(Integer added){
		setCurrentStock(currentStock+added);
		return getCurrentStock();
	}

    public void setShelf(Boolean shelf) {
        this.shelf = shelf;
    }


    public Boolean getShelf() {
        return shelf;
    }

    public Integer deductCurrentStock(Integer deduct){
		if(deduct>getCurrentStock()){
			throw new ApplicationException("货物总库存不足");
		}
		setCurrentStock(currentStock-deduct);
		return getCurrentStock();
	}


    /**
     * 是否显示  下架不显示  活动时间到了不显示
     *
     * @return
     */
    public boolean isShow() {
        if (!shelf) return false;
        if (activity) {
            if (getDeadLine() < 1) return false;
        }

        return true;
    }

    /**
     * 获取两个时间差
     *
     * @return
     */
    public Integer getDeadLine() {
        if(!activity)return 0;
        try {
            Date start = new Date();//当前时间
            Date end = stringToDate(endTime);//结束时间
            return getDayOfTwoDate(start, end) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取两个日期 相隔的日期
     *
     * @return
     */
    private Integer getDayOfTwoDate(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(end);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    private Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateStr);
    }


    public String getDetailImg() {
		return detailImg;
	}

	public void setDetailImg(String detailImg) {
		this.detailImg = detailImg;
	}

	public Integer increaseCurrentBalance(Integer added){
		setCurrentBalance(getCurrentBalance()+added);
		return getCurrentBalance();
	}

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public String getActImg() {
        return actImg;
    }

    public void setActImg(String actImg) {
        this.actImg = actImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBoxamount() {
        return boxamount;
    }

    public void setBoxamount(Integer boxamount) {
        this.boxamount = boxamount;
    }

    public Integer deductCurrentBalance(Integer deduct){
		if(deduct>getCurrentBalance()){
			throw new ApplicationException("货物账户总余额不足");
		}
		setCurrentBalance(getCurrentBalance()-deduct);
		return getCurrentBalance();
	}
	
	public Double increaseCurrentPoint(Double added){
		setCurrentPoint(getCurrentPoint()+added);
		return getCurrentPoint();
	}
	
	public Double deductCurrentPoint(Double deduct){
		if(deduct>getCurrentPoint()){
			throw new ApplicationException("货物账户总积分不足");
		}
		setCurrentPoint(getCurrentPoint()-deduct);
		return getCurrentPoint();
	}
	
	public Double caculatePoints(Integer amount){
		return amount*pointFactor;
	}
	
	public void stockValidate(StockBlotter stock){
		if(stock.getChange()<0){
			if(currentStock+stock.getChange()<0){
				throw new ApplicationException("库存变更为减少时,减少值不能大于现有库存");
			}
		}
		if(stock.getPoint()<0){
			if(currentStock+stock.getPoint()<0.0D){
				throw new ApplicationException("积分变更为减少时,减少值不能大于现有积分");
			}
		}
	}

	// Constructors

	/** default constructor */
	public Goods() {
	}

	/** minimal constructor */
	public Goods(String name) {
		this.name = name;
	}

	/** full constructor */
	public Goods(String name, Integer order, Integer currentBalance,
			Integer currentStock, Timestamp updateTime, Integer version,
			Double pointFactor, Double currentPoint,GoodsType goodsType,String voucher, Set<Price> prices) {
		this.name = name;
		this.order = order;
		this.currentBalance = currentBalance;
		this.currentStock = currentStock;
		this.updateTime = updateTime;
		this.version = version;
		this.pointFactor = pointFactor;
		this.currentPoint = currentPoint;
		this.prices = prices;
		this.goodsType=goodsType;
		this.voucher=voucher;
	}

	// Property accessors

	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getCurrentBalance() {
		return this.currentBalance;
	}

	public void setCurrentBalance(Integer currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Integer getCurrentStock() {
		return this.currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getPointFactor() {
		return this.pointFactor;
	}

	public void setPointFactor(Double pointFactor) {
		this.pointFactor = pointFactor;
	}

	public Double getCurrentPoint() {
		return this.currentPoint;
	}

	public void setCurrentPoint(Double currentPoint) {
		this.currentPoint = currentPoint;
	}

	public Set<Price> getPrices() {
		return this.prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	

	public Boolean getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(Boolean benchmark) {
		this.benchmark = benchmark;
	}

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public AuthorizationType getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(AuthorizationType authorizationType) {
		this.authorizationType = authorizationType;
	}
	
	public Integer getAlertStock() {
		return alertStock;
	}

	public void setAlertStock(Integer alertStock) {
		this.alertStock = alertStock;
	}

	public String getImgFile() {
		return imgFile;
	}
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id,name);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Goods)) {
			return false;
		}
		Goods other = (Goods) object;
		if (Objects.equals(getId(), other.getId()) && Objects.equals(getName(), other.getName())) {
			return true;
		} else {
			return false;
		}
	}

}