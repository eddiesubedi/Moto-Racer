package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;

public class SuccessMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        System.out.println(message);
    }
}
