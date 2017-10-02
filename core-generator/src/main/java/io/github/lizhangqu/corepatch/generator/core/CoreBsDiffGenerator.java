package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * bsdiff
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreBsDiffGenerator implements Generator {
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void generate(InputStream oldInputStream, InputStream newInputStream, OutputStream patchOutputStream) throws GeneratorException {

    }

    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {

    }

    @Override
    public void generate(RandomAccessFile oldFile, RandomAccessFile newFile, RandomAccessFile patchFile) throws GeneratorException {

    }
}
