package com.dreamer.view.user;

import com.dreamer.domain.user.AccountsTransfer;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.service.mobile.AccountsTransferHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.MuteUserHandler;
import com.dreamer.util.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by huangfei on 06/07/2017.
 */
@Controller
@RequestMapping("/accounts")
public class AccountsTransferController {


    @RequestMapping("/index.html")
    public String index(@ModelAttribute("parameter") SearchParameter<AccountsTransfer> parameter, HttpServletRequest request, Model model,Integer typeState) throws Exception {
        User user = (User) WebUtil.getCurrentUser(request);
        AccountsType accountsType = AccountsType.stateOf(typeState);
        parameter.getEntity().setType(accountsType);
        List<AccountsTransfer> transfers = accountsTransferHandler.findAccountsTransfer(parameter, user);
        WebUtil.turnPage(parameter, request);
        model.addAttribute("transfers", transfers);
        model.addAttribute("accountsTypes", AccountsType.values());
        return "/user/accounts_index";
    }


    @RequestMapping("/download.html")
    @ResponseBody
    public void download(@ModelAttribute("parameter") SearchParameter<AccountsTransfer> parameter, HttpServletRequest request, HttpServletResponse response, Integer typeState) throws Exception {
        User user = (User) WebUtil.getCurrentUser(request);
        AccountsType accountsType = AccountsType.stateOf(typeState);
        parameter.getEntity().setType(accountsType);
        parameter.setRowsPerPage(-1);
        List<AccountsTransfer> transfers = accountsTransferHandler.findAccountsTransfer(parameter, user);
//        WebUtil.turnPage(parameter, request);
//        model.addAttribute("transfers", transfers);
//        model.addAttribute("accountsTypes", AccountsType.values());
//        return "/user/accounts_index";
        List<String> headers = new ArrayList<>();
        headers.add("转让人");
        headers.add("接收人");
        headers.add("数量");
        headers.add("时间");
//        headers.add("转让时间");
        headers.add("状态");
        List<Map> datas = new ArrayList<>();
        for(AccountsTransfer transfer:transfers){
            Map m = new HashMap();
            m.put(0,transfer.getFromAgent().getRealName()+"--"+transfer.getFromAgent().getAgentCode());
            m.put(1,transfer.getToAgent().getRealName()+"--"+transfer.getToAgent().getAgentCode());
            m.put(2,transfer.getAmount());
            m.put(3,transfer.getUpdateTime());
//            m.put(4,transfer.getTransferTime());
            m.put(5,transfer.getStatus().getStateInfo());
            datas.add(m);
        }
        List<List> sh = new ArrayList<>();
        sh.add(headers);
        List<List<Map>> ds = new ArrayList<>();
        ds.add(datas);
        List<String> ss = new ArrayList<>();
        ss.add("转券详情");
        ExcelFile.ExpExs("", ss, sh, ds, response);
    }

    @RequestMapping("/transfer.html")
    public String to_transfer(Integer typeState,Integer toId,Model model,HttpServletRequest request) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent fromAgent;
        if(user.isAdmin()){
            fromAgent = muteUserHandler.getMuteUser();//公司账户
        }else {
            fromAgent = agentHandler.get(user.getId());
        }
        Agent toAgent = agentHandler.get(toId);
        model.addAttribute("fromAgent",fromAgent);
        model.addAttribute("toAgent",toAgent);
        AccountsType accountsType = AccountsType.stateOf(typeState);
        model.addAttribute("accountsType",accountsType);
        return "/user/accounts_transfer";
    }

    //后台转账业务
    @RequestMapping(value = "/transfer.json", method = RequestMethod.POST)
    @ResponseBody
    public Message transfer(HttpServletRequest request, String fromName, String fromCode, String toName, String toCode, Integer typeState, Double amount, String remark) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            Agent fromAgent;
            if (user.isAdmin()) {
                fromAgent = muteUserHandler.getMuteUser();
                if (!fromAgent.getAgentCode().equals(fromCode)) {
                    fromAgent = agentHandler.findByAgentCodeOrId(fromCode);
                    if (!fromAgent.getRealName().equals(fromName)) {
                        throw new ApplicationException("输入的转出人名字与编号不匹配！");
                    }
                }
            } else {//不是管理员 转出人就是自己
                fromAgent = agentHandler.get(user.getId());
            }
            Agent toAgent = agentHandler.findByAgentCodeOrId(toCode);
            if (!toAgent.getRealName().equals(toName)) {
                throw new ApplicationException("输入的接收人名字与编号不匹配！");
            }
            remark += "  操作员:"+user.getRealName();
            accountsTransferHandler.transferAccounts(fromAgent.getId(), toAgent.getId(), typeState, amount, remark);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
        return Message.createSuccessMessage();
    }

    @ModelAttribute("parameter")
    public SearchParameter<AccountsTransfer> preprocess(@RequestParam("id") Optional<Integer> id) {
        SearchParameter<AccountsTransfer> parameter = new SearchParameter<>();
        if (id.isPresent()) {
            parameter.setEntity(accountsTransferHandler.get(id.get()));
        } else {
            parameter.setEntity(new AccountsTransfer());
        }
        return parameter;
    }


    @Autowired
    private AccountsTransferHandler accountsTransferHandler;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

}
