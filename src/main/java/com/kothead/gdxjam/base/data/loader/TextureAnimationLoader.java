package com.kothead.gdxjam.base.data.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.kothead.gdxjam.base.util.Direction;
import com.kothead.gdxjam.base.util.TextureAnimation;

public class TextureAnimationLoader extends AsynchronousAssetLoader<TextureAnimation, TextureAnimationLoader.AnimationParameter> {

    private Builder builder;

    public TextureAnimationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, AnimationParameter parameter) {
        builder = new Json().fromJson(Builder.class, file.readString());
    }

    @Override
    public TextureAnimation loadSync(AssetManager manager, String fileName, FileHandle file, AnimationParameter parameter) {
        TextureAnimation animation = builder.build(manager);
        builder = null;
        return animation;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AnimationParameter parameter) {
        Builder builder = new Json().fromJson(Builder.class, file.readString());
        Array<AssetDescriptor> dependencies = new Array<>();
        dependencies.add(new AssetDescriptor<TextureAtlas>(builder.atlasName, TextureAtlas.class));
        return dependencies;
    }

    public static class AnimationParameter extends AssetLoaderParameters<TextureAnimation> {
    }

    private static class Builder {
        private static final float BASE_ANIMATION_SPEED = 0.05f;

        private String atlasName;
        private String frameName;
        private int count = 1;
        private float duration = BASE_ANIMATION_SPEED;
        private Animation.PlayMode mode;
        private boolean flipx = false;
        private boolean flipy = false;

        public Builder(String atlasName, String frameName) {
            this.atlasName = atlasName;
            this.frameName = frameName;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder duration(float duration) {
            this.duration = duration;
            return this;
        }

        public Builder mode(Animation.PlayMode mode) {
            this.mode = mode;
            return this;
        }

        public Builder flip(boolean x, boolean y) {
            this.flipx = x;
            this.flipy = y;
            return this;
        }

        public Builder flip(Direction direction) {
            if (!direction.isOrthogonal()) {
                throw new IllegalArgumentException("Cannot flip in animation diagonal direction: " + direction);
            }

            flipx = direction.getDx() < 0;
            flipy = direction.getDy() < 0;

            return this;
        }

        public TextureAnimation build(AssetManager manager) {
            Array<TextureRegion> regions = new Array<>();
            TextureAtlas atlas = manager.get(atlasName);
            for (int i = 0; i < count; i++) {
                TextureRegion region = atlas.findRegion(frameName, i);
                if (flipx || flipy) {
                    region = new TextureRegion(region);
                    region.flip(flipx, flipy);
                }
                regions.add(region);
            }
            return new TextureAnimation(duration, regions, mode);
        }
    }
}
