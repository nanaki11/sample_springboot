package com.example.demo.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

import com.example.demo.entity.CustomerImage;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetProfileimageResponse;
import com.example.demo.service.CustomerImageService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetProfileimageControllerTest {

	// RestController試験の設定
	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;

	// 試験対象の設定
	private static final String path = "/api/profileimage/get";
	@InjectMocks
	private GetProfileimageController controller;
	@MockBean
    private CustomerImageService service;

	// 試験準備
	private static final String  customerId = "00000000001";

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * API実行試験用
	 * @throws JsonProcessingException
	 */
	private String setupApiTest(MultiValueMap<String, String> params, CustomerImage profileimage) throws JsonProcessingException {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();

		// 想定する返却値の設定
		GetProfileimageResponse result = new GetProfileimageResponse();
		result.setImage(profileimage.getImageUrl());
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

		CustomerImage profileimage = new CustomerImage();

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", customerId);
		String apiResponse = setupApiTest(params, profileimage);

		// 取得データのモック処理
		when(service.findByCustomerId(anyLong())).thenReturn(profileimage);

		// api試験実施
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(service, times(1)).findByCustomerId(anyLong());
	}

	/**
	 * API実行試験
	 * 条件：1件以上取得の場合
	 */
	@Test
	public void main_success_test2() throws Exception {

		// 取得データ
		CustomerImage profileimage = new CustomerImage();
		profileimage.setCustomerId(Long.valueOf(customerId));
		profileimage.setImageUrl("imageUrl");

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", customerId);
		String apiResponse = setupApiTest(params, profileimage);

		// 取得データのモック処理
		when(service.findByCustomerId(anyLong())).thenReturn(profileimage);

		// api試験実施
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(service, times(1)).findByCustomerId(anyLong());
	}

	/**
	 * API実行試験
	 * 条件：異常終了の場合
	 */
	@Test
	public void main_error_test1() throws Exception {

		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper.setSerializationInclusion(Include.NON_NULL);

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", "123456789012");

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
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
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

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
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

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
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000002, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_REQUEST, res.getMessage());
		assertEquals("不正なクライアントIDが使用されています。[clientId:ABC]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが null の場合
	 */
	@Test
	public void validationCheck_test4()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = null;

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("業務IDが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 空 の場合
	 */
	@Test
	public void validationCheck_test5()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("業務IDが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値以外含む11桁 の場合
	 */
	@Test
	public void validationCheck_test6()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "1234567890a";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F008001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CUSTOMERID, res.getMessage());
		assertEquals("不正な業務IDが使用されています。[customerId:1234567890a]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値のみ12桁 の場合
	 */
	@Test
	public void validationCheck_test7()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "123456789012";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F008001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CUSTOMERID, res.getMessage());
		assertEquals("不正な業務IDが使用されています。[customerId:123456789012]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値のみ11桁 の場合
	 */
	@Test
	public void validationCheck_test8()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "12345678901";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertNull(res);
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値のみ10桁 の場合
	 */
	@Test
	public void validationCheck_test9()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "1234567890";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId);

		assertNull(res);
	}
}


