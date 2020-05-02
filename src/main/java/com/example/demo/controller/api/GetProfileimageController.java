package com.example.demo.controller.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomerImage;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetProfileimageResponse;
import com.example.demo.service.CustomerImageService;

/**
 * プロフィール画像取得APIコントローラ．
 */
@RestController
public class GetProfileimageController {

	private static final Logger logger = LoggerFactory.getLogger(GetProfileimageController.class);

	@Autowired
	private CustomerImageService service;

	@RequestMapping("/api/profileimage/get")
	public ApiResponse get(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "customerId") String customerId) {

		ApiError apiError = validationCheck(clientId, customerId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		GetProfileimageResponse result = new GetProfileimageResponse();
		CustomerImage profileimage = service.findByCustomerId(Long.valueOf(customerId));
		if(null != profileimage) {
			result.setImage(profileimage.getImageUrl());
		}

		return new ApiResponse(result);
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @param customerId 業務ID
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId, String customerId) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		if (StringUtils.isEmpty(customerId)) {
			return ApiResponseUtil.setC000004(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_UNDEFINED);
		}

		if (ApiResponseUtil.CUSTOMER_ID_MAXSIZE < customerId.length() || !StringUtils.isNumeric(customerId)) {
			return ApiResponseUtil.setF008001(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_ILLEGAL);
		}

		return null;
	}

}
