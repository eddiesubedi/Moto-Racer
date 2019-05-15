package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Transform;
import motoracer.Model.World;

import java.util.ArrayList;

public class StartGameHandler implements IClientMessageHandler {
    @Override
    public void handleMessage(ServerMessage message, World world) {
        if(!world.isStartGame()) {
            world.setStartGame(true);
            System.out.println("Starting game");
        }

    }
}
