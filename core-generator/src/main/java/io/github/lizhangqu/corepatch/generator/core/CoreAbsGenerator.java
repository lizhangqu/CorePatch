package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 抽象的生成器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 22:02
 */
abstract class CoreAbsGenerator extends CoreAbsWithMD5AndVerifyGenerator {
    protected static final int LEVEL = 9;
    protected static final boolean NO_WRAP = true;
    protected static final int BUFFER_SIZE = 32768;

    @Override
    public final void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
        verify(oldFile, newFile, patchFile);
        Deflater compressor = new Deflater(LEVEL, NO_WRAP); // to compress the patch
        DeflaterOutputStream compressedPatchOutputStream = null;
        try {
            compressedPatchOutputStream =
                    new DeflaterOutputStream(new FileOutputStream(patchFile), compressor, BUFFER_SIZE);
            generate(oldFile, newFile, compressedPatchOutputStream);
        } catch (FileNotFoundException e) {
            throw new GeneratorException("file not exist", e);
        } finally {
            compressor.end();
        }
    }

    @Override
    public final void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
        verify(oldFile, newFile, patchOutputStream);
        Deflater compressor = null;
        DeflaterOutputStream compressedPatchOutputStream = null;
        if (patchOutputStream instanceof DeflaterOutputStream) {
            compressedPatchOutputStream = (DeflaterOutputStream) patchOutputStream;
        } else {
            compressor = new Deflater(LEVEL, NO_WRAP); // to compress the patch
            compressedPatchOutputStream =
                    new DeflaterOutputStream(patchOutputStream, compressor, BUFFER_SIZE);
        }
        try {
            generatePatch(oldFile, newFile, compressedPatchOutputStream);
            compressedPatchOutputStream.finish();
            compressedPatchOutputStream.flush();
        } catch (Exception e) {
            throw new GeneratorException("generate failure", e);
        } finally {
            if (compressor != null) {
                compressor.end();
            }
            try {
                compressedPatchOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void generatePatch(File oldFile, File newFile, DeflaterOutputStream compressedPatchOutputStream) throws Exception;

}
