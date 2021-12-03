package uk.insrt.coursework.zuul.sound;

import java.util.HashMap;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import uk.insrt.coursework.zuul.events.IEventListener;

public class SoundManager implements IEventListener<EventSound> {
    private TinySound lib;
    private boolean loaded;
    private boolean initialised;
    private HashMap<SoundType, Sound> sounds;

    public SoundManager() {
        this.loaded = false;
        this.initialised = false;
        this.sounds = new HashMap<>();
    }

    public void init() throws Exception {
        this.lib = TinySound.init();
        this.initialised = true;

        this.sounds.put(SoundType.MoneyBag, lib.loadSound("/sounds/money-bag.wav"));
        this.loaded = true;
    }

    public void dispose() {
        if (!this.initialised) return;
        this.lib.shutdown();
    }

    @Override
    public void onEvent(EventSound event) {
        if (!this.loaded) return;
        this.sounds.get(event.getTarget()).play();
    }
}
