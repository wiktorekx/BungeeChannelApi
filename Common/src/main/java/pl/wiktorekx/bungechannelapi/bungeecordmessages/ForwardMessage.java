package pl.wiktorekx.bungechannelapi.bungeecordmessages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungechannelapi.channel.BMessage;

import java.util.Objects;

@Getter
@Setter
public class ForwardMessage extends BungeeCordMessage {
    private String server;
    private String channel;
    private BMessage message;

    public ForwardMessage() {
        message = new BMessage();
    }

    public ForwardMessage(@NotNull BMessage message) {
        super(message);
        server = message.readString();
        channel = message.readString();
        byte[] bytes = new byte[message.readShort()];
        message.readBytes(bytes);
        this.message = new BMessage(bytes);
    }

    @Override
    public @NotNull BMessage save() {
        BMessage forwardMessage = new BMessage();
        forwardMessage.writeString("Forward");
        forwardMessage.writeString(Objects.requireNonNull(server));
        forwardMessage.writeString(Objects.requireNonNull(channel));
        byte[] bytes = Objects.requireNonNull(message).toByteArray();
        forwardMessage.writeShort((short) bytes.length);
        forwardMessage.writeBytes(bytes);
        return forwardMessage;
    }
}
