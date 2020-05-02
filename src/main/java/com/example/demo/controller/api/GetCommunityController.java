package com.example.demo.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CommunityInfo;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetCommunityResponse;
import com.example.demo.service.CommunityInfoService;

/**
 * 会員コミュニティ一覧取得APIコントローラ．
 */
@RestController
public class GetCommunityController {

	private static final Logger logger = LoggerFactory.getLogger(GetCommunityController.class);

	@Autowired
	private CommunityInfoService service;

	@RequestMapping("/api/community/getlist")
	public ApiResponse getList(
			@RequestParam(value = "clientId") String clientId) {

		ApiError apiError = validationCheck(clientId);
		if (null != apiError) {
			logger.error(ToStringBuilder.reflectionToString(apiError, ToStringStyle.DEFAULT_STYLE));
			return new ApiResponse(apiError);
		}

		List<CommunityInfo> communityInfoList = service.findAll();
		GetCommunityResponse result = new GetCommunityResponse();
		result.setCommunity(communityInfoList.stream()
				.map(communityInfo -> result.new Community(
						communityInfo.getCommunityId(),
						communityInfo.getCommunityName(),
						communityInfo.getComent(),
						communityInfo.getCommunityImage(),
						communityInfo.getExternalLink()))
				.collect(Collectors.toList()));

		return new ApiResponse(result);
	}

	/**
	 * バリデーションチェック
	 *
	 * @param clientId クライアントID
	 * @return ApiError　※エラーがなければ null を返却
	 */
	private ApiError validationCheck(String clientId) {

		if (!ApiResponseUtil.CLIENT_ID.equals(clientId)) {
			return ApiResponseUtil.setC000002(clientId);
		}

		return null;
	}
}
