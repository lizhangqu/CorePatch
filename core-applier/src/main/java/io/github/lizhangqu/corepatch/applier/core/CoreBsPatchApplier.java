package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import io.github.lizhangqu.corepatch.applier.Applier;
import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * bspatch
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:37
 */
final class CoreBsPatchApplier implements Applier {
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void apply(InputStream oldInputStream, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {

    }

    @Override
    public void apply(File oldFile, File patchFile, File newFile) throws ApplierException {

    }

    @Override
    public void apply(RandomAccessFile oldFile, RandomAccessFile patchFile, RandomAccessFile newFile) throws ApplierException {

    }
}
