package motoracer.Model.Bike;

public interface BikeControl {
    public float[] steer(float steeringAngle, float delta);
    public void toggleAcceleration();
    public void toggleBrake();
}
