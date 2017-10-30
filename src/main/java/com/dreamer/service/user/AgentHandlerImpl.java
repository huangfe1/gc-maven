package com.dreamer.service.user;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.authorization.Authorization;
import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.user.enums.UserStatus;
import com.dreamer.repository.account.GoodsAccountDAO;
import com.dreamer.repository.authorization.AuthorizationDAO;
import com.dreamer.repository.authorization.AuthorizationTypeDAO;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.repository.system.SystemParameterDAOInf;
import com.dreamer.repository.user.*;
import com.dreamer.service.user.agentCode.AgentCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.exception.DataDuplicateException;
import ps.mx.otter.exception.DataNotFoundException;
import ps.mx.otter.exception.ValidationException;
import ps.mx.otter.utils.date.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("agentHandler")
public class AgentHandlerImpl implements AgentHandler {


    @Override
    @Transactional
    public void setWxOpenIdTo(Agent agent, String openId) {
        // TODO Auto-generated method stub
//		agent.setWxOpenid(openId);
        agentDAO.update(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#selfRegister(com.dreamer.domain.user.Agent, java.lang.String)
     */
    @Override
    @Transactional
    public Agent selfRegister(Agent agent, String referrerAgentCode) {
    return null;
    }


    /**
     * 绑定代理
     *
     * @param agentCode代理的编号
     * @return
     */
    @Override
    @Transactional
    public Agent bindAgent(String agentCode, String password, Agent agent) {
        Agent ba = agentDAO.findByAgentCode(agentCode);
        if (null == ba) throw new ApplicationException("要绑定的代理" + agentCode + "不存在");
        if (!ba.getPassword().equals(password)) throw new ApplicationException("编号与密码不匹配");
        ba.setNickName(agent.getNickName());
        ba.setHeadimgurl(agent.getHeadimgurl());
        ba.setWxOpenid(agent.getWxOpenid());
        agentDAO.changeParentAgent(agent.getId(), ba.getId());//修改上级
        orderDAO.changeAgent(agent.getId(), ba.getId());//修改订单
        if (null != agent.getAccounts()) {
            Double sumB = ba.getAccounts().getVoucherBalance() + agent.getAccounts().getVoucherBalance();
            ba.getAccounts().setVoucherBalance(sumB);
        }
        Agent result = agentDAO.merge(ba);//获取之前的代理
        agent.setWxOpenid("");
        agentDAO.merge(agent);//删除之前的代理
        return result;
    }


    /**
     * 完善信息
     *
     * @param agent
     * @return
     */
    @Override
    @Transactional
    public Agent bulidAgent(Agent newAgent, Agent agent) {
        fieldsValiate(newAgent);
        if (null == agent.getParent()) {//上级为空
            if (Objects.isNull(newAgent.getParent()) || newAgent.getParent().getAgentCode().isEmpty()) {
                throw new ValidationException("推荐人不能为空");
            }
            Agent parent = agentDAO.findByAgentCode(newAgent.getParent().getAgentCode());
            if (parent == null) {
                throw new DataDuplicateException("推荐人编号对应的代理不存在！");
            }
            agent.setParent(parent);
        }
        if (agentDuplicate(newAgent)) {
            throw new DataDuplicateException("微信号" + newAgent.getWeixin() + "或者电话" + newAgent.getMobile() + "已经被注册过");
        }
        agent.setMobile(newAgent.getMobile());
        agent.setRealName(newAgent.getRealName());
        agent.setWeixin(newAgent.getWeixin());
        agent.setIdCard(newAgent.getIdCard());
        buildAgent(agent);
        return agentDAO.merge(agent);
    }

//    /**
//     * 创建访客
//     *
//     * @param unionId
//     * @param openId
//     * @param nickName
//     * @param headImg
//     * @return
//     */
//    @Transactional
//    public Agent createVisitor(String unionId, String openId,  String refCode,String nickName, String headImg) {
//      return null;
//    }

//    @Transactional
//    @Override
//    public Agent bulidVisitor(String openId, String nickName, String headImg) {
//        List<Agent> agents = agentDAO.findByOpenid(openId);
//        Agent agent;
//        if(agents.size()>0){//存在有账号
//            agent=agents.get(0);
//            //只要有一个信息不符合
////            if(!nickName.equals(agent.getNickName())||!headImg.equals(agent.getHeadimgurl())){
//				agent.setNickName(nickName);
//				agent.setHeadimgurl(headImg);
//				agent = agentDAO.merge(agent);
////			}
//        }else {
//            agent = new Agent();
//			agent.setRealName(nickName);
//			agent.setWxOpenid(openId);
//			agent.setNickName(nickName);
//			agent.setAgentStatus(AgentStatus.ACTIVE);
//			agent.setUserStatus(UserStatus.NEW);
//			agent.setHeadimgurl(headImg);
//			agent.setJoinDate(new Date());
//            agent.generateAccounts();//生成库存
//			agent=agentDAO.merge(agent);
//        }
//
//        return agent;
//    }

    /* (non-Javadoc)
         * @see com.dreamer.service.user.AgentHandler#addAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent, java.lang.Integer[])
         */
    @Override
    @Transactional
    public void addAgent(User operator, Agent agent, Integer[] ids, Agent parent, Agent teqparent) {
        fieldsValiate(agent);
        if (agentDuplicate(agent)) {
            throw new DataDuplicateException("微信号+电话+身份证信息已经注册");
        }
        assembleAuthorization(operator, agent, ids);
        buildAgent(agent);
        agent.setParent(parent);
        agent.setUserStatus(UserStatus.NEW);
        agentDAO.save(agent);
    }

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
//		if(Objects.isNull(agent.getIdCard())||agent.getIdCard().isEmpty()){
//			throw new ValidationException("身份证号码不能为空");
//		}
    }

    private void buildAgent(Agent agent) {
        String agentCode = agentCodeGenerator.generateAgentCode();
        while (Objects.nonNull(agentDAO.findByAgentCode(agentCode))) {
            agentCode = agentCodeGenerator.generateAgentCode();
            //throw new DataDuplicateException("系统产生的代理编码已存在,请检查系统参数");
        }
        agent.setAgentCode(agentCode);
        agent.setUserStatus(UserStatus.NORMAL);
//		String exemptAudit=systemParameterDAO.getAvoidAudit();
//		if(exemptAudit.equals("1")){
//			agent.setUserStatus(UserStatus.NORMAL);
//		}else{
//			agent.setUserStatus(UserStatus.NEW);
//		}
        //没有密码设置密码
        if(agent.getPassword()==null||"".equals(agent.getPassword())){
            agent.setPassword(agent.defaultPassword());
        }
//        agent.generateGoodsAccount();
        generateMainGoodsAccount(agent);//主打产品授权与账户
        agent.setJoinDate(new Date());
        String subDomain = agentDomainGenerateStrategy.gererateSubDomain(agent);
        agent.setSubDomain(subDomain);
    }

    /**
     * 初始化主打产品授权
     */
    private void generateMainGoodsAccount(Agent agent) {
        List<AuthorizationType> types = authorizationTypeDAO.findMainGoodsAuthorizationType();
        types.forEach((t) -> {
            Authorization auth = agent.buildAuthorization(t);
            auth.setStatus(AgentStatus.ACTIVE);
            agent.addAuthorization(auth);
        });
        agent.generateGoodsAccount();//增加账户
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#agentDuplicate(com.dreamer.domain.user.Agent)
     */
    @Override
    public boolean agentDuplicate(Agent agent) {
//		Long count=agentDAO.countAgent(agent.getIdCard(), agent.getMobile(), agent.getWeixin());
        Long count = agentDAO.countAgent(agent.getMobile(), agent.getWeixin());
        return count > 0;
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#transfer(com.dreamer.domain.user.Agent, com.dreamer.domain.user.Agent, com.dreamer.domain.user.User)
     */
    @Override
    @Transactional
    public void transfer(Agent fromAgent, Agent toAgent, User operator) {
        List<Agent> agents = agentDAO.findChildren(fromAgent);
        agents.forEach(a -> {
            a.setParent(toAgent);
        });
        agentDAO.merge(toAgent);
    }


    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#removeAuthorization(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent, com.dreamer.domain.authorization.Authorization)
     */
    @Override
    @Transactional
    public void removeAuthorization(User operator, Agent agent, Authorization authorization) {
        if (agent.alreadyAuthorizated(authorization)) {
            agent.removeAuthorization(authorization);
        }
        agentDAO.merge(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#updateAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent, java.lang.Integer[])
     */
    @Override
    @Transactional
    public void updateAgent(User operator, Agent agent, Integer[] ids, Agent parent, Agent teqparent) {
        assembleAuthorization(operator, agent, ids);
        agent.setParent(parent);
        agent.generateGoodsAccount();
        String subDomain = agentDomainGenerateStrategy.gererateSubDomain(agent);
        agent.setSubDomain(subDomain);
        agentDAO.merge(agent);
    }

    @Override
    @Transactional
    public Agent mergeAgent(Agent agent) {
        return agentDAO.merge(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#auditAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent, java.lang.Integer[])
     */
    @Override
    @Transactional
    public void auditAgent(User operator, Agent agent, Integer[] ids) {
        agent.passAudit();
        assembleAuthorization(operator, agent, ids);
        agent.generateGoodsAccount();
        agentDAO.merge(agent);
    }

    private void assembleAuthorization(User operator, Agent agent, Integer[] ids) {
        if (ids != null && ids.length > 0) {
            List<AuthorizationType> types = authorizationTypeDAO.findByIds(ids);
            operator.addAuthorizationToAgent(agent, types);
        }
    }


    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#activeAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent)
     */
    @Override
    @Transactional
    public void activeAgent(User operator, Agent agent) {
        agent.activeAll();
        agentDAO.merge(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#activeSingleAuthorization(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent, com.dreamer.domain.authorization.Authorization)
     */
    @Override
    @Transactional
    public void activeSingleAuthorization(User operator, Agent agent, Authorization auth) {
        auth.active();
        agentDAO.merge(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#georganizeAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent)
     */
    @Override
    @Transactional
    public void georganizeAgent(User operator, Agent agent) {
        agent.reorganizeAll();
        agentDAO.merge(agent);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#removeAgent(com.dreamer.domain.user.User, com.dreamer.domain.user.Agent)
     */
    @Override
    @Transactional
    public void removeAgent(User operator, Agent agent) {
        agentDAO.delete(agent);
    }

    @Override
    @Transactional
    public void removeVoucher(User operator, VoucherTransfer voucherTransfer) {
        voucherTransferDAO.delete(voucherTransfer);
    }

    /* (non-Javadoc)
     * @see com.dreamer.service.user.AgentHandler#transferPoints(com.dreamer.domain.user.User, com.dreamer.domain.user.PointsTransfer, java.lang.String, java.lang.String, java.lang.Double)
     */
    @Override
    @Transactional
    public void transferPoints(User operator, PointsTransfer transfer, String toAgentCode, String toAgentName, Double transferPoints) {
        Agent toAgent = agentDAO.findByAgentCode(toAgentCode);
        Agent fromAgent = (Agent) transfer.getUserByFromAgent();
        if (Objects.isNull(toAgent)) {
            throw new DataNotFoundException("代理编号对应的代理不存在");
        }
        if (!Objects.equals(toAgent.getRealName(), toAgentName)) {
            throw new DataNotFoundException("代理姓名错误");
        }
        transfer.transferPoints(fromAgent, toAgent, transferPoints);
        pointsTransferDAO.save(transfer);
    }

//    @Transactional
//    @Override
//    public void transferVoucher(User operator, VoucherTransfer transfer,
//                                String toAgentCode, String toAgentName, Double transferVoucher) {
//        // TODO Auto-generated method stub
//        Agent toAgent = agentDAO.findByAgentCode(toAgentCode);
//        Agent fromAgent = (Agent) transfer.getUserByFromAgent();
//        if (Objects.isNull(toAgent)) {
//            throw new DataNotFoundException("代理编号对应的代理不存在");
//        }
//        if (!Objects.equals(toAgent.getRealName(), toAgentName)) {
//            throw new DataNotFoundException("代理姓名错误");
//        }
//        transfer.transferVoucher(fromAgent, toAgent, transferVoucher);
//        voucherTransferDAO.merge(transfer);
//    }

//    @Transactional
//    @Override
//    public void transferPurchase(User operator, PurchaseTransfer transfer,
//                                 String toAgentCode, String toAgentName, Double transferPurchase) {
//        // TODO Auto-generated method stub
//        Agent toAgent = agentDAO.findByAgentCode(toAgentCode);
//        Agent fromAgent = (Agent) transfer.getUserByFromAgent();
//        if (Objects.isNull(toAgent)) {
//            throw new DataNotFoundException("代理编号对应的代理不存在");
//        }
//        if (!Objects.equals(toAgent.getRealName(), toAgentName)) {
//            throw new DataNotFoundException("代理姓名错误");
//        }
//        transfer.transferpurchase(fromAgent, toAgent, transferPurchase);
//        purchaseTransferDAO.merge(transfer);
//    }

    /**
     * 修改等级授权
     *
     * @param agent
     * @param goodsId
     * @param levelId
     */
    @Override
    @Transactional
    public void changePriceLevel(Agent agent, Integer goodsId, Integer levelId) {
        // TODO Auto-generated method stub
        GoodsAccount gac = agent.loadAccountForGoodsId(goodsId);
        AgentLevel level = agentLevelDAO.findById(levelId);
        gac.setAgentLevel(level);
        if (gac.isMainGoodsAccount()) {
            agent.getGoodsAccounts().stream().forEach(g -> {
                if (g.getAgentLevel().getGoodsType() == gac.getGoods().getGoodsType()) {//什么产品的主打产品,只影响对用的产品
                    g.setAgentLevel(level);
                }

            });
        }
        goodsAccountDAO.merge(gac);
    }

    @Override
    @Transactional
    public void addVoucher(Agent operator, VoucherTransfer transfer) {
        Agent user_temp = mutedUserDAO.findById(3);//公司
        transfer.commit(user_temp, operator);//提交订单
        voucherTransferDAO.save(transfer);//保存
    }


    @Override
    @Transactional
    public void addAdvance(User operator, AdvanceTransfer transfer) {
        Agent user_temp = mutedUserDAO.findById(3);//公司
        transfer.commit(user_temp, operator);//提交订单
        advanceTransferDAO.save(transfer);//保存
    }

    @Override
    @Transactional
    public void batchGenerateSubdomain() {
        // TODO Auto-generated method stub
        agentDAO.batchGenerateSubdomain();
    }

//    @Override
//    @Transactional
//    public void payForVoucher(String time, VoucherTransfer transfer) {
//        transfer.setTransferTime(DateUtil.string2date(time, "yyyyMMddHHmmss"));
//        transfer.setType(VoucherTransferType.PAY);
//        transfer.setRemark(VoucherTransferType.PAY.desc);
//        transfer.getUserByToAgent().getAccounts().increaseVoucher(transfer.getVoucher(), "在线充值,订单" + transfer.getOut_trade_no());
//        voucherTransferDAO.merge(transfer);//更新transfer
//    }

//    /**
//     * 奖金转换成预存款
//     *
//     * @param time
//     * @param transfer
//     */
//    @Override
//    @Transactional
//    public void payForAdvanceByVoucher(String time, AdvanceTransfer transfer) {
//        //用预存款支付
//        transfer.payForAdvanceByVoucher(time);
//        advanceTransferDAO.merge(transfer);//更新transfer
//    }
//
//    /**
//     * 充值预存款
//     *
//     * @param time
//     * @param transfer
//     */
//    @Override
//    @Transactional
//    public void payForAdvance(String time, AdvanceTransfer transfer) {
//        transfer.payForAdvance(time);
//        advanceTransferDAO.merge(transfer);//更新transfer
//    }


    /**
     * 充值预存款
     *
     * @param time
     * @param transfer
     */
    @Transactional
    public void mergeAdvance(AdvanceTransfer transfer) {
        advanceTransferDAO.merge(transfer);//更新transfer
    }

    /**
     * 充值预存款
     *
     * @param time
     * @param transfer
     */
    @Transactional
    public void mergeVoucher(VoucherTransfer transfer) {
        voucherTransferDAO.merge(transfer);//更新transfer
    }

    @Override
    public Agent findAgentById(Integer id) {
        return agentDAO.findById(id);
    }

    @Autowired
    private GoodsAccountDAO goodsAccountDAO;
    @Autowired
    private AgentLevelDAO agentLevelDAO;

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private AgentDAO agentDAO;
    @Autowired
    private MutedUserDAO mutedUserDAO;
    @Autowired
    @Qualifier("randomAgentCodeGenerator")
    private AgentCodeGenerator agentCodeGenerator;
    @Autowired
    private AuthorizationTypeDAO authorizationTypeDAO;
    @Autowired
    private AuthorizationDAO authorizationDAO;
    @Autowired
    private PointsTransferDAO pointsTransferDAO;
    @Autowired
    private VoucherTransferDAO voucherTransferDAO;
    @Autowired
    private PurchaseTransferDAO purchaseTransferDAO;
    @Autowired
    private AdvanceTransferDAO advanceTransferDAO;

    @Autowired
    private com.dreamer.service.mobile.AgentHandler agentHandler;

    @Autowired
    private com.dreamer.service.mobile.AgentLevelHandler levelHandler;

    @Autowired
    private SystemParameterDAOInf systemParameterDAO;
    @Autowired
    private AgentDomainGenerateStrategy agentDomainGenerateStrategy;
}
