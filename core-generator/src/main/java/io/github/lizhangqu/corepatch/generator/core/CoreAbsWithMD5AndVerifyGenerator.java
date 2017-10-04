package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 带MD5计算和校验的抽象实现
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-04 10:34
 */
abstract class CoreAbsWithMD5AndVerifyGenerator implements Generator {
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
    public final String calculateMD5(File patchFile) throws GeneratorException {
        String fileMD5 = getFileMD5(patchFile);
        if (fileMD5 == null || fileMD5.length() == 0) {
            throw new GeneratorException("calculate md5 error");
        }
        return fileMD5;
    }

    protected void verify(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
        if (!isSupport()) {
            throw new GeneratorException("not support");
        }
        if (oldFile == null) {
            throw new GeneratorException("oldFile == null");
        }
        if (newFile == null) {
            throw new GeneratorException("newFile == null");
        }
        if (!oldFile.exists()) {
            throw new GeneratorException("oldFile not exists");
        }
        if (!newFile.exists()) {
            throw new GeneratorException("newFile not exists");
        }
        if (patchOutputStream == null) {
            throw new GeneratorException("patchOutputStream == null");
        }
    }

    protected void verify(File oldFile, File newFile, File patchFile) throws GeneratorException {
        if (!isSupport()) {
            throw new GeneratorException("not support");
        }
        if (oldFile == null) {
            throw new GeneratorException("oldFile == null");
        }
        if (newFile == null) {
            throw new GeneratorException("newFile == null");
        }
        if (!oldFile.exists()) {
            throw new GeneratorException("oldFile not exists");
        }
        if (!newFile.exists()) {
            throw new GeneratorException("newFile not exists");
        }
        if (patchFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            patchFile.delete();
        }
        if (!patchFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            patchFile.getParentFile().mkdirs();
        }
        if (!patchFile.exists()) {
            try {
                boolean newFileResult = patchFile.createNewFile();
                if (!newFileResult) {
                    throw new GeneratorException("create patchFile failure");
                }
            } catch (IOException e) {
                throw new GeneratorException("create patchFile failure", e);
            }
        }
    }


}
