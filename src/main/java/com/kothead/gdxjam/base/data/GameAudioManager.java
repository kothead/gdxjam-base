package com.kothead.gdxjam.base.data;

import com.badlogic.gdx.audio.Music;

public class GameAudioManager implements Music{

    private Music music;
    private OnCompletionListener listener;
    private boolean looping;
    private float pan;
    private float volume;

    public void setMusic(Music music) {
        this.music = music;
        music.setOnCompletionListener(listener);
        music.setLooping(looping);
        music.setPan(pan, volume);
        music.setPosition(0);
    }

    @Override
    public void play() {
        if (music != null) music.play();
    }

    @Override
    public void pause() {
        if (music != null) music.pause();
    }

    @Override
    public void stop() {
        if (music != null) music.stop();
    }

    @Override
    public boolean isPlaying() {
        return music != null ? music.isPlaying() : false;
    }

    @Override
    public void setLooping(boolean isLooping) {
        looping = isLooping;
        if (music != null) music.setLooping(looping);
    }

    @Override
    public boolean isLooping() {
        return looping;
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
        if (music != null) music.setVolume(volume);
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public void setPan(float pan, float volume) {
        this.pan = pan;
        this.volume = volume;
        music.setPan(pan, volume);
    }

    @Override
    public void setPosition(float position) {
        if (music != null) music.setPosition(position);
    }

    @Override
    public float getPosition() {
        return music != null ? music.getPosition() : 0;
    }

    @Override
    public void dispose() {
        if (music != null) music.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.listener = listener;
        if (music != null) music.setOnCompletionListener(listener);
    }
}
