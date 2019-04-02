package Client.MessageHandler;

import ServerClientMessage.Messages.serverMessageType;

import java.io.Serializable;
import java.util.UUID;

public class ClientMessage implements Serializable {
    private static final long serialVersionUID = 3133623575600628881L;
    private serverMessageType message;
    private UUID clientUUID;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "message=" + message +
                ", clientUUID=" + clientUUID +
                ", data=" + data +
                '}';
    }
}
