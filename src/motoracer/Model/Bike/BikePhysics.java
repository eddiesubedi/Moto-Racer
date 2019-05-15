package motoracer.Model.Bike;

import java.text.DecimalFormat;

import static motoracer.utils.Utils.clamp;

public class BikePhysics implements BikeControl{
    private float velocity;
    private float maxSpeed;
    private float mass;
    private float accelerationStrength;
    private float drag;
    private float brakeStrength;
    private double handling;
    private float acceleration;
    private boolean isAccelerating = false;
    private boolean isBreaking = false;

    BikePhysics(float maxSpeed, float engineForce, float mass, float drag, float brakeStrength,
                double handling) {
        this.maxSpeed = maxSpeed;
        this.mass = mass;
        this.drag = drag;
        this.brakeStrength = brakeStrength;
        this.handling = handling;
        accelerationStrength = engineForce / mass;
    }

    public void update(float delta){
        calculatePhysics(delta);
    }

    private void calculatePhysics(float delta) {
        calculateAcceleration();
        calculateBreaking();
        calculateVelocity(delta);
        calculateDrag();
        clampValues();
    }

    private void clampValues() {
        velocity = clamp(velocity, 0, maxSpeed);
        acceleration = clamp(acceleration, 0, maxSpeed *15);
    }

    private void calculateDrag() {
        velocity -= drag;
        if(!isAccelerating){
            acceleration-=drag*40;
        }
    }

    private void calculateVelocity(float delta) {
        float force = mass * acceleration;
        velocity = (force/mass)*delta;
    }

    private void calculateBreaking() {
        if(isBreaking){
            acceleration -= brakeStrength;
        }
    }

    private void calculateAcceleration() {
        if(isAccelerating && !isBreaking){
            acceleration += accelerationStrength;
        }
    }

    @Override
    public float[] steer(float steeringAngle, float delta) {
        if (velocity > 0) {
            delta = delta/100;
            float radius = calculateTurningRadius(steeringAngle, velocity);
            float distanceToTravel = velocity * delta;
            float circumference = (float) (2 * Math.PI * radius);
            float percentRevolutions = distanceToTravel / circumference;
            float degreesToTravel = percentRevolutions * 360f;
            float forwardToTravel = (float) (radius * Math.sin(degreesToTravel * Math.PI / 180)) / velocity;
            float rightToTravel = (float) (radius * (1 - Math.cos(degreesToTravel * Math.PI / 180)))/ velocity;
            return new float[]{degreesToTravel, forwardToTravel, rightToTravel};
        }
        return new float[0];
    }

    private float calculateTurningRadius(float steeringAngle, float howEasyIsItToTurn) {
        double currHandling = handling;
        if(isBreaking){
            currHandling = handling * 2f;
        }
        return (float) ((1 / (currHandling * (1 / howEasyIsItToTurn))) / Math.cos((90 - steeringAngle) * Math.PI / 180));
    }

    @Override
    public void toggleAcceleration() {
        isAccelerating ^= true;
    }

    @Override
    public void toggleBrake() {
        isBreaking ^= true;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void stop() {
        this.velocity = 0;
        this.acceleration = 0;
    }

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat("##.0000");
        return
                "velocity=" + f.format(velocity)+
                ", maxSpeed=" + maxSpeed +
                ", mass=" + mass +
                ", accelerationStrength=" + accelerationStrength +
                ", drag=" + drag +
                ", brakeStrength=" + brakeStrength +
                ", handling=" + handling +
                ", acceleration=" + f.format(acceleration);
    }

    public void updateValues(float maxSpeed, float engineForce, float mass, float drag,
                             float brakeStrength, double handling) {
        this.maxSpeed = maxSpeed;
        this.mass = mass;
        this.drag = drag;
        this.brakeStrength = brakeStrength;
        this.handling = handling;
        accelerationStrength = engineForce / mass;
    }
}
