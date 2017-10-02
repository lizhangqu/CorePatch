package io.github.lizhangqu.corepatch.applier.core;

import com.google.archivepatcher.applier.FileByFileV1DeltaApplier;
import com.google.archivepatcher.shared.DefaultDeflateCompatibilityWindow;
import com.google.archivepatcher.shared.JreDeflateParameters;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.InflaterInputStream;


/**
 * archive patch
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:37
 */
final class CoreArchivePatchApplier extends CoreAbsApplier {
    DefaultDeflateCompatibilityWindow compatibilityWindow = new DefaultDeflateCompatibilityWindow();

    @Override
    public boolean isSupport() {
        Map<JreDeflateParameters, String> incompatibleValues = compatibilityWindow.getIncompatibleValues();
        if (incompatibleValues == null || incompatibleValues.size() == 0) {
            return true;
        }
        for (JreDeflateParameters jreDeflateParameters : incompatibleValues.keySet()) {
            if (jreDeflateParameters.level == LEVEL && jreDeflateParameters.nowrap == NO_WRAP) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void applyPatch(File oldFile, OutputStream newOutputStream, InflaterInputStream patchInflaterInputStream) throws Exception {
        new FileByFileV1DeltaApplier().applyDelta(oldFile, patchInflaterInputStream, newOutputStream);
    }
}
