package game.utils;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.IInteractable;
import game.objects.items.Heart;
import game.objects.items.RedBooster;
import game.objects.items.YellowBooster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DroppableList {

    private List<Class<? extends AbstractGameObject>> list;

    public DroppableList(){
        this.list = new ArrayList<>();
    }

    public void addItem(Class<? extends AbstractGameObject> item){
        this.list.add(item);
    }

    public AbstractGameObject getItem(double x, double y){
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        Class<? extends AbstractGameObject> randomClass = list.get(randomIndex);

        try {
            return randomClass.getConstructor(double.class, double.class).newInstance(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
