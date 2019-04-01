package a2.Controller.Actions.player;

import a2.Model.Bike.Bike;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class SteerAction extends AbstractInputAction {
    private final Bike bike;

    public SteerAction(Bike bike) {
        this.bike = bike;
    }

    @Override
    public void performAction(float delta, Event event) {
        if (event.getValue() > 0.2) {
            bike.steer(event.getValue()*-1, delta);
        } else if (event.getValue() < -0.2) {
            bike.steer(event.getValue()*-1, delta);
        }

    }
}
