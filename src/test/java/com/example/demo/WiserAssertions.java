package com.example.demo;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

/**
 * 仮想SMTPサーバーのアサーションクラス.
 */
public class WiserAssertions {
    private final List<WiserMessage> messages;

    /**
     * コンストラクター.
     * 仮想SMTPサーバーインスタンスを取得するメソッドから静的に呼ぶ.
     *
     * assertReceivedMessageから呼ぶ
     * @param messages ログ一覧
     */
    private WiserAssertions(List<WiserMessage> messages) {
        this.messages = messages;
    }

    /**
     * 仮想SMTPサーバーインスタンスを取得.
     *
     * @param wiser 仮想SMTPサーバーインスタンス
     * @return 仮想SMTPサーバーのアサーションインスタンス
     */
    public static WiserAssertions assertReceivedMessage(Wiser wiser) {
        return new WiserAssertions(wiser.getMessages());
    }

    /**
     * サーバーのログ一覧を取得.
     *
     * @return サーバーのログ一覧
     */
    public List<WiserMessage> getWiserMessageList() {
        return this.messages;
    }

    /**
     * 送信元のアサーション.
     *
     * @param from 期待する送信元
     * @return 自己インスタンス（チェーンメソッド用）
     */
    public WiserAssertions from(String from) {
        findFirstOrElseThrow(m -> m.getEnvelopeSender().equals(from),
                assertionError("No message from [{0}] found!", from));
        return this;
    }

    /**
     * 送信先のアサーション.
     *
     * @param to 期待する送信先
     * @return 自己インスタンス（チェーンメソッド用）
     */
    public WiserAssertions to(String to) {
        findFirstOrElseThrow(m -> m.getEnvelopeReceiver().equals(to),
                assertionError("No message to [{0}] found!", to));
        return this;
    }

    /**
     * メール件名のアサーション.
     *
     * @param subject 期待するメール件名
     * @return 自己インスタンス（チェーンメソッド用）
     */
    public WiserAssertions withSubject(String subject) {
        findFirstOrElseThrow(m -> subject.equals(unchecked(getMimeMessage(m)::getSubject)),
                assertionError("No message with subject [{0}] found!", subject));
        return this;
    }

    /**
     * メール本文のアサーション.
     *
     * @param content 期待するメール本文
     * @return 自己インスタンス（チェーンメソッド用）
     */
    public WiserAssertions withContent(String content) {
        findFirstOrElseThrow(m -> {
            ThrowingSupplier<String> contentAsString = () -> ((String) getMimeMessage(m).getContent()).trim();
            return content.equals(unchecked(contentAsString));
        }, assertionError("No message with content [{0}] found!", content));
        return this;
    }

    /**
     * 値が存在する場合は値を返し、そうでない場合は例外提供関数によって生成された例外をスロー.
     *
     * @param predicate 値
     * @param exceptionSupplier 例外提供
     */
    private void findFirstOrElseThrow(Predicate<WiserMessage> predicate, Supplier<AssertionError> exceptionSupplier) {
        messages.stream().filter(predicate)
                .findFirst().orElseThrow(exceptionSupplier);
    }

    /**
     * ログをマイムメッセージとして取得.
     *
     * @param wiserMessage ログ
     * @return マイムメッセージ
     */
    private MimeMessage getMimeMessage(WiserMessage wiserMessage) {
        return unchecked(wiserMessage::getMimeMessage);
    }

    /**
     * アサーションエラーの生成.
     *
     * @param errorMessage エラーメッセージ
     * @param args 引数
     * @return アサーションエラー
     */
    private static Supplier<AssertionError> assertionError(String errorMessage, String... args) {
        return () -> new AssertionError(MessageFormat.format(errorMessage, args));
    }

    /**
     * 供給をget取得.
     *
     * @param <T> 総称型
     * @param supplier 供給
     * @return 供給のget取得した総称型値
     */
    public static <T> T unchecked(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * スロー供給機能.
     *
     * @param <T> 総称型
     */
    interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }
}
