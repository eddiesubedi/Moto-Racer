package motoracer.utils;

import ray.rage.scene.Node;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class VectorHelpers {
    public static float distance(Vector3f v1, Vector3f v2) {
        double xPow = Math.pow(v1.x() - v2.x(), 2);
        double yPow = Math.pow(v1.y() - v2.y(), 2);
        double zPow = Math.pow(v1.z() - v2.z(), 2);
        return (float) Math.sqrt(xPow+yPow+zPow);
    }
    public static float distance(Vector3 v1, Vector3 v2) {
        return VectorHelpers.distance((Vector3f)v1, (Vector3f)v2);
    }
    public static float distance(Node n1, Node n2) {
        return VectorHelpers.distance(n1.getWorldPosition(), n2.getWorldPosition());
    }
}
