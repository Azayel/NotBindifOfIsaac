package Game;

import Game.*;

public class main {
    public static void main(String[] args) {
        World world = null;
        Frame frame = new Frame();
        frame.displayOnScreen();

        world = new Isaac_World();

        world.setGraphicSystem(frame.getGraphicSystem());
        world.setInputSystem(frame.getInputSystem());

        GameObject.setWorld(world);
        TextObject.setWorld(world);
        frame.getGraphicSystem().setWorld(world);

        world.init();
        world.run();
    }
}
