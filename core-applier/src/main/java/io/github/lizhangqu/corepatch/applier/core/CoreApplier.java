package io.github.lizhangqu.corepatch.applier.core;

import io.github.lizhangqu.corepatch.applier.Applier;
import io.github.lizhangqu.corepatch.applier.ApplierFactory;

/**
 * applier 单例
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:50
 */
public class CoreApplier {
    private static CoreApplier sInstance;
    private ApplierFactory<CoreApplierType> factory;

    private CoreApplier() {
        factory = new CoreApplierFactory();
    }

    /**
     * 单例
     *
     * @return CoreApplier
     */
    public CoreApplier getInstance() {
        if (sInstance == null) {
            synchronized (CoreApplier.class) {
                if (sInstance == null) {
                    sInstance = new CoreApplier();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得应用器
     *
     * @param type 类型
     * @return 应用器，不为空
     */
    public Applier getApplier(CoreApplierType type) {
        if (factory == null) {
            return new CoreEmptyApplier();
        }
        return factory.create(type);
    }
}
