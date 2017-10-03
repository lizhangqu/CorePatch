package io.github.lizhangqu.corepatch.sample;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-03 15:27
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}
