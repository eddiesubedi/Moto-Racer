package Server;

import ServerClientMessage.Transform;
import ray.rage.scene.SceneNode;

import java.util.UUID;

public class JoinedClient {
    private Transform transform;
    private UUID uuid;
    public JoinedClient(Transform transform, UUID uuid) {
        this.transform = transform;
        this.uuid = uuid;
    }

    public Transform getTransform() {
        return transform;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    @Override
    public String toString() {
        return "JoinedClient{" +
                "transform=" + transform +
                ", uuid=" + uuid +
                '}';
    }
}
