package io.github.lizhangqu.corepatch.generator.core;

import com.google.archivepatcher.generator.bsdiff.BsDiffPatchWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorException;

/**
 * 抽象的生成器
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 22:02
 */
public abstract class CoreAbsGenerator implements Generator {
    protected static final int LEVEL = 9;
    protected static final boolean NO_WRAP = true;
    protected static final int BUFFER_SIZE = 32768;

    private String getFileMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        FileInputStream in = null;
        byte buffer[] = new byte[2048];
        int len;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 2048)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected void verify(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
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
    }

    private void verify(File oldFile, File newFile, File patchFile) throws GeneratorException {
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
    }


    @Override
    public void generate(File oldFile, File newFile, File patchFile) throws GeneratorException {
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
    public void generate(File oldFile, File newFile, OutputStream patchOutputStream) throws GeneratorException {
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


    @Override
    public String calculateMD5(File patchFile) throws GeneratorException {
        String fileMD5 = getFileMD5(patchFile);
        if (fileMD5 == null || fileMD5.length() == 0) {
            throw new GeneratorException("calculate md5 error");
        }
        return fileMD5;
    }
}
