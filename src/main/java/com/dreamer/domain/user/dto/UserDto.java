package com.dreamer.domain.user.dto;

import com.dreamer.domain.user.Agent;

/**
 * Created by huangfei on 17/07/2017.
 */
public class UserDto {

    private Integer id;

    private String name;

    private String agentCode;

    private String headImgUrl;

    public UserDto() {
    }


    public UserDto(Agent agent) {
        this.id=agent.getId();
        this.name=agent.getRealName();
        this.agentCode=agent.getAgentCode();
        this.headImgUrl=agent.getHeadimgurl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
