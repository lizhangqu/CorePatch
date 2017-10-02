package io.github.lizhangqu.corepatch.generator.core;

import com.google.archivepatcher.generator.FileByFileV1DeltaGenerator;
import com.google.archivepatcher.shared.DefaultDeflateCompatibilityWindow;
import com.google.archivepatcher.shared.JreDeflateParameters;

import java.io.File;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;


/**
 * archive diff
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:38
 */
final class CoreArchiveGenerator extends CoreAbsGenerator {
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
    protected void generatePatch(File oldFile, File newFile, DeflaterOutputStream compressedPatchOutputStream) throws Exception {
        new FileByFileV1DeltaGenerator().generateDelta(oldFile, newFile, compressedPatchOutputStream);
    }

}
