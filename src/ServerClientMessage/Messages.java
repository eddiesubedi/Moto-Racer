package ServerClientMessage;

public class Messages {
    public enum serverMessageType {
        TEST, JOIN, LEAVE
    }
    public enum clientMessageType{
        SUCCESS, FAIL, ADD_PLAYER, REMOVE_PLAYER
    }
}
