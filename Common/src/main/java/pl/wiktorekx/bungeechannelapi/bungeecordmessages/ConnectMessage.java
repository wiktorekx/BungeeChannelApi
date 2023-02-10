package pl.wiktorekx.bungeechannelapi.bungeecordmessages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungeechannelapi.channel.BMessage;

import java.util.Objects;

@Getter
@Setter
public class ConnectMessage extends BungeeCordMessage {
    @NotNull
    private String server;

    public ConnectMessage() {}

    public ConnectMessage(@NotNull BMessage message) {
        super(message);
        server = message.readString();
    }

    @NotNull
    @Override
    public BMessage save() {
        BMessage message = new BMessage();
        message.writeString("Connect");
        message.writeString(Objects.requireNonNull(server));
        return message;
    }
}
