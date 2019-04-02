package Server.MessageHandler;

import ServerClientMessage.Messages;
import ServerClientMessage.Messages.clientMessageType;
import ServerClientMessage.Messages.serverMessageType;

import java.io.Serializable;
import java.util.UUID;

public class ServerMessage implements Serializable {
    private static final long serialVersionUID = -4522719043270819785L;
    private clientMessageType message;
    private Object data;

    public ServerMessage(clientMessageType message) {
        this.message = message;
    }

    public clientMessageType getMessageType() {
        return message;
    }

    public clientMessageType getMessage() {
        return message;
    }

    public void setMessage(clientMessageType message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "message=" + message +
                ", data=" + data +
                '}';
    }
}
