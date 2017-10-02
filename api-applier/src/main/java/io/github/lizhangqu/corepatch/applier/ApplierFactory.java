package io.github.lizhangqu.corepatch.applier;

/**
 * patch合成器工厂
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:28
 */
public interface ApplierFactory<T extends Enum> {
    Applier create(T type);
}
