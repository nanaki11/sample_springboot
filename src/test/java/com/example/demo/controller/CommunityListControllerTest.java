/**
 *
 */
package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.entity.CommunityInfo;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.entity.UserInfoBean.Community;
import com.example.demo.form.CommunityListForm;
import com.example.demo.service.CommunityInfoService;

/**
 * コミュニティ一覧画面の単体試験
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommunityListControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private MockHttpSession session;
	@Autowired
	private WebApplicationContext wac;

	// 試験対象の設定
	private static final String path = "/communitylist/index";
	private static final String path_join = "/communitylist/join";
	private static final String path_withdraw = "/communitylist/withdraw";

	@InjectMocks
	private CommunityListController controller;
	@MockBean
	private CommunityInfoService communityInfoService;

	// 試験準備
	List<CommunityInfo> communityInfoList = new LinkedList();

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		session = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

		// 取得データ
		CommunityInfo communityInfo1 = new CommunityInfo();
		communityInfo1.setCommunityId(1);
		communityInfo1.setCommunityImage("コミュニティ画像URL1");
		communityInfo1.setCommunityName("コミュニティ名称1");
		communityInfo1.setComent("コミュニティ内容1");
		communityInfo1.setExternalLink("外部サイトURL1");
		communityInfoList.add(communityInfo1);
		CommunityInfo communityInfo2 = new CommunityInfo();
		communityInfo2.setCommunityId(2);
		communityInfo2.setCommunityImage("コミュニティ画像URL2");
		communityInfo2.setCommunityName("コミュニティ名称2");
		communityInfo2.setComent("コミュニティ内容2");
		communityInfo2.setExternalLink("外部サイトURL2");
		communityInfoList.add(communityInfo2);
		CommunityInfo communityInfo3 = new CommunityInfo();
		communityInfo3.setCommunityId(3);
		communityInfo3.setCommunityImage("コミュニティ画像URL3");
		communityInfo3.setCommunityName("コミュニティ名称3");
		communityInfo3.setComent("コミュニティ内容3");
		communityInfo3.setExternalLink("外部サイトURL3");
		communityInfoList.add(communityInfo3);
	}

	/**
	 * 正常系：コミュニティ一覧画面の表示
	 * 条件：未ログインの場合
	 */
	@Test
	public void index_test1() throws Exception {

		//レスポンスの確認
		MvcResult result = mvc.perform(post(path).session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("communitylist"))
				.andReturn();

		List<CommunityListForm> testList = (List<CommunityListForm>) result.getModelAndView().getModel().get("communityList");

		assertNull(testList);
	}

	/**
	 * 正常系：コミュニティ一覧画面の表示
	 * 条件：ログイン済みの場合
	 */
	@Test
	public void index_test2() throws Exception {
		// 試験パラメータの設定
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		Community communityId1 = (new UserInfoBean()).new Community();
		communityId1.setId(1);
		communityIdList.add(communityId1);
		UserInfoBean.Community communityId2 = (new UserInfoBean()).new Community();
		communityId2.setId(2);
		communityIdList.add(communityId2);
		userInfo.setCommunity(communityIdList);
		session.setAttribute("userInfo", userInfo);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();
		CommunityListForm community1 = new CommunityListForm();
		community1.setCommunityId(1);
		community1.setCommunityImage("コミュニティ画像URL1");
		community1.setCommunityName("コミュニティ名称1");
		community1.setComent("コミュニティ内容1");
		community1.setExternalLink("外部サイトURL1");
		community1.setJoined(true);
		communityList.add(community1);
		CommunityListForm community2 = new CommunityListForm();
		community2.setCommunityId(2);
		community2.setCommunityImage("コミュニティ画像URL2");
		community2.setCommunityName("コミュニティ名称2");
		community2.setComent("コミュニティ内容2");
		community2.setExternalLink("外部サイトURL2");
		community2.setJoined(true);
		communityList.add(community2);
		CommunityListForm community3 = new CommunityListForm();
		community3.setCommunityId(3);
		community3.setCommunityImage("コミュニティ画像URL3");
		community3.setCommunityName("コミュニティ名称3");
		community3.setComent("コミュニティ内容3");
		community3.setExternalLink("外部サイトURL3");
		community3.setJoined(false);
		communityList.add(community3);

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		//レスポンスの確認
		MvcResult result = mvc.perform(post(path).session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("communitylist"))
				.andReturn();

		List<CommunityListForm> testList = (List<CommunityListForm>) result.getModelAndView().getModel().get("communityList");

		assertEquals(communityList, testList);
	}

	/**
	 * 正常系：コミュニティ参加完了ダイアログの表示
	 */
	@Test
	public void join_test1() throws Exception {
		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("joinId", "3");
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		Community communityId1 = (new UserInfoBean()).new Community();
		communityId1.setId(1);
		communityIdList.add(communityId1);
		UserInfoBean.Community communityId2 = (new UserInfoBean()).new Community();
		communityId2.setId(2);
		communityIdList.add(communityId2);
		userInfo.setCommunity(communityIdList);
		session.setAttribute("userInfo", userInfo);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();
		CommunityListForm community1 = new CommunityListForm();
		community1.setCommunityId(1);
		community1.setCommunityImage("コミュニティ画像URL1");
		community1.setCommunityName("コミュニティ名称1");
		community1.setComent("コミュニティ内容1");
		community1.setExternalLink("外部サイトURL1");
		community1.setJoined(true);
		communityList.add(community1);
		CommunityListForm community2 = new CommunityListForm();
		community2.setCommunityId(2);
		community2.setCommunityImage("コミュニティ画像URL2");
		community2.setCommunityName("コミュニティ名称2");
		community2.setComent("コミュニティ内容2");
		community2.setExternalLink("外部サイトURL2");
		community2.setJoined(true);
		communityList.add(community2);
		CommunityListForm community3 = new CommunityListForm();
		community3.setCommunityId(3);
		community3.setCommunityImage("コミュニティ画像URL3");
		community3.setCommunityName("コミュニティ名称3");
		community3.setComent("コミュニティ内容3");
		community3.setExternalLink("外部サイトURL3");
		community3.setJoined(true);
		communityList.add(community3);

		UserInfoBean userSession = userInfo;
		Community communityId3 = (new UserInfoBean()).new Community();
		communityId3.setId(3);
		userSession.getCommunity().add(communityId3);

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		//レスポンスの確認
		MvcResult result = mvc.perform(post(path_join).params(params).session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("communitylist"))
				.andReturn();

		List<CommunityListForm> testList = (List<CommunityListForm>) result.getModelAndView().getModel().get("communityList");
		CommunityListForm testCommunity = (CommunityListForm) result.getModelAndView().getModel().get("joinCommunity");
		UserInfoBean testUser = (UserInfoBean) session.getAttribute("userInfo");

		assertEquals(communityList, testList);
		assertEquals(community3, testCommunity);
		assertEquals(userSession, testUser);
	}

	/**
	 * 異常系：コミュニティ参加完了ダイアログの表示
	 * 条件：会員情報が取得できなかった場合
	 */
	@Test
	public void join_test2() throws Exception {
		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("joinId", "3");

		//レスポンスの確認
		mvc.perform(post(path_join).params(params).session(session))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:" + ErrorController.ERROR_VIEW));
	}

	/**
	 * 正常系：コミュニティ退会完了ダイアログの表示
	 */
	@Test
	public void withdraw_test1() throws Exception {
		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("withdrawId", "1");
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		Community communityId1 = (new UserInfoBean()).new Community();
		communityId1.setId(1);
		communityIdList.add(communityId1);
		UserInfoBean.Community communityId2 = (new UserInfoBean()).new Community();
		communityId2.setId(2);
		communityIdList.add(communityId2);
		userInfo.setCommunity(communityIdList);
		session.setAttribute("userInfo", userInfo);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();
		CommunityListForm community1 = new CommunityListForm();
		community1.setCommunityId(1);
		community1.setCommunityImage("コミュニティ画像URL1");
		community1.setCommunityName("コミュニティ名称1");
		community1.setComent("コミュニティ内容1");
		community1.setExternalLink("外部サイトURL1");
		community1.setJoined(false);
		communityList.add(community1);
		CommunityListForm community2 = new CommunityListForm();
		community2.setCommunityId(2);
		community2.setCommunityImage("コミュニティ画像URL2");
		community2.setCommunityName("コミュニティ名称2");
		community2.setComent("コミュニティ内容2");
		community2.setExternalLink("外部サイトURL2");
		community2.setJoined(true);
		communityList.add(community2);
		CommunityListForm community3 = new CommunityListForm();
		community3.setCommunityId(3);
		community3.setCommunityImage("コミュニティ画像URL3");
		community3.setCommunityName("コミュニティ名称3");
		community3.setComent("コミュニティ内容3");
		community3.setExternalLink("外部サイトURL3");
		community3.setJoined(false);
		communityList.add(community3);

		UserInfoBean userSession = userInfo;
		userSession.getCommunity().remove(0);

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		//レスポンスの確認
		MvcResult result = mvc.perform(post(path_withdraw).params(params).session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("communitylist"))
				.andReturn();

		List<CommunityListForm> testList = (List<CommunityListForm>) result.getModelAndView().getModel().get("communityList");
		CommunityListForm testCommunity = (CommunityListForm) result.getModelAndView().getModel().get("withdrawCommunity");
		UserInfoBean testUser = (UserInfoBean) session.getAttribute("userInfo");

		assertEquals(communityList, testList);
		assertEquals(community1, testCommunity);
		assertEquals(userSession, testUser);
	}

	/**
	 * 異常系：コミュニティ退会完了ダイアログの表示
	 * 条件：会員情報が取得できなかった場合
	 */
	@Test
	public void withdraw_test2() throws Exception {
		// 試験パラメータの設定
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("withdrawId", "1");

		//レスポンスの確認
		mvc.perform(post(path_withdraw).params(params).session(session))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:" + ErrorController.ERROR_VIEW));
	}

	/**
	 * コミュニティ一覧の取得処理の試験
	 * 条件：取得したコミュニティ付帯情報が 0件 の場合
	 */
	@Test
	public void getCommunityList_test1() throws Exception {

		// 試験パラメータの設定
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		userInfo.setCommunity(communityIdList);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt())).thenReturn(null);

		Method method = controller.getClass().getDeclaredMethod("getCommunityList", UserInfoBean.class);
		method.setAccessible(true);
		List<CommunityListForm> testList = (List<CommunityListForm>) method.invoke(controller, userInfo);

		assertEquals(communityList, testList);
	}

	/**
	 * コミュニティ一覧のセット処理の試験
	 * 条件：取得したコミュニティ付帯情報が 0件 の場合
	 */
	@Test
	public void getCommunityList_test2() throws Exception {

		// 試験パラメータの設定
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		userInfo.setCommunity(communityIdList);

		// 取得データ
		communityInfoList = new ArrayList<>();

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		Method method = controller.getClass().getDeclaredMethod("getCommunityList", UserInfoBean.class);
		method.setAccessible(true);
		List<CommunityListForm> testList = (List<CommunityListForm>) method.invoke(controller, userInfo);

		assertEquals(communityList, testList);
	}

	/**
	 * コミュニティ一覧のセット処理の試験
	 * 条件：参加コミュニティが 0件 の場合
	 */
	@Test
	public void getCommunityList_test3() throws Exception {

		// 試験パラメータの設定
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		userInfo.setCommunity(communityIdList);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();
		CommunityListForm community1 = new CommunityListForm();
		community1.setCommunityId(1);
		community1.setCommunityImage("コミュニティ画像URL1");
		community1.setCommunityName("コミュニティ名称1");
		community1.setComent("コミュニティ内容1");
		community1.setExternalLink("外部サイトURL1");
		community1.setJoined(false);
		communityList.add(community1);
		CommunityListForm community2 = new CommunityListForm();
		community2.setCommunityId(2);
		community2.setCommunityImage("コミュニティ画像URL2");
		community2.setCommunityName("コミュニティ名称2");
		community2.setComent("コミュニティ内容2");
		community2.setExternalLink("外部サイトURL2");
		community2.setJoined(false);
		communityList.add(community2);
		CommunityListForm community3 = new CommunityListForm();
		community3.setCommunityId(3);
		community3.setCommunityImage("コミュニティ画像URL3");
		community3.setCommunityName("コミュニティ名称3");
		community3.setComent("コミュニティ内容3");
		community3.setExternalLink("外部サイトURL3");
		community3.setJoined(false);
		communityList.add(community3);

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		Method method = controller.getClass().getDeclaredMethod("getCommunityList", UserInfoBean.class);
		method.setAccessible(true);
		List<CommunityListForm> testList = (List<CommunityListForm>) method.invoke(controller, userInfo);

		assertEquals(communityList, testList);
	}

	/**
	 * コミュニティ一覧のセット処理の試験
	 * 条件：参加コミュニティが 1件以上 の場合
	 */
	@Test
	public void getCommunityList_test4() throws Exception {

		// 試験パラメータの設定
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setAge(1);
		userInfo.setIsStaff(0);
		List<Community> communityIdList = new LinkedList<>();
		Community communityId1 = (new UserInfoBean()).new Community();
		communityId1.setId(2);
		communityIdList.add(communityId1);
		UserInfoBean.Community communityId2 = (new UserInfoBean()).new Community();
		communityId2.setId(1);
		communityIdList.add(communityId2);
		UserInfoBean.Community communityId3 = (new UserInfoBean()).new Community();
		communityId3.setId(5);
		communityIdList.add(communityId3);
		userInfo.setCommunity(communityIdList);

		// 想定結果の作成
		List<CommunityListForm> communityList = new LinkedList();
		CommunityListForm community1 = new CommunityListForm();
		community1.setCommunityId(1);
		community1.setCommunityImage("コミュニティ画像URL1");
		community1.setCommunityName("コミュニティ名称1");
		community1.setComent("コミュニティ内容1");
		community1.setExternalLink("外部サイトURL1");
		community1.setJoined(true);
		communityList.add(community1);
		CommunityListForm community2 = new CommunityListForm();
		community2.setCommunityId(2);
		community2.setCommunityImage("コミュニティ画像URL2");
		community2.setCommunityName("コミュニティ名称2");
		community2.setComent("コミュニティ内容2");
		community2.setExternalLink("外部サイトURL2");
		community2.setJoined(true);
		communityList.add(community2);
		CommunityListForm community3 = new CommunityListForm();
		community3.setCommunityId(3);
		community3.setCommunityImage("コミュニティ画像URL3");
		community3.setCommunityName("コミュニティ名称3");
		community3.setComent("コミュニティ内容3");
		community3.setExternalLink("外部サイトURL3");
		community3.setJoined(false);
		communityList.add(community3);

		// 取得データのモック処理
		when(communityInfoService.orderByCommunityId(isNull(), anyInt(), anyInt()))
				.thenReturn(communityInfoList);

		Method method = controller.getClass().getDeclaredMethod("getCommunityList", UserInfoBean.class);
		method.setAccessible(true);
		List<CommunityListForm> testList = (List<CommunityListForm>) method.invoke(controller, userInfo);

		assertEquals(communityList, testList);
	}

}
