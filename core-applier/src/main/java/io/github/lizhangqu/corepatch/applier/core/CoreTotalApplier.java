package io.github.lizhangqu.corepatch.applier.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import io.github.lizhangqu.corepatch.applier.ApplierException;

/**
 * 全量
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-04 10:23
 */
class CoreTotalApplier extends CoreAbsWithMD5AndVerifyApplier {

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public void apply(File oldFile, InputStream patchInputStream, OutputStream newOutputStream) throws ApplierException {
        verify(oldFile, patchInputStream, newOutputStream);
        ReadableByteChannel fromChannel = null;
        WritableByteChannel toChannel = null;
        try {
            fromChannel = Channels.newChannel(patchInputStream);
            toChannel = Channels.newChannel(newOutputStream);

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (fromChannel.read(buffer) != -1) {
                buffer.flip();
                toChannel.write(buffer);
                buffer.clear();
            }
            newOutputStream.flush();
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
    public void apply(File oldFile, File patchFile, File newFile) throws ApplierException {
        verify(oldFile, patchFile, newFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(patchFile);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            apply(oldFile, fileInputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ApplierException("file not exist", e);
        }

    }
}
