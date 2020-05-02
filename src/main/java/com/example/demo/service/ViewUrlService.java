package com.example.demo.service;

/**
 * 画面パスをURLやリダイレクトへ変換する.
 */
public class ViewUrlService {

    public static final String SECURE = "/secure";

    /**
     * 画面パス文字列をリクエストURL用文字列に変換（_を/に置換）.
     *
     * @param view 画面パス
     * @return URL
     */
    public static String viewToUrl(String view) {
        String ret = view.replace("_", "/");
        return ret;
    }

    /**
     * リクエストURLにリダイレクトを付加.
     *
     * @param url リダイレクト先URL
     * @return リダイレクトを付加したURL
     */
    public static String toRedirectUrl(String url) {
        return "redirect:" + url;
    }

    /**
     * 画面パス文字列をリダイレクトを付加したURLに変換.
     *
     * @param view 画面パス
     * @return リダイレクトを付加したURL
     */
    public static String viewToRedirectUrl(String view) {
        return toRedirectUrl(viewToUrl(view));
    }

    /**
     * 画面パス文字列をログイン必須URLに変換.
     *
     * @param view 画面パス
     * @return ログイン必須URL
     */
    public static String viewToSecureUrl(String view) {
        return SECURE + viewToUrl(view);
    }

    /**
     * 画面パス文字列をリダイレクトを付加したログイン必須URLに変換.
     *
     * @param view 画面パス
     * @return リダイレクトを付加したログイン必須URL
     */
    public static String viewToSecureRedirectUrl(String view) {
        return toRedirectUrl(viewToSecureUrl(view));
    }
}
