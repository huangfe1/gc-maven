package com.dreamer.view.system;

import com.dreamer.domain.system.SystemParameter;
import com.dreamer.repository.system.SystemParameterDAOInf;
import com.dreamer.service.system.SystemParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.message.Message;

import java.util.Optional;

@RestController
@RequestMapping("/system/param")
public class SystemParameterController {

	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	public Message edit(@ModelAttribute("parameter") SystemParameter param,@RequestParam(required = false) String detailImg) {

		//如果有图片 设置图片
		if(detailImg!=null&&!detailImg.equals("")){
			param.setParamValue(detailImg);
		}

		try {
			if (param.getVersion() == null) {
				LOG.debug("新增系统参数 {} - {}", param.getCode(), param.getName());
				systemParameterHandler.addParameter(param);
			} else {
				LOG.debug("更新系统参数 {} - {}", param.getCode(), param.getName());
				systemParameterHandler.updateParameter(param);
			}
			LOG.debug("系统参数 {} - {} 保存成功", param.getCode(), param.getName());
			return Message.createSuccessMessage();
		}catch(DataIntegrityViolationException dve){
			LOG.error("系统参数保存失败 {}", dve);
			return Message.createFailedMessage("编码已存在");
		} 
		catch (Exception exp) {
			LOG.error("系统参数保存失败 {}", exp);
			return Message.createFailedMessage(exp.getMessage());
		}
	}

	@ModelAttribute("parameter")
	public SystemParameter preprocess(@RequestParam("id") Optional<String> id) {
		SystemParameter param = null;
		if (id.isPresent() && !id.get().isEmpty()) {
			param = systemParameterDAO.findById(id.get());
		} else {
			param = new SystemParameter();
		}
		return param;
	}

	@Autowired
	private SystemParameterDAOInf systemParameterDAO;

	@Autowired
	private SystemParameterHandler systemParameterHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());
}
