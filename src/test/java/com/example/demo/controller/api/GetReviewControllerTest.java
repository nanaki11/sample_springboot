package com.example.demo.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Content;
import com.example.demo.entity.Review;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetReviewResponse;
import com.example.demo.service.ContentService;
import com.example.demo.service.ReviewService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetReviewControllerTest {

	// RestController試験の設定
	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;

	// 試験対象の設定
	private static final String path = "/api/review/getlist";
	@InjectMocks
	private GetReviewController controller;
	@MockBean
	private ContentService contentService;
	@MockBean
	private ReviewService reviewService;

	// 試験準備
	private static final String  contentId = "00000000001";

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * API実行試験用
	 * @throws JsonProcessingException
	 */
	private String setupApiTest(MultiValueMap<String, String> params, List<Review> reviews) throws JsonProcessingException {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper.setSerializationInclusion(Include.NON_NULL);

		// 想定する返却値の設定
		GetReviewResponse result = new GetReviewResponse();
		result.setReview(reviews.stream()
				.map(review -> result.new Review(
						review.getReviewId(),
						review.getNickname(),
						review.getComent(),
						review.getAddDate()))
				.collect(Collectors.toList()));
		ApiResponse res = new ApiResponse(result);
		res.setStatus(0);
		// 想定の返却値をコンソールに出力
		String apiResponse = mapper.writeValueAsString(res);
		System.out.println(apiResponse);

		return apiResponse;
	}

	/**
	 * API実行試験
	 * 条件：0件取得の場合
	 */
	@Test
	public void main_success_test1() throws Exception {

		// 取得データ
		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		Optional<Content> contents = Optional.of(content);

		List<Review> reviews = new ArrayList<>();

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("contentId", contentId);
		String apiResponse = setupApiTest(params, reviews);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), isNull())).thenReturn(contents);
		when(reviewService.findByContentId(anyLong())).thenReturn(reviews);

		// api試験実施
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(contentService, times(1)).findById(anyLong(), isNull());
		verify(reviewService, times(1)).findByContentId(anyLong());
	}

	/**
	 * API実行試験
	 * 条件：1件以上取得の場合
	 */
	@Test
	public void main_success_test2() throws Exception {

		// 取得データ
		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		Optional<Content> contents = Optional.of(content);

		List<Review> reviews = new ArrayList<>();
		Review review1 = new Review();
		review1.setReviewId(1);
		review1.setNickname("ニックネーム1");
		review1.setComent("コメント内容1");
		review1.setAddDate(new Date());
		reviews.add(review1);
		Review review2 = new Review();
		review2.setReviewId(2);
		review2.setNickname("ニックネーム2");
		review2.setComent("コメント内容2");
		review2.setAddDate(new Date());
		reviews.add(review2);

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("contentId", contentId);
		String apiResponse = setupApiTest(params, reviews);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), isNull())).thenReturn(contents);
		when(reviewService.findByContentId(anyLong())).thenReturn(reviews);

		// api試験実施
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(contentService, times(1)).findById(anyLong(), isNull());
		verify(reviewService, times(1)).findByContentId(anyLong());
	}

	/**
	 * API実行試験
	 * 条件：異常終了（バリデーションチェックエラー）の場合
	 */
	@Test
	public void main_error_test1() throws Exception {

		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper.setSerializationInclusion(Include.NON_NULL);

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("contentId", "123456789012");

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(contentService, times(0)).findById(anyLong(), isNull());
		verify(reviewService, times(0)).findByContentId(anyLong());
	}

	/**
	 * API実行試験
	 * 条件：異常終了（存在するコンテンツIDか判定エラー）の場合
	 */
	@Test
	public void main_error_test2() throws Exception {

		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper.setSerializationInclusion(Include.NON_NULL);

		// 取得データ
		Optional<Content> contents = Optional.ofNullable(null);

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("contentId", contentId);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), isNull())).thenReturn(contents);

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(contentService, times(1)).findById(anyLong(), isNull());
		verify(reviewService, times(0)).findByContentId(anyLong());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：クライアントIDが null の場合
	 */
	@Test
	public void validationCheck_test1()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = null;

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000002, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_REQUEST, res.getMessage());
		assertEquals("不正なクライアントIDが使用されています。[clientId:null]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：クライアントIDが 空 の場合
	 */
	@Test
	public void validationCheck_test2()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = "";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000002, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_REQUEST, res.getMessage());
		assertEquals("不正なクライアントIDが使用されています。[clientId:]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：クライアントIDが 想定外 の場合
	 */
	@Test
	public void validationCheck_test3()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = "ABC";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000002, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_REQUEST, res.getMessage());
		assertEquals("不正なクライアントIDが使用されています。[clientId:ABC]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが null の場合
	 */
	@Test
	public void validationCheck_test4()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = null;

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("コンテンツIDが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが 空 の場合
	 */
	@Test
	public void validationCheck_test5()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = "";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("コンテンツIDが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが 数値以外含む11桁 の場合
	 */
	@Test
	public void validationCheck_test6()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = "1234567890a";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F009001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CONTENTID, res.getMessage());
		assertEquals("不正なコンテンツIDが使用されています。[contentId:1234567890a]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが 数値のみ12桁 の場合
	 */
	@Test
	public void validationCheck_test7()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = "123456789012";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F009001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CONTENTID, res.getMessage());
		assertEquals("不正なコンテンツIDが使用されています。[contentId:123456789012]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが 数値のみ11桁 の場合
	 */
	@Test
	public void validationCheck_test8()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = "12345678901";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertNull(res);
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：コンテンツIDが 数値のみ10桁 の場合
	 */
	@Test
	public void validationCheck_test9()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String contentId = "1234567890";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertNull(res);
	}


	/**
	 * 存在するコンテンツIDか判定処理の試験
	 * 条件：コンテンツマスタにコンテンツIDが 存在しない 場合
	 */
	@Test
	public void isExistingContent_test1()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		Optional<Content> contents = Optional.ofNullable(null);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), isNull())).thenReturn(contents);

		Method method = controller.getClass().getDeclaredMethod("isExistingContent", String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, contentId);

		assertFalse(res);
		verify(contentService, times(1)).findById(anyLong(), isNull());
	}

	/**
	 * 存在するコンテンツIDか判定処理の試験
	 * 条件：コンテンツマスタにコンテンツIDが 存在する 場合
	 */
	@Test
	public void isExistingContent_test2()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		Optional<Content> contents = Optional.of(content);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), isNull())).thenReturn(contents);

		Method method = controller.getClass().getDeclaredMethod("isExistingContent", String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, contentId);

		assertTrue(res);
		verify(contentService, times(1)).findById(anyLong(), isNull());
	}
}


