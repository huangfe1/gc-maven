package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.AgentLevel;
import com.dreamer.repository.mobile.AgentLevelDao;
import com.dreamer.service.mobile.AgentLevelHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
@Service
public class AgentLevelHandlerImpl extends BaseHandlerImpl<AgentLevel> implements AgentLevelHandler {


    @Override
    public List<AgentLevel> findAllOrderByLevel() {

        return agentLevelDao.findAllOrderByLevel();
    }

    /**
     * 找出最低的级别的等级
     *
     * @return
     */
    @Override
    public AgentLevel findLowestLevel() {
        return agentLevelDao.findLowestLevel();
    }

    private AgentLevelDao agentLevelDao;

    public AgentLevelDao getAgentLevelDao() {
        return agentLevelDao;
    }

    @Autowired
    public void setAgentLevelDao(AgentLevelDao agentLevelDao) {
        this.agentLevelDao = agentLevelDao;
        super.setBaseDao(agentLevelDao);
    }
}
