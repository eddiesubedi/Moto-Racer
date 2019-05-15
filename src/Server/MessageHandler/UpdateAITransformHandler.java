package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.JoinedClient;
import Server.Position;
import Server.ServerUDP;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import ServerClientMessage.Utils;
import ray.rml.Matrix3;
import ray.rml.Vector3;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class UpdateAITransformHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage clientMessage, InetAddress senderIP, int senderPort) {
        ServerMessage updateMessage = new ServerMessage(Messages.clientMessageType.UPDATE_AIS);
        ArrayList<Transform> transforms = new ArrayList<>();
//        int index = (int) clientMessage.getData();
        int index = Position.getInstance().i;
        for (int i = 0; i<Position.getInstance().aiRotations.size(); i++) {
            Vector3 position = Position.getInstance().aiPositions.get(i).get(index);
            Matrix3 rotation = Position.getInstance().aiRotations.get(i).get(index);
            Transform transform = new Transform();
            transform.setLocalPosition(position.toFloatArray());
            transform.setLocalRotation(rotation.toFloatArray());
            transforms.add(transform);
        }
        updateMessage.setData(transforms);
        Position.getInstance().i++;
        try {
            sendMessageToClient(server, clientMessage.getClientUUID(), updateMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {
        server.sendPacket(Utils.toStream(message), uuid);
    }
}
