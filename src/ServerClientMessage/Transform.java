package ServerClientMessage;

import ray.rage.scene.SceneNode;
import ray.rml.Vector3;

import java.io.Serializable;
import java.util.Arrays;

public class Transform implements Serializable {
    private static final long serialVersionUID = -5106108860540894118L;
    private float[] localForwardAxis, localPosition, localRightAxis, localRotation, localScale, localTransform, localUpAxis;
    private float[] worldForwardAxis, worldPosition, worldRightAxis, worldRotation, worldScale, worldTransform, worldUpAxis;
    public Transform(SceneNode n){
        setValues(n);
    }

    public void setValues(SceneNode n) {
        localForwardAxis = vector3ToArray(n.getLocalForwardAxis());
        localRightAxis = vector3ToArray(n.getLocalRightAxis());
        localUpAxis = vector3ToArray(n.getLocalUpAxis());
        localPosition = vector3ToArray(n.getLocalPosition());
        localRotation = n.getLocalRotation().toFloatArray();
        localScale  = vector3ToArray(n.getLocalScale());
        localTransform = n.getLocalTransform().toFloatArray();
        worldForwardAxis = vector3ToArray(n.getWorldForwardAxis());
        worldRightAxis = vector3ToArray(n.getWorldRightAxis());
        worldUpAxis = vector3ToArray(n.getWorldUpAxis());
        worldPosition = vector3ToArray(n.getWorldPosition());
        worldRotation = n.getWorldRotation().toFloatArray();
        worldScale  = vector3ToArray(n.getWorldScale());
        worldTransform = n.getWorldTransform().toFloatArray();
    }

    private float[] vector3ToArray(Vector3 v){
        return new float[]{v.x(), v.y(), v.z()};
    }

    public float[] getLocalForwardAxis() {
        return localForwardAxis;
    }

    public void setLocalForwardAxis(float[] localForwardAxis) {
        this.localForwardAxis = localForwardAxis;
    }

    public float[] getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(float[] localPosition) {
        this.localPosition = localPosition;
    }

    public float[] getLocalRightAxis() {
        return localRightAxis;
    }

    public void setLocalRightAxis(float[] localRightAxis) {
        this.localRightAxis = localRightAxis;
    }

    public float[] getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(float[] localRotation) {
        this.localRotation = localRotation;
    }

    public float[] getLocalScale() {
        return localScale;
    }

    public void setLocalScale(float[] localScale) {
        this.localScale = localScale;
    }

    public float[] getLocalTransform() {
        return localTransform;
    }

    public void setLocalTransform(float[] localTransform) {
        this.localTransform = localTransform;
    }

    public float[] getLocalUpAxis() {
        return localUpAxis;
    }

    public void setLocalUpAxis(float[] localUpAxis) {
        this.localUpAxis = localUpAxis;
    }

    public float[] getWorldForwardAxis() {
        return worldForwardAxis;
    }

    public void setWorldForwardAxis(float[] worldForwardAxis) {
        this.worldForwardAxis = worldForwardAxis;
    }

    public float[] getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(float[] worldPosition) {
        this.worldPosition = worldPosition;
    }

    public float[] getWorldRightAxis() {
        return worldRightAxis;
    }

    public void setWorldRightAxis(float[] worldRightAxis) {
        this.worldRightAxis = worldRightAxis;
    }

    public float[] getWorldRotation() {
        return worldRotation;
    }

    public void setWorldRotation(float[] worldRotation) {
        this.worldRotation = worldRotation;
    }

    public float[] getWorldScale() {
        return worldScale;
    }

    public void setWorldScale(float[] worldScale) {
        this.worldScale = worldScale;
    }

    public float[] getWorldTransform() {
        return worldTransform;
    }

    public void setWorldTransform(float[] worldTransform) {
        this.worldTransform = worldTransform;
    }

    public float[] getWorldUpAxis() {
        return worldUpAxis;
    }

    public void setWorldUpAxis(float[] worldUpAxis) {
        this.worldUpAxis = worldUpAxis;
    }

    @Override
    public String toString() {
        return "Transform{" +
                "localForwardAxis=" + Arrays.toString(localForwardAxis) +
                ", localPosition=" + Arrays.toString(localPosition) +
                ", localRightAxis=" + Arrays.toString(localRightAxis) +
                ", localRotation=" + Arrays.toString(localRotation) +
                ", localScale=" + Arrays.toString(localScale) +
                ", localTransform=" + Arrays.toString(localTransform) +
                ", localUpAxis=" + Arrays.toString(localUpAxis) +
                ", worldForwardAxis=" + Arrays.toString(worldForwardAxis) +
                ", worldPosition=" + Arrays.toString(worldPosition) +
                ", worldRightAxis=" + Arrays.toString(worldRightAxis) +
                ", worldRotation=" + Arrays.toString(worldRotation) +
                ", worldScale=" + Arrays.toString(worldScale) +
                ", worldTransform=" + Arrays.toString(worldTransform) +
                ", worldUpAxis=" + Arrays.toString(worldUpAxis) +
                '}';
    }
}
