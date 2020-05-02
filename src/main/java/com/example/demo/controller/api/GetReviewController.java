package com.example.demo.controller.api;

import java.util.List;
import java.util.Optional;
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

import com.example.demo.entity.Content;
import com.example.demo.entity.Review;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetReviewResponse;
import com.example.demo.service.ContentService;
import com.example.demo.service.ReviewService;

/**
 * コンテンツレビューコメント取得APIコントローラ．
 */
@RestController
public class GetReviewController {

	private static final Logger logger = LoggerFactory.getLogger(GetReviewController.class);

	@Autowired
	private ContentService contentService;

	@Autowired
	private ReviewService reviewService;

	@RequestMapping("/api/review/getlist")
	public ApiResponse getList(
			@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "contentId") String contentId) {

		ApiError apiError = validationCheck(clientId, contentId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		if (!isExistingContent(contentId)) {
			apiError = ApiResponseUtil.setF009001(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		List<Review> reviews = reviewService.findByContentId(Long.valueOf(contentId));
		GetReviewResponse result = new GetReviewResponse();
		result.setReview(reviews.stream()
				.map(review -> result.new Review(
						review.getReviewId(),
						review.getNickname(),
						review.getComent(),
						review.getAddDate()))
				.collect(Collectors.toList()));

		return new ApiResponse(result);
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
			return ApiResponseUtil.setF009001(contentId, ApiResponseUtil.ERROR_DETAIL_CONTENTID_ILLEGAL);
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
}
