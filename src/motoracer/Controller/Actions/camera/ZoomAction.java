package motoracer.Controller.Actions.camera;

import motoracer.Controller.Camera3PController;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class ZoomAction extends AbstractInputAction {
    private final Camera3PController camera;

    public ZoomAction(Camera3PController camera) {
        this.camera = camera;
    }
    @Override
    public void performAction(float delta, Event event) {
        float radius = camera.getRadius();
        if(event.getValue()<-.2) {
            radius = radius-0.02f;
        } else if(event.getValue()>.2) {
            radius = radius+0.02f;
        }
        camera.setRadius(radius);
        camera.updateCameraPosition();
    }
}
