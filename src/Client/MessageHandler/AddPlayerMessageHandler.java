package Client.MessageHandler;

import Server.JoinedClient;
import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;
import ray.rml.Vector3f;

import java.util.ArrayList;

public class AddPlayerMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        ArrayList<?> serverJoinedClients = (ArrayList<?>) message.getData();
        ArrayList<JoinedClient> alreadyJoinedPlayers = world.getJoinedPlayers();
        serverJoinedClients.forEach(serverJoinedClient -> {
            if(!world.getUuid().equals(((JoinedClient)serverJoinedClient).getUuid())) {
                if(!isTheServerClientAlreadyAdded( (JoinedClient)serverJoinedClient, alreadyJoinedPlayers)){
                    System.out.println("Adding "+serverJoinedClient);
                    alreadyJoinedPlayers.add((JoinedClient)serverJoinedClient);
                    world.addPlayer((Vector3f) Vector3f.createFrom(((JoinedClient)serverJoinedClient).getTransform().getLocalPosition()),
                            ((JoinedClient)serverJoinedClient).getUuid());
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
