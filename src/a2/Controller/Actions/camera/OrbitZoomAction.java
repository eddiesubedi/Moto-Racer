package a2.Controller.Actions.camera;

import a2.Controller.Camera3PController;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

import static net.java.games.input.Component.POV.*;

public class OrbitZoomAction extends AbstractInputAction {
    private final Camera3PController camera;

    public OrbitZoomAction(Camera3PController camera) {
        this.camera = camera;
    }

    @Override
    public void performAction(float delta, Event event) {
        float povValue = event.getValue();
        float rotateAmount = 0;
        float radius = camera.getRadius();
        if (povValue == LEFT) {
            rotateAmount = -0.2f;
        } else if (povValue == RIGHT) {
            rotateAmount = 0.2f;
        } else if (povValue == UP) {
            radius = radius+0.02f;
        } else if (povValue == DOWN) {
            radius = radius-0.02f;
        }
        float cameraAzimuth = camera.getCameraAzimuth();
        cameraAzimuth += rotateAmount;
        cameraAzimuth = cameraAzimuth % 360;
        camera.setCameraAzimuth(cameraAzimuth);
        camera.setRadius(radius);
        camera.updateCameraPosition();
    }
}
