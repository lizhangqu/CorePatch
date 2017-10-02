package io.github.lizhangqu.corepatch.generator.core;

import com.google.archivepatcher.generator.bsdiff.BsDiffPatchWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * bsdiff
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreBsDiffGenerator extends CoreAbsGenerator {
    private static final int MATCH_LENGTH_BYTES = 16;

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
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
        Deflater compressor = null;
        DeflaterOutputStream compressedPatchOutputStream = null;
        if (patchOutputStream instanceof DeflaterOutputStream) {
            compressedPatchOutputStream = (DeflaterOutputStream) patchOutputStream;
        } else {
            compressor = new Deflater(9, true); // to compress the patch
            compressedPatchOutputStream =
                    new DeflaterOutputStream(patchOutputStream, compressor, 32768);
        }
        try {
            BsDiffPatchWriter.generatePatch(oldFile, newFile, compressedPatchOutputStream, MATCH_LENGTH_BYTES);
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

    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
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
                throw new GeneratorException("create patchFile failure");
            }
        }
        Deflater compressor = new Deflater(9, true); // to compress the patch
        DeflaterOutputStream compressedPatchOutputStream = null;
        try {
            compressedPatchOutputStream =
                    new DeflaterOutputStream(new FileOutputStream(patchFile), compressor, 32768);
            generate(oldFile, newFile, compressedPatchOutputStream);
        } catch (FileNotFoundException e) {
            throw new GeneratorException("file not exist", e);
        } finally {
            compressor.end();
        }
    }

}
