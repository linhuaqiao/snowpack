package juinfo.struts.action;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.*;

import juinfo.image.VerificationImage;
import juinfo.lang.RandomInt;
import juinfo.servlet.session.VerificationSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerificationAction extends Action
{
	private Logger log = LoggerFactory.getLogger(VerificationAction.class);

	/**
	 * In this method, the length of verification is 4 and useing default
	 * paramters;
	 * */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		response.addHeader("Content-Type", "image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);

		String code = RandomInt.getString(4);
		VerificationImage image = new VerificationImage();
		image.setCode(code);
		BufferedImage img = image.createImage();
		VerificationSession.getInstance().setCode(request, code);

		try
		{
			ImageIO.write(img, "JPEG", response.getOutputStream());
		} catch (Exception ex)
		{
			log.error("ImageIO.write", ex);
			ex.printStackTrace();
		}
		return null;
	}
}
