package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * archive patch
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:37
 */
final class CoreArchivePatchApplier extends CoreAbsApplier {
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void apply(RandomAccessFile oldFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        if (!isSupport()) {
            throw new ApplierException("not support");
        }
    }

    @Override
    public void apply(File oldFile, File patchFile, File newFile) throws ApplierException {
        if (!isSupport()) {
            throw new ApplierException("not support");
        }
    }

}
