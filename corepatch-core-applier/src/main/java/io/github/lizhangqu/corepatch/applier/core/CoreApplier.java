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
    public static CoreApplier getInstance() {
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

    /**
     * 获得应用器
     *
     * @param type 类型
     * @return 应用器，不为空
     */
    public Applier getApplier(String type) {
        if (factory == null || type == null || type.length() == 0) {
            return new CoreEmptyApplier();
        }
        CoreApplierType[] values = CoreApplierType.values();
        //noinspection ConstantConditions
        if (values != null && values.length > 0) {
            for (CoreApplierType coreApplierType : values) {
                if (coreApplierType.getName().equalsIgnoreCase(type)) {
                    return factory.create(coreApplierType);
                }
            }
        }
        return new CoreEmptyApplier();
    }
}
