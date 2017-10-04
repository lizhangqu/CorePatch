package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import io.github.lizhangqu.corepatch.applier.Applier;
import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * 带md5计算和验证的抽象实现
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-04 10:40
 */
abstract class CoreAbsWithMD5AndVerifyApplier implements Applier {

    private String getFileMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        FileInputStream in = null;
        byte buffer[] = new byte[2048];
        int len;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 2048)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public final String calculateMD5(File newFile) throws ApplierException {
        String fileMD5 = getFileMD5(newFile);
        if (fileMD5 == null || fileMD5.length() == 0) {
            throw new ApplierException("calculate md5 error");
        }
        return fileMD5;
    }


    protected void verify(File oldFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        if (!isSupport()) {
            throw new ApplierException("not support");
        }
        if (oldFile == null) {
            throw new ApplierException("oldRandomAccessFile == null");
        }
        if (!oldFile.exists()) {
            throw new ApplierException("oldFile not exists");
        }
        if (patchInputStream == null) {
            throw new ApplierException("patchInputStream == null");
        }
        if (newOutputStream == null) {
            throw new ApplierException("newOutputStream == null");
        }
    }

    protected void verify(File oldFile, File patchFile, File newFile) throws ApplierException {
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
                throw new ApplierException("create newFile failure", e);
            }
        }
    }


}
