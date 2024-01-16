import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    private Clip clip;
    private File[] sounds = new File[10];

    public Sound(int type) {
        if (type == 0) {
            sounds[0] = new File("src/Sound/BackgroundMusic_1.wav");
            sounds[1] = new File("src/Sound/green.wav");
        } else if (type == 1) {
            sounds[0] = new File("src/Sound/keypickup.wav");
            sounds[1] = new File("src/Sound/selectNoise.wav");
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setClip(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
