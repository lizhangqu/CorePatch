package io.github.lizhangqu.corepatch.sample;

import com.google.archivepatcher.explainer.EntryExplanation;
import com.google.archivepatcher.explainer.PatchExplainer;
import com.google.archivepatcher.explainer.PatchExplanation;
import com.google.archivepatcher.generator.DeltaFriendlyOldBlobSizeLimiter;
import com.google.archivepatcher.generator.RecommendationModifier;
import com.google.archivepatcher.generator.RecommendationReason;
import com.google.archivepatcher.generator.TotalRecompressionLimiter;
import com.google.archivepatcher.generator.bsdiff.BsDiffDeltaGenerator;
import com.google.archivepatcher.shared.DeflateCompressor;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-03 08:32
 */
public class ExplainerTest {

    static File oldFile = new File("app/files/2.2.4.apk");
    static File newFile = new File("app/files/2.3.0.apk");
    static boolean outputJson = true;
    static Long totalRecompressionLimit = null;
    static Long deltaFriendlyOldBlobSizeLimit = null;

    @Test
    public void testExplainer() throws IOException, InterruptedException {
        DeflateCompressor compressor = new DeflateCompressor();
        compressor.setCompressionLevel(9);
        compressor.setNowrap(true);
        PatchExplainer explainer =
                new PatchExplainer(compressor, new BsDiffDeltaGenerator());
        List<RecommendationModifier> recommendationModifiers = new ArrayList<RecommendationModifier>();
        if (totalRecompressionLimit != null) {
            recommendationModifiers.add(new TotalRecompressionLimiter(totalRecompressionLimit));
        }
        if (deltaFriendlyOldBlobSizeLimit != null) {
            recommendationModifiers.add(
                    new DeltaFriendlyOldBlobSizeLimiter(deltaFriendlyOldBlobSizeLimit));
        }
        PatchExplanation patchExplanation =
                new PatchExplanation(
                        explainer.explainPatch(
                                oldFile,
                                newFile,
                                recommendationModifiers.toArray(new RecommendationModifier[]{})));
        if (outputJson) {
            patchExplanation.writeJson(new PrintWriter(System.out));
        } else {
            dumpPlainText(patchExplanation);
        }
    }

    private final NumberFormat format = NumberFormat.getNumberInstance();

    private void dumpPlainText(PatchExplanation patchExplanation) {
        dumpPlainText(patchExplanation.getExplainedAsNew());
        dumpPlainText(patchExplanation.getExplainedAsChanged());
        dumpPlainText(patchExplanation.getExplainedAsUnchangedOrFree());
        System.out.println("----------");
        System.out.println(
                "Num unchanged files: " + patchExplanation.getExplainedAsUnchangedOrFree().size());
        System.out.println(
                "Num changed files:   "
                        + patchExplanation.getExplainedAsChanged().size()
                        + " (estimated patch size "
                        + format.format(patchExplanation.getEstimatedChangedSize())
                        + " bytes)");
        System.out.println(
                "Num new files:       "
                        + patchExplanation.getExplainedAsNew().size()
                        + " (estimated patch size "
                        + format.format(patchExplanation.getEstimatedNewSize())
                        + " bytes)");
        System.out.println(
                "Num files changed but forced to stay compressed by the total recompression limit: "
                        + patchExplanation.getExplainedAsResourceConstrained().size()
                        + " (estimated patch size "
                        + format.format(patchExplanation.getEstimatedResourceConstrainedSize())
                        + " bytes)");
        long estimatedTotalSize =
                patchExplanation.getEstimatedChangedSize()
                        + patchExplanation.getEstimatedNewSize()
                        + patchExplanation.getEstimatedResourceConstrainedSize();
        System.out.println(
                "Estimated total patch size: " + format.format(estimatedTotalSize) + " bytes");
    }

    private void dumpPlainText(List<EntryExplanation> explanations) {
        for (EntryExplanation entryExplanation : explanations) {
            String text = toPlainText(entryExplanation);
            if (text != null) {
                System.out.println(text);
            }
        }
    }

    private static String path(EntryExplanation explanation) {
        try {
            return new String(explanation.getPath().getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("System doesn't support UTF-8", e);
        }
    }

    private static String toPlainText(EntryExplanation explanation) {
        String path = path(explanation);
        if (explanation.isNew()) {
            return "New file '"
                    + path
                    + "', approximate size of data in patch: "
                    + explanation.getCompressedSizeInPatch()
                    + " bytes";
        }
        if (explanation.getCompressedSizeInPatch() > 0) {
            String metadata = "";
            if (explanation.getReasonIncludedIfNotNew() == RecommendationReason.RESOURCE_CONSTRAINED) {
                metadata = " (forced to stay compressed by a limit)";
            }
            return "Changed file '"
                    + path
                    + "'"
                    + metadata
                    + ", approximate size of data in patch: "
                    + explanation.getCompressedSizeInPatch()
                    + " bytes";
        } else {
            return "Unchanged or zero-delta-cost file '" + path + "'";
        }
    }

}
