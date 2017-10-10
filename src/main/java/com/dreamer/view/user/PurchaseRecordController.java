package com.dreamer.view.user;

import com.dreamer.domain.account.PurchaseRecord;
import com.dreamer.domain.user.User;
import com.dreamer.repository.account.PurchaseRecordDAO;
import com.dreamer.repository.user.MutedUserDAO;
import com.dreamer.util.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/purchase")
public class PurchaseRecordController {

	@RequestMapping(value = "/record.html", method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<PurchaseRecord> parameter,
			Model model, HttpServletRequest request) {
		try {
			List<PurchaseRecord> pts;
			User user = (User) WebUtil.getCurrentUser(request);
			if (user.isAdmin()) {
				pts = purchaseRecordDAO.searchEntityByPage(parameter);
			} else {
				pts = purchaseRecordDAO.searchEntityByPage(parameter, user);
			}
			WebUtil.turnPage(parameter, request);
			model.addAttribute("pts", pts);
			model.addAttribute("from", user.getId());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return "user/purchase_record";
	}

	@ResponseBody
	@RequestMapping(value = "/downRecord.html", method = RequestMethod.GET)
	public void download(
			@ModelAttribute("parameter") SearchParameter<PurchaseRecord> parameter,
			Model model, HttpServletResponse response) {
		try {
			List<PurchaseRecord> pts;
				pts = purchaseRecordDAO.downPurchaseRecord(parameter);
			Double  jin = 0.0;
			Double  chu = 0.0;

            List<Map>   datas1 = new ArrayList<>();
            List<Map>   datas2 = new ArrayList<>();

            List<String> headers1 = new ArrayList<>();
            headers1.add("姓名");
            headers1.add("支初/进账");
            headers1.add("详情");
            headers1.add("数量");
            headers1.add("变更后");
            headers1.add("时间");

            List<String> headers2 = new ArrayList<>();
            headers2.add("代理进账总数");
            headers2.add("代理支出总数");
            Map m =null;
			for(PurchaseRecord record : pts) {
			    m=new HashMap();
			    m.put(0,record.getAgent().getRealName()+record.getAgent().getAgentCode());
			    m.put(1,record.getType());
			    m.put(2,record.getMore());
			    m.put(3,record.getPurchase());
			    m.put(4,record.getPurchase_now());
			    m.put(4,record.getUpdateTime());
                datas1.add(m);
                if (record.getType().equals(0)) {
                    BigDecimal b1 = new BigDecimal(chu.toString());
                    BigDecimal b2 = new BigDecimal(record.getPurchase().toString());
                    chu=new Double(b1.subtract(b2).doubleValue());
                } else {
                    BigDecimal b1 = new BigDecimal(jin.toString());
                    BigDecimal b2 = new BigDecimal(record.getPurchase().toString());
                    jin=new Double(b1.add(b2).doubleValue());
                }
            }

            Map m1 = new HashMap();
			m1.put(0,jin);
			m1.put(1,chu);
			datas2.add(m1);

            List<String> ss=new ArrayList<>();
            ss.add("进货券详情");
            ss.add("金额总数");


            List<List> hs=new ArrayList<>();
            hs.add(headers1);
            hs.add(headers2);
            List<List<Map>> ds=new ArrayList<>();
            ds.add(datas1);
            ds.add(datas2);

            ExcelFile.ExpExs("",ss,hs,ds,response);//创建表格并写入


		} catch (Exception exp) {
			exp.printStackTrace();
		}
//		return "user/voucher_record";
	}
	
	@ModelAttribute("parameter")
	public SearchParameter<PurchaseRecord> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<PurchaseRecord> parameter = new SearchParameter();
		if (id.isPresent()) {
			//parameter.setEntity(voucherRecordDAO.findById(id.get()));
		} else {
			parameter.setEntity(new PurchaseRecord());
		}
		return parameter;
	}
	

	@Autowired
	private MutedUserDAO mutedUserDAO;
	@Autowired
	private PurchaseRecordDAO purchaseRecordDAO;
}
