package com.example.demo.controller.api;

import java.text.MessageFormat;
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
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.DeleteContentResponse;
import com.example.demo.service.ContentService;


/**
 * コンテンツ情報削除APIコントローラ．
 */
@RestController
public class DeleteContentController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteContentController.class);


	@Autowired
	private ContentService contentService;

	@RequestMapping("/api/content/delete")
	public ApiResponse delete(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "contentId") String contentId) {

		ApiError apiError = validationCheck(clientId, contentId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if(!isExistingContent(contentId)) {
			String detail = MessageFormat.format(ApiResponseUtil.ERROR_DETAIL_CONTENTID_NOT_EXIST, contentId, "ja");
			apiError = ApiResponseUtil.setA003001(detail);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		deleteContent(contentId);

		return new ApiResponse(new DeleteContentResponse());
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @param contentId コンテンツID
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId, String contentId) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		if (StringUtils.isEmpty(contentId)) {
			return ApiResponseUtil.setC000004(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_UNDEFINED);
		}

		if (ApiResponseUtil.CONTENT_ID_MAXSIZE < contentId.length() || !StringUtils.isNumeric(contentId)) {
			String detail = MessageFormat.format(ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL, contentId);
			return ApiResponseUtil.setA003001(detail);
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
	 * コンテンツ情報削除　※既にデータが存在しない場合もエラーとしない
	 *
	 * @param contentId コンテンツID
	 */
	private void deleteContent(String contentId) {

		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		contentService.delete(content);
	}
}
