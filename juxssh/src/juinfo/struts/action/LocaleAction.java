/**
 * 
 * 
 * @author bridge
 * @version 1.0 2013-10-25<br>
 * @version Revision: x.x, Date: 2014-1-26, Author: bridge; <br />
 *  
 */


package juinfo.struts.action;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import juinfo.struts.LocaleSwitcher;
import juinfo.util.LocaleFactory;

public class LocaleAction extends DispatchAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		return super.execute(mapping, form, request, response);
	}

	/**
	 * @deprecated
	 * @param language
	 * @result format: success,newlanguage,newcountry,oldLanguage,oldCountry; <br/>
	 *         sucess: 1 - OK; 1,newlanguage,newcountry,oldLanguage,oldCountry; <br />
	 *         0 - Error; 0,errorMessage,'',oldLanguage,oldCountry;
	 */
	public ActionForward switchLanguage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String language = request.getParameter("language");

		Locale locale = LocaleFactory.getInstance(language);
		LocaleSwitcher.switchLocale(request, locale);
		String result = String.format("%d,%s,%s,%s,%s", 1, locale.getLanguage(), locale.getCountry(), language, "");
		response.getWriter().write(result);
		return null;
	}

	/**
	 * @param language
	 * @param country
	 * */
	public ActionForward switchLocale(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String language = request.getParameter("language");
		String country = request.getParameter("country");

		Locale locale = LocaleFactory.getInstance(language, country);
		LocaleSwitcher.switchLocale(request, locale);
		String result = String
				.format("%d,%s,%s,%s,%s", 1, locale.getLanguage(), locale.getCountry(), language, country);
		response.getWriter().write(result);
		return null;
	}

	/**
	 * @deprecated
	 * */
	public ActionForward goLanguage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		String redirect = request.getParameter("redirect");
		String language = request.getParameter("language");

		Locale locale = LocaleFactory.getInstance(language);
		if (!locale.getLanguage().equals(language))
			request.setAttribute("locale.language.error", "true");
		LocaleSwitcher.switchLocale(request, locale);

		if (forward != null)
			return mapping.findForward(forward);
		else
		{
			response.sendRedirect(redirect);
			return null;
		}
	}

	/**
	 * 这个函数较常用
	 * */
	public ActionForward goLocale(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		String redirect = request.getParameter("redirect");
		String language = request.getParameter("language");
		String country = request.getParameter("country");

		Locale locale = LocaleFactory.getInstance(language, country);
		if (!locale.getLanguage().equals(language) || !locale.getCountry().equals(country))
			request.setAttribute("locale.language.error", "true");
		LocaleSwitcher.switchLocale(request, locale);

		if (forward != null)
			return mapping.findForward(forward);
		else
		{
			response.sendRedirect(redirect);
			return null;
		}
	}

}
