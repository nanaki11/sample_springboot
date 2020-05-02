package com.example.demo.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetMemberRelationResponse.MemberRelation;
import com.example.demo.service.CancelRelationService;
import com.example.demo.service.CustomerImageService;
import com.example.demo.service.GetMemberRelationService;

/**
 * ファミリーメンバーズ連携解除画面コントローラークラス
 */
@Controller
public class CancelRelationController {

	private static final String VIEW = "memberInfo/family_member_delete";

	@Autowired
	private GetMemberRelationService getMemberRelationService;
	@Autowired
	private CancelRelationService cancelRelationService;

	@Autowired
	private CustomerImageService customerImageService;

	private static final Logger logger = LoggerFactory.getLogger(CancelRelationController.class);

	/**
	 * ファミリーメンバーズ連携取得
	 */
	@RequestMapping(value = "/secure/memberInfo/getRelation", method = RequestMethod.GET)
	public String getRelationMember(Model model) {

		return VIEW;
	}

	/**
		* ファミリーメンバーズ連携解除
		*/
	@RequestMapping(value = "/secure/memberInfo/cancelRelation", method = RequestMethod.POST)
	public String cancelRelation(Model model, @RequestParam String relationId) {
		//ファミリーメンバーズ連携解除
		ApiResponse apiResponse = cancelRelationService.cancelRelation(Long.valueOf(relationId));
		ApiError apiError = apiResponse.getError();

		if (apiError != null) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
		}

		//メンバーシップ連携状態再取得
		List<MemberRelation> memberRelationList = getMemberRelationService.getMemberRelation();
		model.addAttribute("memberRelationList", memberRelationList);

		model.addAttribute("relationImageMap", customerImageService.getRelationImageMap(memberRelationList));

		return VIEW;
	}
}
