package info.sumantv.flow.chat.interfaces;

import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;

public interface ICallsListAdapter {
    void openProfileDialog(ChatSenderReceiverInfo chatSenderReceiverInfo, Integer position);
    void initiateCall(String serviceType, ChatSenderReceiverInfo chatSenderReceiverInfo);
}
