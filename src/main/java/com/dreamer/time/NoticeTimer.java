package com.dreamer.time;

import com.dreamer.service.wxchat.Notcie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangfei on 16/7/19.
 */
public class NoticeTimer {


    @Autowired
    private Notcie notcie;

    public void voucherNotice() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notice();
            }
        }, 0, 5);
//        notice();
    }

    public void notice() {
        notcie.sendRecord();
    }

    public static void main(String[] args) {
        new NoticeTimer().voucherNotice();
    }


}
