package com.dreamer.view.user;

import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.MuteUserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.Constant;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public Message edit(@ModelAttribute("parameter") Agent parameter, Integer[] ids, @RequestParam(value = "parentAgentCode", required = false) String parentAgentCode, @RequestParam(value = "teqparentAgentCode", required = false) String teqparentAgentCode, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            if (user.isAdmin()) {
                agentHandler.addAgentByAdmin(parameter, parentAgentCode);
            }
            return Message.createSuccessMessage();
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("代理信息保存失败", exp);
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    private Agent getParentFromCode(String code, HttpServletRequest request) {
        Agent temAgent;
        User mutedUser = (User) WebUtil.getSessionAttribute(request, Constant.MUTED_USER_KEY);
        if (Objects.nonNull(code) && !code.isEmpty()) {
            temAgent = agentHandler.findByAgentCodeOrId(code);
        } else {
            temAgent = muteUserHandler.get(mutedUser.getId());
        }
        return temAgent;
    }


//    @RequestMapping(value = "/sendMessage.json", method = RequestMethod.POST)
//    private Message sendMessage(String title, String[] keywords, String template_id, String url, String remark) {
//        int index = 0;
//        List<Agent> agents = agentDAO.findHasOpenId();//找到有openID的小伙伴
//        for (Agent agent : agents) {
//            if (Objects.nonNull(agent.getWxOpenid())) {
//                if (notcie.sendMessage(agent.getWxOpenid(), url, template_id, title, keywords, remark)) {
//                    index++;
//                }
//            }
//        }
//        Message successMessage = Message.createSuccessMessage();
//        successMessage.setData("本次成功发送" + index);
//        return successMessage;
//
//    }


//    /**
//     * 用户审核
//     *
//     * @param parameter
//     * @param ids
//     * @param request
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/audit.json", method = RequestMethod.POST)
//    public Message audit(
//            @ModelAttribute("parameter") Agent parameter,
//            Integer[] ids,
//            HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                agentHandler.auditAgent(user, parameter, ids);
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("代理信息通过审核失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/active.json", method = RequestMethod.POST)
//    public Message active(
//            @ModelAttribute("parameter") Agent parameter,
//            Integer[] authedIds,
//            HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                agentHandler.activeAgent(user, parameter);
//            } else if (user.isAgent()) {
//                return Message.createFailedMessage("无激活操作权限");
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("代理激活失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/reorganize.json", method = RequestMethod.POST)
//    public Message reorganize(
//            @ModelAttribute("parameter") Agent parameter, HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                agentHandler.georganizeAgent(user, parameter);
//            } else if (user.isAgent()) {
//                return Message.createFailedMessage("本操作仅限于管理员权限");
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("代理信息保存失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/remove.json", method = RequestMethod.DELETE)
//    public Message remove(
//            @ModelAttribute("parameter") Agent parameter, HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                agentHandler.removeAgent(user, parameter);
//            } else if (user.isAgent()) {
//                return Message.createFailedMessage("本操作仅限于管理员权限");
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("代理信息保存失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/transfer.json", method = RequestMethod.POST)
//    public Message transfer(
//            @ModelAttribute("parameter") Agent parameter,
//            @RequestParam("toId") Integer toId, HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                Agent toAgent = agentDAO.findById(toId);
//                agentHandler.transfer(parameter, toAgent, user);
//            } else {
//                return Message.createFailedMessage("本操作仅限于管理员权限");
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("代理转让失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

//    @RequestMapping(value = "/auth/remove.json", method = RequestMethod.DELETE)
//    public Message removeAuthorization(
//            @ModelAttribute("parameter") Agent parameter,
//            Integer authId,
//            HttpServletRequest request,
//            Model model) {
//        try {
//            User user = (User) WebUtil.getCurrentUser(request);
//            if (user.isAdmin()) {
//                Authorization auth = authorizationDAO.findById(authId);
//                agentHandler.removeAuthorization(user, parameter, auth);
//            } else if (user.isAgent()) {
//            }
//            return Message.createSuccessMessage();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            LOG.error("取消代理授权失败", exp);
//            return Message.createFailedMessage(exp.getMessage());
//        }
//    }

    @RequestMapping(value = "/level/change.json", method = RequestMethod.POST)
    public Message changePriceLevel(@ModelAttribute("parameter") Agent parameter, Integer goodsId, Integer levelId, HttpServletRequest request) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            if (user.isAdmin()) {
                agentHandler.changeAgentLevel(parameter, goodsId, levelId);
            } else {
                return Message.createFailedMessage("非管理员角色操作");
            }
            return Message.createSuccessMessage();
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("取消代理授权失败", exp);
            return Message.createFailedMessage(exp.getMessage());
        }
    }


    @RequestMapping(value = "/changeStatus.json", method = RequestMethod.POST)
    public Message changeStatus(@ModelAttribute("parameter") Agent parameter, Integer tid, HttpServletRequest request) {
        try {
            agentHandler.changeStatus(parameter.getId(),tid);
            return Message.createSuccessMessage();
        } catch (Exception exp) {
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }

    @ModelAttribute("parameter")
    public Agent preprocess(
            @RequestParam("id") Optional<Integer> id) {
        Agent agent;
        if (id.isPresent()) {
            agent = agentHandler.get(id.get());
        } else {
            agent = Agent.build();
        }
        return agent;
    }

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

}
