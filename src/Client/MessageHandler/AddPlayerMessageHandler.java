package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;

public class AddPlayerMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        world.addPlayer();
    }
}
