package com.dreamer.view.user;

import com.dreamer.domain.user.Agent;
import com.dreamer.service.mobile.AgentHandler;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.img.QRCodeGenerater;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/dmz/agent")
public class AgentDMZQueryController {

    private static final QRCodeGenerater QRCODE = QRCodeGenerater.getInstance();


    @RequestMapping(value = {"/index.html", "/search.html"}, method = RequestMethod.GET)
    public String index(
            @ModelAttribute("parameter") SearchParameter<Agent> parameter, Model model) {
        try {
            Map map = new HashedMap();
            map.put("agentCode",parameter.getEntity().getAgentCode());
            map.put("mobile",parameter.getEntity().getAgentCode());
            map.put("realName",parameter.getEntity().getAgentCode());
            List<Agent> agents = agentHandler.getOr(map);
            if(agents!=null&&!agents.isEmpty()){
                model.addAttribute("agent", agents.get(0));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("代理查询失败", exp);
        }
        return "user/dmz/agent_search";
    }





    @RequestMapping(value = "/exists.json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> queryByAgentCode(@RequestParam("agentCode") String agentCode) {
        Agent agent = agentHandler.get("agentCode",agentCode.trim());
        if (Objects.nonNull(agent)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @ModelAttribute("parameter")
    public SearchParameter<Agent> preprocess(
            @RequestParam("id") Optional<Integer> id) {
        Agent agent ;
        if (id.isPresent()) {
            agent = agentHandler.get(id.get());
        } else {
            agent = new Agent();
        }
        return new SearchParameter<>(agent);
    }



    @Autowired
    private AgentHandler agentHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

}
