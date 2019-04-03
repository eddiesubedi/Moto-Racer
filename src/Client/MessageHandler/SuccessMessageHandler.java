package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Transform;
import motoracer.Model.World;
import ray.rml.Vector3f;

public class SuccessMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        if(message.getData()!=null) {
            Transform transform = (Transform) message.getData();
            System.out.println(transform);
            world.getPlayerNode().setLocalPosition(Vector3f.createFrom(transform.getLocalPosition()));
        }
    }
}
