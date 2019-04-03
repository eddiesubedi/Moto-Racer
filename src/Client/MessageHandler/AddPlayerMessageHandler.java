package Client.MessageHandler;

import Server.JoinedClient;
import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;
import ray.rml.Vector3f;

import java.util.ArrayList;

public class AddPlayerMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        ArrayList<JoinedClient> serverJoinedClients = (ArrayList<JoinedClient>) message.getData();
        ArrayList<JoinedClient> alreadyJoinedPlayers = world.getJoinedPlayers();
        serverJoinedClients.forEach(serverJoinedClient -> {
            if(!world.getUuid().equals(serverJoinedClient.getUuid())) {
                if(!isTheServerClientAlreadyAdded(serverJoinedClient, alreadyJoinedPlayers)){
                    System.out.println("Adding "+serverJoinedClient);
                    alreadyJoinedPlayers.add(serverJoinedClient);
                    world.addPlayer((Vector3f) Vector3f.createFrom(serverJoinedClient.getTransform().getLocalPosition()));
                }
            }
        });
    }

    private boolean isTheServerClientAlreadyAdded(JoinedClient serverJoinedClient, ArrayList<JoinedClient> alreadyJoinedPlayers) {
        for (JoinedClient alreadyJoinedPlayer : alreadyJoinedPlayers) {
            if (alreadyJoinedPlayer.getUuid().equals(serverJoinedClient.getUuid())) {
                return true;
            }
        }
        return false;
    }

}
