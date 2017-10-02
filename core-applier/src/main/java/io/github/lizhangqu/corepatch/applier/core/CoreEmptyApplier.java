package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * 空实现，不支持的实现
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:56
 */
final class CoreEmptyApplier extends CoreAbsApplier {
    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public void apply(RandomAccessFile oldFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        throw new ApplierException("not support");
    }

    @Override
    public void apply(File oldFile, File patchFile, File newFile) throws ApplierException {
        throw new ApplierException("not support");
    }

    @Override
    public String calculateMD5(File newFile) throws ApplierException {
        throw new ApplierException("not support");
    }

}
