/**
 * @version Revision: x.x, Date: 2013-7-4, Author: bridge; <br />
 * 
 * */
package juinfo.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public interface DataAction 
{

	public static final String FORWARD_GOLIST = "goList";
	public static final String FORWARD_GOSHOW = "goShow";
	public static final String FORWARD_GOINSERT = "goInsert";
	public static final String FORWARD_GOUPDATE = "goUpdate";
	public static final String FORWARD_GOERROR = "goError";
	public static final String FORWARD_GONOLIMIT = "goNoLimit";
	public static final String FORWARD_GOSUCCESS = "goSuccess";
	
	public static final String ATTRIBUTE_METHOD = "method";
	public static final String ATTRIBUTE_DATALIST = "dataList";
	public static final String ATTRIBUTE_ERROR = "error";
	public static final String ATTRIBUTE_MESSAGE = "message";

	public static final String METHOD_MERGER = "merge";
	public static final String METHOD_INSERT = "insert";
	public static final String METHOD_UPDATE = "update";
	public static final String METHOD_DELETE = "delete";
	
	public static final String PARAMTER_ID = "id";

	public ActionForward goList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward goInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward goUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward goShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward merge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
