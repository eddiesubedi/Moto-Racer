package a2.Controller.Actions;

import a2.Controller.Camera3PController;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class ElevationUpAction extends AbstractInputAction {
    private final Camera3PController camera;

    public ElevationUpAction(Camera3PController camera) {
        this.camera = camera;
    }
    @Override
    public void performAction(float delta, Event event) {
        float elevation = camera.getCameraElevation();
        elevation = elevation+0.2f;
        camera.setCameraElevation(elevation);
        camera.updateCameraPosition();
    }
}
