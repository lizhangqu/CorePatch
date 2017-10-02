package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorFactory;

/**
 * 工厂
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreGeneratorFactory implements GeneratorFactory<CoreGeneratorType> {
    @Override
    public Generator create(CoreGeneratorType type) {
        if (type == null) {
            return new CoreEmptyGenerator();
        }
        Generator generator = null;
        switch (type) {
            case BS:
                generator = new CoreBsDiffGenerator();
                break;
            case ARCHIVE:
                generator = new CoreArchiveGenerator();
                break;
            default:
                generator = new CoreEmptyGenerator();
                break;
        }
        return generator;
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
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
            return bigInt.toString(16);
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
}
