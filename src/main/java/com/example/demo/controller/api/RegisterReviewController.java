package com.example.demo.controller.api;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Content;
import com.example.demo.entity.Review;
import com.example.demo.entity.request.RegisterReviewRequest;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.RegisterReviewResponse;
import com.example.demo.service.ContentService;
import com.example.demo.service.ReviewService;


/**
 * コンテンツレビューコメント登録APIコントローラ．
 */
@RestController
public class RegisterReviewController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterReviewController.class);

	@Autowired
	private ContentService contentService;
	@Autowired
	private ReviewService reviewService;

	@RequestMapping("/api/review/register")
	public ApiResponse register(
			@RequestBody RegisterReviewRequest form) {

		String contentId = form.getContentId();
		String nickname = form.getReview().getNickname();
		String coment = form.getReview().getComent();

		ApiError apiError = validationCheck(form.getClientId(), contentId, nickname, coment);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if(!isExistingContent(contentId)) {
			apiError = ApiResponseUtil.setF010001(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		saveReview(contentId, nickname, coment);

		return new ApiResponse(new RegisterReviewResponse());
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @param contentId コンテンツID
	 * @param nickname ニックネーム
	 * @param coment コメント内容
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId, String contentId, String nickname, String coment) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		if (StringUtils.isEmpty(contentId)) {
			return ApiResponseUtil.setC000004(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_UNDEFINED);
		}

		if (ApiResponseUtil.CONTENT_ID_MAXSIZE < contentId.length() || !StringUtils.isNumeric(contentId)) {
			return ApiResponseUtil.setF010001(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
		}

		if (StringUtils.isEmpty(nickname)) {
			return ApiResponseUtil.setC000004(nickname, ApiResponseUtil.ERROR_DETAIL_NICKNAME_UNDEFINED);
		}

		if (StringUtils.isEmpty(coment)) {
			return ApiResponseUtil.setC000004(coment, ApiResponseUtil.ERROR_DETAIL_COMENT_UNDEFINED);
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
	 * コンテンツレビューコメント登録
	 *
	 * @param contentId コンテンツID
	 * @param nickname ニックネーム
	 * @param coment コメント内容
	 */
	private void saveReview(String contentId, String nickname, String coment) {

		Review review = new Review();
		review.setContentId(Long.valueOf(contentId));
		review.setNickname(nickname);
		review.setComent(coment);

		reviewService.save(review);
	}
}
