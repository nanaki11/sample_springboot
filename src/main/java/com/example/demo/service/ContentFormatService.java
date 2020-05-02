package com.example.demo.service;

import com.example.demo.entity.UserInfoBean;

public class ContentFormatService {
    public String contactContentFormat(String content, UserInfoBean userInfo) {
        String ret = content;
        //TODO お問い合わせ内容をユーザー情報をもとに成形し、本文を作成(フォーマット定義待ち)
        ret += "\n お問い合わせ"
             + "\n メンバーシップID：" + userInfo.getCustomerId()
             + "\n 氏名　　　　　　：" + userInfo.getLastNameKanji() + userInfo.getFirstNameKanji()
             + "\n メールアドレス　：" + userInfo.getMailAddress1();

        return ret;
    }
}
