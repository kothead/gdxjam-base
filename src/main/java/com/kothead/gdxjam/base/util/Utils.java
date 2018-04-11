package com.kothead.gdxjam.base.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class Utils {

    public static boolean isLandscape() {
        return Gdx.input.getNativeOrientation() == Input.Orientation.Landscape
                && (Gdx.input.getRotation() == 0 || Gdx.input.getRotation() == 180)
                || Gdx.input.getNativeOrientation() == Input.Orientation.Portrait
                && (Gdx.input.getRotation() == 90 || Gdx.input.getRotation() == 270);
    }

    public static <T> T choose(T... objects) {
        return objects[(int) (Math.random() * objects.length)];
    }

    public static <T> T[] join(T[] first, T[]... rest) {
        int length = first.length;
        for (T[] array: rest) {
            length += array.length;
        }

        T[] result = Arrays.copyOf(first, length);
        int offset = first.length;
        for (T[] array: rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}
