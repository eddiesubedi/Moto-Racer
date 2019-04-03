package Client;

import Client.MessageHandler.*;
import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Messages;
import ServerClientMessage.Utils;
import motoracer.Model.World;
import ray.networking.client.GameConnectionClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class Client extends GameConnectionClient {
    private HashMap<Messages.clientMessageType, IClientMessageHandler> strategyHandlers;
    private UUID uuid;
    private World world;
    public Client(InetAddress remoteAddr, int remotePort) throws IOException {
        super(remoteAddr, remotePort, ProtocolType.UDP);
        uuid = UUID.randomUUID();
    }
    public void setUpMessages(World world){
        this.world = world;
        strategyHandlers = new HashMap<>();
        strategyHandlers.put(Messages.clientMessageType.SUCCESS, new SuccessMessageHandler());
        strategyHandlers.put(Messages.clientMessageType.FAIL, new FailMessageHandler());
        strategyHandlers.put(Messages.clientMessageType.ADD_PLAYER, new AddPlayerMessageHandler());
        strategyHandlers.put(Messages.clientMessageType.REMOVE_PLAYER, new RemovePlayerMessageHandler());
        strategyHandlers.put(Messages.clientMessageType.UPDATE_PLAYERS, new UpdatePlayersHandler());
    }

    @Override
    protected void processPacket(Object object) {
        if(world!=null){
            ServerMessage message = Utils.toServerMessage((byte[]) object);
            try {
                IClientMessageHandler messageHandler = strategyHandlers.get(message.getMessageType());
                messageHandler.handleMessage(message, world);
            } catch (Exception e){
                System.out.println("ServerClientMessage not found");
            }
        }
    }

    public void sendMessage(Object testing) {
        try {
            sendPacket((Serializable) testing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UUID getUuid() {
        return uuid;
    }
}
