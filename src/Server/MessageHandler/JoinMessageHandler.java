package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.Config;
import Server.JoinedClient;
import Server.ServerUDP;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import ServerClientMessage.Utils;
import ray.networking.server.IClientInfo;
import ray.rage.scene.SceneNode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class JoinMessageHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage clientMessage, InetAddress senderIP, int senderPort) {
        System.out.println(clientMessage.getMessageType()+" "+clientMessage.getClientUUID());
        try {
            IClientInfo clientInfo = server.getServerSocket().createClientInfo(senderIP, senderPort);
            System.out.println(server.getClients().size() < Config.Server.maxClients);
            if(server.getClients().size() < Config.Server.maxClients){
                addClientToServer(server, clientInfo, clientMessage);
                keepTrackOfClient(server, (Transform) clientMessage.getData(), clientMessage.getClientUUID());
            } else {
                ServerMessage serverMessage = new ServerMessage(Messages.clientMessageType.FAIL);
                clientInfo.sendPacket(Utils.toStream(serverMessage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void keepTrackOfClient(ServerUDP server, Transform transform, UUID uuid) {
        JoinedClient joinedClient = new JoinedClient(transform, uuid);
        server.addJoinedClient(joinedClient);
    }

    private void addClientToServer(ServerUDP server, IClientInfo a, ClientMessage message) throws IOException {
        server.addClient(a, message.getClientUUID());
        ServerMessage successMessage = new ServerMessage(Messages.clientMessageType.SUCCESS);
//        notify client about other clients
        sendMessageToClient(server, message.getClientUUID(), successMessage);
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {
        server.sendPacket(Utils.toStream(message), uuid);
    }
}
