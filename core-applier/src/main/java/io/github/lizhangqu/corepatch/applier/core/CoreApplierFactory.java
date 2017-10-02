package io.github.lizhangqu.corepatch.applier.core;

import io.github.lizhangqu.corepatch.applier.Applier;
import io.github.lizhangqu.corepatch.applier.ApplierFactory;

/**
 * 工厂
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:37
 */
final class CoreApplierFactory implements ApplierFactory<CoreApplierType> {
    @Override
    public Applier create(CoreApplierType type) {
        if (type == null) {
            return new CoreEmptyApplier();
        }
        Applier applier = null;
        switch (type) {
            case BS:
                applier = new CoreBsPatchApplier();
                break;
            case ARCHIVE:
                applier = new CoreArchivePatchApplier();
                break;
            default:
                applier = new CoreEmptyApplier();
                break;
        }
        return applier;
    }
}
