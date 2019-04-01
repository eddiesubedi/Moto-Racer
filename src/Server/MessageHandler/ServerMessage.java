package Server.MessageHandler;

import ServerClientMessage.Messages;
import ServerClientMessage.Messages.clientMessageType;
import ServerClientMessage.Messages.serverMessageType;

import java.io.Serializable;
import java.util.UUID;

public class ServerMessage implements Serializable {
    private static final long serialVersionUID = -4522719043270819785L;
    private clientMessageType message;

    public ServerMessage(clientMessageType message) {
        this.message = message;
    }

    public clientMessageType getMessageType() {
        return message;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "message=" + message +
                '}';
    }
}
