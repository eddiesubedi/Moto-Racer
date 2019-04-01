package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;

public interface IClientMessageHandler {
    public void handleMessage(ServerMessage message);
}
