package com.dreamer.view.user;

import com.dreamer.domain.account.VoucherRecord;
import com.dreamer.domain.user.User;
import com.dreamer.repository.account.VoucherRecordDAO;
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
@RequestMapping("/voucher")
public class VoucherRecordController {

	@RequestMapping(value = "/record.html", method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<VoucherRecord> parameter,
			Model model, HttpServletRequest request) {
		try {
			List<VoucherRecord> pts;
			User user = (User) WebUtil.getCurrentUser(request);
			if (user.isAdmin()) {
				pts = voucherRecordDAO.searchEntityByPage(parameter);
			} else {
				pts = voucherRecordDAO.searchEntityByPage(parameter, user);
			}
			WebUtil.turnPage(parameter, request);
			model.addAttribute("pts", pts);
			model.addAttribute("from", user.getId());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return "user/voucher_record";
	}

	@ResponseBody
	@RequestMapping(value = "/downRecord.html", method = RequestMethod.GET)
	public void download(
			@ModelAttribute("parameter") SearchParameter<VoucherRecord> parameter,
			Model model, HttpServletResponse response) {
		try {
			List<VoucherRecord> pts;
				pts = voucherRecordDAO.downVoucherRecord(parameter);
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
			for(VoucherRecord voucherRecord : pts) {
			    m=new HashMap();
			    m.put(0,voucherRecord.getAgent().getRealName()+voucherRecord.getAgent().getAgentCode());
			    m.put(1,voucherRecord.getType());
			    m.put(2,voucherRecord.getMore());
			    m.put(3,voucherRecord.getVoucher());
			    m.put(4,voucherRecord.getVoucher_now());
			    m.put(5,voucherRecord.getUpdateTime());
                datas1.add(m);
                if (voucherRecord.getType().equals(0)) {
                    BigDecimal b1 = new BigDecimal(chu.toString());
                    BigDecimal b2 = new BigDecimal(voucherRecord.getVoucher().toString());
                    chu=new Double(b1.subtract(b2).doubleValue());
                } else {
                    BigDecimal b1 = new BigDecimal(jin.toString());
                    BigDecimal b2 = new BigDecimal(voucherRecord.getVoucher().toString());
                    jin=new Double(b1.add(b2).doubleValue());
                }
            }

            Map m1 = new HashMap();
			m1.put(0,jin);
			m1.put(1,chu);
			datas2.add(m1);

            List<String> ss=new ArrayList<>();
            ss.add("代金券详情");
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
	public SearchParameter<VoucherRecord> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<VoucherRecord> parameter = new SearchParameter<VoucherRecord>();
		if (id.isPresent()) {
			//parameter.setEntity(voucherRecordDAO.findById(id.get()));
		} else {
			parameter.setEntity(new VoucherRecord());
		}
		return parameter;
	}
	

	@Autowired
	private MutedUserDAO mutedUserDAO;
	@Autowired
	private VoucherRecordDAO voucherRecordDAO; 
}
