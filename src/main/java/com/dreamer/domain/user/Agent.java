package com.dreamer.domain.user;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.authorization.Authorization;
import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsType;
import com.dreamer.domain.mall.goods.Price;
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
	private Set<Authorization> authorizations = new HashSet<>();//授权
	private String registerAddress;//注册地址
	private String imgFile;//营业执照
    private Date buyTime;//需要重新购买的时间，每次购物更新
	private String taxCode;//纳税识别号
	private String payWay;//支付方式,业务员选择
	private String info;//客户性质

	public String getPayWay() {
		return payWay;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

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


	//是否需要买
	public boolean isNeedBuy(){
        if(buyTime==null) return false;//没有的就不用提醒 也就是新公司
        Date date = new Date();
        //今天超过补货时间 需要补货
        if(buyTime.equals(date))return true;
        if(buyTime.before(date)) return true;

        return false;
    }


	/**
	 * 是否是特权商城的Vip 品牌代言/发起者
	 * @return true or false
     */
	public boolean isTeqVip(){
		String name1=AgentLevelName.分公司.toString();
//		String name2=AgentLevelName.联盟单位.toString();
//		String name3=AgentLevelName.董事.toString();
//		String name4=AgentLevelName.金董.toString();
		String names=name1;
		GoodsAccount account=getGoodsAccounts().stream().filter(g->g.getGoods().getGoodsType().equals(GoodsType.TEQ)&&(names.contains(g.getAgentLevel().getName()))).findFirst().orElse(null);
		return !Objects.isNull(account);
	}


//	//是否需要补货
//	public boolean needBuy(){
//        Date today =
//    }
//




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








	//获取主打产品账户
    public GoodsAccount getMainGoodsAccount(GoodsType gt){
        List<GoodsAccount> gs= getGoodsAccounts().stream().filter(g->g.isMainGoodsAccount()&&g.getGoods().getGoodsType()==gt).collect(Collectors.toList());
        return gs.get(0);
    }

	//获取主打产品级别的价格 就是分公司的价格
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
