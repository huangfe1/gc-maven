package com.dreamer.repository.user;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;
import java.util.function.Function;

public interface AgentDAO {

    // property constants
    String REAL_NAME = "realName";
    String LOGIN_NAME = "loginName";
    String MOBILE = "mobile";
    String WEIXIN = "weixin";
    String ID_CARD = "idCard";
    String AGENT_CODE = "agentCode";
    String PARENT = "parent";
    String REMITTANCE = "remittance";
    String AGENT_LEVEL = "referrer";
    String AGENT_STATUS = "agentStatus";
    String USER_STATUS = "userStatus";
    String PASSWORD = "password";
    String VERSION = "version";
    String OPERATOR = "operator";
    String WX_OPENID = "wxOpenid";
    String NICK_NAME = "nickName";
    String IDENTITY = "identity";


    Long countAgent(String mobile, String weixin);

    void batchGenerateSubdomain();

    List<Agent> findChildren(Agent parent);


    Long countNewer();

    List<Agent> searchEntityByPage(SearchParameter<Agent> p,
                                   Function<SearchParameter<Agent>, ? extends Object> getSQL,
                                   Function<Void, Boolean> getCacheQueries);

    List<Agent> searchChildrenByPage(SearchParameter<Agent> p,
                                     Agent parent, Integer type);

    void save(Agent transientInstance);

    void delete(Agent persistentInstance);

    Agent findById(java.lang.Integer id);


    List<Agent> findByExample(Agent instance);

    List<Agent> findByProperty(String propertyName, Object value);

    List<Agent> findByRealName(Object realName);

    List<Agent> findByLoginName(Object loginName);

    List<Agent> findByMobile(Object mobile);

    List<Agent> findByWeixin(Object weixin);

    List<Agent> findByIdCard(Object idCard);

    Agent findByAgentCode(Object agentCode);

    Agent findByAgentCodeOrId(Object agentCode);

    public Agent findByDmz(Object agentCode);

    public List<Agent> searchEntityByMutilValue(SearchParameter<Agent> p, String value);

    Agent findByAgentCodeAndName(String agentCode, String name);

    List<Agent> findByParent(Object parent);

    List<Agent> findByParentHasUnionId(Integer parent);

    List<Agent> findByRemittance(Object remittance);

    List<Agent> findByAgentLevel(Object agentLevel);

    List<Agent> findByAgentStatus(Object agentStatus);

    List<Agent> findByUserStatus(Object userStatus);

    List<Agent> findByPassword(Object password);

    List<Agent> findByVersion(Object version);

    List<Agent> findByOperator(Object operator);


    Agent findByWxUnionid(Object uid);

    Agent findByOpenId(Object uid);

    List<Agent> findByNickName(Object nickName);

    List<Agent> findByIdentity(Object identity);

    List<Agent> findAll();

    List<Agent> findHasOpenId();

    List<Object[]> findMarketByParentAgent(Integer parent, Integer level);

    List<Object[]> findMarketByParentAgent(Integer parent, AgentLevel level);
    List<Object[]> findMarket();

    Agent merge(Agent detachedInstance);

    void changeParentAgent(Integer oldId,Integer newId);

    void update(Agent detachedInstance);

    void attachDirty(Agent instance);

    void attachClean(Agent instance);

}