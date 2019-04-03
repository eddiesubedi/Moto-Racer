package motoracer.Model;

import Client.Client;
import Client.MessageHandler.ClientMessage;
import Server.JoinedClient;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.rage.Engine;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static ServerClientMessage.Utils.toStream;
import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;


public class World {
    private InputManager inputManager;
    private Player player;
    private SceneManager sm;
    private Engine engine;
    private Client client;
    private ArrayList<JoinedClient> joinedPlayers;
    public World() {
        inputManager = new GenericInputManager();
    }

    public void setUpEntities(SceneManager sceneManager, Engine engine, Client client) throws IOException {
        this.sm = sceneManager;
        this.engine =engine;
        this.client = client;
        joinedPlayers = new ArrayList<>();
        setupPlayers();
        setupCamera();
        setupLights();
        setupWorld();
        new Skybox(sceneManager, engine);
        sendJoinMessageToServer();
    }

    private void sendJoinMessageToServer() {
        ClientMessage message = new ClientMessage(Messages.serverMessageType.JOIN, client.getUuid());
        client.sendMessage(toStream(message));
    }

    private void setupWorld() {
        try {
            Entity trackEntity = sm.createEntity("Track", "untitled.obj");
            trackEntity.setPrimitive(TRIANGLES);
            SceneNode trackNode = sm.getRootSceneNode().createChildSceneNode("Track" + "Node");
            trackNode.attachObject(trackEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupLights() {
        sm.getAmbientLight().setIntensity(new Color(.6f, .6f, .6f));
    }

    private void setupPlayers() throws IOException {
        player = new Player(sm, "player", 0);
    }

    private void setupCamera() {
        player.setupPlayerCamera();
        player.setupControls(inputManager);
    }

    public void updateWorld(float delta, Engine engine) {
        player.updatePlayer(delta);
        inputManager.update(delta);
        player.updateHUD(engine.getRenderSystem(), engine.getRenderSystem().getCanvas().getHeight()-20);
        if(player.getVelocity()>0){
            ClientMessage message = new ClientMessage(Messages.serverMessageType.UPDATE_PLAYER_TRANSFORM, client.getUuid());
            Transform transform = new Transform(player.getTransform());
            JoinedClient joinedClient = new JoinedClient(transform, getUuid());
            message.setData(joinedClient);
            client.sendMessage(toStream(message));
        }
    }

    public void addPlayer(Vector3f localPosition, UUID uuid){
        new GamePlayer(sm, localPosition, engine.getTextureManager(), uuid);
    }
    public SceneNode getPlayerNode(){
        return player.getTransform();
    }

    public ArrayList<JoinedClient> getJoinedPlayers() {
        return joinedPlayers;
    }
    public UUID getUuid(){
        return client.getUuid();
    }

    public void removePlayer(UUID uuid) {
        sm.destroySceneNode(uuid+"Node");
    }

    public void updateConnectedPlayer(JoinedClient client) {
        SceneNode node = sm.getSceneNode(client.getUuid() + "Node");
        Vector3 updatedLocalPosition = Vector3f.createFrom(client.getTransform().getLocalPosition());
        Vector3 updatedLocalScale = Vector3f.createFrom(client.getTransform().getLocalScale());
        Matrix3 updatedLocalRotation = Matrix3f.createFrom(client.getTransform().getLocalRotation());

        node.setLocalPosition(node.getLocalPosition().lerp(updatedLocalPosition, .2f));
        node.setLocalRotation(node.getLocalRotation().toQuaternion().lerp(updatedLocalRotation.toQuaternion(), .2f).toMatrix3());
        node.setLocalScale(node.getLocalScale().lerp(updatedLocalScale, .2f));
    }
}
