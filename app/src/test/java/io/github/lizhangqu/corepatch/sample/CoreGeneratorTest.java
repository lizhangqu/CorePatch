package io.github.lizhangqu.corepatch.sample;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import io.github.lizhangqu.corepatch.generator.Generator;
import io.github.lizhangqu.corepatch.generator.GeneratorException;
import io.github.lizhangqu.corepatch.generator.core.CoreGenerator;
import io.github.lizhangqu.corepatch.generator.core.CoreGeneratorType;

/**
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 21:21
 */
public class CoreGeneratorTest {
    static File oldFile = new File("app/files/2.2.4.apk");
    static File newFile = new File("app/files/2.3.0.apk");
    static File archivePatchFileToFile = new File("app/files/archive-patch-out-to-file.diff");
    static File archivePatchFileToStream = new File("app/files/archive-patch-out-to-stream.diff");
    static File bsPatchFileToFile = new File("app/files/bs-patch-out-to-file.diff");
    static File bsPatchFileToStream = new File("app/files/bs-patch2-out-to-stream.diff");


    @Test
    public void testGenerateBsDiffOutToFile() throws GeneratorException {
        long start = System.currentTimeMillis();
        Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.BS);
        try {
            generator.generate(oldFile, newFile, bsPatchFileToFile);
            Assert.assertTrue(bsPatchFileToFile.exists());
            Assert.assertTrue(bsPatchFileToFile.length() > 0);
            String md5 = generator.calculateMD5(bsPatchFileToFile);
            System.out.println("md5:" + md5);
            Assert.assertNotNull(md5);
        } catch (GeneratorException e) {
            e.printStackTrace();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("testGenerateBsDiffOutToFile time:" + (end - start));
        }

    }

    @Test
    public void testGenerateBsDiffOutToStream() throws GeneratorException, FileNotFoundException {
        long start = System.currentTimeMillis();
        Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.BS);
        try {
            generator.generate(oldFile, newFile, new FileOutputStream(bsPatchFileToStream));
            Assert.assertTrue(bsPatchFileToStream.exists());
            Assert.assertTrue(bsPatchFileToStream.length() > 0);
            String md5 = generator.calculateMD5(bsPatchFileToStream);
            System.out.println("md5:" + md5);
            Assert.assertNotNull(md5);
        } catch (GeneratorException e) {
            e.printStackTrace();
            throw e;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("testGenerateBsDiffOutToStream time:" + (end - start));
        }
    }

    @Test
    public void testGenerateArchivePatchOutToFile() throws GeneratorException {
        long start = System.currentTimeMillis();
        Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.ARCHIVE);
        try {
            generator.generate(oldFile, newFile, archivePatchFileToFile);
            Assert.assertTrue(archivePatchFileToFile.exists());
            Assert.assertTrue(archivePatchFileToFile.length() > 0);
            String md5 = generator.calculateMD5(archivePatchFileToFile);
            System.out.println("md5:" + md5);
            Assert.assertNotNull(md5);
        } catch (GeneratorException e) {
            e.printStackTrace();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("testGenerateArchivePatchOutToFile time:" + (end - start));
        }

    }

    @Test
    public void testGenerateArchivePatchOutToStream() throws GeneratorException, FileNotFoundException {
        long start = System.currentTimeMillis();
        Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.ARCHIVE);
        try {
            generator.generate(oldFile, newFile, new FileOutputStream(archivePatchFileToStream));
            Assert.assertTrue(archivePatchFileToStream.exists());
            Assert.assertTrue(archivePatchFileToStream.length() > 0);
            String md5 = generator.calculateMD5(archivePatchFileToStream);
            System.out.println("md5:" + md5);
            Assert.assertNotNull(md5);
        } catch (GeneratorException e) {
            e.printStackTrace();
            throw e;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("testGenerateArchivePatchOutToStream time:" + (end - start));
        }
    }
}
