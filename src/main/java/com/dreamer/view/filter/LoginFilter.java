package com.dreamer.view.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
@Component("loginFilter")
public final class LoginFilter implements Filter {

    private String debug;
	private String dmz;
//	private final static String VMALL_LOGIN_PATH = "/vmall/uc/dmz/login.html";
	private final static String LOGIN_PATH = "/login.html";
	private final static String WX_LOGIN_PATH = "/wxLogin.html";
	private final static String LOGIN_PATH_JSON = "/login.json";

	private final static String LOGIN = "login";
	private final static String REGISTER = "register";

	private final static String DEBUG_PARAMETER_NAME = "debug";
	private final static String DMZ_PARAMETER_NAME = "dmz";
	private final static String DMZ = "/dmz/";
	private final static String XHR_HEADER="XMLHttpRequest";
	private final static String XHR_HEADER_NAME="X-Requested-With";

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String fullRequestPath = WebUtil.getRequstPath(request);


//		没有的登录，积分商城微信登录，特殊处理
//        if (!WebUtil.isLogin(request)&&fullRequestPath.indexOf("/dmz/pmall/")>-1){
//            String ua = request.getHeader("user-agent").toLowerCase();
//            if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
//                String state = request.getServletPath()+"?"+request.getQueryString();
//                state = state.replace("&","-");
//                UriComponents uri = ServletUriComponentsBuilder.fromContextPath(request).path("/agent/WxLogin").build();
//                String redirectUrl = uri.toUriString();
//                String getCodeUrl = getOpenIdHandler.createGetUserInfoCodeCallbackUrl(wxConfig,redirectUrl,state);
//                response.sendRedirect(getCodeUrl);
//            }
//        }
//        if (!WebUtil.isLogin(request)&&fullRequestPath.indexOf("/pmall/")>-1){
//                String state = fullRequestPath;
//                state = state.replace("&&","--");
//                UriComponents uri = ServletUriComponentsBuilder.fromContextPath(request).path("/agent/WxLogin").queryParam("state",state).build();
//                String redirectUrl = uri.toUriString();
//                response.sendRedirect(redirectUrl);
//        }else {
//            chain.doFilter(request,response);
//        }

        //用户自动登录
//		if(!WebUtil.isLogin(request))	autoLogin(request,response);LOG.debug("用户没登录自动登陆路径");
		//用户已经登录或者不需要登录的地址
        if (WebUtil.isLogin(request) || notNeedValidatePath(WebUtil.getRequstPathWithoutQueryString(request))) {
			chain.doFilter(request, response);
			return;
		} else {//跳转到登陆页面进行登陆
			    String xhr = request.getHeader(XHR_HEADER_NAME);
				String redirectUrl = WebUtil.getContextPath(request) + LOGIN_PATH ;
				if (Objects.equals(xhr, XHR_HEADER)) {//如果是ajax请求
					response.setHeader("Location", redirectUrl+ "?url="
							+ URLEncoder.encode(request.getHeader("Referer"), "UTF-8"));
					response.setHeader("Content-Type", "application/json");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//设置未认证
				} else {//跳转到登陆页面
					response.sendRedirect(redirectUrl+ "?url=" + URLEncoder.encode(fullRequestPath, "UTF-8"));
			}
		}
	}

//	public void autoLogin(HttpServletRequest request,HttpServletResponse response){
//		Cookie[] cookies = request.getCookies();
//		Cookie userCookie=null,tokenCookie=null;
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("user")) {
//					userCookie=cookie;
//					LOG.debug("user:{}",cookie.getValue());
//				}
//				if(cookie.getName().equals("token")){
//					tokenCookie=cookie;
//					LOG.debug("token:{}",cookie.getName());
//				}
//			}
//		}
//		if(Objects.nonNull(userCookie) && Objects.nonNull(tokenCookie)) {
//            if (Objects.nonNull(userCookie.getValue())&&Objects.nonNull(tokenCookie.getValue())) {
////                System.out.println(userHandler+"----------");
//                Agent agent = userHandler.agentAutoLogin(userCookie.getValue(), tokenCookie.getValue());
//
//                if (Objects.nonNull(agent)) {
//                    LoginHelper.recordLoginInfo(request, response, agent);
//                    userHandler.recordLogin(agent);
//                    WebUtil.addSessionAttribute(request, Constant.MUTED_USER_KEY, mutedUserDAO.loadFirstOne());
//                    LoginHelper.recordAgentLoginInfo(request, agent);
//                }
//            }
//        }
//	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		debug = fConfig.getInitParameter(DEBUG_PARAMETER_NAME);
		dmz = fConfig.getInitParameter(DMZ_PARAMETER_NAME);
		if (Objects.isNull(dmz) || dmz.isEmpty()) {
			dmz = DMZ;
		}
	}

	public void validate(HttpServletRequest request) {
		if (this.isIllegalClient(request)) {
			throw new RuntimeException("您的访问方式不正确!");
		}
	}

	private boolean isIllegalClient(HttpServletRequest request) {
		if (request.getHeader("referer") == null) {
			if (needValidatePath(request.getRequestURI())) {
				return true;
			}
		}
		return false;
	}

	private boolean needValidatePath(String uri) {
		return !notNeedValidatePath(uri);
	}

	/**
	 * 带dmz/shopcart/login的免登录
	 * @param uri
	 * @return
	 */
	private boolean notNeedValidatePath(String uri) {
		return uri.indexOf(dmz) > -1 || uri.indexOf(LOGIN) > -1
				|| uri.indexOf(REGISTER) > -1||uri.indexOf(WX_LOGIN_PATH)>-1;
	}

	private final Logger LOG = LoggerFactory.getLogger(getClass());



}
