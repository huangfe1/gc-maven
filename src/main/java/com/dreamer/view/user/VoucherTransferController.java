package com.dreamer.view.user;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.VoucherTransfer;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.repository.pmall.order.OrderItemDAO;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.repository.user.MutedUserDAO;
import com.dreamer.repository.user.UserDAO;
import com.dreamer.repository.user.VoucherTransferDAO;
import com.dreamer.service.pmall.order.OrderPayHandler;
import com.dreamer.service.user.AgentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/voucher")
public class VoucherTransferController {


	@RequestMapping(value = "/transfer.json", method = RequestMethod.POST)
	public Message transfer(
			@ModelAttribute("parameter") VoucherTransfer transfer,
			@RequestParam("realName") String realName,
			@RequestParam("agentCode") String agentCode,
			@RequestParam(value = "agentFromrealName",required = false) String agentFromrealName,
			@RequestParam(value = "agentFromCode",required = false) String agentFromCode,
			Model model,
			HttpServletRequest request) {
		try {
			User user = (User) WebUtil.getCurrentUser(request);
			transfer.setOperator(user.getRealName());//设置管理员
			if (user.isAgent()) {
				Agent agent = agentDAO.findById(user.getId());
				transfer.setUserByFromAgent(agent);
//				agentHandler.transferVoucher(user, transfer, agentCode,
//						realName, transfer.getVoucher());
			}
			if (user.isAdmin()) {
//				Agent agent = mutedUserDAO.loadFirstOne();
//				transfer.setUserByFromAgent(agent);
				//这里修改一下,转出人由管理员自己设定
				Agent agent= (Agent) userDAO.findByAgentCode(agentFromCode.trim()).get(0);
				transfer.setUserByFromAgent(agent);
//				agentHandler.transferVoucher(user, transfer, agentCode,
//						realName, transfer.getVoucher());
			}
			return Message.createSuccessMessage();
		} catch (Exception exp) {
			exp.printStackTrace();
			return Message.createFailedMessage(exp.getMessage());
		}
	}

	@RequestMapping(value = "/remove.json")
	public Message remove(
			@ModelAttribute("parameter") VoucherTransfer voucherTransfer,HttpServletRequest request) {
		try{
			User user=(User)WebUtil.getCurrentUser(request);
			if(user.isAdmin()){
				agentHandler.removeVoucher(user, voucherTransfer);
			}else if(user.isAgent()){
				return Message.createFailedMessage("本操作仅限于管理员权限");
			}
			return Message.createSuccessMessage();
		}catch(Exception exp){
			exp.printStackTrace();
			return Message.createFailedMessage(exp.getMessage());
		}
	}




	@ModelAttribute("parameter")
	public VoucherTransfer preprocess(@RequestParam("id") Optional<Integer> id) {
		VoucherTransfer parameter;
		if (id.isPresent()) {
			parameter = voucherTransferDAO.findById(id.get());
		} else {
			parameter = new VoucherTransfer();
		}
		return parameter;
	}
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderItemDAO orderItem;
	@Autowired
	private AgentDAO agentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private VoucherTransferDAO voucherTransferDAO;
	@Autowired
	private AgentHandler agentHandler;
    @Autowired
    private OrderPayHandler orderPayHandler;
	@Autowired
	private MutedUserDAO mutedUserDAO;
}
