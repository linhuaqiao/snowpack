/*
 * @(#)LocaleSwitcher.java	
 *
 * Copyright ...
 * @author	bridge
 * @create	2013-10-24
 * 
 */

package juinfo.struts;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import juinfo.util.LocaleFactory;

import org.apache.struts.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Change locale.
 * 
 * @author bridge
 * @version 1.0 2013-10-24<br>
 * 
 */
public class LocaleSwitcher
{
	public static final String LOCALE_SWITCHED = "locale.switched";
	public static final String LOCALE_LANGUAGE = "locale.language";
	public static final String LOCALE_COUNTRY = "locale.country";

	private static final Logger log = LoggerFactory.getLogger(LocaleSwitcher.class);

	public static Locale getLocale(HttpServletRequest request)
	{
		return (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public static void switchLocale(HttpServletRequest request, Locale locale)
	{
		request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
		log.debug(String.format("set locale language: %s, country: %s", locale.getLanguage(), locale.getCountry()));

		request.getSession().setAttribute(LOCALE_SWITCHED, "true");
		request.getSession().setAttribute(LOCALE_LANGUAGE, locale.getLanguage());
		request.getSession().setAttribute(LOCALE_COUNTRY, locale.getCountry());
	}

	public static void switchLocale(HttpServletRequest request, String language, String country)
	{
		Locale locale = LocaleFactory.getInstance(language, country);
		LocaleSwitcher.switchLocale(request, locale);
	}

	/**
	 * @deprecated Please use method 'switchLocale(HttpSevletRequest, String,
	 *             String)' instead of this.
	 * 
	 * */
	public static void switchLocale(HttpServletRequest request, String language)
	{
		Locale locale = LocaleFactory.getInstance(language);
		LocaleSwitcher.switchLocale(request, locale);
	}

}
