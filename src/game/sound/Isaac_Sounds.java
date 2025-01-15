package game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

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
    public static Clip SpiderDeath;
    public static Clip SpiderAttack;
    public static Clip BossDeath;
    public static Clip BossLazer;
    public static Clip BossLazerBallzs;
    public static Clip BossFire;
    public static Clip AvatarDamage;
    public static Clip Teleport;
    public static Clip HeartPickup;
    static {
        try {
            File audioFile = new File("src/assets/Sound/Effect/pop-268648.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            WaterSpell = AudioSystem.getClip();
            WaterSpell.open(audioStream);

            File audioFile2 = new File("src/assets/Sound/Effect/slap-hurt-pain-sound-effect-262618.wav");
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
            SpiderDeath = AudioSystem.getClip();
            SpiderDeath.open(audioStream2);

            File audioFile3 = new File("src/assets/Sound/Effect/ough-47202.wav");
            AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(audioFile3);
            AvatarDamage = AudioSystem.getClip();
            AvatarDamage.open(audioStream3);

            File audioFile4 = new File("src/assets/Sound/Effect/warp-sfx-6897.wav");
            AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(audioFile4);
            Teleport = AudioSystem.getClip();
            Teleport.open(audioStream4);

            File audioFile5 = new File("src/assets/Sound/Effect/coin-collect-retro-8-bit-sound-effect-145251.wav");
            AudioInputStream audioStream5 = AudioSystem.getAudioInputStream(audioFile5);
            HeartPickup = AudioSystem.getClip();
            HeartPickup.open(audioStream5);

            File audioFile6 = new File("src/assets/Sound/Effect/slow-wind-sound-effect-108401.wav");
            AudioInputStream audioStream6 = AudioSystem.getAudioInputStream(audioFile6);
            BossDeath = AudioSystem.getClip();
            BossDeath.open(audioStream6);

            File audioFile7 = new File("src/assets/Sound/Effect/fizzy-water-bottle-open-001-93570.wav");
            AudioInputStream audioStream7 = AudioSystem.getAudioInputStream(audioFile7);
            SpiderAttack = AudioSystem.getClip();
            SpiderAttack.open(audioStream7);

            File audioFile8 = new File("src/assets/Sound/Effect/11-105697.wav");
            AudioInputStream audioStream8 = AudioSystem.getAudioInputStream(audioFile8);
            BossLazer = AudioSystem.getClip();
            BossLazer.open(audioStream8);

            File audioFile9 = new File("src/assets/Sound/Effect/fireball-whoosh-7-201453.wav");
            AudioInputStream audioStream9 = AudioSystem.getAudioInputStream(audioFile9);
            BossFire = AudioSystem.getClip();
            BossFire.open(audioStream9);

            File audioFile10 = new File("src/assets/Sound/Effect/laser-beam-76426.wav");
            AudioInputStream audioStream10 = AudioSystem.getAudioInputStream(audioFile10);
            BossLazerBallzs = AudioSystem.getClip();
            BossLazerBallzs.open(audioStream10);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
