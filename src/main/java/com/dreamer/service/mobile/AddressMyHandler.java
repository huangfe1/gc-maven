package com.dreamer.service.mobile;

import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;

import java.util.List;

/**
 * Created by huangfei on 05/07/2017.
 */
public interface AddressMyHandler extends BaseHandler<AddressMy>  {

    List<AddressMy> findAddressByAgent(Agent agent, Agent toAgent);

    List<AddressMy> findAddressByAgent(Agent agent);
}
