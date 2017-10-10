package com.hfpay.domain;

/**
 * 统一下单返回的参数
 * Created by huangfei on 12/05/2017.
 * api详情请前往
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 */
public class UnifiedOrderResData {

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String prepay_id;

    private String code_url;//trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
