package com.kothead.gdxjam.base.data.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureRegionLoader extends AsynchronousAssetLoader<TextureRegion, TextureRegionLoader.TextureRegionParameter> {

    public TextureRegionLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextureRegionParameter parameter) {
    }

    @Override
    public TextureRegion loadSync(AssetManager manager, String fileName, FileHandle file, TextureRegionParameter parameter) {
        if (parameter != null && parameter.atlasName != null && parameter.frameName != null) {
            TextureAtlas atlas = manager.get(parameter.atlasName, TextureAtlas.class);
            if (parameter.index != null) {
                return atlas.findRegion(parameter.frameName, parameter.index);
            } else {
                return atlas.findRegion(parameter.frameName);
            }
        } else {
            Texture texture = manager.get(fileName, Texture.class);
            return new TextureRegion(texture);
        }
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextureRegionParameter parameter) {
        Array<AssetDescriptor> dependencies = new Array<>();
        if (parameter != null && parameter.atlasName != null && parameter.frameName != null) {
            dependencies.add(new AssetDescriptor<TextureAtlas>(parameter.atlasName, TextureAtlas.class));
        } else {
            dependencies.add(new AssetDescriptor<Texture>(fileName, Texture.class));
        }
        return dependencies;
    }

    public static class TextureRegionParameter extends AssetLoaderParameters<TextureRegion> {
        public String atlasName;
        public String frameName;
        public Integer index;

        public TextureRegionParameter(String atlasName, String frameName) {
            this.atlasName = atlasName;
            this.frameName = frameName;
        }

        public TextureRegionParameter(String atlasName, String frameName, int index) {
            this(atlasName, frameName);
            this.index = index;
        }
    }
}
