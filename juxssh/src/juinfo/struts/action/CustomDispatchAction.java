/*
 * @(#)CustomDispatchAction.java	
 *
 * Copyright ...
 * @author	bridge
 * @create	2013-12-20
 * 
 */

package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * 
 * 
 * @author	bridge
 * @version	1.0		2013-12-20<br>
 *
 */
public class CustomDispatchAction extends DispatchAction
{

	/**
	 * @getParameter <br />
	 *               method <br />
	 * @setAttribute <br />
	 *               action.method <br />
	 * */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String parameter = getParameter(mapping, form, request, response);
		String name = getMethodName(mapping, form, request, response, parameter);
		
		log.info(String.format("ActionClass: %s, Method: %s", this.getClass().getName(), name));
		
		request.setAttribute("action.method", name);
		return super.execute(mapping, form, request, response);
	}
}
