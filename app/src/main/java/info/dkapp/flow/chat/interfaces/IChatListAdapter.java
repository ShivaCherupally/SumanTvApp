package info.dkapp.flow.chat.interfaces;

import info.dkapp.flow.chat.models.ChatDataConvertModel;

public interface IChatListAdapter {
    void openSingleChatWindow(ChatDataConvertModel chatDataConvertModel, Integer position);
    void openProfileDialog(ChatDataConvertModel chatDataConvertModel, Integer position);
}
