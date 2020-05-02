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

import com.example.demo.entity.response.GetUseHistoryResponse.UseHistory;
import com.example.demo.service.UseHistoryService;

/**
 * 利用履歴画面コントローラークラス
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UseHistoryControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private UseHistoryService service;

	@InjectMocks
	private UseHistoryController controller;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * 正常系：データ取得の確認
	 */
	@Test
	void test1_getdata() throws Exception {

		MvcResult result = mvc.perform(get("/secure/mypage/useHistory"))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/useHistory"))
				.andReturn();

		List<UseHistory> list = (List<UseHistory>) result.getModelAndView().getModel().get("useHistorys");
		assertEquals(1, list.size());
	}

	/**
	 * 正常系：クライアントIDなしの確認
	 */
	@Test
	void test2_EmptyCustomerId() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", "");

		MvcResult result = mvc.perform(get("/secure/mypage/useHistory"))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/useHistory"))
				.andReturn();

		List<UseHistory> list = (List<UseHistory>) result.getModelAndView().getModel().get("useHistorys");
		assertTrue(list.isEmpty());

	}

	/**
	 * 異常系
	 */
	@Test
	void test3_Exception() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("customerId", "");

		MvcResult result = mvc.perform(get("/secure/mypage/useHistory"))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("mypage/useHistory"))
				.andReturn();

	}

}
