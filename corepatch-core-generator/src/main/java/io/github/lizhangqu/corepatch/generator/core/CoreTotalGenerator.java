package io.github.lizhangqu.corepatch.generator.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 全量
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-04 10:24
 */
class CoreTotalGenerator extends CoreAbsWithMD5AndVerifyGenerator {
    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
        verify(oldFile, newFile, patchOutputStream);
        FileChannel fromChannel = null;
        WritableByteChannel toChannel = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(newFile);
            fromChannel = fileInputStream.getChannel();
            toChannel = Channels.newChannel(patchOutputStream);
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            patchOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (toChannel != null) {
                try {
                    toChannel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fromChannel != null) {
                try {
                    fromChannel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
        verify(oldFile, newFile, patchFile);
        try {
            generate(oldFile, newFile, new FileOutputStream(patchFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new GeneratorException("file not exist", e);
        }
    }
}
