package uk.insrt.coursework.zuul.sound;

import java.util.HashMap;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import uk.insrt.coursework.zuul.events.EventSystem;

/**
 * Class used to manage background music and foreground sounds.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.1-SNAPSHOT
 */
public class SoundManager {
    private TinySound lib;
    private boolean loaded;
    private boolean initialised;

    private HashMap<SoundType, Sound> sounds;
    private HashMap<MusicType, Sound> music;

    /**
     * Construct a new SoundManager
     */
    public SoundManager() {
        this.loaded = false;
        this.initialised = false;
        this.sounds = new HashMap<>();
        this.music = new HashMap<>();
    }

    /**
     * Initialise the sound manager and load all music and sound files.
     * @throws Exception if something goes wrong.
     */
    public void init() throws Exception {
        this.lib = TinySound.init();
        this.initialised = true;

        this.sounds.put(SoundType.MoneyBag, lib.loadSound("/sounds/money-bag.ogg"));

        this.music.put(MusicType.Bay1, lib.loadSound("/sounds/bay1-enc.ogg"));
        this.music.put(MusicType.Bay2, lib.loadSound("/sounds/bay2-enc.ogg"));
        this.music.put(MusicType.Nature, lib.loadSound("/sounds/nature-enc.ogg"));

        this.music.put(MusicType.BgmExplore, lib.loadSound("/sounds/bgm-explore.ogg"));
        this.music.put(MusicType.BgmMission, lib.loadSound("/sounds/bgm-mission.ogg"));
        this.music.put(MusicType.BgmConclusion, lib.loadSound("/sounds/bgm-conclude.ogg"));

        this.loaded = true;
    }

    /**
     * Clean up the sound library.
     */
    public void dispose() {
        if (!this.initialised) return;
        this.lib.shutdown();
    }

    /**
     * Register sound and music events with provided event system.
     * @param eventSystem Event system
     */
    public void register(EventSystem eventSystem) {
        eventSystem.addListener(EventSound.class, event -> {
            if (!this.loaded) return;
            this.sounds.get(event.getTarget()).play();
        });

        eventSystem.addListener(EventMusic.class, event -> {
            if (!this.loaded) return;
            Sound song = this.music.get(event.getTarget());
            if (event.shouldPlay()) {
                song.play();
            } else {
                song.stop();
            }
        });
    }
}
