package com.dreamer.domain.user;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.system.Module;
import com.dreamer.domain.user.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;
import ps.mx.otter.utils.json.BaseView;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.*;

@Entity
public abstract class User implements java.io.Serializable {

	private static final long serialVersionUID = -6857285918433321049L;
	private Integer id;
	@JsonView(PmallGoodsStockBlotter.MallGoodsStockBlotterView.class)
	private String realName;//真实名字
	private String nickName;//微信昵称
	private String headimgurl;//微信头像
	private String mobile;
	private String weixin;
	private String idCard;
	private Agent parent;
	private UserStatus userStatus;
	private String password;
	private Timestamp updateTime;
	private Integer version;
	private String wxOpenid;
	private String payOpenid;
	private String wxUnionID;
	private Integer identity;
	private Set<GoodsAccount> goodsAccounts = new HashSet<>();
	private String lastVisitIP;
	private Timestamp lastVisitTime;
	private String subDomain;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date joinDate;//注册时间
	private String loginToken;


	public String getPayOpenid() {
		return payOpenid;
	}

	public void setPayOpenid(String payOpenid) {
		this.payOpenid = payOpenid;
	}

	public abstract void addAuthorizationToAgent(Agent agent,
												 List<AuthorizationType> types);

	public abstract List<Module> getLeafModules();

	public abstract Set<Module> getTopModules();

	/**
	 * 用户是否已拥有指定产品授权(拥有指定产品授权或者任一主产品授权)且授权激活
	 * @param goods 检查是否授权的产品
	 * @return false-未授权 true-已授权
	 */
	public boolean isActivedAuthorizedGoods(Goods goods) {
		return isMutedUser();
	}

	public boolean isMyParent(User agent){
		return Objects.equals(getParent(), agent);
	}
	
	public boolean notParentChildRelation(User agent){
		return !(this.isMyParent(agent) || agent.isMyParent(this));
	}
	

	public boolean hasGoodsAccount(Goods goods) {
		Optional<GoodsAccount> optional = goodsAccounts.stream()
				.filter((g) -> Objects.equals(g.getGoods(), goods)).findFirst();
		return optional.isPresent();
	}

	public String getWxUnionID() {
		return wxUnionID;
	}

	public void setWxUnionID(String wxUnionID) {
		this.wxUnionID = wxUnionID;
	}




	public void addGoodsAccount(GoodsAccount accs) {
		if (goodsAccounts.add(accs)) {
			accs.setUser(this);
		}
	}

	public abstract boolean isAdmin();

	public abstract boolean isAgent();


	public abstract boolean isMutedUser();


	public boolean isTopAgent() {
		return isAgent() && (Objects.nonNull(parent) && parent.isMutedUser());
	}


	public boolean isNew() {
		return UserStatus.NEW == userStatus;
	}

	public boolean isStoped() {
		return UserStatus.STOP == userStatus;
	}

	public boolean isLocked() {
		return UserStatus.LOCKED == userStatus;
	}

	public void lock() {
		if (isNormal()) {
			this.userStatus = UserStatus.LOCKED;
		}
	}

	public void unlock() {
		if (isLocked()) {
			this.userStatus = UserStatus.NORMAL;
		}
	}

	public boolean isNormal() {
		return !isLocked() && !isStoped();
	}

	public void normal() {
		if (!isNormal()) {
			this.userStatus = UserStatus.NORMAL;
		}
	}

	public String defaultPassword() {
		String idCard = getIdCard();
		if (idCard != null && idCard.length() > 5) {
			return idCard.substring(idCard.length() - 6, idCard.length());
		} else {
			return null;
		}
	}

	// Constructors

	/** default constructor */
	public User() {
	}




	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}



	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public UserStatus getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}



	public String getWxOpenid() {
		return this.wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getIdentity() {
		return this.identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public Set<GoodsAccount> getGoodsAccounts() {
		return this.goodsAccounts;
	}

	public void setGoodsAccounts(Set<GoodsAccount> accounts) {
		this.goodsAccounts = accounts;
	}

	public Agent getParent() {
		return parent;
	}

	public void setParent(Agent parent) {
		this.parent = parent;
	}

	public String getLastVisitIP() {
		return lastVisitIP;
	}

	public void setLastVisitIP(String lastVisitIP) {
		this.lastVisitIP = lastVisitIP;
	}

	public Timestamp getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Timestamp lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}








	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public interface UserBaseView extends BaseView {
	}

	
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}


	


}