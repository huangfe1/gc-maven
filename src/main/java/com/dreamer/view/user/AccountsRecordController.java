package com.dreamer.view.user;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.service.mobile.AccountsRecordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by huangfei on 06/07/2017.
 */
@Controller
@RequestMapping("/accounts/record")
public class AccountsRecordController {

    @RequestMapping("/index.html")
    public String index(@ModelAttribute("parameter") SearchParameter<AccountsRecord> parameter, Integer typeState, HttpServletRequest request, Model model){
       try {
           User user = (User) WebUtil.getCurrentUser(request);
           AccountsType accountsType = AccountsType.stateOf(typeState);
           parameter.getEntity().setAccountsType(accountsType);
           List<AccountsRecord> accountsRecords = accountsRecordHandler.findAccountsRecords(parameter,user);
           WebUtil.turnPage(parameter, request);
           model.addAttribute("records",accountsRecords);
           model.addAttribute("accountsTypes",AccountsType.values());
           return "/user/accounts_record";
       }catch (Exception e){
           e.printStackTrace();
           model.addAttribute("error",e.getMessage());
           return "/mobile/error";
       }
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

}
