package a2.Model.Bike;

import ray.rage.scene.Node;

public interface BikeControl {
    public float[] steer(float steeringAngle, float delta);
    public void toggleAcceleration();
    public void toggleBrake();
}
