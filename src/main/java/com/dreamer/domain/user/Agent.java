package com.dreamer.domain.user;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.authorization.Authorization;
import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsType;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.system.Module;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.user.enums.UserStatus;

import javax.persistence.Entity;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Agent extends User {

	private static final long serialVersionUID = -2336150649364845385L;
	private String agentCode;
	private Boolean needCheck;//是否需要考核
	private String remittance;
	private AgentStatus agentStatus;
	private Accounts accounts;//账户
	private Set<Authorization> authorizations = new HashSet<>();


    public Boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(Boolean needCheck) {
        this.needCheck = needCheck;
    }

    public static Agent build() {
		Agent agent = new Agent();
		return agent;
	}

	public Accounts getAccounts() {
		return accounts;
	}

	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}

	public void giftPoint(int point) {
		getAccounts().increasePoints(Double.valueOf(point));
	}

	public Double getPointsBalance() {
		return getAccounts().getPointsBalance();
	}

	public void passAudit() {
		if (isNew()) {
			this.setUserStatus(UserStatus.NORMAL);
		}
	}

	public GoodsAccount loadAccountForGoodsNotNull(Goods goods) {
		Optional<GoodsAccount> optional = getGoodsAccounts().stream()
				.filter((g) -> Objects.equals(g.getGoods(), goods)).findFirst();
		return optional.orElse(this.addGoodsAccount(goods));
	}

//	public GoodsAccount loadAccountForGoods(Goods goods) {
//		Optional<GoodsAccount> optional = getGoodsAccounts().stream()
//				.filter((g) -> Objects.equals(g.getGoods(), goods)).findFirst();
//		return optional.orElse(null);
//	}
//	/**
//	 * add by hf  获取某种授权产品的等级
//	 * @param type
//	 * @return
//	 */
//	public GoodsAccount loadAccountForGoodsType(GoodsType type) {
//		Optional<GoodsAccount> optional = getGoodsAccounts().stream()
//				.filter((g) -> Objects.equals(g.getGoods().getGoodsType(), type)).findFirst();
//		return optional.orElse(null);
//	}

	/**
	 * 是否是特权商城的Vip 品牌代言/发起者
	 * @return true or false
     */
	public boolean isTeqVip(){
		String name1=AgentLevelName.品牌代言.toString();
//		String name2=AgentLevelName.联盟单位.toString();
//		String name3=AgentLevelName.董事.toString();
//		String name4=AgentLevelName.金董.toString();
		String names=name1;
		GoodsAccount account=getGoodsAccounts().stream().filter(g->g.getGoods().getGoodsType().equals(GoodsType.TEQ)&&(names.contains(g.getAgentLevel().getName()))).findFirst().orElse(null);
		return !Objects.isNull(account);
	}



    /**
	 * 是否是代理vip
	 * @return true or false
	 */
	public boolean isVip(){
//		String name1=AgentLevelName.官方.toString();
//		String name2=AgentLevelName.发起者.toString();
//		GoodsAccount account=getGoodsAccounts().stream().filter(g->g.getGoods().getGoodsType().equals(GoodsType.MALL)&&(g.getAgentLevel().getName().contains(name1)||g.getAgentLevel().getName().contains(name2))).findFirst().orElse(null);
//		return !Objects.isNull(account);
		return false;
	}

	public GoodsAccount loadAccountForGoodsId(Integer goodsId) {
		Optional<GoodsAccount> optional = getGoodsAccounts().stream()
				.filter((g) -> Objects.equals(g.getGoods().getId(), goodsId))
				.findFirst();
		return optional.orElse(null);
	}



    public boolean isBalanceNotEnough(Goods goods, Integer quantity) {
		GoodsAccount account = this.loadAccountForGoodsNotNull(goods);
		return account.getCurrentBalance() < quantity;
	}

//	public void delivery(DeliveryNote note) {
//		Iterator<DeliveryItem> its = note.getDeliveryItems().iterator();
//		while (its.hasNext()) {
//			DeliveryItem item = its.next();
//			GoodsAccount gac = loadAccountForGoodsNotNull(item.getGoods());
//			gac.deductBalance(item.getQuantity());
//		}
//		deductPoints(note.caculatePoints());
//	}

//	@Override
//	public boolean hasMainGoodsAuthorization(GoodsType gt) {
//		// TODO Auto-generated method stub
//		List<GoodsAccount> mainAccs = mainGoodsAccount(gt);
//		return Objects.nonNull(mainAccs) && mainAccs.size() > 0;
//	}

//	/**
//	 * 获取已授权的主打产品账户
//	 *
//	 * @return
//	 */
//	public List<GoodsAccount> mainGoodsAccount(GoodsType gt) {
//		// TODO Auto-generated method stub
//		List<GoodsAccount> accs = new ArrayList<>();
//		loadActivedAuthorizedGoodses().forEach(goods -> {
//            GoodsAccount acc = null;
//            if(Objects.isNull(gt)){//如果传入的参数为空
//                if(goods.isMainGoods()){
//                     acc = loadAccountForGoods(goods);
//                }
//            }else {
//                if (goods.isMainGoods()&&goods.getGoodsType()==gt) {
//                     acc = loadAccountForGoods(goods);
//				}
//            }
//            if (Objects.nonNull(acc)) {
//                accs.add(acc);
//            }
//		});
//		return accs;
//	}
	
	
//	/**
//	 * 获取指定产品的所处等级
//	 * <ul>
//	 * <li>不存在主打产品账户,取当前产品等级</li>
//	 * <li>当前产品等级低于或等于主打产品等级,取主打产品等级</li>
//	 * <li>当前产品等级高于主打产品等级,取当前产品等级</li>
//	 * </ul>
//	 *
//	 * @param goods
//	 * @return
//	 */
//	public AgentLevel getGoodsLowestAgentLevel(Goods goods) {
//		GoodsAccount gac = this.loadAccountForGoodsNotNull(goods);
//		if (Objects.isNull(gac)) {
//			gac = this.addGoodsAccount(goods);
//		}
//		AgentLevel mainGoodsLevel = mainGoodsTopAgentLevel(goods.getGoodsType());//获取当前主打产品的等级
//		if (Objects.isNull(mainGoodsLevel)) {
//			return gac.getAgentLevel();
//		}
//		return gac.getAgentLevel().higherThanMe(mainGoodsLevel) ? mainGoodsLevel
//				: gac.getAgentLevel();
//	}

//	/**
//	 * 获取某种产品最高价格等级的主打产品价格等级
//	 *
//	 * @return
//	 */
//	public AgentLevel mainGoodsTopAgentLevel(GoodsType goodsType) {
//		List<GoodsAccount> accs = mainGoodsAccount(goodsType);
//        AgentLevel tempLevel = null;
//        accs=accs.stream().filter(g->{return g.getGoods().getGoodsType()==goodsType;}).collect(Collectors.toList());//找到对应的主打产品
//        for (GoodsAccount acc : accs) {
//			if (acc.getAgentLevel().lowerThanMe(tempLevel)) {
//				tempLevel = acc.getAgentLevel();
//			}
//		}
//		return tempLevel;
//	}

	public void transferGoodsToAnother(Agent user, Transfer transfer) {
		// TODO Auto-generated method stub
//		transfer.getItems().forEach((k, v) -> {
//			GoodsAccount accTo = user.loadAccountForGoodsId(k);
//			if (Objects.isNull(accTo)) {
//				throw new DataNotFoundException("转入方对应货物账户不存在");
//			}
//			GoodsAccount accFrom = this.loadAccountForGoodsId(k);
//			if (Objects.isNull(accFrom)) {
//				throw new DataNotFoundException("转出方对应货物账户不存在");
//			}
//			accFrom.transferGoodsToAnother(accTo, v.getQuantity(),transfer.getApplyOrigin());//不同来源的订单不同的处理
//		});
	}

//	public Price caculatePrice(Goods goods) {
//		GoodsAccount gac = loadAccountForGoodsNotNull(goods);
//		AgentLevel mainAgentLevel = getMainLevel(goods);
//		if (Objects.isNull(gac)) {
//			gac = this.addGoodsAccount(goods);
//			if (Objects.isNull(mainAgentLevel)) {
//				// throw new ApplicationException("代理商没有该商品以及主打商品账户");
//				return goods.getLowestPrice();
//			}
//			return goods.getPrice(mainAgentLevel);
//		}
//
//		Price price = null;
//		if (gac.mainGoodsAgentLevelHigherThanMe(mainAgentLevel)) {
//			price = goods.getPrice(mainAgentLevel);
//		} else {
//			price = goods.getPrice(gac.getAgentLevel());
//		}
//
//		if (Objects.isNull(price)) {
//			price = goods.getLowestPrice();
//		}
//		return price;
//	}

	//获取主打产品账户
    public GoodsAccount getMainGoodsAccount(GoodsType gt){
        List<GoodsAccount> gs= getGoodsAccounts().stream().filter(g->g.isMainGoodsAccount()&&g.getGoods().getGoodsType()==gt).collect(Collectors.toList());
        return gs.get(0);
    }

	//获取主打产品级别的价格
	public Price getMainLevelPrice(Goods goods){
		loadAccountForGoodsNotNull(goods);//防止为空
		 return goods.getPrice(getMainLevel(goods));
	}

    //获取主打产品级别
    public AgentLevel getMainLevel(Goods goods){
        GoodsAccount gac = getMainGoodsAccount(goods.getGoodsType());
        return gac.getAgentLevel();
    }

    //获取特权商城主打产品级别
	public String getLevelName(){
    	if(agentCode==null)return "访客";
		GoodsAccount gac = getMainGoodsAccount(GoodsType.MALL);
		return gac.getAgentLevel().getName();
	}



//	public Price caculatePrice(Goods goods, Integer quantity) {
//		GoodsAccount gac = loadAccountForGoodsNotNull(goods);
//		if (Objects.isNull(gac)) {
//			gac = this.addGoodsAccount(goods);
//		}
//		Integer amount = gac.getCumulative() + quantity;
//		Price currentPriceLevel = goods.getPrice(gac.getAgentLevel());
//        AgentLevel tempLevel = currentPriceLevel.getAgentLevel().getParent();
//		while (tempLevel != null && tempLevel.canAutoPromotion()) {
//			Price tempPriceLevel = goods.getPrice(tempLevel);
//			if (tempPriceLevel.thresholdLowerThan(amount)) {
//				currentPriceLevel = tempPriceLevel;
//				tempLevel = tempLevel.getParent();
//			} else {
//				break;
//			}
//		}
//
//		if (this.hasMainGoodsAuthorization(goods.getGoodsType())) {
//			AgentLevel mainAgentLevel = caculateMainGoodsPriceLevel(goods.getGoodsType()).getAgentLevel();
//            if (currentPriceLevel.getAgentLevel().higherThanMe(mainAgentLevel)) {
//				currentPriceLevel = goods.getPrice(mainAgentLevel);
//			}
//		}
//
//		if (Objects.isNull(currentPriceLevel)) {
//			currentPriceLevel = goods.getLowestPrice();
//		}
//        return currentPriceLevel;
//	}

//	/**
//	 * 动态累积计算出当前代理主打产品所处等级
//	 *
//	 * @return
//	 */
//	public Price caculateMainGoodsPriceLevel(GoodsType gt) {
//		GoodsAccount gac = this.mainGoodsAccount(gt).get(0);
//		if (Objects.isNull(gac)) {
//			throw new DataNotFoundException("主打产品账户不存在,无法计算主打产品所处等级");
//		}
//		Goods goods = gac.getGoods();
//        Integer amount = gac.getCumulative();
//
//		Price currentPriceLevel = goods.getPrice(gac.getAgentLevel());
//		AgentLevel tempLevel = currentPriceLevel.getAgentLevel().getParent();
//		while (tempLevel != null && tempLevel.canAutoPromotion()) {
//			Price tempPriceLevel = goods.getPrice(tempLevel);
//			if (tempPriceLevel.thresholdLowerThan(amount)) {
//				currentPriceLevel = tempPriceLevel;
//				tempLevel = tempLevel.getParent();
//			} else {
//				break;
//			}
//		}
//
//		if (Objects.isNull(currentPriceLevel)) {
//			currentPriceLevel = goods.getLowestPrice();
//		}
//		return currentPriceLevel;
//	}

	public Accounts generateAccounts() {
		if(getAccounts()==null){
            Accounts accounts = new Accounts();
            accounts.setUser(this);
            accounts.setPointsBalance(0D);
            accounts.setVoucherBalance(0d);
            accounts.setPayBalance(0d);
            accounts.setAdvanceBalance(0d);//设置预存款
            accounts.setBenefitPointsBalance(0D);
            accounts.setPurchaseBalance(0D);//设置进货券
            accounts.setUpdateTime(new Date());
            setAccounts(accounts);
		}
		return getAccounts();
	}

	public void transferPoints(Agent toAgent, Double points) {
		getAccounts().transferPointsToAnoher(toAgent.getAccounts(), points);
	}
	
//	public void transferVoucher(Agent toAgent, Double voucher) {
//		getAccounts().transferVoucherToAnoher(toAgent.getAccounts(), voucher);
//	}

//    public void transferPurchase(Agent toAgent, Double purchase) {
//        getAccounts().transferPurchaseToAnother(toAgent.getAccounts(), purchase);
//    }

//    public void transferAdvance(Agent toAgent,Double advance){
//        getAccounts().transferAdvanceToAnother(toAgent.getAccounts(),advance);
//    }

	public void removeAuthorization(Authorization auth) {
		if (authorizations.remove(auth)) {
			auth.setAgent(null);
		}
	}

	public List<Goods> loadActivedAuthorizedGoodses() {
		List<Goods> goodses = new ArrayList<>();
		getAuthorizations().stream().filter((a) -> a.isActive())
				.forEach(a -> goodses.add(a.getAuthorizedGoods()));
		return goodses;
	}

    /**
     * 为所有的授权增加相应的库存
     */
	public void generateGoodsAccount() {
		List<Goods> goods = loadActivedAuthorizedGoodses();
		if (goods != null) {
			goods.forEach(g -> {
				addGoodsAccount(g);
			});
		}
	}

    /**
     * 为空才会调用  需要修改
     * @param goods
     * @return
     */
	public GoodsAccount addGoodsAccount(Goods goods) {
		if (!hasGoodsAccount(goods)) {
			GoodsAccount account = buildGoodsAccount(goods);
			addGoodsAccount(account);
			return account;
		}
		return null;
	}

    /**
     * 如果有主打产品设置为主打产品级别  如果没有设置为最低的级别
     * @param g
     * @return
     */
	public GoodsAccount buildGoodsAccount(Goods g) {//新增账户、有主打产品的MALL新增主打产品等级  没有的最低等级
        AgentLevel level;
        if(g.isMainGoods()){//如果G是主打产品，授予最低级别，一般为首次授权
            level= g.getLowestPrice().getAgentLevel();
        }else {
            //找出主打产品等级
            level=getMainLevel(g);
        }
		GoodsAccount account = new GoodsAccount();
		account.setGoods(g);
		account.setCurrentBalance(0);
		account.setCurrentPoint(0D);
		account.setCumulative(0);
		account.setAgentLevel(level);
		account.setUpdateTime(new Date());
		return account;
	}


//	/**
//	 * 增加代金券变动记录
//	 */
//	public void addVoucherRecord(Integer type,String more,Double voucher){
//		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
//		VoucherRecord v=new VoucherRecord(type, voucher, more, updateTime,getAccounts().getVoucherBalance());
//		v.setAgent(this);//增加自己
//		voucherRecords.add(v);//增加一条记录
//	}




//    /**
//     * 增加预存款变动记录
//     * @param type
//     * @param more
//     * @param advance
//     */
//
//    @Override
//    public void addAdvanceRecord(Integer type, String more, Double advance) {
//        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
//        AdvanceRecord a = new AdvanceRecord(type,this,advance,more,getAccounts().getAdvanceBalance(),updateTime);
//		advanceRecords.add(a);
//    }

//    /**
//     * 增加预存款变动记录
//     * @param type
//     * @param more
//     * @param advance
//     */
//    @Override
//    public void addPurchaseRecord(Integer type, String more, Double purchase) {
//        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
//        PurchaseRecord a = new PurchaseRecord(type,this,purchase,more,getAccounts().getPurchaseBalance(),updateTime);
//        purchaseRecords.add(a);
//    }

    /**
	 * 增加授权类型
	 */
	@Override
	public void addAuthorizationToAgent(Agent agent,
			List<AuthorizationType> types) {
		// TODO Auto-generated method stub
		types.forEach((t) -> {
			Authorization auth = agent.buildAuthorization(t);
			auth.setStatus(AgentStatus.NO_ACTIVE);
			agent.addAuthorization(auth);
		});
		agent.inactive();
	}

	public Authorization buildAuthorization(AuthorizationType authType) {
		Authorization auth = new Authorization();
		auth.setAuthorizationType(authType);
		auth.setAgent(this);
		auth.setUpdateTime(new Date());
		return auth;
	}

	public boolean isActivedAuthorizedGoods(Goods goods) {
		return getAuthorizations()
				.stream()
				.filter((a) -> a.isActive()
						&& (a.isAuthorizedGoods(goods) || a
								.isMainAuthorization())).count() == 1;
	}



	public void addAuthorization(Authorization auth) {
		if (authorizations.add(auth)) {
			auth.setAgent(this);
		}
	}

//	public Set<AdvanceRecord> getAdvanceRecords() {
//		return advanceRecords;
//	}

//	public void setAdvanceRecords(Set<AdvanceRecord> advanceRecords) {
//		this.advanceRecords = advanceRecords;
//	}

	public boolean alreadyAuthorizated(Authorization auth) {
		return authorizations.contains(auth);
	}

	public boolean isInactive() {
		return agentStatus == null || agentStatus == AgentStatus.NO_ACTIVE;
	}

	public boolean isActive() {
		return agentStatus != null && agentStatus == AgentStatus.ACTIVE;
	}

	public boolean isReorganize() {
		return agentStatus != null && agentStatus == AgentStatus.REORGANIZE;
	}

//    public Set<Address> getAddresses() {
//        return addresses;
//    }

//    public void setAddresses(Set<Address> addresses) {
//        this.addresses = addresses;
//    }

    public List<AuthorizationType> allActivedAuthorizationType() {
		List<AuthorizationType> authTypes = new ArrayList<AuthorizationType>();
		authorizations.stream().filter((auth) -> auth.isActive())
				.forEach((auth) -> {
					authTypes.add(auth.getAuthorizationType());
				});
		return authTypes;
	}

	public List<AuthorizationType> allAuthorizationType() {
		List<AuthorizationType> authTypes = new ArrayList<AuthorizationType>();
		authorizations.stream().forEach((auth) -> {
			authTypes.add(auth.getAuthorizationType());
		});
		return authTypes;
	}

	public void activeAll() {
		active();
		if (authorizations != null) {
			for (Authorization auth : authorizations) {
				auth.active();
			}
		}
		generateGoodsAccount();
	}

	public void active() {
		agentStatus = AgentStatus.ACTIVE;
	}

	public void inactiveAll() {
		inactive();
		if (authorizations != null) {
			for (Authorization auth : authorizations) {
				auth.inactive();
			}
		}
	}

	public void inactive() {
		agentStatus = AgentStatus.NO_ACTIVE;
	}

	public void reorganizeAll() {
		reorganize();
		if (authorizations != null) {
			for (Authorization auth : authorizations) {
				auth.reorganize();
			}
		}
	}

	public void reorganize() {
		agentStatus = AgentStatus.REORGANIZE;
	}

	@Override
	public boolean isAgent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isMutedUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Module> getLeafModules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Module> getTopModules() {
		// TODO Auto-generated method stub
		return null;
	}

	public String defaultPassword() {
		String idCard = getIdCard();
		if (idCard != null && idCard.length() > 5) {
			return idCard.substring(idCard.length() - 6, idCard.length());
		} else {
			return "888888";
		}
	}


	public String getAgentCode() {
		return this.agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getRemittance() {
		return this.remittance;
	}

	public void setRemittance(String remittance) {
		this.remittance = remittance;
	}

	public AgentStatus getAgentStatus() {
		return this.agentStatus;
	}

	public void setAgentStatus(AgentStatus agentStatus) {
		this.agentStatus = agentStatus;
	}

	public Set<Authorization> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<Authorization> authorizations) {
		this.authorizations = authorizations;
	}




}
