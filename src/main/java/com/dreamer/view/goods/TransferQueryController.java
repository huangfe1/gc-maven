package com.dreamer.view.goods;

import com.dreamer.domain.mall.goods.GoodsTransferStatus;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.mall.transfer.TransferItem;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.MuteUserHandler;
import com.dreamer.service.mobile.TransferHandler;
import com.dreamer.util.ExcelFile;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transfer")
public class TransferQueryController {

//	@RequestMapping(value = { "/my.html", "/search.html" }, method = RequestMethod.GET)
//	public String index(
//			@ModelAttribute("parameter") SearchParameter<Goods> parameter,
//			@RequestParam(value = "from", required = false) Integer from,
//			HttpServletRequest request, Model model) {
//		try {
//			User user = (User) WebUtil.getCurrentUser(request);
//			if (user.isAdmin()) {
//				List<GoodsAccountDTO> dtos = new ArrayList<GoodsAccountDTO>();
//				List<Goods> goods = goodsDAO.searchEntityByPage(parameter,
//						null, (t) -> true);
//				WebUtil.turnPage(parameter, request);
//				goods.forEach((g) -> {
//					GoodsAccountDTO d = new GoodsAccountDTO();
//					d.setCurrentBalance(g.getCurrentBalance());
//					d.setCumulative(0);
//					d.setCurrentPoint(g.getCurrentPoint());
//					d.setStatus(AgentStatus.ACTIVE);
//					d.setPrice(0.0);
//					d.setGoods(g);
//					dtos.add(d);
//				});
//				model.addAttribute("goods", dtos);
//			} else {
//				Agent agent = agentDAO.findById(user.getId());
//				List<GoodsAccountDTO> dtos = new ArrayList<GoodsAccountDTO>();
//				Set<Authorization> auths = agent.getAuthorizations();
//				auths.forEach((g) -> {
//					Goods goods = g.getAuthorizedGoods();
//					String searchName = parameter.getEntity().getName();
//					if (searchName != null && !searchName.trim().isEmpty()) {
//						if (goods.getName().indexOf(searchName) < 0) {
//							return;
//						}
//					}
//					GoodsAccountDTO d = new GoodsAccountDTO();
//					GoodsAccount acs = agent.loadAccountForGoodsNotNull(goods);
//					if (acs != null) {
//						d.setCumulative(acs.getCumulative());
//						d.setCurrentBalance(acs.getCurrentBalance());
//						d.setCurrentPoint(acs.getCurrentPoint());
//						d.setPrice(acs.getPrice());
//					} else {
//						d.setCumulative(0);
//						d.setCurrentBalance(0);
//						d.setCurrentPoint(0D);
//						d.setPrice(0.0);
//					}
//					d.setGoods(g.getAuthorizedGoods());
//					d.setStatus(g.getStatus());
//					dtos.add(d);
//				});
//				model.addAttribute("goods", dtos);
//				model.addAttribute("from", user.getId());
//				model.addAttribute("teqVip",agent.isTeqVip());
//                System.out.println(agent.isTeqVip()+"-----");
//            }
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("可转货产品查询失败", exp);
//		}
//		return "/goods/transfer_index";
//	}



//    @RequestMapping(value = { "/my.html", "/search.html" }, method = RequestMethod.GET)
//    public String index(
//            HttpServletRequest request, Model model) {
//            User user = (User) WebUtil.getCurrentUser(request);
//            Agent agent =transferHandler.get(user.getId());
//            model.addAttribute("teqVip",agent.isTeqVip());
//        return "/goods/transfer_index";
//    }


/**
 * 转货操作的代码
 * @param parameter
 * @param request
 * @param model
 * @return
 */
	@RequestMapping(value = "/from.html", method = RequestMethod.GET)
	public String edit_enter(
			@ModelAttribute("transfer") SearchParameter<Transfer> parameter,
			HttpServletRequest request, Model model) {
		User user = (User) WebUtil.getCurrentUser(request);
		try {
			if (user.isAdmin()) {
			/*	Goods goods = parameter.getEntity();
				model.addAttribute("currentBalance", goods.getCurrentBalance());
				model.addAttribute("price", 0.0D);*/
			} else {
				if (user.isAgent()) {
//					Agent agent = agentDAO.findById(user.getId());
				/*	if (!agent.isActivedAuthorizedGoods(parameter.getEntity())) {
						throw new NotAuthorizationException("您对该产品没有授权");
					}
					if (!user.isTopAgent()) {
						Agent parent = (Agent) user.getParent();
						if (!parent.isActivedAuthorizedGoods(parameter
								.getEntity())) {
							throw new NotAuthorizationException(
									"您申请转货的产品对方未获得该产品授权");
						}
						GoodsAccount gac = parent.loadAccountForGoods(parameter
								.getEntity());
						if (gac == null) {
							throw new DataNotFoundException("对方产品账户不存在");
						}
						model.addAttribute("currentBalance",
								gac.getCurrentBalance());
					} else {
						model.addAttribute("currentBalance", parameter
								.getEntity().getCurrentBalance());
					}
					
					GoodsAccount gac = agent.loadAccountForGoods(parameter
							.getEntity());
					if (gac == null) {
						throw new DataNotFoundException("产品账户不存在");
					}*/
//					Accounts acs = agent.getAccounts();
//					model.addAttribute("voucher", acs.getVoucherBalance());
					/*model.addAttribute("price", gac.getPrice());*/
				}
			}
		} catch (Exception nep) {
			model.addAttribute("message", nep.getMessage());
			return "goods/transfer_403";
		}
		return "goods/transfer_from";

	}

    /**
     * 转货
     * @param parameter
     * @param back
     * @param request
     * @param model
     * @return
     */
	@RequestMapping(value = "/to.html", method = RequestMethod.GET)
	public String transferTo(@ModelAttribute("transfer") SearchParameter<Transfer> parameter,Integer toId,Model model,HttpServletRequest request) {
		try {

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
		} catch (Exception exp) {
			exp.printStackTrace();
			model.addAttribute("message", exp.getMessage());
			return "goods/transfer_403";
		}
		return "goods/transfer_to";

	}
	
	@RequestMapping(value = "/records.html", method = RequestMethod.GET)
	public String records(
			@ModelAttribute("transfer") SearchParameter<Transfer> parameter,
			@RequestParam(value="agent",required=false) Integer agentId,
			@RequestParam(required=false) Optional<String> applyAgent,
			HttpServletRequest request, Model model) {
		try {
			User user = (User) WebUtil.getCurrentUser(request);		
			if(applyAgent.isPresent()){
				model.addAttribute("applyAgent", applyAgent.get());
			}
			List<Transfer> ts=transferHandler.findRecords(parameter,user);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("trans", ts);
			model.addAttribute("from", user.getId());
		} catch (Exception exp) {
			exp.printStackTrace();
			model.addAttribute("message", exp.getMessage());
			return "goods/transfer_403";
		}
        model.addAttribute("parameter",parameter);
		return "goods/transfer_records";

	}

	/**
	 * 下载订单
	 * @param parameter
	 * @param agentId
	 * @param applyAgent
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recordsDownload.html")
	public void recordsDownload(
            @ModelAttribute("transfer") SearchParameter<Transfer> parameter,
            HttpServletResponse response,HttpServletRequest request) {
		try {
			User user = (User) WebUtil.getCurrentUser(request);
            parameter.setRowsPerPage(-1);
            List<Transfer> orders=transferHandler.findRecords(parameter,user);
			List<String> headers = new ArrayList<>();
            headers.add("转入方");
            headers.add("转出方");
            headers.add("申请时间");
            headers.add("使用奖金");
            headers.add("使用进货券");
            headers.add("状态");
            headers.add("备注");
            headers.add("类型");
            headers.add("数量");
            List<Map>   datas = new ArrayList<>();
            List<Map>   datas1 = new ArrayList<>();
            List<Map>   datas2 = new ArrayList<>();
            //统计货物总数
            Map<String,Integer> goodsMap = new HashMap<>();
            //统计金钱
            Double  vb=0.0 ;
            Double ab=0.0;
            Map m ;
            Transfer order;
            for(int i=0;i<orders.size();i++){
                order=orders.get(i);
                m=new HashedMap();
                m.put(0,order.getToAgent().getRealName()+order.getToAgent().getAgentCode());
                m.put(1,order.getFromAgent().getRealName()+order.getFromAgent().getAgentCode());
                m.put(2,order.getApplyTime());
                m.put(3,order.getVoucher());
                m.put(4,order.getPurchase());
                if(order.getStatus()== GoodsTransferStatus.CONFIRM){
                    vb+=order.getVoucher();
                    ab+=order.getPurchase();
                }
                m.put(5,order.getStatus().getDesc());
                m.put(6,order.getRemittance());
                m.put(7,"");
                StringBuffer stringBuffer=new StringBuffer();
                //统计金钱


                for(TransferItem item:order.getItems()){//遍历所有的item
                    String gn=item.getGoods().getName();
                    Integer gq=item.getQuantity();
                    if(order.getStatus()== GoodsTransferStatus.CONFIRM){
                        if(goodsMap.containsKey(gn)){
                            Integer oldG = goodsMap.get(gn);
                            goodsMap.put(gn,oldG+gq);
                        }else {
                            goodsMap.put(gn,gq);
                        }
                    }
                    stringBuffer.append(gn);
                    stringBuffer.append(gq);
                    stringBuffer.append("/");
                }
                m.put(8,stringBuffer.toString());
                datas.add(m);
            }

            List<String> headers1 = new ArrayList<>();
            headers1.add("货物名称");
            headers1.add("货物数量");

            List<String> headers2 = new ArrayList<>();
            headers2.add("奖金总数");
            headers2.add("进货券总数");

            List<String> ss=new ArrayList<>();
            ss.add("订单详情");
            ss.add("产品总数");
            ss.add("进账总数");


            List<List> hs=new ArrayList<>();
            hs.add(headers);
            hs.add(headers1);
            hs.add(headers2);
            goodsMap.keySet().forEach(gm->{
                Map mt = new HashMap();
                mt.put(0,gm);
                mt.put(1,goodsMap.get(gm));
                datas1.add(mt);
            });
            Map vm = new HashMap();
//            Map am = new HashMap();

            vm.put(0,vb);
            vm.put(1,ab);
//            am.put(2,ab);

            datas2.add(vm);
//            datas2.add(am);

            List<List<Map>> ds=new ArrayList<>();
            ds.add(datas);
            ds.add(datas1);
            ds.add(datas2);
//		ExcelFile.ExpExs("","特权商城订单",headers,datas,response);//创建表格并写入
            ExcelFile.ExpExs("",ss,hs,ds,response);//创建表格并写入

		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/confirm.html", method = RequestMethod.GET)
	public String confirm(
			@ModelAttribute("transfer") SearchParameter<Transfer> parameter,
			@RequestParam(required=false) Optional<String> applyAgent,
			HttpServletRequest request, Model model) {
		try {
			if(applyAgent.isPresent()){
				model.addAttribute("applyAgent", applyAgent.get());
			}
			User user = (User) WebUtil.getCurrentUser(request);
//			parameter.getEntity().setStatus(GoodsTransferStatus.NEW);
//			if(user.isAdmin()){
//				MutedUser mutedUser=(MutedUser)WebUtil.getSessionAttribute(request, Constant.MUTED_USER_KEY);
//				parameter.getEntity().setFromAgent(mutedUser);
//
//			}else{
//				Agent agent=agentDAO.findById(user.getId());
//				parameter.getEntity().setFromAgent(agent);
//				List<Transfer> ts=transferDAO.searchEntityByPage(parameter, null, null,null);
//				WebUtil.turnPage(parameter, request);
//				model.addAttribute("trans", ts);
//			}
		} catch (Exception exp) {
			exp.printStackTrace();
			model.addAttribute("message", exp.getMessage());
			return "goods/transfer_403";
		}
		return "goods/transfer_confirm";

	}
	
	/*@RequestMapping(value = "/detail.html", method = RequestMethod.GET)
	public String detail(@ModelAttribute("transfer") SearchParameter<Transfer> parameter,HttpServletRequest request,Model model){
		try{
			//model.addAttribute("items",parameter.getEntity().getItems().values().stream().collect(Collectors.toList()));
			Transfer transfer=parameter.getEntity();
			List<TransferApplySuccessDTO> dtos=transfer.getItems().values().stream().map(item->{
				TransferApplySuccessDTO dto=new TransferApplySuccessDTO();
				dto.setGoodsName(item.getGoodsName());
				dto.setPrice(item.getPrice());
				dto.setAmount(item.getAmount());
				dto.setQuantity(item.getQuantity());
				dto.setLevelName(item.getPriceLevelName());
				return dto;
			}).collect(Collectors.toList());
			model.addAttribute("items",dtos);
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return "goods/transfer_success";
	}*/
	
	@RequestMapping(value = {"/success.html","/detail.html"}, method = RequestMethod.GET)
	public String success(@ModelAttribute("transfer") SearchParameter<Transfer> parameter,HttpServletRequest request,Model model){
		try{
			Transfer transfer=parameter.getEntity();
			List<TransferApplySuccessDTO> dtos=transfer.getItems().stream().map(item->{
				TransferApplySuccessDTO dto=new TransferApplySuccessDTO();
				dto.setGoodsName(item.getGoods().getName());
				dto.setPrice(item.getPrice());
				dto.setAmount(item.getAmount());
				dto.setQuantity(item.getQuantity());
				dto.setLevelName(item.getPriceLevelName());
				return dto;
			}).collect(Collectors.toList());
			model.addAttribute("items",dtos);
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return "goods/transfer_success";
	}



	@RequestMapping(value = {"/calculate.html"}, method = RequestMethod.GET)
	public String caculate(HttpServletRequest request,Model model,String type){
//		try{
//			Transfer transfer=(Transfer)WebUtil.getSessionAttribute(request, "transfer_calculate");
//			List<TransferApplySuccessDTO> dtos=transfer.getItems().values().stream().map(item->{
//				TransferApplySuccessDTO dto=new TransferApplySuccessDTO();
//				dto.setGoodsName(item.getGoodsName());
//				dto.setPrice(item.getPrice());
//				dto.setAmount(item.getAmount());
//				dto.setQuantity(item.getQuantity());
//				dto.setLevelName(item.getPriceLevelName());
//				return dto;
//			}).collect(Collectors.toList());
//			model.addAttribute("items",dtos);
//			model.addAttribute("transfer_calculate", transfer);
//		}catch(Exception exp){
//			exp.printStackTrace();
//		}
//		if(type!=null&&type.equals("mall")){
//			return "goods/mall_transfer_calculate";
//		}
		return "goods/transfer_calculate";
	}




	
	/*@ModelAttribute("parameter")
	public SearchParameter<Goods> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<Goods> parameter = new SearchParameter<Goods>();
		Goods goods = null;
		if (id.isPresent()) {
			goods = (Goods) goodsDAO.findById(id.get());
		} else {
			goods = new Goods();
		}
		parameter.setEntity(goods);
		return parameter;
	}*/
	
	@ModelAttribute("transfer")
	public SearchParameter<Transfer> getTransfer(@RequestParam("id") Optional<Integer> id) {
		SearchParameter<Transfer> parameter = new SearchParameter<>();
		Transfer entity ;
		if (id.isPresent()) {
			entity =  transferHandler.get(id.get());
		} else {
			entity = new Transfer();
		}
		parameter.setEntity(entity);
		return parameter;
	}


	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private TransferHandler transferHandler;

	@Autowired
	private MuteUserHandler muteUserHandler;

	@Autowired
	private AgentHandler agentHandler;




}
