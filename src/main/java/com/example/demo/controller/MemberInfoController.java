package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.response.GetMemberInfoResponse;
import com.example.demo.entity.response.GetMemberRelationResponse.MemberRelation;
import com.example.demo.service.CustomerImageService;
import com.example.demo.service.GetMemberInfoService;
import com.example.demo.service.GetMemberRelationService;

/**
 * 会員情報画面コントローラークラス
 */
@Controller
public class MemberInfoController {

	private static final String VIEW = "mypage/MemberInfo";

	@Autowired
	private GetMemberInfoService memberInfoService;

	@Autowired
	private GetMemberRelationService memberRelationService;

	@Autowired
	private CustomerImageService customerImageService;

	@RequestMapping(value = "/secure/mypage/memberInfo", method = RequestMethod.GET)
	public String getMemberInfo(Model model) {
		//会員情報取得
		GetMemberInfoResponse memberInfo = memberInfoService.getMemberInfo();
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("memberImageUrl",
				customerImageService.findByCustomerId(memberInfo.getCustomerId()).getImageUrl());

		//メンバーシップ連携状態取得
		List<MemberRelation> memberRelations = memberRelationService.getMemberRelation();
		model.addAttribute("memberRelations", memberRelations);

		Map<Long, String> relationImageMap = new HashMap<Long, String>();
		for (MemberRelation memberRelation : memberRelations) {
			relationImageMap.put(memberRelation.getCustomerId(),
					customerImageService.findByCustomerId(memberRelation.getCustomerId()).getImageUrl());
		}
		model.addAttribute("relationImageMap", relationImageMap);

		return VIEW;
	}

}
