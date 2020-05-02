package com.example.demo.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.api.ReqGetUserInfo;
import com.example.demo.api.ReqIdTokenBean;
import com.example.demo.api.ResIdTokenBean;
import com.example.demo.entity.ActionInfoBean;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.service.CallLoginService;
import com.example.demo.service.GetUserInfoService;

/**
 * 共立DBサイトログインページ呼び出し画面
 *
 */
@Controller
@RequestMapping("/callLogin")
public class CallLoginController {
	private static final String VIEW = "login/callLogin";
	private static final String CALL_LOGIN = "redirect:/dummy/login";
	private static final String ERROR = "login/error";

	@Autowired
	HttpSession session;

	public CallLoginController() {
	}

	/**
	 * 初期表示
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewPage(Model model) {

		return VIEW;
	}

	/**
	 * 共立DBサイトのログインページを開くための認可APIを実行する
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	public String authorization(Model model, RedirectAttributes redirectAttribute) {

		// TODO 外部ログインページへの切り替え
		CallLoginService service = new CallLoginService();
		String state = service.getRandom();
		String nonce = service.getRandom();

		redirectAttribute.addAttribute("state", state);
		redirectAttribute.addAttribute("nonce", nonce);

		return CALL_LOGIN;
	}

	/**
	 * 共立DBサイトのログイン結果を受け取る
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authorizationed", method = RequestMethod.POST)
	public String authorizationed(HttpServletRequest req, HttpServletResponse resp,
			RedirectAttributes redirectAttributes) {

		String resIdt = req.getParameter("idt");
		String resState = req.getParameter("state");
		String sessionIdt = (String) session.getAttribute("state");

		// CSRF対策
		if (!Objects.equals(resState, sessionIdt)) {
			// エラー終了
			session.invalidate();
			return ERROR;
		}

		// トークン検証APIの呼び出し
		ReqIdTokenBean idBean = new ReqIdTokenBean();
		CallLoginService service = new CallLoginService();
		ResIdTokenBean ret = service.checkIdToken(idBean);

		// リプレイアタック対策
		String sessionNonce = (String) session.getAttribute("nonce");
		// TODO 要削除
		ret.setKyoritsuId("kyoritsuId");
		ret.setAccessToken("accessToken");
		ret.setNonce((String) session.getAttribute("nonce"));
		if (sessionNonce == null || !Objects.equals(sessionNonce, ret.getNonce())) {
			// エラー終了
			session.invalidate();
			return ERROR;
		}

		// 会員情報取得APIの呼び出し
		ReqGetUserInfo reqUser = new ReqGetUserInfo();
		reqUser.setAccessToken(ret.getAccessToken());
		reqUser.setClientId("clientid"); // 定数での管理になるはず;
		GetUserInfoService userService = new GetUserInfoService();
		UserInfoBean user = userService.getUserInfo(reqUser);
		if (user == null) {
			// エラー終了
			session.invalidate(); // クリア
			return ERROR;
		}
		session.setAttribute("userInfo", user);
		session.setAttribute("accessToken", ret.getAccessToken());

		ActionInfoBean forward = (ActionInfoBean) session.getAttribute("actionInfo");
		if (forward != null) {
			session.removeAttribute("actionInfo");
			if (forward.getParameter() != null && forward.getParameter().size() > 0) {
				ModelMap modelMap = new ModelMap();
				modelMap.addAttribute("param", forward.getParameter());
				redirectAttributes.addFlashAttribute("model", modelMap);
			}

			return "redirect:" + forward.getAccessUrl();
		}

		return VIEW;
	}

}
