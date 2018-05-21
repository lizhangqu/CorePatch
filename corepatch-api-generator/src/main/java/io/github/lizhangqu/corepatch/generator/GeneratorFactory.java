package io.github.lizhangqu.corepatch.generator;

/**
 * patch生成器工厂
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:29
 */
public interface GeneratorFactory<T extends Enum> {
    Generator create(T type);
}
