package com.zoontek.rnbootsplash;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Window;
import android.view.WindowInsets;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class RNBootSplash {

  private static boolean showHasRunOnce = false;

  public static void show(final int drawableResId, @NonNull final Activity activity) {
    // if (showHasRunOnce) return;

    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Context context = activity.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View view = new View(context);

        view.setBackgroundResource(drawableResId);
        layout.setId(R.id.bootsplash_layout_id);
        layout.setLayoutTransition(null);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(view, params);

        activity.addContentView(layout, params);
        showHasRunOnce = true;
      }
    });
  }
  public static void adapt(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                    return defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.getSystemWindowInsetLeft(),
                            0,
                            defaultInsets.getSystemWindowInsetRight(),
                            defaultInsets.getSystemWindowInsetBottom());
                }
            });
            ViewCompat.requestApplyInsets(decorView);
            //将状态栏设成透明，如不想透明可设置其他颜色
            window.setStatusBarColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
    }
}
