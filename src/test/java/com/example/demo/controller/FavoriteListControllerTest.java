/**
 *
 */
package com.example.demo.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Content;
import com.example.demo.entity.join.FacilityFavoriteArea;
import com.example.demo.service.ContentService;
import com.example.demo.service.FacilityService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
public class FavoriteListControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private ContentService contentService;

	@Mock
	private FacilityService facilityService;

	@InjectMocks
	private FavoriteListController controller;;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this.controller);
	}

	/**
	 * 正常系：データ取得の確認
	 */
	@Test
	void getListData() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", "1");

		//レスポンスの確認
		MvcResult result = mvc.perform(post("/mypage/favoriteList").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/favoriteList"))
				.andReturn();

		List<FacilityFavoriteArea> hotelList = (List<FacilityFavoriteArea>) result.getModelAndView().getModel()
				.get("hotelList");
		List<FacilityFavoriteArea> restaurantList = (List<FacilityFavoriteArea>) result.getModelAndView().getModel()
				.get("restaurantList");
		List<Content> contentList = (List<Content>) result.getModelAndView().getModel().get("contentList");

		assertEquals(2, hotelList.size());
		assertEquals("A0002", hotelList.get(0).getBusiness_cd());

		assertEquals(1, restaurantList.size());
		assertEquals("A0003", restaurantList.get(0).getBusiness_cd());

		assertEquals(1, contentList.size());
		assertEquals("12345678902", String.valueOf(contentList.get(0).getContentId()));

	}

	/**
	 * 正常系：バリデーションチェック：桁数
	 */
	@Test
	void validationCheck_digit() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", "123456789012");

		//レスポンスの確認
		MvcResult result = mvc.perform(post("/mypage/favoriteList").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/favoriteList"))
				.andReturn();

		assertTrue(result.getModelAndView().getModel().isEmpty());
	}

	/**
	 * 正常系：バリデーションチェック：文字種
	 */
	@Test
	void validationCheck_type() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", "AAA");

		//レスポンスの確認
		MvcResult result = mvc.perform(post("/mypage/favoriteList").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/favoriteList"))
				.andReturn();

		assertTrue(result.getModelAndView().getModel().isEmpty());

	}

	/**
	 * 異常系
	 */
	@Test
	void exceptionTest() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", null);

		//レスポンスの確認
		mvc.perform(post("/mypage/favoriteList").params(params))
				.andExpect(status().isBadRequest());

	}

}
