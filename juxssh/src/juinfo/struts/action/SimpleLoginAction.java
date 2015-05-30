package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juinfo.hibernate.LoginDAO;
import juinfo.servlet.UserSession;
import juinfo.struts.form.LoginUserForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import snow.servlet.LoginUser;


/**
 * @version Revision: 1.2, Date: 2013-5-20, Author: bridge; <br />
 * */
public class SimpleLoginAction extends DispatchAction implements LoginAction
{
	/*
	 * Fields
	 * */
	private LoginDAO loginDAO;
	/*
	 * Get and set methods.
	 * */
	public LoginDAO getLoginDAO()
	{
		return loginDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO)
	{
		this.loginDAO = loginDAO;
	}

	/*
	 * Methods
	 * */
	public ActionForward goGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserSession.getInstance().logout(request.getSession());
		return mapping.findForward(FORWARD_GOGUEST);
	}

	public ActionForward goLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserSession.getInstance().logout(request.getSession());
		return mapping.findForward(FORWARD_GOLOGIN);
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserSession.getInstance().logout(request.getSession());
		return mapping.findForward(FORWARD_GOLOGOUT);
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		LoginUserForm loginForm = (LoginUserForm) form;
		LoginUser user = (LoginUser)loginDAO.login(loginForm.getUserId(), loginForm.getPassWord());
		if (user != null)
		{
			UserSession.getInstance().login(request.getSession(), user);
			return mapping.findForward(FORWARD_SUCCESS);
		}
		else
		{
			UserSession.getInstance().logout(request.getSession());		
			request.setAttribute(ATTRIBUTE_ERROR, "login error");
			return mapping.findForward(FROWARD_ERROR);
		}
	}

}
