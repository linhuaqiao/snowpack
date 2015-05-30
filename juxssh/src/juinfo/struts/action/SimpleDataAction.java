/**
 * @version Revision: 1.1, Date: 2013-4-17, Author: bridge;
 * @version Revision: 1.2, Date: 2013-5-20, Author: bridge; <br />
 * */
package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juinfo.limit.DefaultLimiter;
import juinfo.limit.Limiter;
import juinfo.servlet.session.UserSession;
import juinfo.spring.HibernateDaoCaller;
import juinfo.struts.form.StrutsConvert;
import juinfo.validation.DMLValidator;
import juinfo.validation.Valid;
import juinfo.validation.AbstractValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import snow.servlet.LoginUser;

/**
 * DataDispatchAction 一锟斤拷锟斤拷锟捷讹拷写Action
 * 
 */
public class SimpleDataAction extends DispatchAction implements DataAction
{
	
	/*
	 * Fields
	 * */

	/* HibernateDaoSupport锟斤拷牡锟斤拷锟斤拷锟? 锟斤拷锟斤拷RTTI;
	 */
	private HibernateDaoCaller daoCaller;
	
	/* Action锟斤拷锟接︼拷锟捷匡拷锟斤拷锟斤拷锟? 通锟斤拷daoCaller锟斤拷锟斤拷.
	 */
	private HibernateDaoSupport dao;

	/* Action锟斤拷应Bean锟斤拷锟斤拷锟斤拷
	 */
	private Class<?> beanType;
	
	/* Bean锟斤拷锟斤拷锟斤拷锟斤拷;
	 */
	private DMLValidator checker;
	
	/* 权锟睫硷拷锟斤拷锟?
	 * */
	private Limiter limiter;
	
	/* Login user info; get from session. */
	private LoginUser loginUser;

	/*
	 * Get and set methods 
	 * */
	public HibernateDaoSupport getDao()
	{
		return dao;
	}

	public void setDao(HibernateDaoSupport dao)
	{
		this.dao = dao;
		daoCaller = new HibernateDaoCaller(dao);
	}

	public Class<?> getBeanType()
	{
		return beanType;
	}

	public void setBeanType(Class<?> beanType)
	{
		this.beanType = beanType;
	}

	public DMLValidator getChecker()
	{
		return checker;
	}

	public void setChecker(DMLValidator checker)
	{
		this.checker = checker;
	}

	public Limiter getLimiter()
	{
		return limiter;
	}

	public void setLimiter(Limiter limiter)
	{
		this.limiter = limiter;
	}
	
	public LoginUser getLoginUser()
	{
		return this.loginUser;
	}

	protected void setLoginUser(HttpServletRequest request)
	{
		loginUser = UserSession.getInstance().getLoginUser(request.getSession());
	}
	
	/*
	 * Methods
	 * */

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (checker == null)
			checker = new AbstractValidator();
		if (limiter == null)
			limiter = new DefaultLimiter();
		setLoginUser(request);
		return super.execute(mapping, form, request, response);
	}

	public ActionForward goList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkList(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);
		
		request.setAttribute(ATTRIBUTE_DATALIST, daoCaller.findAll());
		return mapping.findForward(FORWARD_GOLIST);
	}

	public ActionForward goShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkShow(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);

		Integer id = Integer.valueOf(request.getParameter(PARAMTER_ID));
		Object instance = daoCaller.findById(id);
		StrutsConvert.toForm(instance, form);

		request.setAttribute(ATTRIBUTE_METHOD, "");
		return mapping.findForward(FORWARD_GOSHOW);
	}

	public ActionForward goInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkInsert(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);

		form = form.getClass().newInstance();

		request.setAttribute(ATTRIBUTE_METHOD, METHOD_INSERT);
		return mapping.findForward(FORWARD_GOINSERT);
	}

	public ActionForward goUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkUpdate(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);
		
		Integer id = Integer.valueOf(request.getParameter(PARAMTER_ID));
		Object instance = daoCaller.findById(id);
		StrutsConvert.toForm(instance, form);

		request.setAttribute(ATTRIBUTE_METHOD, METHOD_UPDATE);
		return mapping.findForward(FORWARD_GOUPDATE);
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkInsert(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);
		
		Object instance = StrutsConvert.toBean(form, beanType);
		Valid result = checker.insert(instance);
		if (result.toBoolean())
		{
			daoCaller.save(instance);
			return goList(mapping, form, request, response);
		} else
		{
			request.setAttribute(ATTRIBUTE_METHOD, METHOD_INSERT);
			return goForwardError(mapping, request, FORWARD_GOERROR, result);
		}
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (!limiter.checkUpdate(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);

		Object instance = StrutsConvert.toBean(form, beanType);
		Valid result = checker.update(instance); 
		if (result.toBoolean())
		{
			daoCaller.merge(instance);
			return goList(mapping, form, request, response);
		}
		else
		{
			request.setAttribute(ATTRIBUTE_METHOD, METHOD_UPDATE);
			return goForwardError(mapping, request, FORWARD_GOERROR, result);
		}
	}
	
	public ActionForward merge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		Object instance = StrutsConvert.toBean(form, beanType);
		Valid result = checker.merge(instance);
		if (result.toBoolean())
		{
			daoCaller.merge(instance);
			return goList(mapping, form, request, response);
		} else
		{
			String id = request.getParameter(PARAMTER_ID);
			if (id == "")
				request.setAttribute(ATTRIBUTE_METHOD, METHOD_INSERT);
			else
				request.setAttribute(ATTRIBUTE_METHOD, METHOD_UPDATE);
			return goForwardError(mapping, request, FORWARD_GOERROR, result);
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (limiter.checkDelete(loginUser).toBoolean())
			return goForwardLimitError(mapping, request);
		
		Integer id = Integer.valueOf(request.getParameter(PARAMTER_ID));
		Object instance = daoCaller.findById(id);
		Valid result = checker.delete(instance); 
		if (result.toBoolean())
		{
			daoCaller.delete(instance);
			return goList(mapping, form, request, response);
		}
		else
			return goForwardError(mapping, request, FORWARD_GOERROR, result);
	}

	protected ActionForward goForwardError(ActionMapping mapping, HttpServletRequest request, String forward, Valid result)
	{
		request.setAttribute(ATTRIBUTE_ERROR, result.getError());
		request.setAttribute(ATTRIBUTE_MESSAGE, result.getMessage());
		return mapping.findForward(forward);
	}
	
	protected ActionForward goForwardLimitError(ActionMapping mapping, HttpServletRequest request)
	{
		return mapping.findForward(FORWARD_GONOLIMIT);
	}
	
}
