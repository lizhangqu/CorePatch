package io.github.lizhangqu.corepatch.sample.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * apk签名验证
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-03 10:14
 */
public class SignatureVerify {
    private static final String CLASSES_DEX = "classes.dex";

    /**
     * 验证apk签名
     *
     * @param context Context
     * @param apkFile apk文件
     * @return 是否严重成功
     */
    public static boolean verifyApk(Context context, File apkFile) {
        if (context == null || apkFile == null || !apkFile.exists()) {
            return false;
        }
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(apkFile);
            JarEntry jarEntry = jarFile.getJarEntry(CLASSES_DEX);
            if (null == jarEntry) {// no code
                return false;
            }
            loadDigestes(jarFile, jarEntry);
            Certificate[] certs = jarEntry.getCertificates();
            if (certs == null) {
                return false;
            }
            return check(context, certs);
        } catch (Exception e) {
            return false;
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取数据
     *
     * @param jarFile JarFile
     * @param je      JarEntry
     * @throws IOException 异常
     */
    private static void loadDigestes(JarFile jarFile, JarEntry je) throws IOException {
        InputStream is = null;
        try {
            is = jarFile.getInputStream(je);
            byte[] bytes = new byte[8192];
            while (is.read(bytes) > 0) {
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用apk公钥去验证patch
     *
     * @param context Context
     * @param certs   Certificates
     * @return 是否验证成功
     * @throws Exception 异常
     */
    private static boolean check(Context context, Certificate[] certs) throws Exception {
        PublicKey apkPublicKey = getApkPublicKey(context);
        if (apkPublicKey == null) {
            return false;
        }
        if (certs != null && certs.length > 0) {
            for (int i = certs.length - 1; i >= 0; i--) {
                try {
                    certs[i].verify(apkPublicKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获得当前安装apk的公钥
     *
     * @param context Context
     * @return 公钥
     */
    private static PublicKey getApkPublicKey(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = pm.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream(
                    packageInfo.signatures[0].toByteArray());
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(stream);
            PublicKey publicKey = cert.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}