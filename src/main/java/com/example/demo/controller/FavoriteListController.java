package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.constants.FacilityCategoryCdConstants;
import com.example.demo.controller.api.ApiResponseUtil;
import com.example.demo.entity.Content;
import com.example.demo.entity.join.FacilityFavoriteArea;
import com.example.demo.service.ContentService;
import com.example.demo.service.FacilityService;

/**
 * お気に入り一覧取得用コントローラ．
 */
@Controller
public class FavoriteListController {
	@Autowired
	private ContentService contentService;

	@Autowired
	private FacilityService facilityService;

	private static final String VIEW = "mypage/favoriteList";
	private ModelAndView mv = new ModelAndView();

	@RequestMapping("/secure/mypage/favoriteList") ///secure/mypage/favoriteList
	public ModelAndView getList(
			@RequestParam(value = "customerId") String customerId) {
		mv.setViewName(VIEW);

		if (validationCheck(customerId)) {
			return mv;
		}

		//1：ドーミーイン、2：共立リゾートをリストに追加
		List<String> categoryCdListd = new ArrayList<String>();
		categoryCdListd.add(FacilityCategoryCdConstants.DORMY_INN);
		categoryCdListd.add(FacilityCategoryCdConstants.RESORT);

		//施設（宿泊施設）
		List<FacilityFavoriteArea> hotelList = facilityService.findJoinFavorite(Long.valueOf(customerId),
				categoryCdListd);
		mv.addObject("hotelList", hotelList);

		//3：飲食店をリストに追加
		categoryCdListd = new ArrayList<String>();
		categoryCdListd.add(FacilityCategoryCdConstants.RESTAURANT);

		//施設（飲食店）
		List<FacilityFavoriteArea> restaurantList = facilityService.findJoinFavorite(Long.valueOf(customerId),
				categoryCdListd);
		mv.addObject("restaurantList", restaurantList);

		//コンテンツ（記事）
		List<Content> contentList = contentService.findJoinFavorite(Long.valueOf(customerId));
		mv.addObject("contentList", contentList);

		return mv;
	}

	/**
	 * バリデーションチェック
	 *
	 * @param customerId 業務ID
	 * @return エラーがある場合trueを返却
	 */
	private boolean validationCheck(String customerId) {

		if (StringUtils.isEmpty(customerId)) {
			return true;
		}

		if (ApiResponseUtil.CUSTOMER_ID_MAXSIZE < customerId.length() || !StringUtils.isNumeric(customerId)) {
			return true;
		}

		return false;
	}

}
