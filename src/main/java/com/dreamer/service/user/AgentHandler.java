package com.dreamer.service.user;

import com.dreamer.domain.authorization.Authorization;
import com.dreamer.domain.user.*;

public interface AgentHandler {

	 Agent selfRegister(Agent agent, String referrerAgentCode);




	Agent bulidAgent(Agent currentAgent,Agent agent);

	Agent bindAgent(String agentCode,String password,Agent agent);

	void addAgent(User operator, Agent agent, Integer[] ids, Agent parent,Agent teqparent);

	boolean agentDuplicate(Agent agent);

	void transfer(Agent fromAgent, Agent toAgent, User operator);

	void removeAuthorization(User operator, Agent agent,
			Authorization authorization);

	void updateAgent(User operator, Agent agent, Integer[] ids, Agent parent,Agent teqparent);

	 Agent mergeAgent(Agent agent);

	 void auditAgent(User operator, Agent agent, Integer[] ids);

	 void activeAgent(User operator, Agent agent);
	
	 void setWxOpenIdTo(Agent agent,String openId);

	 void activeSingleAuthorization(User operator, Agent agent,
			Authorization auth);

	 void georganizeAgent(User operator, Agent agent);

	 void removeAgent(User operator, Agent agent);

	 void removeVoucher(User operator, VoucherTransfer voucherTransfer);

	 void transferPoints(User operator, PointsTransfer transfer,
			String toAgentCode, String toAgentName, Double transferPoints);
	
//	 void transferVoucher(User operator, VoucherTransfer transfer,
//			String toAgentCode, String toAgentName, Double transferVoucher);

//	 void transferPurchase(User operator, PurchaseTransfer transfer,
//										 String toAgentCode, String toAgentName, Double transferPurchase);



	 void addVoucher(Agent operator, VoucherTransfer transfer);



	 void addAdvance(User operator, AdvanceTransfer transfer);

//     void payForVoucher(String time,VoucherTransfer transfer);

//     void payForAdvanceByVoucher(String time,AdvanceTransfer transfer);
//
//     void payForAdvance(String time,AdvanceTransfer transfer);


	 void mergeAdvance(AdvanceTransfer transfer);

	 void mergeVoucher(VoucherTransfer transfer);



	 Agent findAgentById(Integer id);

     void changePriceLevel(Agent agent,Integer goodsId,Integer levelId);
	
	 void batchGenerateSubdomain();

}