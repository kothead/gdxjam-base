package com.kothead.gdxjam.base.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Arrays;
import java.util.Random;

public class Utils {

    private static Random random = new Random();

    public static boolean isLandscape() {
        return Gdx.input.getNativeOrientation() == Input.Orientation.Landscape
                && (Gdx.input.getRotation() == 0 || Gdx.input.getRotation() == 180)
                || Gdx.input.getNativeOrientation() == Input.Orientation.Portrait
                && (Gdx.input.getRotation() == 90 || Gdx.input.getRotation() == 270);
    }

    public static <T> T choose(T... objects) {
        return choose(random, objects);
    }

    public static <T> T choose(Random random, T... objects) {
        return objects[(int) (random.nextDouble() * objects.length)];
    }

    public static <T> T[] choose(int count, T... objects) {
        return choose(random, count, objects);
    }

    public static <T> T[] choose(Random random, int count, T... objects) {
        T[] result = shuffle(random, objects);
        int length = Math.min(count, result.length);
        return Arrays.copyOf(result, length);
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

    public static <T> T[] shuffle(T... objects) {
        return shuffle(random, objects);
    }

    public static <T> T[] shuffle(Random random, T... objects) {
        T[] result = Arrays.copyOf(objects, objects.length);
        for (int i = result.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            T temp = result[index];
            result[index] = result[i];
            result[i] = temp;
        }
        return result;
    }
}
