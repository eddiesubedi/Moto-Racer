package motoracer.Model;

import motoracer.Controller.Actions.player.*;
import motoracer.Controller.Camera3PController;
import motoracer.Model.Bike.Bike;
import net.java.games.input.Component;
import ray.input.InputManager;
import ray.rage.Engine;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.scene.*;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import java.awt.*;
import java.io.IOException;

import static motoracer.utils.JsEngine.getJsEngine;
import static motoracer.utils.Utils.executeScript;
import static net.java.games.input.Component.Identifier.Axis.*;
import static net.java.games.input.Component.Identifier.Axis.X;
import static ray.input.InputManager.INPUT_ACTION_TYPE.*;
import static ray.rage.scene.Camera.Frustum.Projection.PERSPECTIVE;

public class Player {
    private Camera3PController camera3PController;
    private SceneManager sceneManager;
    private final int viewportNumber;
    private final String nodeName;
    private Bike bike;
    private float maxSpeed;
    private float engineForce;
    private float mass;
    private float drag;
    private float brakeStrength;
    private float handling;

    Player(SceneManager sceneManager, String nodeName, int viewportNumber) throws IOException{
        this.viewportNumber = viewportNumber;
        this.sceneManager = sceneManager;
        this.nodeName = nodeName;
        setupBike();
        Light plight = sceneManager.createLight("testLamp1", Light.Type.POINT);
        plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(.7f, .7f, .7f));
        plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
        plight.setRange(5f);
        SceneNode plightNode =sceneManager.getRootSceneNode().createChildSceneNode("plightNode");
        plightNode.attachObject(plight);
    }

    private void setupBike() throws IOException {
        runScripts();
        bike = new Bike(sceneManager, nodeName, maxSpeed, engineForce, mass, drag, brakeStrength, handling,
                Vector3f.createFrom(36.0f,0.0f,242.66505f));
    }

    void setupPlayerCamera() {
        SceneNode rootNode = sceneManager.getRootSceneNode();
        Camera camera = sceneManager.createCamera(nodeName + "Camera", PERSPECTIVE);
        sceneManager.getRenderSystem().getRenderWindow().getViewport(viewportNumber).setCamera(camera);
        SceneNode cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node");
        camera.setMode('n');
        cameraNode.attachObject(camera);
//        cameraNode.moveBackward(150);
        camera3PController = new Camera3PController(cameraNode, bike.getNode());
    }

    void setupControls(InputManager inputManager) {
        this.camera3PController.setupInput(inputManager);
        String gamepadName = inputManager.getFirstGamepadName();
        String keyboardName = inputManager.getKeyboardName();
        if(gamepadName==null){
            System.out.println("Controller not connected");
        } else {
            inputManager.associateAction(gamepadName, X, new SteerAction(bike), REPEAT_WHILE_DOWN);
            inputManager.associateAction(gamepadName, Component.Identifier.Button._0, new AccelerateAction(bike), ON_PRESS_AND_RELEASE);
            inputManager.associateAction(gamepadName, Component.Identifier.Button._2, new BrakeAction(bike), ON_PRESS_AND_RELEASE);
            inputManager.associateAction(gamepadName, Component.Identifier.Button._3, new RunScripts(this), ON_PRESS_ONLY);
        }

        inputManager.associateAction(keyboardName, Key.W, new AccelerateAction(bike), ON_PRESS_AND_RELEASE);
        inputManager.associateAction(keyboardName, Key.S, new BrakeAction(bike), ON_PRESS_AND_RELEASE);
        inputManager.associateAction(keyboardName, Key.A, new SteerLeftAction(bike), REPEAT_WHILE_DOWN);
        inputManager.associateAction(keyboardName, Key.D, new SteerRightAction(bike), REPEAT_WHILE_DOWN);
        inputManager.associateAction(keyboardName, Key.R, new RunScripts(this), ON_PRESS_ONLY);
    }

    void updatePlayer(float delta) {
        camera3PController.updateCameraPosition();
        bike.update(delta / 100);
    }

    void updateHUD(RenderSystem renderSystem, int y) {
        renderSystem.setHUD(bike.toString(), 0,y);
    }

    private void runScripts() {
        executeScript("playerBike.js");
        maxSpeed = ((Double) getJsEngine().engine.get("maxSpeed")).floatValue();
        engineForce = ((Double) getJsEngine().engine.get("engineForce")).floatValue();
        mass = ((Double) getJsEngine().engine.get("mass")).floatValue();
        drag = ((Double) getJsEngine().engine.get("drag")).floatValue();
        brakeStrength = ((Double) getJsEngine().engine.get("brakeStrength")).floatValue();
        handling = ((Double) getJsEngine().engine.get("handling")).floatValue();
    }

    public void reRunScripts() {
        runScripts();
        bike.updateValues(maxSpeed, engineForce, mass, drag, brakeStrength, handling);
    }

    public SceneNode getTransform(){
        return bike.getTransform();
    }

    public float getVelocity(){
        return bike.getVelocity();
    }

}
