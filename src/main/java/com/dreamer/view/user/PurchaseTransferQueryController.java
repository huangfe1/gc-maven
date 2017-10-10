package com.dreamer.view.user;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.PurchaseTransfer;
import com.dreamer.domain.user.User;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.repository.user.MutedUserDAO;
import com.dreamer.repository.user.PurchaseTransferDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.DatatableDTO;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase")
public class PurchaseTransferQueryController {

	@RequestMapping(value = "/my.html", method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<PurchaseTransfer> parameter,
			Model model, HttpServletRequest request,String toAgent,String fromAgent) {
		try {
			if(toAgent!=null&&!toAgent.equals("")){
				Agent atmpt=agentDAO.findByAgentCode(toAgent);
				parameter.getEntity().setUserByToAgent(atmpt);//设置转出人
			}
			if(fromAgent!=null&&!fromAgent.equals("")){
				Agent from;
				if(fromAgent.equals("01")){
					 from = mutedUserDAO.loadFirstOne();
				}else {
					from = agentDAO.findByAgentCode(fromAgent);
				}
				parameter.getEntity().setUserByFromAgent(from);//设置转入人
			}

			List<PurchaseTransfer> pts;
			User user = (User) WebUtil.getCurrentUser(request);
			if (user.isAdmin()) {
				pts = purchaseTransferDAO.searchEntityByPage(parameter, null,
						null, null);
			} else {
				pts = purchaseTransferDAO.searchEntityByPage(parameter, null,
						null, user.getId());
			}
			WebUtil.turnPage(parameter, request);
			model.addAttribute("pts", pts);
			model.addAttribute("from", user.getId());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return "user/purchase_my";
	}


	
	

	@RequestMapping(value = "/transfer.html", method = RequestMethod.GET)
	public String transfer(
			@ModelAttribute("parameter") SearchParameter<PurchaseTransfer> parameter,
			Model model, HttpServletRequest request) {
		String url="user/purchase_transfer";
		try {
			User user = (User) WebUtil.getCurrentUser(request);
			Agent agent=null;
			if (user.isAgent()) {
				//return "common/403";
				agent = agentDAO.findById(user.getId());
			}else if(user.isAdmin()){
				agent=mutedUserDAO.loadFirstOne();
				url="user/purchase_admin_transfer";
			}
			parameter.getEntity().setUserByFromAgent(agent);
			model.addAttribute("fromAgent", agent);
			model.addAttribute("purchaseBalance", agent.getAccounts().getPurchaseBalance());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return url;
	}
	
	

	@RequestMapping(value = "/query.json", method = RequestMethod.GET)
	@ResponseBody
	public DatatableDTO<PurchaseTransfer> queryAsJson(
			@ModelAttribute("parameter") SearchParameter<PurchaseTransfer> parameter,
			Model model, HttpServletRequest request) {
		DatatableDTO<PurchaseTransfer> dts = new DatatableDTO<PurchaseTransfer>();
		try {
			List<PurchaseTransfer> pts = new ArrayList<PurchaseTransfer>();
			User user = (User) WebUtil.getCurrentUser(request);
			if (!user.isAdmin()) {
				pts = purchaseTransferDAO.searchEntityByPage(parameter, null,
						null, null);
			} else {
				pts = purchaseTransferDAO.searchEntityByPage(parameter, null,
						null, user.getId());
			}
			WebUtil.turnPage(parameter, request);
			dts.setData(pts);
			dts.setRecordsFiltered(pts.size());
			dts.setRecordsTotal(pts.size());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return dts;
	}

	@ModelAttribute("parameter")
	public SearchParameter<PurchaseTransfer> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<PurchaseTransfer> parameter = new SearchParameter<PurchaseTransfer>();
		if (id.isPresent()) {
			parameter.setEntity(purchaseTransferDAO.findById(id.get()));
		} else {
			parameter.setEntity(new PurchaseTransfer());
		}
		return parameter;
	}

	@Autowired
	private AgentDAO agentDAO;
	@Autowired
	private PurchaseTransferDAO purchaseTransferDAO;
	@Autowired
	private MutedUserDAO mutedUserDAO;
}
