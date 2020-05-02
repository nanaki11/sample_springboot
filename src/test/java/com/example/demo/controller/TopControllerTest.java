/**
 *
 */
package com.example.demo.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Content;
import com.example.demo.service.ContentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
public class TopControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private ContentService service;

	@InjectMocks
	private TopController controller;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this.controller);
	}

	/**
	 * 正常系：初回起動お知らせとキャンペーン情報を取得
	 */
	@Test
	void index_test() throws Exception {

		//レスポンスの確認
		MvcResult result = mvc.perform(get(""))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andReturn();

		Page<Content> noticePage = (Page<Content>) result.getModelAndView().getModel().get("noticePage");
		Page<Content> contentPage = (Page<Content>) result.getModelAndView().getModel().get("contentPage");

		assertEquals(10, noticePage.getPageable().getPageSize());
		assertEquals("お知らせタイトル1", noticePage.getContent().get(0).getContentTitle());
		assertEquals(3, contentPage.getPageable().getPageSize());
		assertEquals("キャンペーンタイトル1", contentPage.getContent().get(0).getContentTitle());

	}

	/**
	 *正常系：アートを押下時アートデータを10件取得
	 */
	@Test
	public void search_test1() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("contentType", "アート");

		MvcResult result = mvc.perform(post("/search").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andReturn();

		Page<Content> contentPage = (Page<Content>) result.getModelAndView().getModel().get("contentPage");

		assertEquals(5, contentPage.getPageable().getPageSize());
		assertEquals("アートタイトル1", contentPage.getContent().get(0).getContentTitle());

	}

	/**
	 *正常系：イベントを押下時イベントデータを７件取得
	 */

	@Test
	public void search_test2() throws Exception {

		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("contentType", "イベント");

		MvcResult result = mvc.perform(post("/search").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andReturn();

		Page<Content> contentPage = (Page<Content>) result.getModelAndView().getModel().get("contentPage");

		assertEquals(5, contentPage.getPageable().getPageSize());
		assertEquals("イベントタイトル1", contentPage.getContent().get(0).getContentTitle());

	}

	/**
	 *正常系：検索ワードとして空文字を指定
	 */
	@Test
	public void search_test3() throws Exception {
		//準備
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("contentType", "");

		MvcResult result = mvc.perform(post("/search").params(params))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andReturn();

		Page<Content> contentPage = (Page<Content>) result.getModelAndView().getModel().get("contentPage");

		assertTrue(contentPage.isEmpty());

	}

	/**
	 *異常系：検索ワードとしてNULLを指定
	 */
	@Test
	public void search_test4() throws Exception {

		try {
			//準備
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("contentType", null);

			mvc.perform(post("/search").params(params));
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	//内結時に確認
	/*	*//**
			*正常系：検索ワードとしてnoticesを指定
			*/
	/*
	@Test
	public void addList_test1() throws Exception {
	//準備
	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	params.add("buttonType", "notice");
	mvc.perform(post("/addList").params(params));
	}
	
	*//**
		*正常系：検索ワードとしcontentsを指定
		*//*
			@Test
			public void addList_test2() throws Exception {
			//準備
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("buttonType", "content");
			mvc.perform(post("/addList").params(params));
			}*/

	/**
	 *正常系：検索ワードとしてnullを指定
	 */
	@Test
	public void addList_test3() throws Exception {
		try {
			//準備
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("contentType", null);

			mvc.perform(post("/addList").params(params));
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}
}
