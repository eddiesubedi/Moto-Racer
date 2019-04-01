package ServerClientMessage;

import Client.MessageHandler.ClientMessage;
import Server.MessageHandler.ServerMessage;

import java.io.*;

public class Utils {
    public static ClientMessage toClientMessage(byte[] stream){
        ClientMessage message = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(stream);
             ObjectInputStream ois = new ObjectInputStream(bais);) {
            message = (ClientMessage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }
    public static ServerMessage toServerMessage(byte[] stream){
        ServerMessage message = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(stream);
             ObjectInputStream ois = new ObjectInputStream(bais);) {
            message = (ServerMessage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static byte[] toStream(Object message) {
        byte[] stream = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(message);
            stream = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }

}
