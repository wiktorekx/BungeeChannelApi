package pl.wiktorekx.bungeechannelapi.channel;

import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungeechannelapi.bungeecordmessages.BungeeCordMessage;

public interface IChannelConnection {

    @NotNull
    String getChannel();

    default void sendMessage(@NotNull BungeeCordMessage message) {
        sendMessage(message.save());
    }

    void sendMessage(@NotNull BMessage message);
}
