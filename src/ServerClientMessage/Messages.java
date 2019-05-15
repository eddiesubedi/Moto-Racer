package ServerClientMessage;

public class Messages {
    public enum serverMessageType {
        TEST, JOIN, LEAVE, UPDATE_PLAYER_TRANSFORM, GET_AI_TRANSFORMS
    }
    public enum clientMessageType{
        SUCCESS, FAIL, ADD_PLAYER, REMOVE_PLAYER, UPDATE_PLAYERS, SETUP_AI, UPDATE_AIS, START_GAME
    }
}
