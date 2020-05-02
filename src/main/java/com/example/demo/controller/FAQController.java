package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * よくある質問画面のコントローラー.
 */
@Controller
public class FAQController {
    public static final String FAQ_VIEW = "/faq";

    /**
     * よくある質問画面へ遷移する.
     * メニューからよくある質問を押下時とお問い合わせ入力画面の戻るボタン押下時に実行.
     *
     * @return よくある質問画面.
     */
    @RequestMapping(value = "/secure/faq", method = RequestMethod.GET)
    public String faq() {
        //よくある質問画面へ遷移
        return FAQ_VIEW;
    }
}
