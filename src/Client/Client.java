package Client;

import Client.MessageHandler.IClientMessageHandler;
import Client.MessageHandler.SucessMessageHandler;
import Server.MessageHandler.IServerMessageHandler;
import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Messages;
import ServerClientMessage.Utils;
import ray.networking.client.GameConnectionClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class Client extends GameConnectionClient {
    private HashMap<Messages.clientMessageType, IClientMessageHandler> strategyHandlers;
    private UUID uuid;
    public Client(InetAddress remoteAddr, int remotePort) throws IOException {
        super(remoteAddr, remotePort, ProtocolType.UDP);
        uuid = UUID.randomUUID();
        strategyHandlers = new HashMap<>();
        strategyHandlers.put(Messages.clientMessageType.SUCEESS, new SucessMessageHandler());
    }

    @Override
    protected void processPacket(Object object) {
        ServerMessage message = Utils.toServerMessage((byte[]) object);
        try {
            IClientMessageHandler messageHandler = strategyHandlers.get(message.getMessageType());
            messageHandler.handleMessage(message);
        } catch (Exception e){
            System.out.println("ServerClientMessage not found");
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
