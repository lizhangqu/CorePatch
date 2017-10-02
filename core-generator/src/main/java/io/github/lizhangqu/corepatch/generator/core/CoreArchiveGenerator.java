package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.OutputStream;

import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * archive diff
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreArchiveGenerator extends CoreAbsGenerator {
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
        if (!isSupport()) {
            throw new GeneratorException("not support");
        }
    }

    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
        if (!isSupport()) {
            throw new GeneratorException("not support");
        }
    }

}
