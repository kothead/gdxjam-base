package com.kothead.gdxjam.base.context;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.screen.ScreenBuilder;

public class LoadingContext extends ContextProxy {

    private ScreenBuilder<? extends LoadingProgressListener> builder;
    private AssetDescriptor[] descriptors;
    private boolean isLoading;
    private Array<String> assetsToUnload;

    public interface LoadingProgressListener {
        void onProgress(float progress);
    }

    public LoadingContext(Context context,
                          ScreenBuilder<? extends LoadingProgressListener> builder,
                          AssetDescriptor... descriptors) {
        super(context);
        this.builder = builder;
        this.descriptors = descriptors;
    }

    @Override
    public void enter(GdxJamGame game) {
        isLoading = true;

        AssetManager manager = game.getAssetManager();
        assetsToUnload = manager.getAssetNames();
        for (AssetDescriptor descriptor: descriptors) {
            if (manager.isLoaded(descriptor.fileName)) {
                removeDependency(manager, assetsToUnload, descriptor.fileName);
            } else {
                manager.load(descriptor);
            }
        }
    }

    private void removeDependency(AssetManager manager, Array<String> assets, String asset) {
        assets.removeValue(asset, false);

        Array<String> dependencies = manager.getDependencies(asset);
        if (dependencies != null) {
            for (String dependency : dependencies) {
                removeDependency(manager, assets, dependency);
            }
        }
    }

    @Override
    public void update(GdxJamGame game) {
        if (isLoading) {
            if (game.getScreen() == null) {
                game.setScreen(builder.build(game));
            }

            AssetManager manager = game.getAssetManager();
            ((LoadingProgressListener) game.getScreen()).onProgress(manager.getProgress());

            if (manager.update()) {
                for (String fileName : assetsToUnload) {
                    manager.unload(fileName);
                }

                isLoading = false;
                super.enter(game);
            }
        } else {
            super.update(game);
        }
    }
}
