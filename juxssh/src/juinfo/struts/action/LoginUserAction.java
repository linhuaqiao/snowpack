/*
 * @(#)LoginedAction.java	
 *
 * Copyright ...
 * @author	bridge
 * @create	2013-11-1
 * 
 */

package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import juinfo.servlet.session.UserSession;


/**
 * When none user entering in the system, the mapping find forward "nologined".
 * 
 * 
 * 
 * @author bridge
 * @version 1.0 2013-11-1<br>
 * @version Revision: x.x, Date: 2013-12-20, Author: bridge; <br />
 * 
 */
public class LoginUserAction extends CustomDispatchAction
{
	private String forwardNoUser;

	public String getForwardNoUser()
	{
		return forwardNoUser;
	}

  
	public void setForwardNoUser(String forwardNoUser)
	{
		this.forwardNoUser = forwardNoUser;
	}


	/**
	 * @getParameter <br>
	 *               method <br>
	 * @setAttribute <br>
	 *               action.method <br>
	 *               request.parameter <br>
	 *               loginUser <br>
	 * @findForward <br>
	 *              this.forwardNoUser - No one is logined. <br>
	 *              
	 * 
	 * */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (UserSession.getInstance().getUser(request.getSession()) == null)
		{
			log.debug("It's no user entering into the system in the request.");
			return mapping.findForward(this.forwardNoUser);
		}
		request.setAttribute("loginUser", UserSession.getInstance().getUser(request.getSession()));
		return super.execute(mapping, form, request, response);
	}
}
