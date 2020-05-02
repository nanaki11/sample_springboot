package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.UserInfoBean;
import com.example.demo.service.ContentFormatService;
import com.example.demo.service.ViewUrlService;

/**
 * お問い合わせ画面のコントローラー.
 */
@Controller
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public static final String CONTACT_INPUT_VIEW = "/contact_input";
    public static final String CONTACT_CONFIRM_VIEW = "/contact_confirm";
    public static final String CONTACT_INPUT_BUCK = "/contact/input/back";
    public static final String CONTACT_FINISH = "/contact/finish";
    public static final String CONTACT_COMPLETE_VIEW = "/contact_complete";

    @Value("${spring.contact.mailTo}")
    private String contactMailTo;
    @Autowired
    private MailSender mailSender;

    /**
     * お問い合わせ入力画面へ遷移する.
     * よくある質問画面のお問い合わせボタン押下時とお問い合わせ確認の戻るボタン押下時に実行.
     *
     * @return お問い合わせ入力画面.
     */
    @RequestMapping(value = "/secure/contact/input", method = RequestMethod.GET)
    public String contactInput() {
        //お問い合わせ入力画面へ遷移
        return CONTACT_INPUT_VIEW;
    }

    /**
     * 入力内容をモデルに登録し、お問い合わせ確認画面へ遷移する.
     * お問い合わせ入力画面の送信ボタン押下時に実行.
     *
     * @param contactContent お問い合わせ内容.
     * @return contactContentがblankでない：確認画面, contactContentがblank：入力画面.
     */
    @RequestMapping(value = "/secure/contact/confirm", method = RequestMethod.POST)
    public ModelAndView contactConfirm(@RequestParam(name = "contactContent", required = false) String contactContent) {
        ModelAndView mv = new ModelAndView();

        //入力内容がない場合は入力画面に遷移
        if (StringUtils.isBlank(contactContent)) {
            mv.setViewName(CONTACT_INPUT_VIEW);
            return mv;
        }

        //お問い合わせ内容を登録して確認画面に遷移
        mv.addObject("contactContent", contactContent);
        mv.setViewName(CONTACT_CONFIRM_VIEW);

        return mv;
    }

    /**
     * お問い合わせ確認画面から入力画面へリダイレクトする.
     * お問い合わせ確認画面の戻るボタン押下時に実行.
     *
     * @param contactContent お問い合わせ内容.
     * @param redirectAttributes リダイレクト属性.
     * @return お問い合わせ入力画面.
     */
    @RequestMapping(value = "/secure/contact/input/back", method = RequestMethod.POST)
    public String inputBack(@RequestParam String contactContent, RedirectAttributes redirectAttributes) {
        //お問い合わせ内容を登録して入力画面にリダイレクト
        redirectAttributes.addFlashAttribute("contactContent", contactContent);
        return ViewUrlService.viewToSecureRedirectUrl(CONTACT_INPUT_VIEW);

    }

    /**
     * 入力内容をメールで送信し、結果画面へリダイレクトする.
     * お問い合わせ確認画面の確認ボタン押下時に実行.
     *
     * @param contactContent お問い合わせ内容.
     * @param redirectAttributes リダイレクト属性.
     * @param request HttpServletRequest.
     * @return お問い合わせ完了画面.
     */
    @RequestMapping(value = "/secure/contact/finish", method = RequestMethod.POST)
    public String contactComplete(@RequestParam String contactContent, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        //セッションからユーザー情報を取得.
        UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
        //本文を成形
        ContentFormatService service = new ContentFormatService();
        String content = service.contactContentFormat(contactContent, userInfo);

        //メールを送信.
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("test@co.jp"); //Fromヘッダの内容 ”名前 <アドレス>”の形式で設定できる
        msg.setTo(contactMailTo); //宛先の設定
        msg.setSubject("お問い合わせ内容"); //タイトルの設定
        msg.setText(content); //本文の設定
        try {
            this.mailSender.send(msg);
        } catch (MailSendException e) {
            //エラーメッセージを登録して結果画面をエラー画面としてリダイレクト
            redirectAttributes.addFlashAttribute("errorMsg", "システムエラーが発生しました。");
            logger.error(e.getMessage());

            return ViewUrlService.toRedirectUrl(ErrorController.ERROR_VIEW);
        }

        //結果画面をお問い合わせ完了画面としてリダイレクト.
        return ViewUrlService.viewToSecureRedirectUrl(CONTACT_COMPLETE_VIEW);
    }

    /**
     * お問い合わせ完了画面へ遷移する.
     * お問い合わせ確認画面の確認ボタン押下時に実行.
     *
     * @return お問い合わせ完了画面.
     */
    @RequestMapping("/secure/contact/complete")
    public String contactCompleted() {
        //お問い合わせ完了画面へ遷移
        return CONTACT_COMPLETE_VIEW;
    }
}
