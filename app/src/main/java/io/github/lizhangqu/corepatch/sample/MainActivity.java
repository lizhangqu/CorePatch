package io.github.lizhangqu.corepatch.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import hugo.weaving.DebugLog;
import io.github.lizhangqu.corepatch.applier.Applier;
import io.github.lizhangqu.corepatch.applier.ApplierException;
import io.github.lizhangqu.corepatch.applier.core.CoreApplier;
import io.github.lizhangqu.corepatch.applier.core.CoreApplierType;
import io.github.lizhangqu.corepatch.sample.util.PackageUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String supportInfo = getSupportInfo();
        ((TextView) findViewById(R.id.support_list)).setText(supportInfo);


        findViewById(R.id.bs_patch).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    @DebugLog
                    public void run() {
                        File oldFile = null;
                        try {
                            InputStream open = getResources().getAssets().open("2.2.4.apk");
                            ReadableByteChannel fromChannel = Channels.newChannel(open);
                            oldFile = File.createTempFile("bspatch", "old");
                            FileChannel toChannel = new FileOutputStream(oldFile).getChannel();
                            toChannel.transferFrom(fromChannel, 0, Long.MAX_VALUE);

                            InputStream patch = getResources().getAssets().open("bspatch.diff");

                            final File newFile = File.createTempFile("bspatch", "new");

                            Applier applier = CoreApplier.getInstance().getApplier(CoreApplierType.BS);
                            applier.apply(oldFile, patch, new FileOutputStream(newFile));
                            String md5 = applier.calculateMD5(newFile);
                            if ("6C8947954FB5401D2FD7E43755959D31".equals(md5)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) findViewById(R.id.show)).setText("success");
                                        PackageUtils.installNormal(getApplicationContext(), newFile.getAbsolutePath());
                                    }
                                });
                            }
                        } catch (final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.show)).setText(e.getMessage());
                                }
                            });
                            e.printStackTrace();

                        } catch (final ApplierException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.show)).setText(e.getMessage());

                                }
                            });
                            e.printStackTrace();
                        } finally {
                            if (oldFile != null) {
                                boolean delete = oldFile.delete();
                                if (!delete) {
                                    oldFile.deleteOnExit();
                                }
                            }
                        }
                    }
                }).start();

            }
        });
        findViewById(R.id.archive_patch).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    @DebugLog
                    public void run() {
                        File oldFile = null;
                        try {
                            InputStream open = getResources().getAssets().open("2.2.4.apk");
                            ReadableByteChannel fromChannel = Channels.newChannel(open);
                            oldFile = File.createTempFile("archivepatch", "old");
                            FileChannel toChannel = new FileOutputStream(oldFile).getChannel();
                            toChannel.transferFrom(fromChannel, 0, Long.MAX_VALUE);

                            InputStream patch = getResources().getAssets().open("archivepatch.diff");

                            final File newFile = File.createTempFile("archivepatch", "new");

                            Applier applier = CoreApplier.getInstance().getApplier(CoreApplierType.ARCHIVE);
                            applier.apply(oldFile, patch, new FileOutputStream(newFile));
                            String md5 = applier.calculateMD5(newFile);
                            if ("6C8947954FB5401D2FD7E43755959D31".equals(md5)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) findViewById(R.id.show)).setText("success");
                                        PackageUtils.installNormal(getApplicationContext(), newFile.getAbsolutePath());
                                    }
                                });

                            }
                        } catch (final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.show)).setText(e.getMessage());
                                }
                            });

                            e.printStackTrace();
                        } catch (final ApplierException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.show)).setText(e.getMessage());
                                }
                            });
                            e.printStackTrace();
                        } finally {
                            if (oldFile != null) {
                                boolean delete = oldFile.delete();
                                if (!delete) {
                                    oldFile.deleteOnExit();
                                }
                            }
                        }
                    }
                }).start();

            }
        });
    }

    @DebugLog
    private String getSupportInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        CoreApplierType[] values = CoreApplierType.values();
        if (values != null) {
            for (CoreApplierType type : values) {
                Applier applier = CoreApplier.getInstance().getApplier(type);
                if (applier.isSupport()) {
                    stringBuilder.append("name:");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(" is supported");
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("name:");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(" is not supported");
                    stringBuilder.append("\n");
                }
            }

        }
        return stringBuilder.toString();
    }
}
