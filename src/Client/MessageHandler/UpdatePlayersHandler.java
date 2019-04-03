package Client.MessageHandler;

import Server.JoinedClient;
import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;

public class UpdatePlayersHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        JoinedClient client = (JoinedClient) message.getData();
        world.updateConnectedPlayer(client);
    }
}
