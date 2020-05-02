package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * よくある質問画面の単体試験.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class FAQControllerTest {

    @InjectMocks
    private FAQController controller;

    /**
     * よくある質問画面へ画面遷移処理の試験.
     */
    @Test
    @DisplayName("faq test.")
    void faq_test()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        //実行確認
        Method method = controller.getClass().getDeclaredMethod("faq");
        method.setAccessible(true);
        String res = (String) method.invoke(controller);

        assertEquals(FAQController.FAQ_VIEW, res);
    }
}
