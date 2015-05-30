package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juinfo.message.ActionMessage;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public interface LoginAction extends ActionMessage
{
	public static final String FORWARD_GOLOGIN = "goLogin";
	public static final String FORWARD_GOLOGOUT = "goLogout";
	public static final String FORWARD_GOGUEST = "goGuest";
	public static final String FORWARD_SUCCESS = "success";
	public static final String FROWARD_ERROR = "error";
	
	public static final Integer ERROR_USERNAME_NOTEXISTED = 11;
	public static final Integer ERROR_USERNAME_EXISTED = 12;
	public static final Integer ERROR_USERNAME_NULL = 13;
	public static final Integer ERROR_PASSWORD_NULL = 21;
	public static final Integer ERROR_PASSWORD_LESS = 22;
	public static final Integer ERROR_PASSWORD_NOTEQUAL = 23;

	
	public ActionForward goGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ActionForward goLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

		
}
