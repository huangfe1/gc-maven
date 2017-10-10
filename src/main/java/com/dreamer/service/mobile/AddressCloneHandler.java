package com.dreamer.service.mobile;

import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.AddressMy;

/**
 * Created by huangfei on 12/07/2017.
 */
public interface AddressCloneHandler extends BaseHandler<AddressClone> {


    AddressClone getFromAddressMy(AddressMy addressMy);

}
