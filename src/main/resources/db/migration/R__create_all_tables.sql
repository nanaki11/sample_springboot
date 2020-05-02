-- ==================================================
-- テーブル定義バージョン
-- ==================================================
-- W_AnnexC-2_データ設計_DBテーブル仕様_v0.9.1.xlsx

-- ==================================================
-- 会員情報
-- ==================================================
-- プロフィール画像
DROP TABLE IF EXISTS customer_image CASCADE;
CREATE TABLE customer_image(
  customer_id numeric(11) NOT NULL,
  image_url varchar(300) NOT NULL,
  CONSTRAINT _pk_customer_image PRIMARY KEY (customer_id)
);

COMMENT ON TABLE customer_image IS 'プロフィール画像';
COMMENT ON COLUMN customer_image.customer_id IS '業務ID';
COMMENT ON COLUMN customer_image.image_url IS '画像URL';


-- ==================================================
-- 施設情報
-- ==================================================
-- 地区マスタ
DROP TABLE IF EXISTS facility_area CASCADE;
CREATE TABLE facility_area(
  area_cd varchar(5) NOT NULL,
  lang varchar(5) NOT NULL DEFAULT 'ja',
  disp_order smallint NOT NULL,
  district_nm varchar(100) NOT NULL,
  UNIQUE (area_cd),
  CONSTRAINT _pk_area PRIMARY KEY (area_cd, lang)
);

COMMENT ON TABLE facility_area IS '地区マスタ';
COMMENT ON COLUMN facility_area.area_cd IS '地区CD';
COMMENT ON COLUMN facility_area.lang IS '言語';
COMMENT ON COLUMN facility_area.disp_order IS '表示順';
COMMENT ON COLUMN facility_area.district_nm IS '地区名称';


-- 施設マスタ
DROP TABLE IF EXISTS facility CASCADE;
CREATE TABLE facility(
  business_cd varchar(5) NOT NULL,
  lang varchar(5) NOT NULL DEFAULT 'ja',
  facility_name varchar(300) NOT NULL,
  category_cd varchar(3) NOT NULL,
  area_cd varchar(5) NOT NULL,
  prefectures_cd varchar(2) NOT NULL,
  city_name varchar(300) NOT NULL,
  postal_code varchar(8),
  address varchar(3000),
  reservation_phone  varchar(13),
  phone  varchar(13),
  mail_address varchar(3000),
  detailed_url varchar(300),
  reservation_url varchar(300),
  shop_url varchar(300),
  facility_logo_image_url varchar(300),
  facility_image_url_1 varchar(300),
  facility_image_url_2 varchar(300),
  facility_image_url_3 varchar(300),
  facility_image_url_4 varchar(300),
  facility_image_url_5 varchar(300),
  facility_image_url_6 varchar(300),
  facility_image_url_7 varchar(300),
  facility_image_url_8 varchar(300),
  facility_image_url_9 varchar(300),
  facility_image_url_10 varchar(300),
  facility_image_url_11 varchar(300),
  facility_image_url_12 varchar(300),
  facility_image_url_13 varchar(300),
  facility_image_url_14 varchar(300),
  facility_image_url_15 varchar(300),
  facility_image_title_1 varchar(3000),
  facility_image_title_2 varchar(3000),
  facility_image_title_3 varchar(3000),
  facility_image_title_4 varchar(3000),
  facility_image_title_5 varchar(3000),
  facility_image_title_6 varchar(3000),
  facility_image_title_7 varchar(3000),
  facility_image_title_8 varchar(3000),
  facility_image_title_9 varchar(3000),
  facility_image_title_10 varchar(3000),
  facility_image_title_11 varchar(3000),
  facility_image_title_12 varchar(3000),
  facility_image_title_13 varchar(3000),
  facility_image_title_14 varchar(3000),
  facility_image_title_15 varchar(3000),
  latitude numeric(9,6),
  longitude numeric(9,6),
  CONSTRAINT _pk_facility PRIMARY KEY (business_cd, lang),
  FOREIGN KEY (area_cd) REFERENCES facility_area(area_cd)
);

COMMENT ON TABLE facility IS '施設マスタ';
COMMENT ON COLUMN facility.business_cd IS '事業所CD';
COMMENT ON COLUMN facility.lang IS '言語';
COMMENT ON COLUMN facility.facility_name IS '施設名称';
COMMENT ON COLUMN facility.category_cd IS 'カテゴリCD';
COMMENT ON COLUMN facility.area_cd IS '地区CD';
COMMENT ON COLUMN facility.prefectures_cd IS '都道府県CD';
COMMENT ON COLUMN facility.city_name IS '都市名';
COMMENT ON COLUMN facility.postal_code IS '施設郵便番号';
COMMENT ON COLUMN facility.address IS '施設住所';
COMMENT ON COLUMN facility.reservation_phone  IS '施設予約電話番号';
COMMENT ON COLUMN facility.phone  IS '施設電話番号';
COMMENT ON COLUMN facility.mail_address IS '施設メールアドレス';
COMMENT ON COLUMN facility.detailed_url IS 'ホテルサイトURL';
COMMENT ON COLUMN facility.reservation_url IS '予約サイトURL';
COMMENT ON COLUMN facility.shop_url IS 'ネットショップURL';
COMMENT ON COLUMN facility.facility_logo_image_url IS '施設ロゴ画像URL';
COMMENT ON COLUMN facility.facility_image_url_1 IS '施設画像URL１';
COMMENT ON COLUMN facility.facility_image_url_2 IS '施設画像URL２';
COMMENT ON COLUMN facility.facility_image_url_3 IS '施設画像URL３';
COMMENT ON COLUMN facility.facility_image_url_4 IS '施設画像URL４';
COMMENT ON COLUMN facility.facility_image_url_5 IS '施設画像URL５';
COMMENT ON COLUMN facility.facility_image_url_1 IS '施設画像URL６';
COMMENT ON COLUMN facility.facility_image_url_2 IS '施設画像URL７';
COMMENT ON COLUMN facility.facility_image_url_3 IS '施設画像URL８';
COMMENT ON COLUMN facility.facility_image_url_4 IS '施設画像URL９';
COMMENT ON COLUMN facility.facility_image_url_5 IS '施設画像URL１０';
COMMENT ON COLUMN facility.facility_image_url_1 IS '施設画像URL１１';
COMMENT ON COLUMN facility.facility_image_url_2 IS '施設画像URL１２';
COMMENT ON COLUMN facility.facility_image_url_3 IS '施設画像URL１３';
COMMENT ON COLUMN facility.facility_image_url_4 IS '施設画像URL１４';
COMMENT ON COLUMN facility.facility_image_url_5 IS '施設画像URL１５';
COMMENT ON COLUMN facility.facility_image_title_1 IS '施設画像タイトル１';
COMMENT ON COLUMN facility.facility_image_title_2 IS '施設画像タイトル２';
COMMENT ON COLUMN facility.facility_image_title_3 IS '施設画像タイトル３';
COMMENT ON COLUMN facility.facility_image_title_4 IS '施設画像タイトル４';
COMMENT ON COLUMN facility.facility_image_title_5 IS '施設画像タイトル５';
COMMENT ON COLUMN facility.facility_image_title_1 IS '施設画像タイトル６';
COMMENT ON COLUMN facility.facility_image_title_2 IS '施設画像タイトル７';
COMMENT ON COLUMN facility.facility_image_title_3 IS '施設画像タイトル８';
COMMENT ON COLUMN facility.facility_image_title_4 IS '施設画像タイトル９';
COMMENT ON COLUMN facility.facility_image_title_5 IS '施設画像タイトル１０';
COMMENT ON COLUMN facility.facility_image_title_1 IS '施設画像タイトル１１';
COMMENT ON COLUMN facility.facility_image_title_2 IS '施設画像タイトル１２';
COMMENT ON COLUMN facility.facility_image_title_3 IS '施設画像タイトル１３';
COMMENT ON COLUMN facility.facility_image_title_4 IS '施設画像タイトル１４';
COMMENT ON COLUMN facility.facility_image_title_5 IS '施設画像タイトル１５';
COMMENT ON COLUMN facility.latitude IS '緯度';
COMMENT ON COLUMN facility.longitude IS '経度';


-- 施設お気に入り
DROP TABLE IF EXISTS facility_favorite CASCADE;
CREATE TABLE facility_favorite(
  customer_id numeric(11) NOT NULL,
  business_cd varchar(5) NOT NULL,
  CONSTRAINT _pk_facility_favorite PRIMARY KEY (customer_id, business_cd)
);

COMMENT ON TABLE facility_favorite IS '施設お気に入り';
COMMENT ON COLUMN facility_favorite.customer_id IS '業務ID';
COMMENT ON COLUMN facility_favorite.business_cd IS '事業所CD';


-- ==================================================
-- コンテンツ情報
-- ==================================================
-- コンテンツマスタ
DROP TABLE IF EXISTS content CASCADE;
CREATE TABLE content(
  content_id numeric(11) NOT NULL,
  lang varchar(5) NOT NULL DEFAULT 'ja',
  content_type varchar(300) NOT NULL,
  category_id numeric(5) NOT NULL,
  content_title varchar(100),
  content varchar(3000),
  content_image_url varchar(300),
  delivery_start_date timestamp DEFAULT current_timestamp,
  delivery_end_date timestamp DEFAULT current_timestamp,
  is_display bool DEFAULT True,
  CONSTRAINT _pk_content PRIMARY KEY (content_id, lang)
);

COMMENT ON TABLE content IS 'コンテンツマスタ';
COMMENT ON COLUMN content.content_id IS 'コンテンツID';
COMMENT ON COLUMN content.lang IS '言語';
COMMENT ON COLUMN content.content_type IS 'コンテンツ種別';
COMMENT ON COLUMN content.category_id IS 'カテゴリID';
COMMENT ON COLUMN content.content_title IS 'コンテンツタイトル';
COMMENT ON COLUMN content.content IS 'コンテンツ内容';
COMMENT ON COLUMN content.content_image_url IS 'コンテンツ画像URL';
COMMENT ON COLUMN content.delivery_start_date IS '配信開始日';
COMMENT ON COLUMN content.delivery_end_date IS '配信終了日';
COMMENT ON COLUMN content.is_display IS '表示/非表示フラグ';


-- コンテンツレビュー
DROP TABLE IF EXISTS review CASCADE;
CREATE TABLE review(
  review_id serial NOT NULL,
  content_id numeric(11) NOT NULL,
  lang varchar(5) NOT NULL DEFAULT 'ja',
  add_date timestamp NOT NULL DEFAULT current_timestamp,
  nickname varchar(300),
  coment varchar(3000),
  is_display bool DEFAULT True,
  not_posted_date date,
  profile_url varchar(300),
  CONSTRAINT _pk_review PRIMARY KEY (review_id),
  FOREIGN KEY (content_id, lang) REFERENCES content(content_id, lang) ON DELETE CASCADE
);

COMMENT ON TABLE review IS 'コンテンツレビュー';
COMMENT ON COLUMN review.review_id IS 'レビューID';
COMMENT ON COLUMN review.content_id IS 'コンテンツID';
COMMENT ON COLUMN review.lang IS '言語';
COMMENT ON COLUMN review.add_date IS '作成日時';
COMMENT ON COLUMN review.nickname IS 'ニックネーム';
COMMENT ON COLUMN review.coment IS 'コメント内容';
COMMENT ON COLUMN review.is_display IS '表示/非表示フラグ';
COMMENT ON COLUMN review.not_posted_date IS '掲載不可日';
COMMENT ON COLUMN review.profile_url IS 'プロフィール画像';


-- コンテンツお気に入り
DROP TABLE IF EXISTS content_favorite CASCADE;
CREATE TABLE content_favorite(
  customer_id numeric(11) NOT NULL,
  content_id numeric(11) NOT NULL,
  lang varchar(5) NOT NULL DEFAULT 'ja',
  CONSTRAINT _pk_favorite PRIMARY KEY (customer_id, content_id),
  FOREIGN KEY (content_id, lang) REFERENCES content(content_id, lang) ON DELETE CASCADE
);

COMMENT ON TABLE content_favorite IS 'コンテンツお気に入り';
COMMENT ON COLUMN content_favorite.customer_id IS '業務ID';
COMMENT ON COLUMN content_favorite.content_id IS 'コンテンツID';
COMMENT ON COLUMN content_favorite.lang IS '言語';


-- ==================================================
-- コミュニティ情報
-- ==================================================
-- コミュニティ情報マスタ
DROP TABLE IF EXISTS community CASCADE;
CREATE TABLE community(
community_id numeric(5) NOT NULL,
display_order smallint,
community_name varchar(300),
is_display bool DEFAULT False,
CONSTRAINT _pk_community PRIMARY KEY (community_id));

COMMENT ON TABLE community IS 'コミュニティ情報マスタ';
COMMENT ON COLUMN community.community_id IS 'コミュニティID';
COMMENT ON COLUMN community.display_order IS '表示順';
COMMENT ON COLUMN community.community_name IS 'コミュニティ名称';
COMMENT ON COLUMN community.is_display IS '表示/非表示フラグ';


-- コミュニティ付帯情報マスタ
DROP TABLE IF EXISTS community_info CASCADE;
CREATE TABLE community_info(
community_id numeric(5) NOT NULL,
lang varchar(5) NOT NULL DEFAULT 'ja',
community_name varchar(300) NOT NULL,
is_staff numeric(1) NOT NULL DEFAULT '0',
is_yuyu numeric(1) NOT NULL DEFAULT '0',
age_upper_limit numeric(3) NOT NULL DEFAULT '999',
age_lower_limit numeric(3) NOT NULL DEFAULT '0',
coment varchar(3000),
community_image varchar(300),
external_link varchar(300),
CONSTRAINT _pk_community_info PRIMARY KEY (community_id, lang),
FOREIGN KEY (community_id) REFERENCES community(community_id));

COMMENT ON TABLE community_info IS 'コミュニティ付帯情報マスタ';
COMMENT ON COLUMN community_info.community_id IS 'コミュニティID';
COMMENT ON COLUMN community_info.lang IS '言語';
COMMENT ON COLUMN community_info.community_name IS 'コミュニティ名称';
COMMENT ON COLUMN community_info.is_staff IS 'スタッフフラグ';
COMMENT ON COLUMN community_info.is_yuyu IS '癒湯自適フラグ';
COMMENT ON COLUMN community_info.age_upper_limit IS '表示対象年齢上限';
COMMENT ON COLUMN community_info.age_lower_limit IS '表示対象年齢下限';
COMMENT ON COLUMN community_info.coment IS 'コミュニティ内容';
COMMENT ON COLUMN community_info.community_image IS 'コミュニティ画像URL';
COMMENT ON COLUMN community_info.external_link IS '外部サイトURL';

