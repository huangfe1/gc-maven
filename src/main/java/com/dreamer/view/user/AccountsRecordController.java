package com.dreamer.view.user;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.service.mobile.AccountsRecordHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.util.ExcelFile;
import com.dreamer.util.PreciseComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by huangfei on 06/07/2017.
 */
@Controller
@RequestMapping("/accounts/record")
public class AccountsRecordController {

    @RequestMapping("/index.html")
    public String index(@ModelAttribute("parameter") SearchParameter<AccountsRecord> parameter, Integer typeState, HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            AccountsType accountsType = AccountsType.stateOf(typeState);
            parameter.getEntity().setAccountsType(accountsType);
            List<AccountsRecord> accountsRecords = accountsRecordHandler.findAccountsRecords(parameter, user);
            WebUtil.turnPage(parameter, request);
            model.addAttribute("records", accountsRecords);
            model.addAttribute("accountsTypes", AccountsType.values());
            return "/user/accounts_record";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "/mobile/error";
        }
    }


    //通过编号  业绩统计
    @RequestMapping("/count.html")
    public String countIndex(@RequestParam(required = false) String agentCode, Model model, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        if (agentCode == null) return "/user/accounts_record_children";
        //找出他下面所有的代理  并且统计级别
        List<Agent> childrens = agentHandler.getAllChildrens(agentCode);
        //统计下级代理级别
        List<Object[]> objects = agentHandler.countAgentsByLevel(childrens);
        if (startTime == null || startTime.equals("")) {
            startTime = "2000-01-01";
            endTime = "2025-01-01";
        }
        //找出所有下级代理的业绩
        List<AccountsRecord> accountsRecords = accountsRecordHandler.listByChlidrens(AccountsType.BENEFIT, childrens, startTime, endTime);
        Double sum = 0.0;
        for (AccountsRecord ac : accountsRecords) {
            if (ac.getAddSub() == 1) {
                sum = PreciseComputeUtil.add(sum, ac.getAmount());
            } else {
                sum = PreciseComputeUtil.sub(sum, ac.getAmount());
            }
        }
        model.addAttribute("levels", objects);//级别
        model.addAttribute("sum", sum);//总业绩
        model.addAttribute("records", accountsRecords);//业绩明细
        model.addAttribute("agentCode", agentCode);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        return "/user/accounts_record_children";
    }

    //通过编号  业绩统计 下载
    @RequestMapping("/count/download.html")
    public void countDownload(@RequestParam(required = false) String agentCode, HttpServletResponse response, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        if (agentCode == null) return;
        //找出他下面所有的代理  并且统计级别
        List<Agent> childrens = agentHandler.getAllChildrens(agentCode);
        if (startTime == null || startTime.equals("")) {
            startTime = "2000-01-01";
            endTime = "2025-01-01";
        }
        //找出所有下级代理的业绩
        List<AccountsRecord> accountsRecords = accountsRecordHandler.listByChlidrens(AccountsType.BENEFIT, childrens, startTime, endTime);
        List<Map> datas = new ArrayList<>();
        List<Map> datas1 = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<String> headers1 = new ArrayList<>();
        headers.add("名字");
        headers.add("业绩");
        headers.add("进出");
        headers.add("变更后");
        headers.add("时间");
        Map m;
        for (int i = 0; i < accountsRecords.size(); i++) {
            m = new HashMap();
            AccountsRecord record = accountsRecords.get(i);
            m.put(0, record.getAgent().getRealName() + "--" + record.getAgent().getAgentCode());
            m.put(1, record.getAmount());
            if (record.getAddSub() == 1) {
                m.put(2, "进货增加");
            } else {
                m.put(2, "退货减少");
            }
            m.put(3, record.getNowAmount());
            m.put(4, record.getUpdateTime());
            datas.add(m);
        }
        headers1.add("姓名");
        headers1.add("上级");
        headers1.add("电话");
        headers1.add("业绩");
        HashMap m1 = new HashMap();
        System.out.println(childrens.size());
        for (Agent agent : childrens) {
            m1.put(0, agent.getRealName() + "--" + agent.getAgentCode());
            m1.put(1, agent.getParent().getRealName() + "--" + agent.getParent().getAgentCode());
            m1.put(2, agent.getMobile());
            m1.put(3, agent.getAccounts().getBenefitPointsBalance());
            datas1.add(m1);
        }

        List<String> ss = new ArrayList<>();
        ss.add("业绩详情");
        ss.add("代理详情");


        List<List> hs = new ArrayList<>();
        hs.add(headers);
        hs.add(headers1);

        List<List<Map>> ds = new ArrayList<>();
        ds.add(datas);
        ds.add(datas1);
//		ExcelFile.ExpExs("","特权代理商城订单",headers,datas,response);//创建表格并写入
        ExcelFile.ExpExs("", ss, hs, ds, response);//创建表格并写入
    }


    @ModelAttribute("parameter")
    public SearchParameter<AccountsRecord> preprocess(@RequestParam("id") Optional<Integer> id) {
        SearchParameter<AccountsRecord> searchParameter = new SearchParameter<>();
        AccountsRecord parameter;
        if (id.isPresent()) {
            parameter = accountsRecordHandler.get(id.get());
        } else {
            parameter = new AccountsRecord();
        }
        searchParameter.setEntity(parameter);
        return searchParameter;
    }

    @Autowired
    private AccountsRecordHandler accountsRecordHandler;


    @Autowired
    private AgentHandler agentHandler;

}
