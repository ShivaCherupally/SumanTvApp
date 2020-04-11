package info.dkapp.flow.chat.interfaces;

import info.dkapp.flow.chat.models.ChatSenderReceiverInfo;

public interface ICallsListAdapter {
    void openProfileDialog(ChatSenderReceiverInfo chatSenderReceiverInfo, Integer position);
    void initiateCall(String serviceType, ChatSenderReceiverInfo chatSenderReceiverInfo);
}
