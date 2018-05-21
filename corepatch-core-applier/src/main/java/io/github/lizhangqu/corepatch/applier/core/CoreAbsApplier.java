package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * 抽象的应用器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 22:00
 */
abstract class CoreAbsApplier extends CoreAbsWithMD5AndVerifyApplier {
    protected static final int LEVEL = 9;
    protected static final boolean NO_WRAP = true;
    protected static final int BUFFER_SIZE = 32768;

    @Override
    public final void apply(File oldFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        verify(oldFile, patchInputStream, newOutputStream);
        Inflater uncompressor = null;
        InflaterInputStream patchInflaterInputStream = null;
        if (patchInputStream instanceof InflaterInputStream) {
            patchInflaterInputStream = (InflaterInputStream) patchInputStream;
        } else {
            uncompressor = new Inflater(NO_WRAP); // to compress the patch
            patchInflaterInputStream =
                    new InflaterInputStream(patchInputStream, uncompressor, BUFFER_SIZE);
        }
        try {
            applyPatch(oldFile, patchInflaterInputStream, newOutputStream);
            newOutputStream.flush();
        } catch (Exception e) {
            throw new ApplierException("apply failure", e);
        } finally {
            if (uncompressor != null) {
                uncompressor.end();
            }
            try {
                patchInflaterInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                newOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public final void apply(File oldFile, File patchFile, File newFile) throws ApplierException {
        verify(oldFile, patchFile, newFile);
        Inflater uncompressor = new Inflater(NO_WRAP);
        InflaterInputStream patchInflaterInputStream = null;
        try {
            patchInflaterInputStream = new InflaterInputStream(new FileInputStream(patchFile), uncompressor, BUFFER_SIZE);
            apply(oldFile, patchInflaterInputStream, new FileOutputStream(newFile));
        } catch (FileNotFoundException e) {
            throw new ApplierException("file not exist", e);
        } finally {
            uncompressor.end();
        }
    }

    protected abstract void applyPatch(File oldFile, InflaterInputStream patchInflaterInputStream, OutputStream newOutputStream) throws Exception;

}
