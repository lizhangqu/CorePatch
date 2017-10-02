package io.github.lizhangqu.corepatch.applier;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * patch合成器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:10
 */
public interface Applier {
    /**
     * 是否支持
     *
     * @return 是否支持
     */
    boolean isSupport();

    /**
     * 合成patch
     *
     * @param oldRandomAccessFile 旧文件
     * @param patchInputStream    patch文件输入流
     * @param newOutputStream     合成的新文件输出流
     * @throws ApplierException 合成异常
     */
    void apply(RandomAccessFile oldRandomAccessFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException;

    /**
     * 合成patch
     *
     * @param oldFile   旧文件
     * @param patchFile patch文件
     * @param newFile   合成的新文件
     * @throws ApplierException 合成异常
     */
    void apply(File oldFile, File patchFile, File newFile) throws ApplierException;

    /**
     * 计算md5
     *
     * @param newFile 合成后的文件
     * @return md5
     */
    String calculateMD5(File newFile) throws ApplierException;

}
