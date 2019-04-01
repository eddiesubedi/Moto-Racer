package motoracer.Controller;

import motoracer.Controller.Actions.ElevationDownAction;
import motoracer.Controller.Actions.ElevationUpAction;
import motoracer.Controller.Actions.camera.OrbitZoomAction;
import net.java.games.input.Component;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import static java.lang.Math.*;
import static net.java.games.input.Component.Identifier.Axis.*;
import static ray.input.InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN;
import static ray.rml.Vector3f.createFrom;

public class Camera3PController {
    private SceneNode cameraNode;
    private SceneNode targetNode;
    private float cameraAzimuth;
    private float cameraElevation;
    private float radius;
    private Vector3 worldUpVector;

    public Camera3PController(SceneNode cameraNode, SceneNode targetNode) {
        this.cameraNode = cameraNode;
        this.targetNode = targetNode;
        this.cameraAzimuth = 180;
        this.cameraElevation = 60;
        this.radius = 5f;
        this.worldUpVector = createFrom(0f, 1f, 0f);
        updateCameraPosition();
    }

    public void setupInput(InputManager inputManager) {
        String controllerName = inputManager.getFirstGamepadName();
        linkActionToGamePad(inputManager, controllerName, new OrbitZoomAction(this), POV);
        linkActionToGamePad(inputManager, controllerName, new ElevationUpAction(this), Button._5);
        linkActionToGamePad(inputManager, controllerName, new ElevationDownAction(this), Button._4);
    }

    private void linkActionToGamePad(InputManager inputManager, String controllerName, Action action,
                                     Component.Identifier identifier) {
        inputManager.associateAction(controllerName, identifier, action, REPEAT_WHILE_DOWN);
    }

    public void updateCameraPosition() {
        float phi = (float) toRadians(cameraElevation);
        cameraNode.setLocalPosition(targetNode.getLocalPosition().sub(targetNode.getLocalForwardAxis().mult(radius)));
        float x = cameraNode.getLocalPosition().x();
        float y = cameraNode.getLocalPosition().y() + phi;
        float z = cameraNode.getLocalPosition().z();
        cameraNode.setLocalPosition(Vector3f.createFrom(x, y, z));
        cameraNode.lookAt(targetNode, worldUpVector);
    }

    public float getCameraAzimuth() {
        return cameraAzimuth;
    }

    public void setCameraAzimuth(float cameraAzimuth) {
        this.cameraAzimuth = cameraAzimuth;
    }

    public float getCameraElevation() {
        return cameraElevation;
    }

    public void setCameraElevation(float cameraElevation) {
        this.cameraElevation = cameraElevation;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
