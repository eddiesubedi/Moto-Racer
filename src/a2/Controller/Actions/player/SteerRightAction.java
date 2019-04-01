package a2.Controller.Actions.player;

import a2.Model.Bike.Bike;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class SteerRightAction extends AbstractInputAction {
    private final Bike bike;

    public SteerRightAction(Bike bike) {
        this.bike = bike;
    }

    @Override
    public void performAction(float delta, Event event) {
        bike.steer(-1, delta);
    }


}
