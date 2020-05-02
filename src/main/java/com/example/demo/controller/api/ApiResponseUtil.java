package com.example.demo.controller.api;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;

import com.example.demo.entity.response.ApiResponse.ApiError;


public class ApiResponseUtil {

	private ApiResponseUtil() {}

	// クライアントID　TODO 正しい値を指定する必要あり（現時点：試験用の値を設定）
	public static final String CLIENT_ID = "XXX";

	// 業務IDの最大桁数
	public static final int CUSTOMER_ID_MAXSIZE = 11;
	// コンテンツIDの最大桁数
	public static final int CONTENT_ID_MAXSIZE = 11;
	// 事業所コードの最大文字数
	public static final int BUSINESS_CD_MAXSIZE = 5;

	// エラーコード：共通
	public static final String ERROR_CODE_C000001 = "C000001";
	public static final String ERROR_CODE_C000002 = "C000002";
	public static final String ERROR_CODE_C000003 = "C000003";
	public static final String ERROR_CODE_C000004 = "C000004";

	// エラーコード：F001　コンテンツお気に入り取得
	public static final String ERROR_CODE_F001001 = "F001001";

	// エラーコード：F002　コンテンツお気に入り登録
	public static final String ERROR_CODE_F002001 = "F002001";
	public static final String ERROR_CODE_F002002 = "F002002";

	// エラーコード：F003　コンテンツお気に入り解除
	public static final String ERROR_CODE_F003001 = "F003001";
	public static final String ERROR_CODE_F003002 = "F003002";

	// エラーコード：F004　施設お気に入り取得
	public static final String ERROR_CODE_F004001 = "F004001";

	// エラーコード：F005　施設お気に入り登録
	public static final String ERROR_CODE_F005001 = "F005001";
	public static final String ERROR_CODE_F005002 = "F005002";

	// エラーコード：F006　施設お気に入り解除
	public static final String ERROR_CODE_F006001 = "F006001";
	public static final String ERROR_CODE_F006002 = "F006002";

	// エラーコード：F008　プロフィール画像取得
	public static final String ERROR_CODE_F008001 = "F008001";

	// エラーコード：F009　コンテンツレビューコメント取得
	public static final String ERROR_CODE_F009001 = "F009001";

	// エラーコード：F010　コンテンツレビューコメント登録
	public static final String ERROR_CODE_F010001 = "F010001";

	// エラーコード：A001　コンテンツ情報登録
	public static final String ERROR_CODE_A001001 = "A001001";

	// エラーコード：A002　コンテンツ情報変更
	public static final String ERROR_CODE_A002001 = "A002001";

	// エラーコード：A003　コンテンツ情報削除
	public static final String ERROR_CODE_A003001 = "A003001";

	// エラーコード：A004　コミュニティ登録
	public static final String ERROR_CODE_A004001 = "A004001";

	// エラーコード：A005　コミュニティ変更
	public static final String ERROR_CODE_A005001 = "A005001";

	// エラーコード：A006　コミュニティ削除
	public static final String ERROR_CODE_A006001 = "A006001";

	// エラーメッセージ
	public static final String ERROR_MESSAGE_UNEXPECTED = "予期せぬエラーが発生しました。";
	public static final String ERROR_MESSAGE_ILLEGAL_REQUEST = "リクエストが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_JSON = "JSONが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_PARAMETER = "パラメータが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_CUSTOMERID = "業務IDが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_CONTENTID = "コンテンツIDが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_BUSINESSCD = "事業所コードが不正です。";
	public static final String ERROR_MESSAGE_ILLEGAL_COMMUNITYID = "コミュニティIDが不正です。";

	// エラー詳細
	public static final String ERROR_DETAIL_CLIENTID_ILLEGAL = "不正なクライアントIDが使用されています。[clientId:{0}]";
	public static final String ERROR_DETAIL_CUSTOMERID_UNDEFINED = "業務IDが未設定です。";
	public static final String ERROR_DETAIL_CUSTOMERID_ILLEGAL = "不正な業務IDが使用されています。[customerId:{0}]";
	public static final String ERROR_DETAIL_CONTENTID_UNDEFINED = "コンテンツIDが未設定です。";
	public static final String ERROR_DETAIL_CONTENTID_ILLEGAL = "不正なコンテンツIDが使用されています。[contentId:{0}]";
	public static final String ERROR_DETAIL_BUSINESSCD_UNDEFINED = "事業所コードが未設定です。";
	public static final String ERROR_DETAIL_BUSINESSCD_ILLEGAL = "不正な事業所コードが使用されています。[businessCd:{0}]";
	public static final String ERROR_DETAIL_NICKNAME_UNDEFINED = "ニックネームが未設定です。";
	public static final String ERROR_DETAIL_COMENT_UNDEFINED = "コメント内容が未設定です。";
	public static final String ERROR_DETAIL_CONTENTID_EXIST = "既に登録されているコンテンツIDです。[contentId:{0},lang:{1}]";
	public static final String ERROR_DETAIL_CONTENTID_NOT_EXIST = "存在しないコンテンツIDです。[contentId:{0},lang:{1}]";

	// エラーIDに使用するフォーマット
	private static final String ERROR_ID_FORMAT = "YYYYMMDDhhmmssSSS";
	// エラーIDに使用する乱数の桁数
	private static final int ERROR_ID_RANDOM_COUNT = 4;

	/**
	 * エラー内容（想定外のエラー）をセット
	 *
	 * @return ApiError
	 */
	public static ApiError setC000001(String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_C000001);
		apiError.setMessage(ERROR_MESSAGE_UNEXPECTED);
		apiError.setDetail(detail);

		return apiError;
	}

	/**
	 * エラー内容（リクエスト不正）をセット
	 *
	 * @return ApiError
	 */
	public static ApiError setC000002(String clientId) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_C000002);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_REQUEST);
		apiError.setDetail(MessageFormat.format(ERROR_DETAIL_CLIENTID_ILLEGAL, clientId));

		return apiError;
	}

	/**
	 * エラー内容（パラメータ不正）をセット
	 *
	 * @param param 不正なパラメータ
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setC000004(String param, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_C000004);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_PARAMETER);
		apiError.setDetail(MessageFormat.format(detail, param));

		return apiError;
	}

	/**
	 * エラー内容（F001　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF001001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F001001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F002　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF002001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F002001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F002　コンテンツIDが不正）をセット
	 *
	 * @param contentId コンテンツID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF002002(String contentId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F002002);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(MessageFormat.format(detail, contentId));

		return apiError;
	}

	/**
	 * エラー内容（F003　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF003001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F003001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F003　コンテンツIDが不正）をセット
	 *
	 * @param contentId コンテンツID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF003002(String contentId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F003002);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(MessageFormat.format(detail, contentId));

		return apiError;
	}

	/**
	 * エラー内容（F004　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF004001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F004001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F005　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF005001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F005001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F005　事業所コードが不正）をセット
	 *
	 * @param businessCd 事業所コード
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF005002(String businessCd, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F005002);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_BUSINESSCD);
		apiError.setDetail(MessageFormat.format(detail, businessCd));

		return apiError;
	}

	/**
	 * エラー内容（F006　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF006001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F006001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F006　事業所コードが不正）をセット
	 *
	 * @param businessCd 事業所コード
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF006002(String businessCd, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F006002);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_BUSINESSCD);
		apiError.setDetail(MessageFormat.format(detail, businessCd));

		return apiError;
	}

	/**
	 * エラー内容（F008　業務IDが不正）をセット
	 *
	 * @param customerId 業務ID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF008001(String customerId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F008001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CUSTOMERID);
		apiError.setDetail(MessageFormat.format(detail, customerId));

		return apiError;
	}

	/**
	 * エラー内容（F009　コンテンツIDが不正）をセット
	 *
	 * @param contentId コンテンツID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF009001(String contentId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F009001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(MessageFormat.format(detail, contentId));

		return apiError;
	}

	/**
	 * エラー内容（F010　コンテンツIDが不正）をセット
	 *
	 * @param contentId コンテンツID
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setF010001(String contentId, String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_F010001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(MessageFormat.format(detail, contentId));

		return apiError;
	}

	/**
	 * エラー内容（A001　コンテンツIDが不正）をセット
	 *
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setA001001(String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_A001001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(detail);

		return apiError;
	}

	/**
	 * エラー内容（A002　コンテンツIDが不正）をセット
	 *
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setA002001(String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_A002001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(detail);

		return apiError;
	}

	/**
	 * エラー内容（A003　コンテンツIDが不正）をセット
	 *
	 * @param detail エラー詳細
	 * @return ApiError
	 */
	public static ApiError setA003001(String detail) {

		ApiError apiError = new ApiError();
		apiError.setId(getErrorId());
		apiError.setCode(ERROR_CODE_A003001);
		apiError.setMessage(ERROR_MESSAGE_ILLEGAL_CONTENTID);
		apiError.setDetail(detail);

		return apiError;
	}

	/**
	 * エラーIDを取得
	 *
	 * @return エラーID（YYYYMMDDhhmmssSSS_乱数4桁）
	 */
	public static String getErrorId() {

		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern(ERROR_ID_FORMAT);

		return f.format(d) + "_" +RandomStringUtils.randomAlphanumeric(ERROR_ID_RANDOM_COUNT);
	}
}
