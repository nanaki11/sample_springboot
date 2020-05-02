/**
 *
 */
package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

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

import com.example.demo.entity.response.GetMemberRelationResponse.MemberRelation;
import com.example.demo.service.GetMemberInfoService;

/**
 * 会員情報画面コントローラーテストクラス
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CancelRelationControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private GetMemberInfoService service;

	@InjectMocks
	private CancelRelationController controller;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test1_getdata() throws Exception {

		MvcResult result = mvc.perform(get("/memberInfo/getRelation"))
				.andExpect(status().isOk())
				.andExpect(view().name("mypage/MemberInfo"))
				.andReturn();
		List<MemberRelation> memberRelationList = (List<MemberRelation>) result.getModelAndView().getModel()
				.get("memberRelationList");

		Map<Long, String> relationImageMap = (Map<Long, String>) result.getModelAndView().getModel()
				.get("relationImageMap");

		assertEquals(1, memberRelationList.size());
		assertEquals(1, relationImageMap.size());

	}

}
