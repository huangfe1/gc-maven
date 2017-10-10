package com.dreamer.service.mobile;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 02/07/2017.
 */
public interface AgentHandler extends BaseHandler<Agent> {


    /**
     * 登陆 手机号/编号
     *
     * @param name
     * @param paw
     * @return
     */
    Agent login(String name, String paw);

    //找出某个代理下面有UnionId的代理
    List<Agent> findByParentHasUnionId(Integer pid);

    //创建游客
    Agent createVisitor(String unionId, String openId, String nickName, String headerImg, String refCode);

    //注册或者完善信息
    Agent selfRegister(Agent agent, String refCode);

    Agent addAgentByAdmin(Agent agent,String refCode);


    boolean canReward(Agent agent);

    boolean canPmallReward(Agent agent);

    Agent findByAgentCodeOrId(String refCode);

    boolean isSuperVip(Agent agent);

    boolean isVip(Agent agent);

    boolean isNewVip(Agent agent);

    List<Agent> findAgents(SearchParameter<Agent> parameter);

    String getLevelName(Agent agent);

    void changeAgentLevel(Agent agent, Integer gid, Integer lid);

    void changeStatus(Integer uid,Integer tid);

    AgentLevel getLevel(Agent agent);

}
