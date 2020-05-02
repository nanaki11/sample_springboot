package com.example.demo.controller.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Facility;
import com.example.demo.entity.FacilityFavorite;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.RegisterFacilityFavoriteResponse;
import com.example.demo.service.FacilityFavoriteService;
import com.example.demo.service.FacilityService;


/**
 * 施設お気に入り登録APIコントローラ．
 */
@RestController
public class RegisterFacilityFavoriteController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterFacilityFavoriteController.class);

	@Autowired
	private FacilityService facilityService;
	@Autowired
	private FacilityFavoriteService facilityFavoriteService;

	@RequestMapping("/api/favorite/facility/register")
	public ApiResponse register(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "customerId") String customerId,
			@RequestParam(value = "businessCd") String businessCd) {

		ApiError apiError = validationCheck(clientId, customerId, businessCd);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if(!isExistingBusinessCd(businessCd)) {
			apiError = ApiResponseUtil.setF005002(businessCd, ApiResponseUtil.ERROR_DETAIL_BUSINESSCD_ILLEGAL);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		saveFacilityFavorite(customerId, businessCd);

		return new ApiResponse(new RegisterFacilityFavoriteResponse());
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @param customerId 業務ID
	 * @param businessCd 事業所コード
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId, String customerId, String businessCd) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		if (StringUtils.isEmpty(customerId)) {
			return ApiResponseUtil.setC000004(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_UNDEFINED);
		}

		if (ApiResponseUtil.CUSTOMER_ID_MAXSIZE < customerId.length() || !StringUtils.isNumeric(customerId)) {
			return ApiResponseUtil.setF005001(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_ILLEGAL);
		}

		if (StringUtils.isEmpty(businessCd)) {
			return ApiResponseUtil.setC000004(businessCd, ApiResponseUtil.ERROR_DETAIL_BUSINESSCD_UNDEFINED);
		}

		if (ApiResponseUtil.BUSINESS_CD_MAXSIZE < businessCd.length()) {
			return ApiResponseUtil.setF005002(businessCd, ApiResponseUtil.ERROR_DETAIL_BUSINESSCD_ILLEGAL);
		}

		return null;
	}

	/**
	 * 存在する事業所コードか判定
	 *
	 * @param businessCd 事業所コード
	 * @return treu:存在する,false:存在しない
	 */
	private boolean isExistingBusinessCd(String businessCd) {

		List<Facility> facilities = facilityService.findByBusinessCd(businessCd);
		if (CollectionUtils.isEmpty(facilities)) {
			return false;
		}

		return true;
	}

	/**
	 * 施設お気に入り登録　※既にデータが存在する場合もエラーとせず、更新を行う
	 *
	 * @param customerId 業務ID
	 * @param businessCd 事業所コード
	 */
	private void saveFacilityFavorite(String customerId, String businessCd) {

		FacilityFavorite facilityFavorite = new FacilityFavorite();
		facilityFavorite.setCustomerId(Long.valueOf(customerId));
		facilityFavorite.setBusinessCd(businessCd);

		facilityFavoriteService.save(facilityFavorite);
	}
}
