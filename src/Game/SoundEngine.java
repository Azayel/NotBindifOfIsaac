package Game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEngine {
    private Clip musicClip;
    private Thread musicThread;
    private boolean looping;

    public static SoundEngine instance;

    public SoundEngine() {
        if (instance == null) {
            instance = this;
        }
    }

    public void playMusic(String filePath, boolean looping) {
        this.looping = looping;

        stopMusic(); // Stop any currently playing music

        musicThread = new Thread(() -> {
            loadMusic(filePath);
            if (musicClip != null) {

                musicClip.setFramePosition(0); // Start from the beginning
                if (looping) {
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    musicClip.start();
                }

                // Wait for the clip to finish if not looping
                if (!looping) {
                    while (musicClip.isRunning() && !Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    stopMusic();
                }
            }
        });

        musicThread.start();
    }

    public void stopMusic() {
        // Stop and close the current music thread
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
            musicThread = null;
        }

        // Stop and close the current clip
        if (musicClip != null && musicClip.isOpen()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    public void playSound(String filePath) {
        new Thread(() -> {
            try {
                System.out.println("Playing sound: " + filePath);
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip soundClip = AudioSystem.getClip();
                soundClip.open(audioStream);

                soundClip.start();

                // Wait for the sound to finish playing
                while (soundClip.isRunning()) {
                    Thread.sleep(50);
                }

                soundClip.close();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }

    private void loadMusic(String filePath) {
        try {
            // Close the existing clip if open
            if (musicClip != null && musicClip.isOpen()) {
                musicClip.stop();
                musicClip.close();
            }

            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }


}
