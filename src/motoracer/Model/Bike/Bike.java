package motoracer.Model.Bike;

import ray.rage.Engine;
import ray.rage.scene.*;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.FloatBuffer;
import java.util.Arrays;

import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;

public class Bike extends BikePhysics {

    private final SceneManager sceneManager;
    private SceneNode playerNode;
    private Tessellation tessE;
    private boolean collision;

    public Bike(SceneManager sceneManager, String name, float maxSpeed, float engineForce, float mass, float drag,
                float brakeStrength, double handling, Vector3 position) throws IOException {
        super(maxSpeed, engineForce, mass, drag, brakeStrength, handling);
        this.sceneManager = sceneManager;
        Entity bikeEntity = sceneManager.createEntity(name, "scooter3.obj");
        bikeEntity.setPrimitive(TRIANGLES);
        playerNode = sceneManager.getRootSceneNode().createChildSceneNode(name + "Node");
        playerNode.attachObject(bikeEntity);
        playerNode.scale(.20f,.20f,.20f);

//        FloatBuffer b = bikeEntity.getMesh().getSubMesh(0).getVertexBuffer();
//        float [] c = new float[b.limit()];
//        b.get(c);
//        System.out.println(Arrays.toString(c));

//        PrintStream o = new PrintStream(new File("ai1.txt"));
//        System.setOut(o);
    }

    @Override
    public void update(float delta) {
        if(tessE == null) {
            tessE = ((Tessellation) sceneManager.getSceneNode("TessN").getAttachedObject("tessE"));
        }
        checkCollision(delta);
    }

    private void checkCollision(float delta) {
        Vector3 worldAvatarPosition = playerNode.getWorldPosition();
        float height = tessE.getWorldHeight(worldAvatarPosition.x(), worldAvatarPosition.z());
        if(height > 0) {
            stop();
        } else {
            calculateTransformations(delta);
            Vector3 localAvatarPosition = playerNode.getLocalPosition();
            Vector3 newPosition = Vector3f.createFrom(localAvatarPosition.x(), height, localAvatarPosition.z());
            playerNode.setLocalPosition(newPosition);
        }
        System.out.println(playerNode.getLocalPosition().x() +","+
                playerNode.getLocalPosition().y()+","+
                playerNode.getLocalPosition().z()+", "+
                Arrays.toString(playerNode.getLocalRotation().toFloatArray()));

        super.update(delta);
    }


    private void calculateTransformations(float delta) {
        Vector3 position =
                playerNode.getLocalPosition().add(playerNode.getLocalForwardAxis().mult(getVelocity()).mult(delta));
        playerNode.setLocalPosition(position);
    }

    public float[] steer(float steeringAngle, float delta) {
        float[] travelValues = super.steer(steeringAngle, delta);
        if(travelValues.length>0){
            float degreesToTravel = travelValues[0];
            float forwardToTravel= travelValues[1];
            float rightToTravel= travelValues[2];
            playerNode.setLocalPosition(
                    playerNode.getLocalPosition().add(playerNode.getLocalRightAxis().mult(rightToTravel)
                            .mult(Math.signum(steeringAngle)))
            );
            playerNode.setLocalPosition(
                    playerNode.getLocalPosition().add(playerNode.getLocalForwardAxis().mult(forwardToTravel))
            );
            playerNode.yaw(Degreef.createFrom(degreesToTravel));
        }
        return new float[0];
    }

    public SceneNode getNode() {
        return playerNode;
    }

    public SceneNode getTransform() {
        return playerNode;
    }

}
