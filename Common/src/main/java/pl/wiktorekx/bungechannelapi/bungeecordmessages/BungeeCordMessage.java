package pl.wiktorekx.bungechannelapi.bungeecordmessages;

import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungechannelapi.channel.BMessage;

public abstract class BungeeCordMessage {

    public BungeeCordMessage() {}

    public BungeeCordMessage(@NotNull BMessage message) {}

    @NotNull
    public abstract BMessage save();
}
