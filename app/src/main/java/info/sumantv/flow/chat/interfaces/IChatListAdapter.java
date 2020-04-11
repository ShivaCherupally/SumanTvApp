package info.sumantv.flow.chat.interfaces;

import info.sumantv.flow.chat.models.ChatDataConvertModel;

public interface IChatListAdapter {
    void openSingleChatWindow(ChatDataConvertModel chatDataConvertModel, Integer position);
    void openProfileDialog(ChatDataConvertModel chatDataConvertModel, Integer position);
}
