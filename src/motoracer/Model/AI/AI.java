package motoracer.Model.AI;

import Server.Position;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Matrix3;
import ray.rml.Vector3;

import java.io.IOException;
import java.util.ArrayList;

import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;

public class AI {
    private final SceneNode aiNode;
    private ArrayList<Vector3> wayPoints;
    private ArrayList<Matrix3> roations;
    private int i = 0;

    public AI(SceneManager sceneManager, String name, Vector3 position) throws IOException {
        Entity bikeEntity = sceneManager.createEntity(name, "scooter3.obj");
        bikeEntity.setPrimitive(TRIANGLES);
        aiNode = sceneManager.getRootSceneNode().createChildSceneNode(name + "Node");
        aiNode.attachObject(bikeEntity);
        aiNode.scale(.20f,.20f,.20f);
        aiNode.setLocalPosition(position);
//        Position wayPoint = new Position("ai1.txt");
//        wayPoints = wayPoint.getPositions();
//        roations = wayPoint.getRotations();
    }
    public void update(SceneNode node){
//        aiNode.setLocalPosition(wayPoints.get(i));
//        i++;
//        node.setLocalPosition(wayPoints.get(i));
//        node.setLocalRotation(roations.get(i));
//        i++;
    }
}
