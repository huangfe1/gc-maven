package com.dreamer.service.mobile;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import org.springframework.web.multipart.MultipartFile;
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

    Agent findVip(Agent agent);//找出分公司

    //创建游客
    Agent createVisitor(String unionId, String openId, String nickName, String headerImg, String refCode);

    //注册或者完善信息
    Agent selfRegister(Agent agent, String refCode, MultipartFile file);


    //
    void updateBuyDate(Agent agent,Integer amount,Integer buyAmount);


    Agent addAgentByAdmin(Agent agent,String refCode);

    List<Agent> getAllChildrens(String agentCode);

    List<Object[]> countAgentsByLevel(List<Agent> agents);


    boolean canReward(Agent agent);

    void changePaw(Integer uid,String oldP,String newP,String conP);

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
