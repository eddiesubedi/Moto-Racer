package a2.Controller.Actions.player;

import a2.Model.Player;
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
