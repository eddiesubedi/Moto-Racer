package Server;

import Client.MessageHandler.ClientMessage;
import ServerClientMessage.Messages;
import ServerClientMessage.Utils;
import Server.MessageHandler.IServerMessageHandler;
import Server.MessageHandler.SendJoinMessageHandler;
import Server.MessageHandler.TestMessageHandler;
import ray.networking.server.GameConnectionServer;
import ray.networking.server.IClientInfo;
import ray.networking.server.IServerSocket;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class Server extends GameConnectionServer<UUID> {
    private HashMap<Messages.serverMessageType, IServerMessageHandler> strategyHandlers;

    Server(int localPort) throws IOException {
        super(localPort, ProtocolType.UDP);
        strategyHandlers = new HashMap<>();
        strategyHandlers.put(Messages.serverMessageType.TEST, new TestMessageHandler());
        strategyHandlers.put(Messages.serverMessageType.JOIN, new SendJoinMessageHandler());
        System.out.println("Server Started");
    }

    @Override
    public void processPacket(Object object, InetAddress senderIP, int senderPort) {
        ClientMessage message = Utils.toClientMessage((byte[]) object);
        try {
            IServerMessageHandler messageHandler = strategyHandlers.get(message.getMessageType());
            messageHandler.handleMessage(this, message, senderIP, senderPort);
        } catch (Exception e){
            System.out.println("ServerClientMessage not found");
        }
    }

    public void sendPacket(Object object, UUID clientUID) throws IOException {
        super.sendPacket((Serializable) object, clientUID);
    }

    @Override
    public IServerSocket getServerSocket() {
        return super.getServerSocket();
    }

    @Override
    public void addClient(IClientInfo clientInfo, UUID clientUID) {
        super.addClient(clientInfo, clientUID);
    }
}
