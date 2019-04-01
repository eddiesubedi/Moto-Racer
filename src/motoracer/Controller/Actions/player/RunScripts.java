package motoracer.Controller.Actions.player;

import motoracer.Model.Player;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class RunScripts extends AbstractInputAction {
    private final Player player;

    public RunScripts(Player player) {
        this.player = player;
    }

    @Override
    public void performAction(float delta, Event event) {
        player.reRunScripts();
    }


}
