package nico.styTool.plus;


import android.content.Context;
import android.view.View;

class CardViewApi21 implements CardViewImpl {

    @Override
    public void initialize(CardViewDelegate cardView, Context context, int backgroundColor,
            float radius, float elevation, float maxElevation) {
        final RoundRectDrawable backgroundDrawable = new RoundRectDrawable(backgroundColor, radius);
        cardView.setBackgroundDrawable(backgroundDrawable);
        View view = (View) cardView;
        view.setClipToOutline(true);
        view.setElevation(elevation);
        setMaxElevation(cardView, maxElevation);
    }

    @Override
    public void setRadius(CardViewDelegate cardView, float radius) {
        ((RoundRectDrawable) (cardView.getBackground())).setRadius(radius);
    }

    @Override
    public void initStatic() {
    }

    @Override
    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        ((RoundRectDrawable) (cardView.getBackground())).setPadding(maxElevation,
                cardView.getUseCompatPadding(), cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override
    public float getMaxElevation(CardViewDelegate cardView) {
        return ((RoundRectDrawable) (cardView.getBackground())).getPadding();
    }

    @Override
    public float getMinWidth(CardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getMinHeight(CardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getRadius(CardViewDelegate cardView) {
        return ((RoundRectDrawable) (cardView.getBackground())).getRadius();
    }

    @Override
    public void setElevation(CardViewDelegate cardView, float elevation) {
        ((View) cardView).setElevation(elevation);
    }

    @Override
    public float getElevation(CardViewDelegate cardView) {
        return ((View) cardView).getElevation();
    }

    @Override
    public void updatePadding(CardViewDelegate cardView) {
        if (!cardView.getUseCompatPadding()) {
            cardView.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float elevation = getMaxElevation(cardView);
        final float radius = getRadius(cardView);
        int hPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateHorizontalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        int vPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateVerticalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        cardView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
    }

    @Override
    public void onCompatPaddingChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void setBackgroundColor(CardViewDelegate cardView, int color) {
        ((RoundRectDrawable) (cardView.getBackground())).setColor(color);
    }
}
