package com.dreamer.view;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/count")
public class CountController {


    //统计产生的奖金  统计
    @RequestMapping("/count/voucher.html")
    public String countVoucher(){
        return "/count/voucher";
    }

}
