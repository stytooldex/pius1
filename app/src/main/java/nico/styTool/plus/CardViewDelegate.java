package nico.styTool.plus;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Interface provided by CardView to implementations.
 * <p>
 * Necessary to resolve circular dependency between base CardView and platform implementations.
 */
interface CardViewDelegate {
    void setBackgroundDrawable(Drawable paramDrawable);
    Drawable getBackground();
    boolean getUseCompatPadding();
    boolean getPreventCornerOverlap();
    float getRadius();
    void setShadowPadding(int left, int top, int right, int bottom);
}
