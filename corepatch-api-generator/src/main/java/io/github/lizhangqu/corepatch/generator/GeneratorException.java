package io.github.lizhangqu.corepatch.generator;

/**
 * patch生成异常
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:18
 */
public class GeneratorException extends Exception {

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }

}
