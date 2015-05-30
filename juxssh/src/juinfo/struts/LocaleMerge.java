/*
 * @(#)LocaleMerge.java	
 *
 * Copyright ...
 * @author	bridge
 * @create	2014-1-26
 * 
 */

package juinfo.struts;

import java.util.Locale;

/**
 * 
 * 
 * @author	bridge
 * @version	1.0		2014-1-26<br>
 *
 */
public class LocaleMerge
{
	public static Locale merge(Locale locale)
	{
		if (locale.getLanguage().equals("zh") && locale.getCountry().equals("CN"))
			return new Locale("zh", "");
		if (locale.getLanguage().equals("zh") && locale.getCountry().equals("TW"))
			return new Locale("zh", "HANT");
		
		return new Locale(locale.getLanguage(), locale.getCountry());
	}
}
