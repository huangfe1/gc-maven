package com.dreamer.view;

import com.dreamer.domain.user.Admin;
import com.dreamer.domain.user.User;
import com.dreamer.repository.goods.DeliveryNoteDAO;
import com.dreamer.repository.goods.TransferDAO;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.repository.user.AccountsDAO;
import com.dreamer.repository.user.AdminDAO;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.repository.user.MutedUserDAO;
import com.dreamer.service.mobile.CountHandler;
import com.dreamer.service.mobile.UserHandler;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.service.user.AgentHandler;
import com.dreamer.util.CheckMobile;
import com.dreamer.view.captcha.GeetestConfig;
import com.dreamer.view.captcha.GeetestLib;
import com.wxjssdk.JSAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.exception.CurrentUserInfoMissingException;
import ps.mx.otter.exception.ValidateCodeFailException;
import ps.mx.otter.utils.Constant;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class IndexController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @Autowired
    private WxConfigFactory wxConfigFactory;

    //引导登录  微信客户端跳转到wxLogin.html  验证openid
    @RequestMapping("/login.html")
    public String login(HttpServletRequest request, @RequestParam(required = false) String url) {
        if (url == null) {
            url = ServletUriComponentsBuilder.fromContextPath(request).path("/mobile/my.html").build().toString();
        }
        request.getSession().setAttribute("redirectUrl", url);
        String USER_AGENT = request.getHeader("USER-AGENT").toLowerCase();
        //如果是手机客户端
        if (CheckMobile.check(USER_AGENT)) {
            if (USER_AGENT.indexOf("micromessenger") > 0) {//微信客户端，自动登录
                String redirctUrl = ServletUriComponentsBuilder.fromContextPath(request).path("/mobile/wxLogin.html").build().toString();
                //获取用户信息的code
                String codeUrl = JSAPI.createGetCodeUrl(wxConfigFactory.getBaseConfig().getAppid(), redirctUrl, "snsapi_userinfo", null);
                return "redirect:" + codeUrl;
            }
            return "mobile/login";
        }
        return "login";
    }

    @RequestMapping("/admin/login.html")
    public String adminLogin(){
        return "login";
    }



    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody
    public Message loginAsJSON(
            @RequestParam("accountName") String name,
            @RequestParam("password") String pwd, HttpServletRequest request,HttpServletResponse response) {
        try {
            //captchaValidate(captcha,request);
            captchaValidate(request);//更换验证码
            User user = userHandler.login(name, pwd);
            recordLoginInfo(request,response, user);
            UriComponents ucb = ServletUriComponentsBuilder.fromContextPath(request).path("/index.html").build();
            response.setHeader("location",ucb.toUriString());
            return Message.createSuccessMessage();
        } catch (ApplicationException aep) {
            aep.printStackTrace();
            return Message.createFailedMessage(aep.getMessage());
        } catch (Exception exp) {
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }





    //首页 1-统计所有产生的奖金  2-还有多少奖金没有提现  3-提现了多少奖金
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(HttpSession session, HttpServletRequest request, Model model) {
        try {
            User user = (User) WebUtil.getCurrentUser(request);
            if(user.isAdmin()){//如果是管理员
                //一共产生了多少奖金
                model.addAttribute("voucherSum",countHandler.countVoucher());
                //提现了多少奖金
                model.addAttribute("withdrawSum",countHandler.countWithdraw());
                //还有多少奖金没有体现
                model.addAttribute("av",countHandler.countAgentsVoucher());
            }
            return "index";
        } catch (CurrentUserInfoMissingException umep) {
            session.invalidate();
            return "login";
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("进入Index 失败", exp);
            return "common/500";
        }
    }











//
//
//    @RequestMapping(value = "/agent/login.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<String> agentLoginAsJSON(
//            @RequestParam("accountName") String name,
//            //@RequestParam("captcha") String captcha,
//            @RequestParam("password") String pwd, Model model, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            //captchaValidate(captcha,request);
//
//            Agent user = userHandler.agentLogin(name, pwd);
//
////			Agent referrer=user.getReferrer();
//            Agent parent = user.getParent();
//            if (Objects.nonNull(parent)) {
//                model.addAttribute("agentCode", parent.getAgentCode());
//            }
//            recordLoginInfo(request, response, user);
////			UriComponents ucb = ServletUriComponentsBuilder
////					.fromContextPath(request).path("/dmz/vmall/index.html").build();
//            UriComponents ucb = ServletUriComponentsBuilder
//                    .fromContextPath(request).path("/dmz/pmall/index.html").build();
//            LOG.debug(ucb.toUri().toString());
//            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(ucb.toUri());
//            LoginHelper.recordAgentLoginInfo(request, user);
//            String json = JsonUtil.objectToJsonStr(user, new String[]{"id",
//                    "version", "weixin", "mobile", "idCard",
//                    "wxOpenid", "nickName", "password", "updateTime", "goodsAccounts", "referrer", "authorizations",
//                    "accounts", "operator", "userStatus", "parent"});
//            return new ResponseEntity<String>(json, headers, HttpStatus.OK);
//        } catch (ApplicationException aep) {
//            aep.printStackTrace();
//            return new ResponseEntity<String>(JsonUtil.objectToJsonStr(Message.createFailedMessage(
//                    aep.getMessage()), null), null, HttpStatus.UNAUTHORIZED);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            return new ResponseEntity<String>(JsonUtil.objectToJsonStr(Message.createFailedMessage(
//                    exp.getMessage()), null), null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    public void recordLoginInfo(HttpServletRequest request, HttpServletResponse response, User user) {
        WebUtil.addSessionAttribute(request, Constant.MUTED_USER_KEY, mutedUserDAO.loadFirstOne());
        LoginHelper.recordLoginInfo(request, response, user);
    }



    private void captchaValidate(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getCaptcha_id(), GeetestConfig.getPrivate_key());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }


        if (gtResult != 1) {
            throw new ValidateCodeFailException();
        }
    }

    @RequestMapping(value = {"/mallmenu.html"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String mallmenu() {
        return "mallmenu";
    }


    @RequestMapping(value = {"/menu.html"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String menu(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (user.isAdmin()) {
            Admin admin = adminDAO.findById(user.getId());
            model.addAttribute("topModules", admin.getTopModules());
            model.addAttribute("myModules", admin.getMyModules());
        }
        Integer agentId = null;
        if (user.isAgent()) {
            agentId = user.getId();
        }
        if (user.isAdmin()) {
            agentId = ((User) WebUtil.getSessionAttribute(request, Constant.MUTED_USER_KEY)).getId();
        }
//        Accounts acs = accountsDAO.findByAgentId(agentId);
//        model.addAttribute("accounts", acs);
        return "menu";
    }


    @RequestMapping(value = {"/dmz/subdomain.html"}, method = RequestMethod.GET)
    public void generateSubdomain(HttpServletRequest request, HttpServletResponse response) {
        try {
            agentHandler.batchGenerateSubdomain();
            response.getWriter().println("batch generate subdomain ok");
        } catch (Exception exp) {
            try {
                response.getWriter().println("failed:" + exp.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        WebUtil.logout(request);
        Cookie tokenCookie = new Cookie("token", "1");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath(request.getServletContext().getContextPath() + "/");
        response.addCookie(tokenCookie);
        return "login";
    }

    @RequestMapping(value = {"/agent/logout"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String agentLogout(HttpServletRequest request, HttpServletResponse response) {
        WebUtil.logout(request);
        Cookie tokenCookie = new Cookie("token", "1");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath(request.getServletContext().getContextPath() + "/");
        response.addCookie(tokenCookie);
        UriComponents ucb = ServletUriComponentsBuilder
                .fromContextPath(request).path("/vmall/uc/dmz/login.html").queryParam("ref", "01").build();
        return "redirect:" + ucb.toUriString();
    }

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private MutedUserDAO mutedUserDAO;

    @Autowired
    private AgentDAO agentDAO;

    @Autowired
    private TransferDAO transferDAO;

    @Autowired
    private DeliveryNoteDAO deliveryNoteDAO;


    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private AccountsDAO accountsDAO;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private CountHandler countHandler;



}
