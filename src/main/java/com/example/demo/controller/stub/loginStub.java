package com.example.demo.controller.stub;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class loginStub {

	@RequestMapping(value="/login",
			method=RequestMethod.GET)
	public void dummyLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(">>>>>>>>>>>> dummy login <<<<<<<<<<<<");
		try {
			String cid = req.getParameter("cid");
			String state = req.getParameter("state");
			String nonce = req.getParameter("nonce");
			String lang = req.getParameter("lang");
			HttpSession session = req.getSession();
			session.setAttribute("state", state);
			session.setAttribute("nonce", nonce);
			resp.addHeader("content-type", "text/html; charset=utf-8");
			resp.setStatus(HttpStatus.FOUND.value());
			resp.setCharacterEncoding("utf-8");
			resp.getWriter()
				.append("<html><body>")
				.append("<form method=\"post\" action=\"http://localhost:8080/callLogin/authorizationed\">")
				.append("state:").append(state).append("<br />")
				.append("<input type=\"submit\" value=\"ログイン\" />")
				.append("<input type=\"hidden\" name=\"idt\" value=\"").append("idt").append("\">")
				.append("<input type=\"hidden\" name=\"state\" value=\"").append(state).append("\">")
				.append("</form>")
				.append("</body></html>")
				.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("<<<<<<<<<<<< dummy login >>>>>>>>>>>>");
	}

}
