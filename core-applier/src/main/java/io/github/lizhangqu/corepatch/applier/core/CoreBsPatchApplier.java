package io.github.lizhangqu.corepatch.applier.core;

import com.google.archivepatcher.applier.bsdiff.BsPatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

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
    public void apply(RandomAccessFile oldRandomAccessFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        if (!isSupport()) {
            throw new ApplierException("not support");
        }
        if (oldRandomAccessFile == null) {
            throw new ApplierException("oldRandomAccessFile == null");
        }
        if (patchInputStream == null) {
            throw new ApplierException("patchInputStream == null");
        }
        if (newOutputStream == null) {
            throw new ApplierException("newOutputStream == null");
        }
        Inflater uncompressor = null;
        InflaterInputStream patchInflaterInputStream = null;
        if (patchInputStream instanceof InflaterInputStream) {
            patchInflaterInputStream = (InflaterInputStream) patchInputStream;
        } else {
            uncompressor = new Inflater(true); // to compress the patch
            patchInflaterInputStream =
                    new InflaterInputStream(patchInputStream, uncompressor, 32768);
        }
        try {
            BsPatch.applyPatch(oldRandomAccessFile, newOutputStream, patchInflaterInputStream);
            newOutputStream.flush();
        } catch (IOException e) {
            throw new ApplierException("apply failure");
        } finally {
            if (uncompressor != null) {
                uncompressor.end();
            }
            try {
                oldRandomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
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
    public void apply(File oldFile, File patchFile, File newFile) throws ApplierException {
        if (!isSupport()) {
            throw new ApplierException("not support");
        }
        if (oldFile == null) {
            throw new ApplierException("oldFile == null");
        }
        if (patchFile == null) {
            throw new ApplierException("oldFile == null");
        }
        if (!oldFile.exists()) {
            throw new ApplierException("oldFile not exists");
        }
        if (!patchFile.exists()) {
            throw new ApplierException("patchFile not exists");
        }
        if (newFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            newFile.delete();
        }
        if (!newFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            newFile.getParentFile().mkdirs();
        }
        if (!newFile.exists()) {
            try {
                boolean newFileResult = newFile.createNewFile();
                if (!newFileResult) {
                    throw new ApplierException("create newFile failure");
                }
            } catch (IOException e) {
                throw new ApplierException("create newFile failure");
            }
        }
        Inflater uncompressor = new Inflater(true);
        InflaterInputStream patchInflaterInputStream = null;
        try {
            patchInflaterInputStream = new InflaterInputStream(new FileInputStream(patchFile), uncompressor, 32768);
            apply(new RandomAccessFile(oldFile, "r"), patchInflaterInputStream, new FileOutputStream(newFile));
        } catch (FileNotFoundException e) {
            throw new ApplierException("file not exist");
        } finally {
            uncompressor.end();
        }
    }

}
