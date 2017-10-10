package com.dreamer.view.user;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.User.UserBaseView;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.user.enums.UserStatus;
import com.dreamer.service.goods.AgentLevelTradingLimitedHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.view.goods.GoodsAccountDTO;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.DatatableDTO;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agent")
public class AgentQueryController {


	@RequestMapping(value = { "/index.html", "/search.html" }, method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter, Model model, HttpServletRequest request) {
		try {
            List<Agent> agents;
            User user = (User) WebUtil.getCurrentUser(request);
		    if(!user.isAdmin()){
                //只能查询自己的下面的代理
                Agent agent = agentHandler.get(user.getId());
                parameter.getEntity().setParent(agent);//上级设置为自己
            }else {
		        //获取上级编号
            }
            agents = agentHandler.findAgents(parameter);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("status", AgentStatus.values());
			model.addAttribute("agents", agents);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("代理查询失败", exp);
		}
		return "user/agent_index";
	}
	
	@RequestMapping(value = { "/accounts_detail.html" }, method = RequestMethod.GET)
	public String accountsDetail(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			Model model, HttpServletRequest request) {
		try {
			model.addAttribute("aTypes", AccountsType.values());
			model.addAttribute("agentStatus", AgentStatus.values());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("代理查询失败", exp);
		}
		return "user/accounts_detail";
	}
	
	@RequestMapping(value = { "/children.html" }, method = RequestMethod.GET)
	public String children(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			Model model, HttpServletRequest request) {
		try {
			List<Agent> children=agentHandler.findAgents(parameter);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("status", AgentStatus.values());
			model.addAttribute("agents", children);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("下级代理查询失败", exp);
		}
		return "user/agent_children";
	}

//	@RequestMapping(value = { "/teqchildren.html" }, method = RequestMethod.GET)
//	public String teqchildren(
//			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
//			Model model, HttpServletRequest request) {
//		try {
//			User user=(User)WebUtil.getCurrentUser(request);
//			Agent parent=null;
//			if(user.isAdmin()){
//				parent=(Agent)WebUtil.getSessionAttribute(request, Constant.MUTED_USER_KEY);
//			}else{
//				parent=(Agent)user;
//			}
//			List<Agent> children=agentDAO.searchChildrenByPage(parameter,parent,1);
//			WebUtil.turnPage(parameter, request);
//			model.addAttribute("status", AgentStatus.values());
//			model.addAttribute("agents", children);
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("下级代理查询失败", exp);
//		}
//		return "user/agent_teqchildren";
//	}

	@RequestMapping(value = { "/audit/index.html", "/audit/search.html" }, method = RequestMethod.GET)
	public String auditIndex(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			Model model, HttpServletRequest request) {
		try {
			parameter.getEntity().setUserStatus(UserStatus.NEW);
			List<Agent> agents = agentHandler.findAgents(parameter);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("status", AgentStatus.values());
			model.addAttribute("agents", agents);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("待审核代理查询失败", exp);
		}
		return "user/agentAudit_index";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String edit_enter(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request, Model model) {
		try {
			if (parameter.getEntity().getId() != null
					&& parameter.getEntity().getVersion() != null) {
				model.addAttribute("action", "修改");
			} else {
				model.addAttribute("action", "新增");
			}
			User user = (User) WebUtil.getCurrentUser(request);
//			List<AuthorizationType> authTypes = null;
//			if (user.isAdmin()) {
//				authTypes = authorizationTypeDAO.findAll();
//			} else if (user.isAgent()) {
//				authTypes = ((Agent) user).allActivedAuthorizationType();
//			}
//			model.addAttribute("types", authTypes);
			model.addAttribute("status", AgentStatus.values());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("进入代理编辑失败", exp);
		}
		return "user/agent_edit";
	}

	@RequestMapping(value = "/audit/edit.html", method = RequestMethod.GET)
	public String audit_enter(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request, Model model) {
		try {
			model.addAttribute("action", "审核");
			User user = (User) WebUtil.getCurrentUser(request);
//			List<AuthorizationType> authTypes = null;
//			if (user.isAdmin()) {
//				authTypes = authorizationTypeDAO.findAll();
//			} else if (user.isAgent()) {
//				authTypes = ((Agent) user).allActivedAuthorizationType();
//			}
//			model.addAttribute("types", authTypes);
			model.addAttribute("status", AgentStatus.values());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("进入代理审核失败", exp);
		}
		return "user/agent_audit";
	}

	@RequestMapping(value = "/transfer.html", method = RequestMethod.GET)
	public String agentTransfer(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request, Model model) {
		return "user/agent_transfer";
	}

	@RequestMapping(value = "/notice.html", method = RequestMethod.GET)
	public String agentNotice(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request, Model model) {
		return "user/agent_notice";
	}

	@RequestMapping(value = "/transfer/query.json", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(UserBaseView.class)
	public DatatableDTO<Agent> queryAgent(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request, Model model) {
		DatatableDTO<Agent> dto = new DatatableDTO<Agent>();
		try {
			parameter.getEntity().setUserStatus(UserStatus.NORMAL);
			List<Agent> agents = agentHandler.findAgents(parameter);
			WebUtil.turnPage(parameter, request);
			agents.remove(parameter.getEntity());
			dto.setData(agents);
			dto.setRecordsTotal(parameter.getTotalRows());
			dto.setRecordsFiltered(parameter.getTotalRows());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("代理转让查询出错", exp);
		}
		return dto;
	}

	@RequestMapping(value = "/query/newRegister.json", method = RequestMethod.GET)
	@ResponseBody
	public Long newRegister() {
	//TODO 统计
        Long l = 0l;
        return l ;
//	    return agentHandler.countNewer();
	}

	@RequestMapping(value = "/myAuth.html", method = RequestMethod.GET)
	public String agentAuthorizations(
			@ModelAttribute("parameter") SearchParameter<Agent> parameter,
			HttpServletRequest request,Model model) {
			User user = (User) WebUtil.getCurrentUser(request);
			Agent agent=agentHandler.get(user.getId());
			parameter.setEntity(agent);
		HashMap<Integer,GoodsAccount> levels=new HashMap<>();
		parameter.getEntity().getGoodsAccounts().forEach(acs->{
			levels.put(acs.getGoods().getId(), acs);
		});
		List<GoodsAccountDTO> gacs=parameter.getEntity().getGoodsAccounts().stream().map(gac->{
			GoodsAccountDTO dto=new GoodsAccountDTO();
			dto.setGoods(gac.getGoods());
			dto.setCurrentBalance(gac.getCurrentBalance());
			dto.setCumulative(gac.getCumulative());
			AgentLevel parentLevel=gac.getAgentLevel().getParent();
			
			if(Objects.isNull(parentLevel)){
				dto.setMaxTradingLimited(-1);
			}else{
                dto.setMaxTradingLimited(gac.getGoods().getPrice(parentLevel).getThreshold()-1);
			}
			dto.setLevelName(gac.getAgentLevel().getName());
			dto.setTradingCumulative(new Long(agentLevelTradingLimitedHandler.sumTradingCumulative(agent, gac.getGoods())));
			return dto;
		}).collect(Collectors.toList());
        model.addAttribute("levels", levels);
        model.addAttribute("gacs", gacs);
		return "user/authorization_my";
	}


	

	@ModelAttribute("parameter")
	public SearchParameter<Agent> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		Agent agent ;
		if (id.isPresent()) {
			agent =  agentHandler.get(id.get());
		} else {
			agent = new Agent();
		}
		return new SearchParameter<>(agent);
	}

	@Autowired
	private AgentHandler agentHandler;

	@Autowired
	private AgentLevelTradingLimitedHandler agentLevelTradingLimitedHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

}
