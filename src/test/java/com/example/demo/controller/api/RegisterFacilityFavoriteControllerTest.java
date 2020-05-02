package com.example.demo.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Facility;
import com.example.demo.entity.FacilityFavorite;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.RegisterFacilityFavoriteResponse;
import com.example.demo.service.FacilityFavoriteService;
import com.example.demo.service.FacilityService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterFacilityFavoriteControllerTest {
	// RestController試験の設定
	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;

	// 試験対象の設定
	private static final String path = "/api/favorite/facility/register";
	@InjectMocks
	private RegisterFacilityFavoriteController controller;
	@MockBean
	private FacilityService facilityService;
	@MockBean
	private FacilityFavoriteService facilityFavoriteService;

	// 試験準備
	private static final String  customerId = "00000000001";
	private static final String  businessCd = "ABCDE";

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
	 */
	@Test
	public void main_success_test1() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", customerId);
		params.add("businessCd", businessCd);

		// 取得データ
		List<Facility> facilities = new ArrayList<>();
		Facility facility = new Facility();
		facility.setBusinessCd(businessCd);
		facilities.add(facility);

		// 想定する返却値の設定
		ApiResponse res = new ApiResponse(new RegisterFacilityFavoriteResponse());
		res.setStatus(0);

		// 想定の返却値をコンソールに出力
		String apiResponse = mapper.writeValueAsString(res);
		System.out.println(apiResponse);

		// 取得データのモック処理
		when(facilityService.findByBusinessCd(anyString())).thenReturn(facilities);
		// 登録データのモック処理
		when(facilityFavoriteService.save(any(FacilityFavorite.class))).thenReturn(new FacilityFavorite());

		// api試験実施
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(content().string(apiResponse));

		verify(facilityService, times(1)).findByBusinessCd(anyString());
		verify(facilityFavoriteService, times(1)).save(any(FacilityFavorite.class));
	}

	/**
	 * API実行試験
	 * 条件：異常終了（バリデーションチェックエラー）の場合
	 */
	@Test
	public void main_error_test1() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", customerId);
		params.add("businessCd", "123456");

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(facilityService, times(0)).findByBusinessCd(anyString());
		verify(facilityFavoriteService, times(0)).save(any(FacilityFavorite.class));
	}

	/**
	 * API実行試験
	 * 条件：異常終了（存在する事業者コードか判定エラー）の場合
	 */
	@Test
	public void main_error_test2() throws Exception {

		setupApiTest();

		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("clientId", ApiResponseUtil.CLIENT_ID);
		params.add("customerId", customerId);
		params.add("businessCd", businessCd);

		// 取得データ
		List<Facility> facilities = new ArrayList<>();

		// 取得データのモック処理
		when(facilityService.findByBusinessCd(anyString())).thenReturn(facilities);

		// api試験実施 TODO:目視確認
		mvc.perform(post(path).params(params))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

		verify(facilityService, times(1)).findByBusinessCd(anyString());
		verify(facilityFavoriteService, times(0)).save(any(FacilityFavorite.class));
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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

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
		String customerId = "123456789a1";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F005001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CUSTOMERID, res.getMessage());
		assertEquals("不正な業務IDが使用されています。[customerId:123456789a1]", res.getDetail());
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

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F005001, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_CUSTOMERID, res.getMessage());
		assertEquals("不正な業務IDが使用されています。[customerId:123456789012]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値のみ11桁 の場合、かつ、事業所コードが null の場合
	 */
	@Test
	public void validationCheck_test8()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "12345678901";
		String businessCd = null;

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("事業所コードが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：業務IDが 数値のみ10桁 の場合、かつ、事業所コードが 空 の場合
	 */
	@Test
	public void validationCheck_test9()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String customerId = "1234567890";
		String businessCd = "";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_C000004, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_PARAMETER, res.getMessage());
		assertEquals("事業所コードが未設定です。", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：事業所コードが 6文字 の場合
	 */
	@Test
	public void validationCheck_test10()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String businessCd = "１２３４５６";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
		assertEquals(ApiResponseUtil.ERROR_CODE_F005002, res.getCode());
		assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_BUSINESSCD, res.getMessage());
		assertEquals("不正な事業所コードが使用されています。[businessCd:１２３４５６]", res.getDetail());
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：事業所コードが 5文字 の場合
	 */
	@Test
	public void validationCheck_test11()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String businessCd = "１２３４５";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertNull(res);
	}

	/**
	 * バリデーションチェック処理の試験
	 * 条件：事業所コードが 4文字 の場合
	 */
	@Test
	public void validationCheck_test12()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 試験データの設定
		String clientId = ApiResponseUtil.CLIENT_ID;
		String businessCd = "１２３４";

		Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class, String.class, String.class);
		method.setAccessible(true);
		ApiError res = (ApiError) method.invoke(controller, clientId, customerId, businessCd);

		assertNull(res);
	}

	/**
	 * 存在する事業所コードか判定処理の試験
	 * 条件：事業所マスタに事業所コードが 存在しない 場合
	 */
	@Test
	public void isExistingBusinessCd_test1()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		List<Facility> facilities = new ArrayList<>();

		// 取得データのモック処理
		when(facilityService.findByBusinessCd(anyString())).thenReturn(facilities);

		Method method = controller.getClass().getDeclaredMethod("isExistingBusinessCd", String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, businessCd);

		assertFalse(res);
		verify(facilityService, times(1)).findByBusinessCd(anyString());
	}

	/**
	 * 存在する事業所コードか判定処理の試験
	 * 条件：事業所マスタに事業所コードが 存在する 場合
	 */
	@Test
	public void isExistingBusinessCd_test2()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 取得データ
		List<Facility> facilities = new ArrayList<>();
		Facility facility = new Facility();
		facility.setBusinessCd(businessCd);
		facilities.add(facility);

		// 取得データのモック処理
		when(facilityService.findByBusinessCd(anyString())).thenReturn(facilities);

		Method method = controller.getClass().getDeclaredMethod("isExistingBusinessCd", String.class);
		method.setAccessible(true);
		boolean res = (boolean) method.invoke(controller, businessCd);

		assertTrue(res);
		verify(facilityService, times(1)).findByBusinessCd(anyString());
	}

	/**
	 * 施設お気に入り登録処理の試験
	 */
	@Test
	public void saveFacilityFavorite_test()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 登録データのモック処理
		ArgumentCaptor<FacilityFavorite> captor = ArgumentCaptor.forClass(FacilityFavorite.class);
		when(facilityFavoriteService.save(captor.capture())).thenReturn(new FacilityFavorite());

		Method method = controller.getClass().getDeclaredMethod("saveFacilityFavorite", String.class, String.class);
		method.setAccessible(true);
		method.invoke(controller, customerId, businessCd);

		// 登録処理の引数（施設お気に入り情報）が想定通りか確認する
		FacilityFavorite captoredEntity = captor.getValue();
		assertEquals(00000000001, captoredEntity.getCustomerId());
		assertEquals("ABCDE", captoredEntity.getBusinessCd());
	}
}


