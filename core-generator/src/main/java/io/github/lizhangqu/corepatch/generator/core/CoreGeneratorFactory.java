package io.github.lizhangqu.corepatch.generator.core;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorFactory;

/**
 * 工厂
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreGeneratorFactory implements GeneratorFactory<CoreGeneratorType> {
    @Override
    public Generator create(CoreGeneratorType type) {
        if (type == null) {
            return new CoreEmptyGenerator();
        }
        Generator generator = null;
        switch (type) {
            case BS:
                generator = new CoreBsDiffGenerator();
                break;
            case ARCHIVE:
                generator = new CoreArchiveGenerator();
                break;
            default:
                generator = new CoreEmptyGenerator();
                break;
        }
        return generator;
    }
}
