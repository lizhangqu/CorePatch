package io.github.lizhangqu.corepatch.generator.core;


import java.io.File;
import java.io.OutputStream;

import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 空实现，不支持的实现
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:57
 */
final class CoreEmptyGenerator extends CoreAbsGenerator {
    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
        throw new GeneratorException("not support");
    }

    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
        throw new GeneratorException("not support");
    }

}
