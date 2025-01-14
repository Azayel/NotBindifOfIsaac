package game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class Isaac_Sounds {
    public static String[] MainMusic=new String[]{
            //"src/assets/Sound/Music/pixel-fight-8-bit-arcade-music-background-music-for-video-208775.wav",
            "src/assets/Sound/Music/Lyde - Wolf - 01 Spirit Animal.wav",
            "src/assets/Sound/Music/Lyde - Homeseeker - 05 Homeseeker.wav",
            "src/assets/Sound/Music/Lyde - Howl - 03 Seclusion.wav",
            "src/assets/Sound/Music/Lyde - Lawless - 01 Cyberwolf.wav",
            "src/assets/Sound/Music/Lyde - Unrest - 03 Neon Light Shootout.wav",
            "src/assets/Sound/Music/Lyde - Howl - 02 Trapped.wav",

    };
    public static String BossMusic="src/assets/Sound/Music/video-game-boss-fiight-259885.wav";
    public static String StartingLevelMusic="src/assets/Sound/Music/dead-by-daylight-10243.wav";


    public static Clip WaterSpell;
    public static Clip EnemyDeath;
    static {
        try {
            File audioFile = new File("src/assets/Sound/Effect/pop-268648.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            WaterSpell = AudioSystem.getClip();
            WaterSpell.open(audioStream);

            File audioFile2 = new File("src/assets/Sound/Effect/slow-wind-sound-effect-108401.wav");
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
            EnemyDeath = AudioSystem.getClip();
            EnemyDeath.open(audioStream2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
