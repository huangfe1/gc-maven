package com.dreamer.util;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevelName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 13/07/2017.
 */
public class RewardUtil {

    /**
     * 将返利模式解析出来
     * @param str
     * @return
     */
    public static Double[] getVsFromStr(String str) {
        String[] strs = str.split("_");
        Double[] vs = new Double[strs.length];
        for (int i = 0; i < strs.length; i++) {
            vs[i] = Double.valueOf(strs[i]);
        }
        return vs;
    }


    /**
     * 获取当前层 品牌代言的返利
     *
     * @param vs       返利值
     * @param index    第几层
     * @param tVipSize 特殊vip的个数
     * @param quantity 数量
     * @return
     */
    public static Double getVipVoucher( Double[] vs , int index, Integer quantity) {
        Integer tVipSize = AgentLevelName.values().length-1;
        int start = tVipSize + index;
        if (start < vs.length) {
            return PreciseComputeUtil.round(vs[start] * quantity);
        }
        return 0.0;
    }




}
