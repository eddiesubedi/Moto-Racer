package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;

public interface IClientMessageHandler {
    public void handleMessage(ServerMessage message, World world);
}
