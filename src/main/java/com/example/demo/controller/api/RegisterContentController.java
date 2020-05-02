package com.example.demo.controller.api;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Content;
import com.example.demo.entity.request.RegisterContentRequest;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.RegisterContentResponse;
import com.example.demo.service.ContentService;


/**
 * コンテンツ情報登録APIコントローラ．
 */
@RestController
public class RegisterContentController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterContentController.class);


	@Autowired
	private ContentService contentService;

	@RequestMapping("/api/content/register")
	public ApiResponse register(
			@RequestBody RegisterContentRequest form) {

		String clientId = form.getClientId();
		String contentId = form.getContentId();
		String lang = form.getLang();

		ApiError apiError = validationCheck(clientId, contentId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if(isExistingContent(contentId, lang)) {
			String detail = MessageFormat.format(ApiResponseUtil.ERROR_DETAIL_CONTENTID_EXIST, contentId, lang);
			apiError = ApiResponseUtil.setA001001(detail);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		try {
			saveContent(contentId, lang, form.getType(), form.getCommunityId(), form.getTitle(), form.getContents(),
					form.getImage(), form.getDeliveryStartDate(), form.getDeliveryEndDate());
		} catch (ParseException e) {
			apiError = ApiResponseUtil.setC000001(e.getMessage());
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		return new ApiResponse(new RegisterContentResponse());
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
			return ApiResponseUtil.setA001001(detail);
		}

		return null;
	}

	/**
	 * 存在するコンテンツ情報か判定
	 *
	 * @param lang 言語コード
	 * @param contentId コンテンツID
	 * @return treu:存在する,false:存在しない
	 */
	private boolean isExistingContent(String contentId, String lang) {

		Optional<Content> content = contentService.findById(Long.valueOf(contentId), lang);
		if (content.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * コンテンツ情報新規登録
	 *
	 * @param contentId コンテンツID
	 * @param lang 言語コード
	 * @param type コンテンツ種別
	 * @param communityId コミュニティID
	 * @param title コンテンツタイトル
	 * @param contents コンテンツ内容
	 * @param image コンテンツ画像
	 * @param deliveryStartDate 配信開始日
	 * @param deliveryEndDate 配信終了日
	 * @throws ParseException
	 */
	private void saveContent(String contentId, String lang, String type, String communityId,
			String title, String contents, String image, String deliveryStartDate, String deliveryEndDate) throws ParseException {

		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		content.setLang(lang);
		content.setContentType(type);
		content.setContentTitle(title);
		content.setContent(contents);
		content.setContentImageUrl(image);
		if(StringUtils.isNotEmpty(deliveryStartDate)) {
			content.setDeliveryStartDate(DateUtils.parseDate(deliveryStartDate, ContentService.DATE_FORMAT));
		}
		if(StringUtils.isNotEmpty(deliveryEndDate)) {
			content.setDeliveryEndDate(DateUtils.parseDate(deliveryEndDate, ContentService.DATE_FORMAT));
		}
		contentService.save(content);
	}
}
