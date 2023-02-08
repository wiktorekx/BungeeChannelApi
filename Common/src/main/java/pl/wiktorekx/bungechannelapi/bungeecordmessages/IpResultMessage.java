package pl.wiktorekx.bungechannelapi.bungeecordmessages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungechannelapi.channel.BMessage;

import java.util.Objects;

@Getter
@Setter
public class IpResultMessage extends BungeeCordMessage {
    private String ip;

    public IpResultMessage() {}

    public IpResultMessage(@NotNull BMessage message) {
        super(message);
        ip = message.readString();
    }

    @NotNull
    @Override
    public BMessage save() {
        BMessage message = new BMessage();
        message.writeString(Objects.requireNonNull(ip));
        return message;
    }
}
