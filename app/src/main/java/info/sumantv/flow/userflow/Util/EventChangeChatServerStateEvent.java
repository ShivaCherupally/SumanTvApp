package info.sumantv.flow.userflow.Util;

public class EventChangeChatServerStateEvent {
    private chatServerState state;

    public EventChangeChatServerStateEvent(chatServerState state) {
        this.state = state;
    }

    public chatServerState getState() {
        return state;
    }

    public enum chatServerState {
        connectedToSocket,
        disconnectedFromSocket,
        flashConnectionIcon
    }
}
