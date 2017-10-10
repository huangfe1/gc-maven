package com.dreamer.service.mobile;

import com.dreamer.domain.user.AgentLevel;

import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
public interface AgentLevelHandler extends BaseHandler<AgentLevel> {
    //找出最低级别的等级
    AgentLevel findLowestLevel();

    List<AgentLevel> findAllOrderByLevel();


}
