package pl.wiktorekx.bungeechannelapi.bungeecordmessages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungeechannelapi.channel.BMessage;

import java.util.Objects;

@Getter
@Setter
public class ConnectOtherMessage extends BungeeCordMessage {
    @NotNull
    private String player;
    @NotNull
    private String server;

    public ConnectOtherMessage() {}

    public ConnectOtherMessage(@NotNull BMessage message) {
        super(message);
        player = message.readString();
        server = message.readString();
    }

    @NotNull
    @Override
    public BMessage save() {
        BMessage message = new BMessage();
        message.writeString("ConnectOther");
        message.writeString(Objects.requireNonNull(player));
        message.writeString(Objects.requireNonNull(server));
        return message;
    }
}
