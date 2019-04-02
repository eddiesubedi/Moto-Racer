package motoracer.Model;

import Client.Client;
import Client.MessageHandler.ClientMessage;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.rage.Engine;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;

import java.awt.*;
import java.io.IOException;

import static ServerClientMessage.Utils.toStream;
import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;


public class World {
    private InputManager inputManager;
    private Player player;
    private SceneManager sm;
    private Engine engine;
    private Client client;
    public World() {
        inputManager = new GenericInputManager();
    }

    public void setUpEntities(SceneManager sceneManager, Engine engine, Client client) throws IOException {
        this.sm = sceneManager;
        this.engine =engine;
        this.client = client;
        setupLights();
        setupPlayers();
        setupCamera();
        setupWorld();
        sendJoinMessageToServer();
    }

    private void sendJoinMessageToServer() {
        ClientMessage message = new ClientMessage(Messages.serverMessageType.JOIN, client.getUuid());
        message.setData(new Transform(player.getTransform()));
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
    }

    public void addPlayer(){
        new GamePlayer(sm, 2, engine.getTextureManager());
    }
}
