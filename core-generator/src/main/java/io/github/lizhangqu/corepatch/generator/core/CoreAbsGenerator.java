package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 抽象的生成器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 22:02
 */
public abstract class CoreAbsGenerator implements Generator {
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
    public String calculateMD5(File patchFile) throws GeneratorException {
        String fileMD5 = getFileMD5(patchFile);
        if (fileMD5 == null || fileMD5.length() == 0) {
            throw new GeneratorException("calculate md5 error");
        }
        return fileMD5;
    }
}
