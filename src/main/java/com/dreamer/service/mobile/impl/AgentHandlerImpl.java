package com.dreamer.service.mobile.impl;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsTransferStatus;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.user.enums.UserStatus;
import com.dreamer.repository.mobile.AgentDao;
import com.dreamer.service.mobile.*;
import com.dreamer.service.user.agentCode.AgentCodeGenerator;
import com.dreamer.util.CommonUtil;
import com.dreamer.util.PreciseComputeUtil;
import com.wxjssdk.util.DateUtil;
import javafx.application.Application;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.exception.ValidationException;
import ps.mx.otter.utils.SearchParameter;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class AgentHandlerImpl extends BaseHandlerImpl<Agent> implements AgentHandler {

    @Transactional
    @Override
    public Agent login(String name, String paw) {
        //编号或者手机号都可以登录
        Agent agent = findByAgentCodeOrMobile(name);
        if (!agent.getPassword().equals(paw)) {
            throw new ApplicationException("账号密码不相匹配！");
        }

        if (agent.getAgentStatus().equals(AgentStatus.NO_ACTIVE)) {
            throw new ApplicationException("账号不相匹配！");
        }

        return agent;
    }


    @Override
    @Transactional
    public void updateBuyDate(Agent agent, Integer amount, Integer buyAmount) {
        Integer d = amount / buyAmount;
        Date date = DateUtil.formatStr(DateUtil.getFetureDate(d), "yyyy-MM-dd");
        agent.setBuyTime(date);
        merge(agent);
    }

    @Override
    public List<Agent> findByParentHasUnionId(Integer pid) {
        return agentDao.findByParentHasUnionId(pid);
    }


    @Override
    public Agent findVip(Agent agent) {
        while (!agent.isMutedUser()) {
            Agent parent = agent.getParent();
            if (getLevelName(parent).contains(AgentLevelName.分公司.toString())) {
                return parent;
            } else {
                agent = parent;
            }
        }
        return null;
    }

    /**
     * 创建访客
     *
     * @param unionId
     * @param openId
     * @param nickName
     * @param headerImg
     * @param refCode
     * @return
     */
    @Transactional
    @Override
    public Agent createVisitor(String unionId, String openId, String nickName, String headerImg, String refCode) {
        Agent agent = buildVisitor(refCode, openId, unionId, nickName, headerImg);//没有密码
        agent = agentDao.merge(agent);
        //通知
        noticeHandler.noticeNewUser(agent);
        return agent;
    }

    /**
     * 注册或者完善信息
     *
     * @param agent
     * @param refCode
     * @return
     */
    @Transactional
    @Override
    public Agent selfRegister(Agent agent, String refCode, MultipartFile file) {

        String fileName = "";

        if (file != null) {
            //营业执照
            fileName = CommonUtil.saveFile(file, systemParameterHandler.getGoodsImgPath());
        }

        //通过统一Id查找
        Agent tem = get("wxUnionID", agent.getWxUnionID());
        if (tem != null) {//判断当前用户是否存在，存在的话是扫二维码过来的,完信息
            tem = buildAgent(tem, agent.getRealName(), agent.getMobile(), agent.getWeixin(), agent.getPassword());
            tem.setImgFile(fileName);
            return merge(tem);
        }
        //否则就是新注册用户
        agent = buildAgent(refCode, agent.getMobile(), agent.getIdCard(), agent.getWeixin(), agent.getRealName(), agent.getRemittance(), agent.getPassword());
        agent.setImgFile(fileName);   //营业执照
        save(agent);
        return agent;
    }

    @Override
    @Transactional
    public Agent addAgentByAdmin(Agent agent, String refCode) {
        if (agent.getId() != null) {
            agent = updateAgent(refCode, agent);
        } else {
            agent = buildAgent(refCode, agent.getMobile(), agent.getIdCard(), agent.getWeixin(), agent.getRealName(), agent.getRemittance(), agent.getPassword());
        }
        return merge(agent);
    }

    /**
     * 创建一个优惠商城的用户 无编号  无产品库存
     *
     * @return
     */
    private Agent buildVisitor(String refCode, String wxOpenid, String wxUnionID, String nickName, String headImgUrl) {
        Agent agent = agentDao.get("wxUnionID", wxUnionID);
        Agent parent = findByAgentCodeOrId(refCode);
        if (agent == null) {
            agent = new Agent();
            //账户库存
            Accounts account = new Accounts();
            account.setUser(agent);
            agent.setAccounts(account);
            //上级
            agent.setParent(parent);
            //基本信息
            agent.setRealName(nickName);
            agent.setJoinDate(new Date());
            agent.setUserStatus(UserStatus.NEW);
            agent.setAgentStatus(AgentStatus.ACTIVE);
            agent.setWxUnionID(wxUnionID);

        }

        agent.setWxOpenid(wxOpenid);

        agent.setNickName(nickName);
        agent.setHeadimgurl(headImgUrl);
        return agent;
    }


    /**
     * 创建一般用户
     *
     * @return
     */
    private Agent buildAgent(String refCode, String mobile, String idCard, String weixin, String realName, String remittance, String password) {
        //验证是否存在
        agentDuplicate(weixin, mobile);//不能传入agent验证 缓存问题
        Agent agent = new Agent();
        Agent parent = muteUserHandler.getMuteUser();
        //如果是管理员的
        if (!parent.getAgentCode().equals(refCode)) {
            parent = findByAgentCodeOrId(refCode);
        }
        agent.setParent(parent);
        agent.setUserStatus(UserStatus.NORMAL);
        agent.setAgentStatus(AgentStatus.NO_ACTIVE);//自己注册的是没有激活的
        agent.setJoinDate(new Date());
        agent.setRemittance(remittance);
        agent.setRealName(realName);
        agent.setMobile(mobile);
        agent.setWeixin(weixin);
        agent.setIdCard(idCard);
        agent.setPassword(password);
        if (password == null || password.equals("")) {
            agent.setPassword("888888");
        }
        //生成编号
        agent.setAgentCode(createAgentCode());
        //生成账户
        Accounts accounts = new Accounts();
        accounts.setUser(agent);
        agent.setAccounts(accounts);
        //生成主打产品账户 TODO 如果是分公司 授予业务员  如果是业务员授权为药店

        GoodsAccount goodsAccount = goodsAccountHandler.generateMainGoodsAccount(agent);
        goodsAccount.setUser(agent);
        agent.addGoodsAccount(goodsAccount);


        //验证是否为空
        fieldsValiate(agent);

        return agent;
    }


    /**
     * 更新代理
     *
     * @return
     */
    private Agent updateAgent(String refCode, Agent param) {
        Map map = new HashedMap();
        String wx = param.getWeixin();
        String mobile = param.getMobile();
        param.setWeixin(null);
        param.setMobile(null);
        map.put("weixin", wx);
        map.put("mobile", mobile);
        List<Agent> agents = getOr(map);
        if (agents.size() > 0) {
            throw new ApplicationException("微信或者电话重复" + agents.get(0).getRealName());
        }
        param.setWeixin(wx);
        param.setMobile(mobile);
        //验证是否存在
        Agent parent = muteUserHandler.getMuteUser();
        //如果是管理员的
        if (!parent.getAgentCode().equals(refCode)) {
            parent = findByAgentCodeOrId(refCode);
        }
        param.setParent(parent);
        //验证是否为空

        return param;
    }


    /**
     * 完善visitor的信息  也就是将visitor转换成Agent
     *
     * @param visitor
     * @param realName
     * @param mobile
     * @param weixin
     * @param password
     * @return
     */
    private Agent buildAgent(Agent visitor, String realName, String mobile, String weixin, String password) {
        //验证是否存在
        agentDuplicate(weixin, mobile);
        visitor.setRealName(realName);
        visitor.setMobile(mobile);
        visitor.setWeixin(weixin);
        visitor.setPassword(password);
        visitor.setAgentCode(createAgentCode());
        //设置编号
        visitor.setAgentCode(createAgentCode());
        //设置产品库存
        //生成主打产品账户
        goodsAccountHandler.generateMainGoodsAccount(visitor);
        //验证是否为空
        fieldsValiate(visitor);

        return visitor;
    }


    private String createAgentCode() {
        String agentCode = agentCodeGenerator.generateAgentCode();
        while (Objects.nonNull(agentDao.get("agentCode", agentCode))) {
            agentCode = agentCodeGenerator.generateAgentCode();
        }
        return agentCode;
    }


    /**
     * 验证用户的参数是否为空
     *
     * @param agent
     */
    private void fieldsValiate(Agent agent) {
        if (Objects.isNull(agent.getRealName()) || agent.getRealName().isEmpty()) {
            throw new ValidationException("真实姓名不能为空");
        }
        if (Objects.isNull(agent.getMobile()) || agent.getMobile().isEmpty()) {
            throw new ValidationException("电话号码不能为空");
        }
        if (Objects.isNull(agent.getWeixin()) || agent.getWeixin().isEmpty()) {
            throw new ValidationException("微信号码不能为空");
        }
    }

    /**
     * 验证用户的是否被注册过
     *
     * @param agent
     * @return
     */
    private void agentDuplicate(String wx, String mobile) {
        Map<String, Object> map = new HashedMap();
        map.put("weixin", wx);
        map.put("mobile", mobile);
//        map.put("idCard", agent.getIdCard());
        List<Agent> agents = agentDao.getOr(map);
        if (agents != null && agents.size() > 0 && !agents.isEmpty()) {
            throw new ApplicationException("微信/手机号,已经被" + agents.get(0).getRealName() + "注册");
        }
    }

    /**
     * 通过Code或者Id查找
     *
     * @param refCode
     * @return
     */
    public Agent findByAgentCodeOrId(String refCode) {
        //通过code或者id查找
        Map<String, Object> map = new HashedMap();
        if (refCode.indexOf("gc") > -1) {
            map.put("agentCode", refCode);
        } else {
            map.put("id", Integer.valueOf(refCode));
        }
        List<Agent> list = agentDao.getOr(map);
        //验证登陆
        if (list == null || list.isEmpty()) {
            throw new ApplicationException("不存在此用户");
        } else if (list.size() > 1) {
            throw new ApplicationException("用户异常，存在多个账号,相关参数:" + refCode);
        }
        return list.get(0);
    }

    public static void main(String[] args) {
//        System.out.println("---");
//        String str = "0_1_2_3_4";
//        Integer s = str.;
//        System.out.println(s);
//        Integer e = str.lastIndexOf("_",2);
//        System.out.println(e);
//        System.out.println(str.substring(str.length()-s,str.length()-e));
    }


    /**
     * 通过Code或者Id查找
     *
     * @param refCode
     * @return
     */
    private Agent findByAgentCodeOrMobile(String name) {
        //通过code或者id查找
        Map<String, Object> map = new HashedMap();
        map.put("agentCode", name);
        map.put("mobile", name);
        List<Agent> list = agentDao.getOr(map);
        //验证登陆
        if (list == null || list.isEmpty()) {
            throw new ApplicationException("不存在此用户");
        } else if (list.size() > 1) {
            throw new ApplicationException("用户异常，存在多个账号,相关参数:" + name);
        }
        return list.get(0);
    }


    @Override
    @Transactional
    public void changePaw(Integer uid, String oldP, String newP, String conP) {
        Agent agent = get(uid);
        if (!oldP.trim().equals(agent.getPassword())) {
            throw new ApplicationException("原始密码不正确");
        }

        if (!newP.trim().equals(conP.trim())) {
            throw new ApplicationException("两次密码不匹配");
        }
        agent.setPassword(newP);
        agentDao.merge(agent);
    }

    /**
     * 商城是否可以返利
     *
     * @param agent
     * @return
     */
    public boolean canReward(Agent agent) {


        //如果被整顿了就跳过
        if (agent.getAgentStatus().equals(AgentStatus.REORGANIZE) || agent.getAgentStatus().equals(AgentStatus.NO_ACTIVE)) {
            return false;//整顿了的直接跳过
        }

        if (isVip(agent)) return true;

        return false;
    }


    //优惠商城能返利
    @Override
    public boolean canPmallReward(Agent agent) {
        //如果被整顿了就跳过
        if (agent.getAgentStatus().equals(AgentStatus.REORGANIZE)) {
            return false;//整顿了的直接跳过
        }
        return true;
    }

    //超级vip
    @Override
    public boolean isSuperVip(Agent agent) {
        GoodsAccount goodsAccount = goodsAccountHandler.getMainGoodsAccount(agent);
        if (goodsAccount != null) {
            String levelName = goodsAccount.getAgentLevel().getName();
            if (AgentLevelName.contains(levelName)) {
                if (!levelName.equals(AgentLevelName.分公司.toString())) {//不是品牌代言
                    return true;
                }
            }
        }
        return false;
    }

    //品牌代言
    @Override
    public boolean isVip(Agent agent) {
        GoodsAccount goodsAccount = goodsAccountHandler.getMainGoodsAccount(agent);
        if (goodsAccount != null) {
            if (goodsAccount.getAgentLevel().getName().contains(AgentLevelName.分公司.toString()) || goodsAccount.getAgentLevel().getName().contains(AgentLevelName.业务员.toString())) {
                return true;
            }
        }
        return false;
    }

    //新品牌代言
    @Override
    public boolean isNewVip(Agent agent) {
        Date startDate = DateUtil.formatStr("2017-06-26", "yyyy-MM-dd");
        if (isVip(agent) && agent.getJoinDate() != null && agent.getJoinDate().after(startDate)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public String getLevelName(Agent agent) {
        String levelName;
        if (agent.getAgentCode() != null && !agent.getAgentCode().equals("")) {
            levelName = goodsAccountHandler.getMainGoodsAccount(agent).getAgentLevel().getName();
        } else {
            levelName = "游客";
        }
        return levelName;
    }

    @Override
    @Transactional
    public void changeAgentLevel(Agent agent, Integer gid, Integer lid) {
        AgentLevel level = agentLevelHandler.get(lid);
        Goods goods = goodsHandler.get(gid);
        if (goods.isMainGoods()) {//如果是主打产品
            List<GoodsAccount> goodsAccounts = goodsAccountHandler.getGoodsAccounts(agent);
            for (GoodsAccount gc : goodsAccounts) {
                gc.setAgentLevel(level);
            }
            if (null == agent.getUpdateTime()) {
                agent.setUpdateTime(new Timestamp(agent.getJoinDate().getTime()));//存储原始加入日期
            }
            agent.setJoinDate(new Date());
        } else {
            GoodsAccount goodsAccount = goodsAccountHandler.getGoodsAccount(agent, goods);
            goodsAccount.setAgentLevel(level);
        }
        merge(agent);
    }

    @Override
    @Transactional
    public void changeStatus(Integer uid, Integer tid) {
        AgentStatus agentStatus = AgentStatus.stateOf(tid);
        Agent agent = get(uid);
        agent.setAgentStatus(agentStatus);
        merge(agent);
    }

    @Override
    public AgentLevel getLevel(Agent agent) {
        return goodsAccountHandler.getMainGoodsAccount(agent).getAgentLevel();
    }

    @Override
    public List<Agent> findAgents(SearchParameter<Agent> parameter) {
        return agentDao.findAgents(parameter);
    }

    @Override
    public List<Agent> getAllChildrens(String agentCode) {
        Agent agent = get("agentCode", agentCode);
        if (agentCode.equals("01")) {
            agent = muteUserHandler.getMuteUser();
        }
        List<Agent> allChildrens = new ArrayList<>();
        List<Agent> myChildrens = getList("parent.id", agent.getId());
        allChildrens.addAll(myChildrens);
        List<Agent> otherChildrens = new ArrayList<>();
        getChildrens(myChildrens, otherChildrens);
        allChildrens.addAll(otherChildrens);
        return allChildrens;
    }

    @Override
    public List<Object[]> countAgentsByLevel(List<Agent> agents) {
        List<Integer> uids = new ArrayList<>();
        for (Agent agent : agents) {
            uids.add(agent.getId());
        }
        return agentDao.countAgentsByLevel(uids);
    }

    //遍历 找出所有的代理
    public void getChildrens(List<Agent> agents, List<Agent> childrens) {
        List<Object> ids = new ArrayList<>();
        for (Agent agent : agents) {
            ids.add(agent.getId());
        }
        if (ids.size() == 0) return;
        List<Agent> temps = getListIn("parent.id", ids);
        childrens.addAll(agents);
        getChildrens(temps, childrens);
    }


    @Autowired
    private AgentDao agentDao;

    @Autowired
    @Qualifier("randomAgentCodeGenerator")
    private AgentCodeGenerator agentCodeGenerator;

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private TransferHandler transferHandler;

    @Autowired
    private NoticeHandler noticeHandler;

    public AgentDao getAgentDao() {
        return agentDao;
    }

    @Autowired
    public void setAgentDao(AgentDao agentDao) {
        this.agentDao = agentDao;
        super.setBaseDao(agentDao);
    }


    @Autowired
    private AgentLevelHandler agentLevelHandler;

    @Autowired
    private GoodsHandler goodsHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    @Autowired
    private SystemParameterHandler systemParameterHandler;
}
