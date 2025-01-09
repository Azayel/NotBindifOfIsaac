package game;

import game.engine.objects.AbstractGameObject;
import game.engine.sound.SoundEngine;
import game.engine.graphics.Frame;
import game.engine.world.AbstractWorld;
import game.map.Isaac_World;

public class Main {
    public static void main(String[] args) {
        AbstractWorld world = null;
        Frame frame = new Frame();
        SoundEngine soundEngine = new SoundEngine();
        frame.displayOnScreen();

        world = new Isaac_World();

        world.setGraphicSystem(frame.getGraphicSystem());
        world.setInputSystem(frame.getInputSystem());
        world.setSoundEngine(soundEngine);


        // Todo this is not good style
        AbstractGameObject.setWorld(world);
        AbstractGameObject.setWorld(world);
        frame.getGraphicSystem().setWorld(world);

        world.init();
        world.run();
    }
}
