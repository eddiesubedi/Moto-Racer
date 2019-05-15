package Server;

import Server.Utils.CircularList;
import ServerClientMessage.Transform;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//public class Position {
//    ArrayList<Vector3> positions;
//    ArrayList<Matrix3> rotations;
//
//    public Position(String filename) {
//        positions = new ArrayList<>();
//        rotations = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            br.lines().forEach((String pos) -> {
//                pos = pos.replace("[", "");
//                pos = pos.replace("]", "");
//                String[] t = pos.split(",");
//                float x = Float.parseFloat(t[0]);
//                float y = Float.parseFloat(t[1]);
//                float z = Float.parseFloat(t[2]);
//                positions.add(Vector3f.createFrom(x,y,z));
//                float[] matrix = new float[]{Float.parseFloat(t[3]), Float.parseFloat(t[4]), Float.parseFloat(t[5])
//                , Float.parseFloat(t[6]), Float.parseFloat(t[7]), Float.parseFloat(t[8]), Float.parseFloat(t[9]),
//                Float.parseFloat(t[10]), Float.parseFloat(t[11])};
//                rotations.add(Matrix3f.createFrom(matrix));
//            });
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public ArrayList<Vector3> getPositions() {
//        return positions;
//    }
//
//    public ArrayList<Matrix3> getRotations() {
//        return rotations;
//    }
//}
public class Position {
    private static Position single_instance = null;
    public ArrayList<CircularList<Vector3>> aiPositions = new ArrayList<>();
    public ArrayList<CircularList<Matrix3>> aiRotations = new ArrayList<>();
    public ArrayList<Transform> initialTransform = new ArrayList<>();
    public int i = 0;
    private Position() {
        for (int i = 1; i <= 10; i++) {
            CircularList<Vector3> positions = new CircularList<>();
            CircularList<Matrix3> rotations = new CircularList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("ai"+i+".txt"))) {
                br.lines().forEach((String pos) -> {
                    Transform transform = new Transform();
                    pos = pos.replace("[", "");
                    pos = pos.replace("]", "");
                    String[] t = pos.split(",");
                    float x = Float.parseFloat(t[0]);
                    float y = Float.parseFloat(t[1]);
                    float z = Float.parseFloat(t[2]);
                    transform.setLocalPosition(Vector3f.createFrom(x, y, z).toFloatArray());
                    positions.add(Vector3f.createFrom(x, y, z));
                    float[] matrix = new float[]{Float.parseFloat(t[3]), Float.parseFloat(t[4]),
                            Float.parseFloat(t[5]), Float.parseFloat(t[6]), Float.parseFloat(t[7]),
                            Float.parseFloat(t[8]), Float.parseFloat(t[9]), Float.parseFloat(t[10]),
                            Float.parseFloat(t[11])};
                    rotations.add(Matrix3f.createFrom(matrix));
                });
                aiPositions.add(positions);
                aiRotations.add(rotations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i<aiPositions.size(); i++) {
            Transform transform = new Transform();
            transform.setLocalPosition(aiPositions.get(i).get(0).toFloatArray());
            transform.setLocalRotation(aiRotations.get(i).get(0).toFloatArray());
            initialTransform.add(transform);
        }
    }

    public static Position getInstance() {
        if (single_instance == null) {
            single_instance = new Position();
        }
        return single_instance;
    }
}
