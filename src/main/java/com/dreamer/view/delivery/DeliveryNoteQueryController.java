package com.dreamer.view.delivery;

import com.dreamer.domain.mall.delivery.DeliveryItem;
import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.delivery.DeliveryStatus;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.DeliveryNoteHandler;
import com.dreamer.service.mobile.GoodsHandler;
import com.dreamer.service.mobile.MuteUserHandler;
import com.dreamer.util.ExcelFile;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryNoteQueryController {

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletRequest request, Model model) {
		try {

			User user=(User)WebUtil.getCurrentUser(request);
            List<DeliveryNote> dls= deliveryNoteHandler.findDeliveryNotes(parameter,user);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("dls", dls);
			model.addAttribute("status", DeliveryStatus.values());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("发货申请查询失败,",exp);
		}
		return "delivery/delivery_index";
	}


	@RequestMapping("/count.html")
	public String count(@RequestParam(required = false) String agentCode, Model model, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime){
		if (agentCode == null) return "/delivery/delivery_record_children";
		//找出他下面所有的代理  并且统计级别
		List<Agent> childrens = agentHandler.getAllChildrens(agentCode);
		//统计下级代理级别
		List<Object[]> objects = agentHandler.countAgentsByLevel(childrens);
		if (startTime == null || startTime.equals("")) {
			startTime = "2000-01-01";
			endTime = "2025-01-01";
		}
		List<DeliveryNote> notes = deliveryNoteHandler.findByChlidrens(childrens,startTime,endTime);
		//统计所有产品总数
        Map<String,Integer> gMap = new HashedMap();
        for(DeliveryNote note : notes){
            for(DeliveryItem item : note.getDeliveryItems()){
                String name = item.getGoods().getName();
                Integer quantity =item.getQuantity();
                if(gMap.containsKey(name)){
                    Integer tem = gMap.get(name);
                    gMap.put(name,tem+quantity);
                }else {
                    gMap.put(name,quantity);
                }
            }
        }
        model.addAttribute("gMap",gMap);//产品总数
        model.addAttribute("levels", objects);//级别
        model.addAttribute("agentCode", agentCode);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("notes",notes);
        return "/delivery/delivery_record_children";
	}



    //通过编号  业绩统计 下载
    @RequestMapping("/count/download.html")
    public void countDownload(@RequestParam(required = false) String agentCode, HttpServletResponse response, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        if (agentCode == null) return ;
        //找出他下面所有的代理  并且统计级别
        List<Agent> childrens = agentHandler.getAllChildrens(agentCode);
        //统计下级代理级别
        List<Object[]> objects = agentHandler.countAgentsByLevel(childrens);
        if (startTime == null || startTime.equals("")) {
            startTime = "2000-01-01";
            endTime = "2025-01-01";
        }
        List<DeliveryNote> notes = deliveryNoteHandler.findByChlidrens(childrens,startTime,endTime);
        List<Map> datas = new ArrayList<>();
        List<Map> datas1 = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<String> headers1 = new ArrayList<>();
        headers.add("单号");
        headers.add("分公司");
        headers.add("业务员");
        headers.add("时间");
        headers.add("产品");
        headers.add("金额");
        headers.add("状态");
        Map m;
        for (int i = 0; i < notes.size(); i++) {
            m = new HashMap();
            DeliveryNote record = notes.get(i);
            m.put(0,record.getLogisticsCode());
            m.put(1, record.getApplyAgent().getParent().getParent().getRealName());
            m.put(2, record.getApplyAgent().getParent().getRealName());
            m.put(3, record.getApplyTime());
            StringBuffer sb = new StringBuffer();
            for(DeliveryItem item : record.getDeliveryItems()){
                sb.append(item.getGoods().getName()).append("X").append(item.getQuantity());
            }
            m.put(4, sb.toString());
            m.put(5,record.getAmount());
            m.put(6,record.getStatus().getDesc());
            datas.add(m);
        }
        headers1.add("姓名");
        headers1.add("上级");
        headers1.add("上上级");
        headers1.add("电话");
        headers1.add("消费值");
        HashMap m1;
//        System.out.println(childrens.size());
        for (Agent agent : childrens) {
            m1 = new HashMap();
            m1.put(0, agent.getRealName() + "--" + agent.getAgentCode());
            if(agent.getParent()!=null){
                m1.put(1, agent.getParent().getRealName() + "--" + agent.getParent().getAgentCode());
            }else {
                m1.put(1,"");
            }

            if(agent.getParent()!=null&&agent.getParent().getParent()!=null){
                m1.put(2, agent.getParent().getRealName() + "--" + agent.getParent().getAgentCode());
            }else {
                m1.put(2,"");
            }

            m1.put(3, agent.getMobile());
            m1.put(4, agent.getAccounts().getBenefitPointsBalance());
            datas1.add(m1);
        }

        List<String> ss = new ArrayList<>();
        ss.add("发货详情");
        ss.add("代理详情");


        List<List> hs = new ArrayList<>();
        hs.add(headers);
        hs.add(headers1);

        List<List<Map>> ds = new ArrayList<>();
        ds.add(datas);
        ds.add(datas1);
//		ExcelFile.ExpExs("","特权商城订单",headers,datas,response);//创建表格并写入
        ExcelFile.ExpExs("", ss, hs, ds, response);//创建表格并写入
    }



    @RequestMapping(value = "/download.html")
    @ResponseBody
	public void download(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletResponse response,HttpServletRequest request) {
		User user =(User) WebUtil.getCurrentUser(request);
		parameter.setRowsPerPage(-1);
		List<DeliveryNote> orders=deliveryNoteHandler.findDeliveryNotes(parameter,user);
        Map<String,Integer> sum =new HashMap<>();
		List<String> headers = new ArrayList<>();
		headers.add("序号");
		headers.add("发货人");
		headers.add("订单日期");
		headers.add("收货人");
		headers.add("收货人电话");
		headers.add("收货地址");
		headers.add("商品名称");
		headers.add("单位");
		headers.add("数量");
		headers.add("快递单号");
		headers.add("发货情况");
		headers.add("防伪码");
		headers.add("备注");
		headers.add("物流费");
		headers.add("订单ID");
		headers.add("状态");
		List<Map>   datas = new ArrayList<>();
		Map m = null;
		DeliveryNote order=null;
		for(int i=0;i<orders.size();i++){
			order=orders.get(i);
            if(!order.getApplyAgent().getParent().getParent().getId().equals(user.getId())){
                continue;
            }
			m=new HashedMap();
			m.put(0,i);
			m.put(1,order.getApplyAgent().getRealName());
//			m.put(2,"高臣药业");
			m.put(2,order.getDeliveryTime());
			m.put(3,order.getAddress().getConsignee());
			m.put(4,order.getAddress().getMobile());
			m.put(5,order.getAddress().getProvince()+order.getAddress().getCity()+order.getAddress().getCounty()+order.getAddress().getAddress());//收货人地址
			StringBuffer stringBuffer=new StringBuffer();
			for(DeliveryItem item:order.getDeliveryItems()){//遍历所有的item
				stringBuffer.append(item.getGoods().getName());
				stringBuffer.append(item.getQuantity());
				stringBuffer.append(item.getGoods().getSpec()+"/");
			}
			m.put(6,stringBuffer.toString());
			m.put(7,"");
			m.put(8,order.getQuantity());
			m.put(9,"");
			if(order.getLogistics()!=null)	m.put(9,order.getLogistics());
			m.put(10,order.getStatus().getDesc());
			m.put(11,"");
			m.put(12,order.getRemark());
			m.put(13,order.getLogisticsFee());//物流费
			m.put(14,""+order.getId());//订单ID
//			if(i<results.size()){
//				m.put(28,""+results.get(i)[0]);//订单ID
//				m.put(29,""+results.get(i)[1]);//订单ID
//			}
			m.put(15,order.getStatus().getDesc());//订单ID
			m.put(16,"");//订单ID
			datas.add(m);
		}
        //总数表格
        List<String> sheaders=new ArrayList<>();
        sheaders.add("产品名字");
        sheaders.add("产品总数");
        Map ms=null;
        List<Map> sdatas=new ArrayList<>();
        for(String key:sum.keySet()){
            ms =new HashedMap();
            ms.put(0,key);
            ms.put(1,""+sum.get(key));
            sdatas.add(ms);
        }
        List<String> ss=new ArrayList<>();
        ss.add("订单详情");
        ss.add("订单总数");

        List<List> hs=new ArrayList<>();
        hs.add(headers);
        hs.add(sheaders);

        List<List<Map>> ds=new ArrayList<>();
        ds.add(datas);
        ds.add(sdatas);
//		ExcelFile.ExpExs("","特权商城订单",headers,datas,response);//创建表格并写入
		ExcelFile.ExpExs("",ss,hs,ds,response);//创建表格并写入
	}
	
	
//	/**
//	 * 确认自己下级从商城发起的发货请求
//	 * @param parameter
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/agent/confirm.html", method = RequestMethod.GET)
//	public String agentConfirm(
//			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
//			HttpServletRequest request, Model model) {
//		try {
//			User user=(User)WebUtil.getCurrentUser(request);
//			Agent agent=null;
//			if(user.isAdmin()){
//				agent=mutedUserDAO.findById(((User)WebUtil.getSessionAttribute(request, Constant.MUTED_USER_KEY)).getId());
//			}else{
//				agent=agentDAO.findById(user.getId());
//			}
//			parameter.getEntity().setParentByOperator(agent);
//			List<DeliveryNote> dls = deliveryNoteDAO.searchAgentConfirmDeliveryNoteByPage(
//					parameter, null, null);
//			WebUtil.turnPage(parameter, request);
//			model.addAttribute("dls", dls);
//			model.addAttribute("status",DeliveryStatus.values());
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("发货申请查询失败,",exp);
//		}
//		return "delivery/agent_confirm_index";
//	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String edit(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletRequest request, Model model) {
		try {
		} catch (Exception exp) {
			LOG.error("进入发货申请失败,",exp);
			exp.printStackTrace();
		}
		User user=(User)WebUtil.getCurrentUser(request);
		Agent agent=agentHandler.get(user.getId());
		model.addAttribute("user",agent);
		return "delivery/delivery_edit";
	}
	
	@RequestMapping(value = "/mobile.html", method = RequestMethod.GET)
	public String mobile(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletRequest request, Model model) {
		try {
			User user=(User)WebUtil.getCurrentUser(request);
			if(!user.isAgent()){
				return "common/403";
			}
		} catch (Exception exp) {
			LOG.error("进入发货管理失败,",exp);
			exp.printStackTrace();
		}
		return "delivery/delivery_mobile_index";
	}
	
//	@RequestMapping(value = "/success.html", method = RequestMethod.GET)
//	public String success(
//			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
//			HttpServletRequest request, Model model) {
//		try {
//			User user=(User)WebUtil.getCurrentUser(request);
//			Agent agent=agentDAO.findById(user.getId());
//			List<TransferApplySuccessDTO> dtos=parameter.getEntity().getDeliveryItems().stream().map(item->{
//				TransferApplySuccessDTO dto=new TransferApplySuccessDTO();
//				dto.setGoodsName(item.getGoods().getName());
//				Price price=agent.caculatePrice(item.getGoods(), item.getQuantity());
//				dto.setPrice(price.getPrice());
//				dto.setAmount(item.getQuantity()*price.getPrice());
//				dto.setQuantity(item.getQuantity());
//				dto.setLevelName(price.getAgentLevel().getName());
//				return dto;
//			}).collect(Collectors.toList());
//			Double amount=dtos.stream().mapToDouble(i->i.getAmount()).sum();
//			model.addAttribute("items", dtos);
//			model.addAttribute("amount", amount);
//            if(parameter.getEntity().getLogisticsFee()!=null)
//			model.addAttribute("logistfee", parameter.getEntity().getLogisticsFee());
//		} catch (Exception exp) {
//			LOG.error("进入发货申请成功清单失败,",exp);
//			exp.printStackTrace();
//		}
//		return "delivery/delivery_success";
//	}
	
	@RequestMapping(value = "/print.html", method = RequestMethod.GET)
	public String print(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletRequest request, Model model) {
		try {
		} catch (Exception exp) {
			LOG.error("进入发货申请失败,",exp);
			exp.printStackTrace();
		}
		return "delivery/delivery_print";
	}
	
//	@RequestMapping(value = "/confirm.html", method = RequestMethod.GET)
//	public String confirm(
//			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
//			HttpServletRequest request, Model model) {
//		try {
//			User user=(User)WebUtil.getCurrentUser(request);
//			if(!user.isAdmin() && !Objects.equals(user.getId(),
//					parameter.getEntity().getParentByOperator().getId())){
//				LOG.error("非管理员身份,无发货确认权限");
//				return "common/403";
//			}
//			if(parameter.getEntity().isMallApply()){
//				return "delivery/delivery_mall_confirm";
//			}
//		} catch (Exception exp) {
//			LOG.error("进入发货确认失败,",exp);
//			exp.printStackTrace();
//		}
//		return "delivery/delivery_confirm";
//	}
	
	@RequestMapping(value = "/delivery.html", method = RequestMethod.GET)
	public String delivery(
			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
			HttpServletRequest request, Model model) {
		try {
			User user=(User)WebUtil.getCurrentUser(request);
			if(!user.isAdmin()){
            if(!parameter.getEntity().getApplyAgent().getParent().getParent().getId().equals(user.getId())){
				LOG.error("无发货权限");
				return "common/403";
			}
			}
		} catch (Exception exp) {
			LOG.error("进入发货确认失败,",exp);
			exp.printStackTrace();
		}
		return "delivery/delivery";
	}

//	@RequestMapping(value = "/authGoods.html", method = RequestMethod.GET)
//	public String selectGoodsEnter(
//			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter, HttpServletRequest request, Model model) {
//		try {
//			//选择我有库存的货物
//
//		} catch (Exception exp) {
//			LOG.error("进入发货申请货物选择界面失败,",exp);
//			exp.printStackTrace();
//		}
//		return "delivery/delivery_authGoods";
//	}
	
//	@RequestMapping(value = "/authGoods.json", method = RequestMethod.GET)
//	@ResponseBody
//	public DatatableDTO<Goods> queryGoods(
//			@ModelAttribute("parameter") SearchParameter<DeliveryNote> parameter,
//			HttpServletRequest request, Model model) {
//		DatatableDTO<Goods> dto=new DatatableDTO<Goods>();
//		try {
//			User user=(User)WebUtil.getCurrentUser(request);
//			Agent agent=agentHandler.get(user.getId());
//			Set<GoodsAccount> goodsAccounts=agent.getGoodsAccounts();
//
//			List<Goods> page=goods.stream().skip(parameter.getStart()).limit(parameter.getLength()).collect(Collectors.toList());
//			dto.setData(page);
//			dto.setRecordsFiltered(goods.size());
//			dto.setRecordsTotal(goods.size());
//		} catch (Exception exp) {
//			LOG.error("进入发货申请货物选择界面失败,",exp);
//			exp.printStackTrace();
//		}
//		return dto;
//	}


    @RequestMapping(value="/upload.html",method=RequestMethod.GET)
    public String uploadShipping(){
        return "delivery/order_upload";
    }

	/**
	 * 获取所有需要发货的订单
	 */
	@RequestMapping(value = "/getOrders.html",method = RequestMethod.GET)
	public void getOrders(HttpServletResponse response,Integer limit,HttpServletRequest request){
	    User user = (User) WebUtil.getCurrentUser(request);
		List<DeliveryNote> orders=deliveryNoteHandler.findNotDelivery(limit);//获取没有发货的订单
		List<Object[]> results=deliveryNoteHandler.getOrdersItemCount(limit);
		for(DeliveryNote order:orders){
			order.setStatus(DeliveryStatus.DELIVERING);//正在出库
			deliveryNoteHandler.merge(order);
		}
		List<String> headers = new ArrayList<>();
		headers.add("序号");
		headers.add("发货人");
		headers.add("订单日期");
		headers.add("收货人");
		headers.add("收货人电话");
		headers.add("收货地址");
		headers.add("商品名称");
		headers.add("单位");
		headers.add("数量");
		headers.add("快递单号");
		headers.add("发货情况");
		headers.add("防伪码");
		headers.add("备注");
        headers.add("物流费");
		headers.add("订单ID");
		List<Map> datas = new ArrayList<>();
		Map m ;
		DeliveryNote order;
		for(int i=0;i<orders.size();i++){
		    if(!orders.get(i).getApplyAgent().getParent().getParent().getId().equals(user.getId())){
		        continue;
            }
			order=orders.get(i);
			m=new HashedMap();
			m.put(0,i);
			m.put(1,order.getApplyAgent().getRealName());
//			m.put(2,"高臣药业");
			m.put(2,order.getDeliveryTime());
			m.put(3,order.getAddress().getConsignee());
			m.put(4,order.getAddress().getMobile());
			m.put(5,order.getAddress().getProvince()+order.getAddress().getCity()+order.getAddress().getCounty()+order.getAddress().getAddress());//收货人地址
			StringBuffer stringBuffer=new StringBuffer();
			for(DeliveryItem item:order.getDeliveryItems()){//遍历所有的item
				stringBuffer.append(item.getGoods().getName());
				stringBuffer.append(item.getQuantity());
				stringBuffer.append(item.getGoods().getSpec()+"/");
			}
			m.put(6,stringBuffer.toString());
			m.put(7,"");
			m.put(8,order.getQuantity());
			if(order.getLogistics()!=null)	m.put(9,order.getLogistics());
			m.put(10,order.getStatus().getDesc());
			m.put(11,"");
			m.put(12,order.getRemark());
            m.put(13,order.getLogisticsFee());//物流费
			m.put(14,""+order.getId());//订单ID
//			if(i<results.size()){
//				m.put(28,""+results.get(i)[0]);//订单ID
//				m.put(29,""+results.get(i)[1]);//订单ID
//			}
			m.put(15,"");//订单ID
			datas.add(m);
		}
//		ExcelFile.ExpExs("","特权商城订单",headers,datas,response);//创建表格并写入

		//总数表格
		List<String> sheaders=new ArrayList<>();
		sheaders.add("产品名字");
		sheaders.add("产品总数");
		Map ms;
		List<Map> sdatas=new ArrayList<>();
		for(Object[] key:results){
			ms =new HashedMap();
//			System.out.println(key[0]);
//			System.out.println(key[1]);
			ms.put(0,key[0]);
			ms.put(1,""+key[1]);
			sdatas.add(ms);
		}
		List<String> ss=new ArrayList<>();
		ss.add("订单详情");
		ss.add("订单总数");

		List<List> hs=new ArrayList<>();
		hs.add(headers);
		hs.add(sheaders);

		List<List<Map>> ds=new ArrayList<>();
		ds.add(datas);
		ds.add(sdatas);
//		ExcelFile.ExpExs("","特权商城订单",headers,datas,response);//创建表格并写入
		ExcelFile.ExpExs("",ss,hs,ds,response);//创建表格并写入

//        model.addAttribute("order_size",orders.size());
//        return "/pmall/order/order_print";
	}



	@ModelAttribute("parameter")
	public SearchParameter<DeliveryNote> preprocess(@RequestParam("id")Optional<Integer> id) {
		SearchParameter<DeliveryNote> parameter = new SearchParameter<>();
		if (id.isPresent()) {
			parameter.setEntity(deliveryNoteHandler.get(id.get()));
		} else {
			parameter.setEntity(new DeliveryNote());
		}
		return parameter;
	}

	@Autowired
	DeliveryNoteHandler deliveryNoteHandler;

    @Autowired
    AgentHandler agentHandler;

    @Autowired
    MuteUserHandler muteUserHandler;

    @Autowired
    GoodsHandler goodsHandler;

	private final Logger LOG=LoggerFactory.getLogger(getClass());

}
