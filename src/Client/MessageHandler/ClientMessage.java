package Client.MessageHandler;

import ServerClientMessage.Messages.serverMessageType;

import java.io.Serializable;
import java.util.UUID;

public class ClientMessage implements Serializable {
    private static final long serialVersionUID = 3133623575600628881L;
    private serverMessageType message;
    private UUID clientUUID;

    public ClientMessage(serverMessageType message, UUID clientUUID) {
        this.message = message;
        this.clientUUID = clientUUID;
    }

    public serverMessageType getMessageType() {
        return message;
    }

    public UUID getClientUUID() {
        return clientUUID;
    }

    @Override
    public String toString() {
        return "ServerClientMessage{" +
                "message='" + message + '\'' +
                ", clientUUID=" + clientUUID +
                '}';
    }
}
