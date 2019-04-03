package ServerClientMessage;

public class Messages {
    public enum serverMessageType {
        TEST, JOIN, LEAVE, UPDATE_PLAYER_TRANSFORM
    }
    public enum clientMessageType{
        SUCCESS, FAIL, ADD_PLAYER, REMOVE_PLAYER, UPDATE_PLAYERS
    }
}
