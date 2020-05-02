package com.example.demo.controller.api;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Content;
import com.example.demo.entity.ContentFavorite;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.DeleteContentFavoriteResponse;
import com.example.demo.service.ContentFavoriteService;
import com.example.demo.service.ContentService;


/**
 * コンテンツお気に入り解除APIコントローラ．
 */
@RestController
public class DeleteContentFavoriteController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteContentFavoriteController.class);

	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentFavoriteService contentFavoriteService;

	@RequestMapping("/api/favorite/content/delete")
	public ApiResponse delete(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "customerId") String customerId,
			@RequestParam(value = "contentId") String contentId) {

		ApiError apiError = validationCheck(clientId, customerId, contentId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if(!isExistingContent(contentId)) {
			apiError = ApiResponseUtil.setF003002(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		deleteContentFavorite(customerId, contentId);

		return new ApiResponse(new DeleteContentFavoriteResponse());
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @param customerId 業務ID
	 * @param contentId コンテンツID
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId, String customerId, String contentId) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		if (StringUtils.isEmpty(customerId)) {
			return ApiResponseUtil.setC000004(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_UNDEFINED);
		}

		if (ApiResponseUtil.CUSTOMER_ID_MAXSIZE < customerId.length() || !StringUtils.isNumeric(customerId)) {
			return ApiResponseUtil.setF003001(customerId, ApiResponseUtil.ERROR_DETAIL_CUSTOMERID_ILLEGAL);
		}

		if (StringUtils.isEmpty(contentId)) {
			return ApiResponseUtil.setC000004(customerId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_UNDEFINED);
		}

		if (ApiResponseUtil.CONTENT_ID_MAXSIZE < contentId.length() || !StringUtils.isNumeric(contentId)) {
			return ApiResponseUtil.setF003002(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
		}

		return null;
	}

	/**
	 * 存在するコンテンツ情報か判定
	 *
	 * @param contentId コンテンツID
	 * @return treu:存在する,false:存在しない
	 */
	private boolean isExistingContent(String contentId) {

		Optional<Content> content = contentService.findById(Long.valueOf(contentId), null);
		if (content.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * コンテンツお気に入り削除　※既にデータが存在しない場合もエラーとしない
	 *
	 * @param customerId 業務ID
	 * @param contentId コンテンツID
	 */
	private void deleteContentFavorite(String customerId, String contentId) {

		ContentFavorite contentFavorite = new ContentFavorite();
		contentFavorite.setCustomerId(Long.valueOf(customerId));
		contentFavorite.setContentId(Long.valueOf(contentId));

		contentFavoriteService.delete(contentFavorite);
	}
}
