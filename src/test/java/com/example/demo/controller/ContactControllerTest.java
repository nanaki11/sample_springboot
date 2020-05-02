package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.subethamail.wiser.Wiser;

import com.example.demo.entity.UserInfoBean;
import com.example.demo.service.ContentFormatService;
import com.example.demo.service.ViewUrlService;

/**
 * お問い合わせ画面の単体試験.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
public class ContactControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mSession;

    @InjectMocks
    private ContactController controller;

    @Value("${spring.contact.mailTo}")
    private String contactMainTo;

    private static Wiser wiser;

    @Value("${spring.mail.host}")
    private String hostName;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.contact.mailTo}")
    private String mailTo;

    @MockBean
    private MailSender mailSender;

    @Value("${spring.contact.mailTo}")
    private String contactMailTo;

    private UserInfoBean userInfo;

    /**
     * ユーザー情報を生成
     */
    @BeforeAll
    public void setUpUserInfo() {
        //ユーザー情報を生成
        userInfo = new UserInfoBean();
        userInfo.setMembershipId("test_member01");
        userInfo.setLastNameKanji("test姓");
        userInfo.setFirstNameKanji("test名");
        userInfo.setMailAddress1("testUserMail@test.com");
    }

    /**
     * メールサーバーをセットアップする
     */
//TODO メール送信テスト時に実装
//    @BeforeAll
//    public void setUpBeforeClass() throws Exception {
//        wiser = new Wiser();
//        wiser.setPort(port);
//        wiser.setHostname(hostName);
//        wiser.start();
//    }

    /**
     * 各単体試験前のセットアップ.
     * モックの初期化、セッション取得、ログインユーザーを設定値に変更する.
     */
    @BeforeEach
    void setup() {
        //mock初期化
        MockitoAnnotations.initMocks(this);
        mSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        //ログイン状態に
        mSession.setAttribute("userInfo", userInfo);
    }

    /**
     * メールサーバを停止する.
     */
//TODO メール送信テスト時に実装
//    @AfterAll
//    public void tearDownAfterClass() throws Exception {
//        wiser.stop();
//    }

    /**
     * お問い合わせ入力画面への画面遷移処理の試験.
     */
    @Test
    @DisplayName("contactInput test.")
    void contactInput_test() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        //実行確認
        Method method = controller.getClass().getDeclaredMethod("contactInput");
        method.setAccessible(true);
        String res = (String) method.invoke(controller);

        assertEquals(ContactController.CONTACT_INPUT_VIEW, res);
    }

    /**
     * お問い合わせ確認画面要求で、お問い合わせ確認画面への遷移処理の試験.
     * 条件：お問い合わせ内容が設定されている場合
     */
    @Test
    @DisplayName("contactConfirm test1.")
    void contactConfirm_test1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        //試験用データ
        String contactContent = "お問い合わせ内容テスト";

        //実行確認
        Method method = controller.getClass().getDeclaredMethod("contactConfirm", String.class);
        method.setAccessible(true);
        ModelAndView res = (ModelAndView) method.invoke(controller, contactContent);

        assertEquals(ContactController.CONTACT_CONFIRM_VIEW, res.getViewName());
        assertTrue(res.getModel().get("contactContent") instanceof String);
        assertEquals(contactContent, (String) res.getModel().get("contactContent"));
    }

    /**
     * お問い合わせ確認画面要求で、お問い合わせ入力画面への遷移処理の試験.
     * 条件：お問い合わせ内容がnullの場合
     */
    @Test
    @DisplayName("contactConfirm test2.")
    void contactConfirm_test2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        //試験用データ
        String contactContent = null;

        //実行確認
        Method method = controller.getClass().getDeclaredMethod("contactConfirm", String.class);
        method.setAccessible(true);
        ModelAndView res = (ModelAndView) method.invoke(controller, contactContent);

        assertEquals(ContactController.CONTACT_INPUT_VIEW, res.getViewName());
        assertNull(res.getModel().get("contactContent"));
    }

    /**
     * お問い合わせ確認画面要求で、お問い合わせ入力画面への遷移処理の試験.
     * 条件：お問い合わせ内容が空文字の場合
     */
    @Test
    @DisplayName("contactConfirm test3.")
    void contactConfirm_test3() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        //試験用データ
        String contactContent = "";

        //実行確認
        Method method = controller.getClass().getDeclaredMethod("contactConfirm", String.class);
        method.setAccessible(true);
        ModelAndView res = (ModelAndView) method.invoke(controller, contactContent);

        assertEquals(ContactController.CONTACT_INPUT_VIEW, res.getViewName());
        assertNull(res.getModel().get("contactContent"));
    }

    /**
     * お問い合わせ確認画面要求で、お問い合わせ入力画面への遷移処理の試験.
     * 条件：お問い合わせ内容がblank文字の場合
     */
    @Test
    @DisplayName("contactConfirm test4.")
    void contactConfirm_test4() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        //試験用データ
        String contactContent = " ";
        //実行確認
        Method method = controller.getClass().getDeclaredMethod("contactConfirm", String.class);
        method.setAccessible(true);
        ModelAndView res = (ModelAndView) method.invoke(controller, contactContent);

        assertEquals(ContactController.CONTACT_INPUT_VIEW, res.getViewName());
        assertNull(res.getModel().get("contactContent"));
    }

    /**
     * お問い合わせ確認画面から入力画面へリダイレクト処理の試験.
     */
    @Test
    @DisplayName("inputBack test1.")
    void inputBack_test1() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        //試験用データ
        String contactContent = "お問い合わせ内容テスト";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactContent", contactContent);

        //実行確認
        mvc.perform(
                post("/secure/contact/input/back")
                        .params(params)
                        .session(mSession))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(ViewUrlService.viewToSecureUrl(ContactController.CONTACT_INPUT_VIEW)))
                .andExpect(flash().attributeExists("contactContent"))
                .andExpect(flash().attribute("contactContent", contactContent));
    }

    /**
     * 入力内容をメールで送信し、結果画面へリダイレクト処理をする試験.
     * 条件：メール送信に成功
     */
    @Test
    @DisplayName("contactComplete test1.")
    void contactComplete_test1() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        //試験用データ
        String contactContent = "お問い合わせ内容テスト";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactContent", contactContent);

        //本文を成形
        ContentFormatService service = new ContentFormatService();
        String content = service.contactContentFormat(contactContent, userInfo);
        SimpleMailMessage msg = new SimpleMailMessage();

        //メール本体を成形
        String from = "test@co.jp";
        String subject = "お問い合わせ内容";
        msg.setFrom(from); //Fromヘッダの内容 ”名前 <アドレス>”の形式で設定できる
        msg.setTo(contactMailTo); //宛先の設定
        msg.setSubject(subject); //タイトルの設定
        msg.setText(content); //本文の設定

        //実行確認
        mvc.perform(
                post("/secure/contact/finish")
                        .params(params)
                        .session(mSession))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(ViewUrlService.viewToSecureUrl(ContactController.CONTACT_COMPLETE_VIEW)));
        //TODO メール送信テストは残課題
        //        //メール送信結果確認
        //        WiserAssertions.assertReceivedMessage(wiser)
        //                .from(from)
        //                .to(mailTo)
        //                .withSubject(subject)
        //                .withContent(service.contactContentFormat(contactContent, userInfo));
    }

    //TODO メール送信失敗ケースは残課題
    //    /**
    //     * 入力内容をメールで送信し、結果画面へリダイレクト処理をする試験.
    //     * 条件：メール送信に失敗
    //     */
    //    @Test
    //    @DisplayName("contactComplete test2.")
    //    void contactComplete_test2() throws Exception {
    //        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    //
    //        //試験用データ
    //        String contactContent = "お問い合わせ内容テスト";
    //        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    //        params.add("contactContent", contactContent);
    //
    //        wiser.stop();
    //
    //        //本文を成形
    //        ContentFormatService service = new ContentFormatService();
    //        String content = service.contactContentFormat(contactContent, userInfo);
    //        SimpleMailMessage msg = new SimpleMailMessage();
    //        //メール本体を成形
    //        String from = "test@co.jp";
    //        String subject = "お問い合わせ内容";
    //        msg.setFrom(from);        //Fromヘッダの内容 ”名前 <アドレス>”の形式で設定できる
    //        msg.setTo(contactMailTo); //宛先の設定
    //        msg.setSubject(subject);  //タイトルの設定
    //        msg.setText(content);     //本文の設定
    //
    //        //モック処理
    ////        MailSender spy = spy(mailSender);
    ////        doNothing().when(spy).send(msg);
    //        //実行確認
    //        mvc.perform(
    //                post("/secure/contact/finish")
    //                    .params(params)
    //                    .session(mSession))
    //                .andExpect(status().isFound())
    //                .andExpect(redirectedUrl(ViewPathService.viewToUrl(ErrorController.ERROR_VIEW)))
    //                .andExpect(flash().attributeExists("errorMsg"));
    //
    //    }

    /**
     * お問い合わせ完了画面へ遷移処理の試験.
     */
    @Test
    @DisplayName("contactCompleted test.")
    void contactCompleted_test()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        Method method = controller.getClass().getDeclaredMethod("contactCompleted");
        method.setAccessible(true);

        String res = (String) method.invoke(controller);

        assertEquals(ContactController.CONTACT_COMPLETE_VIEW, res);
    }
}
