package android.zeroh729.com.pcari.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtil {
    private float dpWidth;

    public ScreenUtil(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = activity.getResources().getDisplayMetrics().density;
        dpWidth = outMetrics.widthPixels / density;
    }

    public float getWidth() {
        return dpWidth;
    }
}
