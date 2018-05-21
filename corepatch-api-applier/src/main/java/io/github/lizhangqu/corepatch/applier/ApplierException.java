package io.github.lizhangqu.corepatch.applier;

/**
 * patch应用异常
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:15
 */
public class ApplierException extends Exception {

    public ApplierException(String message) {
        super(message);
    }

    public ApplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplierException(Throwable cause) {
        super(cause);
    }

}
