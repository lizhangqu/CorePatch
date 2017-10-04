package io.github.lizhangqu.corepatch.applier.core;


import com.google.archivepatcher.applier.bsdiff.BsPatch;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.InflaterInputStream;

/**
 * bspatch
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:37
 */
final class CoreBsPatchApplier extends CoreAbsApplier {

    @Override
    public boolean isSupport() {
        return true;
    }


    @Override
    protected void applyPatch(File oldFile, InflaterInputStream patchInflaterInputStream, OutputStream newOutputStream) throws Exception {
        RandomAccessFile oldRandomAccessFile = new RandomAccessFile(oldFile, "r");
        try {
            BsPatch.applyPatch(oldRandomAccessFile, newOutputStream, patchInflaterInputStream);
        } finally {
            try {
                oldRandomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
