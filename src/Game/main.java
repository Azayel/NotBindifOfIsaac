package Game;

import Game.*;

public class main {
    public static void main(String[] args) {
        World world = null;
        Frame frame = new Frame();
        SoundEngine soundEngine = new SoundEngine();
        frame.displayOnScreen();

        world = new Isaac_World();

        world.setGraphicSystem(frame.getGraphicSystem());
        world.setInputSystem(frame.getInputSystem());
        world.setSoundEngine(soundEngine);

        GameObject.setWorld(world);
        TextObject.setWorld(world);
        frame.getGraphicSystem().setWorld(world);

        world.init();
        world.run();
    }
}
