/*
 * @(#)BeanConvert.java		1.1.0	2012-09-04
 * 
 * Author bridge
 * CopyRight 2012 snowpack, All rights reserved.
 */

package juinfo.struts.form;

import java.lang.reflect.Field;
import org.apache.struts.action.ActionForm;
import java.sql.ResultSet;

import juinfo.rtti.Rtti;

/**
 * BeanConvert 实现Hibernate-bean与Struts-ActionForm对象之间赋值
 * 
 * @notice Hibernate-bean, Struts-ActionForm两者的字段名称必须一样; 两者字段个数不一定是相等;
 * 
 * @author bridge
 * */

public class StrutsConvert
{
	/**
	 * Struts-Form To Hibernate-bean;
	 * */
	public static Object toBean(ActionForm form, Class<?> beanClass) throws Exception
	{
		Rtti beanObj = new Rtti(beanClass);
		Rtti formObj = new Rtti(form);

		Field[] fs = beanClass.getDeclaredFields();
		for (Field field: fs)
			if (formObj.hasField(field.getName()))
			{
				Object value = formObj.getValue(field.getName());
				beanObj.setValue(field.getName(), value);
			}
		return beanObj.getInstance();
	}

	/**
	 * Hibernate-bean to Struts-Form;
	 * */
	public static void toForm(Object bean, ActionForm form) throws Exception
	{
		Rtti beanObj = new Rtti(bean);
		Rtti formObj = new Rtti(form);

		Field[] fs = bean.getClass().getDeclaredFields();
		for (Field field: fs)
			if (formObj.hasField(field.getName()))
			{
				Object value = beanObj.getValue(field.getName());
				formObj.setValue(field.getName(), value);
			}
	}

	/**
	 * ResultSet to Struts-Form
	 * */
	public static void toForm(ResultSet resultSet, ActionForm form) throws Exception
	{
		Rtti formObj = new Rtti(form);

		Field[] fs = form.getClass().getDeclaredFields();
		for(Field field: fs)
			if (resultSet.findColumn(field.getName()) >= 0) 
			{
				Object value = resultSet.getObject(field.getName());
				formObj.setValue(field.getName(), value);
			}
	}
	


}
