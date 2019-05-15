package motoracer.Model;

import Client.Client;
import Client.MessageHandler.ClientMessage;
import Server.JoinedClient;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import motoracer.Model.AI.AI;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.*;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;
import ray.rml.Vector3;
import ray.rml.Vector3f;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static ServerClientMessage.Utils.toStream;


public class World {
    private InputManager inputManager;
    private Player player;
    private SceneManager sm;
    private Engine engine;
    private Client client;
    private ArrayList<JoinedClient> joinedPlayers;
    private SkeletalEntity skeletalEntity;
    private boolean startGame = false;

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
        setupTerrain();
        setupAnimation();
        new Skybox(sceneManager, engine);
        sendJoinMessageToServer();
    }

    private void setupAnimation() throws IOException {
        startGame = true;
        skeletalEntity = sm.createSkeletalEntity("manAv","tinycowboy_01.rkm","tinycowboy_01.rks");
        Texture texture = sm.getTextureManager().getAssetByPath("tinycowboy_color.png");
        TextureState textureState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
        textureState.setTexture(texture);
        skeletalEntity.setRenderState(textureState);

        SceneNode manN = sm.getRootSceneNode().createChildSceneNode("manNode");
        manN.attachObject(skeletalEntity);

        skeletalEntity.loadAnimation("drive","tinycowboy_01.rka");
        skeletalEntity.playAnimation("drive", 0.5f, SkeletalEntity.EndType.LOOP, 0);
    }

    private void setupTerrain() {
        Tessellation tessellation = sm.createTessellation("tessE", 6);
        tessellation.setSubdivisions(9000);
        SceneNode tessN = sm.getRootSceneNode().createChildSceneNode("TessN");
        tessN.attachObject(tessellation);
        tessN.scale(500*2,(500*2)*5,500*2);
        tessellation.setHeightMap(engine, "heightmap_x0_y0_road.png");
        tessellation.setTexture(engine, "colormap.png");
//        tessellation.setNormalMap(engine, "normalmap.PNG");
    }

    private void sendJoinMessageToServer() {
        ClientMessage message = new ClientMessage(Messages.serverMessageType.JOIN, client.getUuid());
        client.sendMessage(toStream(message));
    }

    private void setupWorld() {

    }

    private void setupLights() {
        sm.getAmbientLight().setIntensity(new Color(1f, 1f, 1f));
    }

    private void setupPlayers() throws IOException {
        player = new Player(sm, "player", 0);
    }

    private void setupCamera() {
        player.setupPlayerCamera();
        player.setupControls(inputManager);
    }

    public void updateWorld(float delta, Engine engine) {
        if(startGame) {
            player.updatePlayer(delta);
            updateAI();
//        ai.update(player.getTransform());
            inputManager.update(delta);
            player.updateHUD(engine.getRenderSystem(), engine.getRenderSystem().getCanvas().getHeight()-20);
            if(player.getVelocity()>0){
                ClientMessage message = new ClientMessage(Messages.serverMessageType.UPDATE_PLAYER_TRANSFORM, client.getUuid());
                Transform transform = new Transform(player.getTransform());
                JoinedClient joinedClient = new JoinedClient(transform, getUuid());
                message.setData(joinedClient);
                client.sendMessage(toStream(message));
            }
        skeletalEntity.update();
        }

    }

    private void updateAI() {
        ClientMessage message = new ClientMessage(Messages.serverMessageType.GET_AI_TRANSFORMS, client.getUuid());
        client.sendMessage(toStream(message));
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

    public void addAI(Transform transform, String name) {
        try {
            new AI(sm, name, Vector3f.createFrom(transform.getLocalPosition()));
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAITransforms(Transform transform, String name) {
        SceneNode aiNode = sm.getSceneNode(name);
        aiNode.setLocalPosition(Vector3f.createFrom(transform.getLocalPosition()));
        aiNode.setLocalRotation(Matrix3f.createFrom(transform.getLocalRotation()));
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }
}
