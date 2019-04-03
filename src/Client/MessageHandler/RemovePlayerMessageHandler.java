package Client.MessageHandler;

import Server.JoinedClient;
import Server.MessageHandler.ServerMessage;
import motoracer.Model.World;
import ray.rml.Vector3f;

import java.util.ArrayList;
import java.util.UUID;

public class RemovePlayerMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        UUID uuid = (UUID) message.getData();
        int removeClientIndex = -1;
        for (int i = 0; i < world.getJoinedPlayers().size(); i ++) {
            if(world.getJoinedPlayers().get(i).getUuid().equals(uuid)){
                removeClientIndex = i;
            }
        }
        if(removeClientIndex!=-1){
            world.getJoinedPlayers().remove(removeClientIndex);
            world.removePlayer(uuid);
        }

    }
}
