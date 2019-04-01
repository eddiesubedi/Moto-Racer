package a2.Controller.Actions.camera;

import a2.Model.Player;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class YawAction extends AbstractInputAction {
    private final Player player;

    public YawAction(Player player) {
        this.player = player;
    }

    @Override
    public void performAction(float delta, Event event) {
        if (event.getValue() > 0.5) {
//            player.yaw(Degreef.createFrom(-2.5f*delta));
        } else if (event.getValue() < -0.5) {
//            player.yaw(Degreef.createFrom(2.5f*delta));
        }
    }
}
