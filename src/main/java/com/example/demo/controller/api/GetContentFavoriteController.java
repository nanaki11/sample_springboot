package com.example.demo.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ContentFavorite;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetContentFavoriteResponse;
import com.example.demo.service.ContentFavoriteService;


/**
 * コンテンツお気に入り取得APIコントローラ．
 */
@RestController
public class GetContentFavoriteController {

	private static final Logger logger = LoggerFactory.getLogger(GetContentFavoriteController.class);

	@Autowired
	private ContentFavoriteService service;

	@RequestMapping("/api/favorite/content/getlist")
	public ApiResponse getList(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "customerId") String customerId) {

		ApiError apiError = validationCheck(clientId, customerId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		List<ContentFavorite> favorites = service.findByCustomerId(Long.valueOf(customerId));
		GetContentFavoriteResponse result = new GetContentFavoriteResponse();
		result.setFavorite(favorites.stream()
				.map(favorite -> result.new Favorite(favorite.getContentId()))
				.collect(Collectors.toList()));

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
			return ApiResponseUtil.setF001001(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_ILLEGAL);
		}

		return null;
	}
}
