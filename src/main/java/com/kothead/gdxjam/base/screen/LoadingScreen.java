package com.kothead.gdxjam.base.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.context.LoadingContext;

public class LoadingScreen extends BaseScreen implements LoadingContext.LoadingProgressListener {

    private static final int HEIGHT = 20;
    private static final int PADDING = 40;

    private ProgressBar progressBar;

    public LoadingScreen(GdxJamGame game) {
        super(game);
    }

    @Override
    protected void layoutViewsLandscape(int width, int height) {
        layout(width, height);
    }

    @Override
    protected void layoutViewsPortrait(int width, int height) {
        layout(width, height);
    }

    protected void layout(int width, int height) {
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.knob = fillWithColor(0, HEIGHT, Color.GREEN);
        style.background = fillWithColor(width - PADDING * 2, HEIGHT, Color.DARK_GRAY);

        progressBar = new ProgressBar(0f, 1f, 0.01f, false, style);
        progressBar.setAnimateDuration(0.25f);
        progressBar.setBounds(PADDING, (height - HEIGHT) / 2f, width - PADDING * 2, HEIGHT);
    }

    protected Drawable fillWithColor(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    @Override
    public void onProgress(float progress) {
        progressBar.setValue(progress);
    }

    public static class Builder implements ScreenBuilder<LoadingScreen> {

        @Override
        public LoadingScreen build(GdxJamGame game) {
            return new LoadingScreen(game);
        }
    }
}
