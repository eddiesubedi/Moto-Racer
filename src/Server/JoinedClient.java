package Server;

import ServerClientMessage.Transform;

import java.io.Serializable;
import java.util.UUID;

public class JoinedClient implements Serializable {
    private static final long serialVersionUID = -8321408766234064230L;
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
