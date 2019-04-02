package Server;

import Client.MessageHandler.ClientMessage;
import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Messages;
import ServerClientMessage.Utils;
import Server.MessageHandler.IServerMessageHandler;
import Server.MessageHandler.JoinMessageHandler;
import Server.MessageHandler.TestMessageHandler;
import ray.networking.server.GameConnectionServer;
import ray.networking.server.IClientInfo;
import ray.networking.server.IServerSocket;
import ray.rage.scene.SceneNode;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerUDP extends GameConnectionServer<UUID> {
    private HashMap<Messages.serverMessageType, IServerMessageHandler> strategyHandlers;
    private ArrayList<JoinedClient> clients;
    ServerUDP(int localPort) throws IOException {
        super(localPort, ProtocolType.UDP);
        clients = new ArrayList<>();
        strategyHandlers = new HashMap<>();
        strategyHandlers.put(Messages.serverMessageType.TEST, new TestMessageHandler());
        strategyHandlers.put(Messages.serverMessageType.JOIN, new JoinMessageHandler());
        System.out.println("ServerUDP Started");
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

    public void forwardPacketToAll(ServerMessage message, UUID originalClientUID) throws IOException {
        super.forwardPacketToAll(Utils.toStream(message), originalClientUID);
    }

    @Override
    public IServerSocket getServerSocket() {
        return super.getServerSocket();
    }

    @Override
    public void addClient(IClientInfo clientInfo, UUID clientUID) {
        super.addClient(clientInfo, clientUID);
    }

    @Override
    public ConcurrentHashMap<UUID, IClientInfo> getClients() {
        return super.getClients();
    }

    public void addJoinedClient(JoinedClient joinedClient) {
        clients.add(joinedClient);
    }

    public ArrayList<JoinedClient> getConnectedClients() {
        return clients;
    }
}
