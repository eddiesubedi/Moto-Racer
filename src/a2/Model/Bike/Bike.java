package a2.Model.Bike;

import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Degreef;
import ray.rml.Vector3;

import java.io.IOException;

import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;

public class Bike extends BikePhysics {

    private SceneNode transform;
    private SceneNode playerNode;

    public Bike(SceneManager sceneManager, String name, float maxSpeed, float engineForce, float mass, float drag,
                float brakeStrength, double handling) throws IOException {
        super(maxSpeed, engineForce, mass, drag, brakeStrength, handling);
        Entity dolphinE = sceneManager.createEntity(name, "dirt_bike_blender.obj");
        dolphinE.setPrimitive(TRIANGLES);
        playerNode = sceneManager.getRootSceneNode().createChildSceneNode(name + "Node");
        playerNode.attachObject(dolphinE);
        transform = playerNode.createChildSceneNode(name + "TransformNode");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        calculateTransformations(delta);
        applyTransformations();
    }

    private void calculateTransformations(float delta) {
        Vector3 position =
                transform.getLocalPosition().add(transform.getLocalForwardAxis().mult(getVelocity()).mult(delta));
        transform.setLocalPosition(position);
    }

    private void applyTransformations() {
        Vector3 newPosition = playerNode.getLocalPosition().lerp(transform.getLocalPosition(), .2f);
        playerNode.setLocalPosition(newPosition);
        playerNode.setLocalRotation(transform.getLocalRotation());
    }

    public float[] steer(float steeringAngle, float delta) {
        float[] travelValues = super.steer(steeringAngle, delta);
        if(travelValues.length>0){
            float degreesToTravel = travelValues[0];
            float forwardToTravel= travelValues[1];
            float rightToTravel= travelValues[2];
            transform.setLocalPosition(
                    transform.getLocalPosition().add(transform.getLocalRightAxis().mult(rightToTravel)
                            .mult(Math.signum(steeringAngle)))
            );
            transform.setLocalPosition(
                    transform.getLocalPosition().add(transform.getLocalForwardAxis().mult(forwardToTravel))
            );
            transform.yaw(Degreef.createFrom(degreesToTravel));
        }
        return new float[0];
    }

    public SceneNode getNode() {
        return playerNode;
    }

}
