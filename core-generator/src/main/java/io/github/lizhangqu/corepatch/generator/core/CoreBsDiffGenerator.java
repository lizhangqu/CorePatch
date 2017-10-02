package io.github.lizhangqu.corepatch.generator.core;

import com.google.archivepatcher.generator.bsdiff.BsDiffPatchWriter;

import java.io.File;
import java.util.zip.DeflaterOutputStream;


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
    protected void generatePatch(File oldFile, File newFile, DeflaterOutputStream compressedPatchOutputStream) throws Exception {
        BsDiffPatchWriter.generatePatch(oldFile, newFile, compressedPatchOutputStream, MATCH_LENGTH_BYTES);
    }
}
