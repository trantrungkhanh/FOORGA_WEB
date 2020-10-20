package vn.com.unit.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

	@Autowired
    private SessionRegistry sessionRegistry;
	
	@GetMapping("/test/chat")
	public ModelAndView testChat(Model model) {

		return new ModelAndView("chat");
	}
	
	@GetMapping("/test/session")
	public ModelAndView testSession(Model model) {

		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		
		String current_session_id = requestAttributes.getSessionId();
		
		SessionInformation sessionInformation = sessionRegistry.getSessionInformation(current_session_id);
		
//		(HttpSession) application.getAttribute("vF5_lvmxDtPf2ERO09DjctoGxBbPm1MhBl2ib1BM.desktop-c3292p7");
		
		return new ModelAndView("chat");
	}
	
}
