package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CommunityInfo;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.entity.UserInfoBean.Community;
import com.example.demo.form.CommunityListForm;
import com.example.demo.service.CommunityInfoService;
import com.example.demo.service.ViewUrlService;

/**
 * コミュニティ一覧画面
 *
 */
@Controller
@RequestMapping("/communitylist")
public class CommunityListController {

	private static final Logger logger = LoggerFactory.getLogger(CommunityListController.class);
	private static final String VIEW_INDEX = "communitylist";

	@Autowired
	private CommunityInfoService communityInfoService;

	/**
	 * 初期表示
	 * @param request
	 * @param model
	 * @return コミュニティ一覧画面
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {

		List<CommunityListForm> communityList = null;

		UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
		if (null != userInfo) {
			communityList = getCommunityList(userInfo);
		}

		model.addAttribute("communityList", communityList);
		return VIEW_INDEX;
	}

	/**
	 * コミュニティ参加完了ダイアログ（参加ボタン押下時の処理）
	 * @param request
	 * @param model
	 * @param joinId 参加するコミュニティID
	 */
	@RequestMapping("/join")
	public String join(HttpServletRequest request, Model model, @RequestParam int joinId) {

		UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
		if (null == userInfo) {
			// TODO エラーメッセージ・ハンドリング方法の確認が必要
			logger.error("会員情報が取得できませんでした。");
			return ViewUrlService.toRedirectUrl(ErrorController.ERROR_VIEW);
		}

		// TODO 統合DBの更新が必要？
		Community community = (new UserInfoBean()).new Community();
		community.setId(joinId);
		userInfo.getCommunity().add(community);
		request.getSession().setAttribute("userInfo", userInfo);

		List<CommunityListForm> communityList = getCommunityList(userInfo);
		model.addAttribute("communityList", communityList);
		CommunityListForm joinCommunity = communityList.stream().filter(joinedCommunity -> joinedCommunity.getCommunityId() == joinId).findFirst().get();
		model.addAttribute("joinCommunity", joinCommunity);
		return VIEW_INDEX;
	}

	/**
	 * コミュニティ退会完了ダイアログ（退会確認ダイアログにて"はい"押下時の処理）
	 * @param request
	 * @param model
	 * @param withdrawId 退会するコミュニティID
	 */
	@RequestMapping("/withdraw")
	public String withdraw(HttpServletRequest request, Model model, @RequestParam int withdrawId) {

		UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
		if (null == userInfo) {
			// TODO エラーメッセージ・ハンドリング方法の確認が必要
			logger.error("会員情報が取得できませんでした。");
			return ViewUrlService.toRedirectUrl(ErrorController.ERROR_VIEW);
		}

		// TODO 統合DBの更新が必要？
		userInfo.getCommunity().removeIf(joinedCommunity -> joinedCommunity.getId() == withdrawId);

		List<CommunityListForm> communityList = getCommunityList(userInfo);
		model.addAttribute("communityList", communityList);
		CommunityListForm withdrawCommunity = communityList.stream().filter(community -> community.getCommunityId() == withdrawId).findFirst().get();
		model.addAttribute("withdrawCommunity", withdrawCommunity);
		return VIEW_INDEX;
	}

	/**
	 * コミュニティ一覧の取得
	 * @param communityList
	 * @param userInfo
	 * @return コミュニティ一覧
	 */
	private List<CommunityListForm> getCommunityList(UserInfoBean userInfo) {

		List<CommunityListForm> communityList = new LinkedList();

		// コミュニティ付帯情報の取得
		List<CommunityInfo> communityInfoList =
				communityInfoService.orderByCommunityId(null, userInfo.getIsStaff(), userInfo.getAge());
		if (CollectionUtils.isEmpty(communityInfoList)) {
			return communityList;
		}

		// 参加済みコミュニティID一覧の作成
		List<Integer> communityIdList = userInfo.getCommunity().stream()
				.map(joinedCommunity -> joinedCommunity.getId())
				.collect(Collectors.toList());

		// コミュニティ一覧の作成
		for (CommunityInfo communityInfo : communityInfoList) {
			CommunityListForm community = new CommunityListForm();
			community.setCommunityId(communityInfo.getCommunityId());
			community.setCommunityImage(communityInfo.getCommunityImage());
			community.setCommunityName(communityInfo.getCommunityName());
			community.setComent(communityInfo.getComent());
			community.setExternalLink(communityInfo.getExternalLink());
			community.setJoined(communityIdList.contains(communityInfo.getCommunityId()));
			communityList.add(community);
		}

		return communityList;
	}
}
