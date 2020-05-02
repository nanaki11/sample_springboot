package com.example.demo.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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

import com.example.demo.entity.CommunityInfo;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.entity.response.ApiResponse.ApiError;
import com.example.demo.entity.response.GetCommunityResponse;
import com.example.demo.service.CommunityInfoService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetCommunityControllerTest {

    // RestController試験の設定
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    // 試験対象の設定
    private static final String path = "/api/community/getlist";
    @InjectMocks
    private GetCommunityController controller;
    @MockBean
    private CommunityInfoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * API実行試験用
     * @throws JsonProcessingException
     */
    private String setupApiTest(MultiValueMap<String, String> params, List<CommunityInfo> communityInfoList)
            throws JsonProcessingException {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper.setSerializationInclusion(Include.NON_NULL);

        // 想定する返却値の設定
        GetCommunityResponse result = new GetCommunityResponse();
        result.setCommunity(communityInfoList.stream()
                .map(communityInfo -> result.new Community(
                        communityInfo.getCommunityId(),
                        communityInfo.getCommunityName(),
                        communityInfo.getComent(),
                        communityInfo.getCommunityImage(),
                        communityInfo.getExternalLink()))
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
        List<CommunityInfo> communityInfoList = new ArrayList<>();

        // 試験パラメータの設定
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("clientId", ApiResponseUtil.CLIENT_ID);
        String apiResponse = setupApiTest(params, communityInfoList);

        // 取得データのモック処理
        when(service.findAll()).thenReturn(communityInfoList);

        // api試験実施
        mvc.perform(post(path).params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));

        verify(this.service, times(1)).findAll();
    }

    /**
     * API実行試験
     * 条件：1件以上取得の場合
     */
    @Test
    public void main_success_test2() throws Exception {

        // 取得データ
        List<CommunityInfo> communityInfoList = new ArrayList<>();
        CommunityInfo communityInfo1 = new CommunityInfo();
        communityInfo1.setCommunityId(1);
        communityInfo1.setCommunityName("コミュニティ名称1");
        communityInfo1.setComent("コミュニティ内容1");
        communityInfo1.setCommunityImage("コミュニティ画像1");
        communityInfo1.setExternalLink("外部URL1");
        communityInfoList.add(communityInfo1);
        CommunityInfo communityInfo2 = new CommunityInfo();
        communityInfo2.setCommunityId(2);
        communityInfo2.setCommunityName("コミュニティ名称2");
        communityInfo2.setComent("コミュニティ内容2");
        communityInfo2.setCommunityImage("コミュニティ画像2");
        communityInfo2.setExternalLink("外部URL2");
        communityInfoList.add(communityInfo2);

        // 試験パラメータの設定
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("clientId", ApiResponseUtil.CLIENT_ID);
        String apiResponse = setupApiTest(params, communityInfoList);

        // 取得データのモック処理
        when(service.findAll()).thenReturn(communityInfoList);

        // api試験実施
        mvc.perform(post(path).params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));

        verify(this.service, times(1)).findAll();
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
        params.add("clientId", "ZZZ");

        // api試験実施 TODO:目視確認
        mvc.perform(post(path).params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service, times(0)).findAll();
    }

    /**
     * バリデーションチェック処理の試験
     * 条件：クライアントIDが null の場合
     */
    @Test
    public void validationCheck_test1()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        // 試験データの設定
        String clientId = null;

        Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class);
        method.setAccessible(true);
        ApiError res = (ApiError) method.invoke(controller, clientId);

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
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        // 試験データの設定
        String clientId = "";

        Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class);
        method.setAccessible(true);
        ApiError res = (ApiError) method.invoke(controller, clientId);

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
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        // 試験データの設定
        String clientId = "ABC";

        Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class);
        method.setAccessible(true);
        ApiError res = (ApiError) method.invoke(controller, clientId);

        assertEquals(true, (res.getId().matches("^[0-9]{18}_[0-9a-zA-Z]{4}$")));
        assertEquals(ApiResponseUtil.ERROR_CODE_C000002, res.getCode());
        assertEquals(ApiResponseUtil.ERROR_MESSAGE_ILLEGAL_REQUEST, res.getMessage());
        assertEquals("不正なクライアントIDが使用されています。[clientId:ABC]", res.getDetail());
    }

    /**
     * バリデーションチェック処理の試験
     * 条件：クライアントIDが 想定通り の場合
     */
    @Test
    public void validationCheck_test4()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        // 試験データの設定
        String clientId = ApiResponseUtil.CLIENT_ID;

        Method method = controller.getClass().getDeclaredMethod("validationCheck", String.class);
        method.setAccessible(true);
        ApiError res = (ApiError) method.invoke(controller, clientId);

        assertNull(res);
    }
}
