package io.github.lizhangqu.corepatch.generator.core;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorFactory;

/**
 * generator 单例
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:50
 */
public class CoreGenerator {
    private static CoreGenerator sInstance;
    private GeneratorFactory<CoreGeneratorType> factory;

    private CoreGenerator() {
        factory = new CoreGeneratorFactory();
    }

    /**
     * 单例
     *
     * @return CoreGenerator
     */
    public static CoreGenerator getInstance() {
        if (sInstance == null) {
            synchronized (CoreGenerator.class) {
                if (sInstance == null) {
                    sInstance = new CoreGenerator();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得生成器
     *
     * @param type 类型
     * @return 生成器，不为空
     */
    public Generator getGenerator(CoreGeneratorType type) {
        if (factory == null) {
            return new CoreEmptyGenerator();
        }
        return factory.create(type);
    }

    /**
     * 获得生成器
     *
     * @param type 类型
     * @return 生成器，不为空
     */
    public Generator getGenerator(String type) {
        if (factory == null || type == null || type.length() == 0) {
            return new CoreEmptyGenerator();
        }
        CoreGeneratorType[] values = CoreGeneratorType.values();
        if (values != null && values.length > 0) {
            for (CoreGeneratorType coreGeneratorType : values) {
                if (coreGeneratorType.getName().equalsIgnoreCase(type)) {
                    return factory.create(coreGeneratorType);
                }
            }
        }
        return new CoreEmptyGenerator();
    }
}
