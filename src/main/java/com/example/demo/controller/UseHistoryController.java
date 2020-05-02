package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Facility;
import com.example.demo.entity.response.GetUseHistoryResponse.UseHistory;
import com.example.demo.service.FacilityService;
import com.example.demo.service.UseHistoryService;

/**
 * 利用履歴画面コントローラークラス
 */
@Controller
public class UseHistoryController {

	private static final String VIEW = "mypage/useHistory";
	@Autowired
	private UseHistoryService useHistoryService;
	@Autowired
	private FacilityService facilityService;

	@RequestMapping(value = "/secure/mypage/useHistory", method = RequestMethod.GET)
	public String getUseHistorys(Model model) {
		List<UseHistory> useHistoryList = useHistoryService.getUseHistory();

		Map<Date, List<Facility>> useHistoryMap = new HashMap<Date, List<Facility>>();
		for (UseHistory useHistory : useHistoryList) {
			useHistoryMap.put(useHistory.getUseDate(),
					facilityService.findByBusinessCd(useHistory.getOfficeCd()));
		}
		model.addAttribute("useHistoryMap", useHistoryMap);
		return VIEW;
	}
}
