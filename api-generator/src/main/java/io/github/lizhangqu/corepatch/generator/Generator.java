package io.github.lizhangqu.corepatch.generator;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * patch生成器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:09
 */
public interface Generator {
    /**
     * 是否支持
     *
     * @return 是否支持
     */
    boolean isSupport();

    /**
     * 生成patch
     *
     * @param oldFile           旧文件
     * @param newFile           新文件
     * @param patchOutputStream 目标patch文件输出流
     * @throws GeneratorException 生成异常
     */
    void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException;

    /**
     * 生成patch
     *
     * @param oldFile   旧文件
     * @param newFile   新文件
     * @param patchFile 目标patch文件
     * @throws GeneratorException 生成异常
     */
    void generate(File oldFile, File newFile, File patchFile) throws GeneratorException;

}
