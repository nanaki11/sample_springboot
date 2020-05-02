package com.example.demo.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entity.Content;
import com.example.demo.entity.request.RegisterContentRequest;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.RegisterReviewResponse;
import com.example.demo.service.ContentService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterContentControllerTest {

	// RestController試験の設定
	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;

	// 試験対象の設定
	private static final String path = "/api/content/register";
	@InjectMocks
	private RegisterContentController controller;
	@MockBean
	private ContentService contentService;

	// 試験準備
	private static final String  contentId = "00000000001";
	private static final String  lang = "en";

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * API実行試験用
	 * @throws Exception
	 */
	private void setupApiTest() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * API実行試験
	 * 条件：全パラメータを設定
	 */
	@Test
	public void main_success_test1() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		RegisterContentRequest req = new RegisterContentRequest();
		req.setClientId(ApiResponseUtil.CLIENT_ID);
		req.setLang(lang);
		req.setContentId(contentId);
		req.setType("type");
		req.setCommunityId("12345");
		req.setTitle("title");
		req.setContents("contents");
		req.setImage("image");
		req.setDeliveryStartDate("2022/01/11");
		req.setDeliveryEndDate("2022/02/22");
		String apiRequest = mapper.writeValueAsString(req);

		// 取得データ
		Optional<Content> contents = Optional.ofNullable(null);

		// 想定する返却値の設定
		ApiResponse res = new ApiResponse(new RegisterReviewResponse());
		res.setStatus(0);

		// 想定の返却値をコンソールに出力
		String apiResponse = mapper.writeValueAsString(res);
		System.out.println(apiResponse);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), anyString())).thenReturn(contents);
		// 登録データのモック処理
		when(contentService.save(any(Content.class))).thenReturn(new Content());

		// api試験実施
		mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(apiRequest))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(contentService, times(1)).findById(anyLong(), anyString());
		verify(contentService, times(1)).save(any(Content.class));
	}

	/**
	 * API実行試験
	 * 条件：異常終了（バリデーションチェックエラー）の場合
	 */
	@Test
	public void main_error_test1() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		RegisterContentRequest req = new RegisterContentRequest();
		req.setClientId(ApiResponseUtil.CLIENT_ID);
		req.setLang(lang);
		req.setContentId("contentId");
		req.setType("type");
		req.setCommunityId("12345");
		req.setTitle("title");
		req.setContents("contents");
		req.setImage("image");
		req.setDeliveryStartDate("2022/01/11");
		req.setDeliveryEndDate("2022/02/22");
		String apiRequest = mapper.writeValueAsString(req);

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(apiRequest))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(contentService, times(0)).findById(anyLong(), anyString());
		verify(contentService, times(0)).save(any(Content.class));
	}

	/**
	 * API実行試験
	 * 条件：異常終了（存在するコンテンツ情報か判定エラー）の場合
	 */
	@Test
	public void main_error_test2() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		RegisterContentRequest req = new RegisterContentRequest();
		req.setClientId(ApiResponseUtil.CLIENT_ID);
		req.setLang(lang);
		req.setContentId(contentId);
		req.setType("type");
		req.setCommunityId("12345");
		req.setTitle("title");
		req.setContents("contents");
		req.setImage("image");
		req.setDeliveryStartDate("2022/01/11");
		req.setDeliveryEndDate("2022/02/22");
		String apiRequest = mapper.writeValueAsString(req);

		// 取得データ
		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		Optional<Content> contents = Optional.of(content);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), anyString())).thenReturn(contents);

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(apiRequest))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(contentService, times(1)).findById(anyLong(), anyString());
		verify(contentService, times(0)).save(any(Content.class));
	}


	/**
	 * API実行試験
	 * 条件：異常終了（登録失敗：ParseException）の場合
	 */
	@Test
	public void main_error_test3() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		RegisterContentRequest req = new RegisterContentRequest();
		req.setClientId(ApiResponseUtil.CLIENT_ID);
		req.setLang(lang);
		req.setContentId(contentId);
		req.setType("type");
		req.setCommunityId("12345");
		req.setTitle("title");
		req.setContents("contents");
		req.setImage("image");
		req.setDeliveryStartDate("2022111");
		req.setDeliveryStartDate("2022222");
		String apiRequest = mapper.writeValueAsString(req);

		// 取得データ
		Optional<Content> contents = Optional.ofNullable(null);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), anyString())).thenReturn(contents);

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(apiRequest))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(contentService, times(1)).findById(anyLong(), anyString());
		verify(contentService, times(0)).save(any(Content.class));
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
		String contentId = "123456789a1";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, contentId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_A001001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CONTENTID, res.getMessage());
		assertEquals("不正なコンテンツIDが使用されています。[contentId:123456789a1]", res.getDetail());
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
		assertEquals(ApiResponseUtil.ERROR_CODE_A001001, res.getCode());
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
	 * 条件：コンテンツIDが 数値のみ11桁 の場合
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
	 * 存在するコンテンツ情報か判定処理の試験
	 * 条件：コンテンツマスタにコンテンツ情報が 存在しない 場合
	 */
	@Test
	public void isExistingContent_test1()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		Optional<Content> contents = Optional.ofNullable(null);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), anyString())).thenReturn(contents);

		Method method = controller.getClass().getDeclaredMethod("isExistingContent", String.class, String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, contentId, lang);

		assertFalse(res);
		verify(contentService, times(1)).findById(anyLong(), anyString());
	}

	/**
	 * 存在するコンテンツ情報か判定処理の試験
	 * 条件：コンテンツマスタにコンテンツ情報が 存在する 場合
	 */
	@Test
	public void isExistingContent_test2()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		Content content = new Content();
		content.setContentId(Long.valueOf(contentId));
		Optional<Content> contents = Optional.of(content);

		// 取得データのモック処理
		when(contentService.findById(anyLong(), anyString())).thenReturn(contents);

		Method method = controller.getClass().getDeclaredMethod("isExistingContent", String.class, String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, contentId, lang);

		assertTrue(res);
		verify(contentService, times(1)).findById(anyLong(), anyString());
	}

	/**
	 * コンテンツ情報新規登録処理の試験
	 * 条件：配信開始日・終了日が 未設定 の場合
	 */
	@Test
	public void saveContent_test1()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		// 試験データの設定
		String deliveryStartDate = "";
		String deliveryEndDate = null;

		// 登録データのモック処理
		ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
		when(contentService.save(captor.capture())).thenReturn(new Content());

		Method method = controller.getClass().getDeclaredMethod("saveContent", String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class);
		method.setAccessible(true);
		method.invoke(controller, contentId, lang, "コンテンツ種別", "12345", "コンテンツタイトル", "コンテンツ内容", "コンテンツ画像URL", deliveryStartDate, deliveryEndDate);

		// 登録処理の引数（コンテンツ情報）が想定通りか確認する
		Content captoredEntity = captor.getValue();
		assertEquals(00000000001, captoredEntity.getContentId());
		assertEquals("en", captoredEntity.getLang());
		assertEquals("コンテンツ種別", captoredEntity.getContentType());
		assertEquals("コンテンツタイトル", captoredEntity.getContentTitle());
		assertEquals("コンテンツ内容", captoredEntity.getContent());
		assertEquals("コンテンツ画像URL", captoredEntity.getContentImageUrl());
		assertNull(captoredEntity.getDeliveryStartDate());
		assertNull(captoredEntity.getDeliveryEndDate());

	}

	/**
	 * コンテンツ情報新規登録処理の試験
	 */
	@Test
	public void saveContent_test2()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		// 試験データの設定
		String deliveryStartDate = "2022/01/01";
		String deliveryEndDate = "2022/02/02";

		// 登録データのモック処理
		ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
		when(contentService.save(captor.capture())).thenReturn(new Content());

		Method method = controller.getClass().getDeclaredMethod("saveContent", String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class);
		method.setAccessible(true);
		method.invoke(controller, contentId, lang, "コンテンツ種別", "12345", "コンテンツタイトル", "コンテンツ内容", "コンテンツ画像URL", deliveryStartDate, deliveryEndDate);

		// 登録処理の引数（コンテンツ情報）が想定通りか確認する
		Content captoredEntity = captor.getValue();
		assertEquals(00000000001, captoredEntity.getContentId());
		assertEquals("en", captoredEntity.getLang());
		assertEquals("コンテンツ種別", captoredEntity.getContentType());
		assertEquals("コンテンツタイトル", captoredEntity.getContentTitle());
		assertEquals("コンテンツ内容", captoredEntity.getContent());
		assertEquals("コンテンツ画像URL", captoredEntity.getContentImageUrl());
		assertEquals(DateUtils.parseDate("2022/01/01", ContentService.DATE_FORMAT), captoredEntity.getDeliveryStartDate());
		assertEquals(DateUtils.parseDate("2022/02/02", ContentService.DATE_FORMAT), captoredEntity.getDeliveryEndDate());

	}
}


