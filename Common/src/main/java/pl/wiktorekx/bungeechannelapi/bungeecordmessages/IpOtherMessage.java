package pl.wiktorekx.bungeechannelapi.bungeecordmessages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungeechannelapi.channel.BMessage;

import java.util.Objects;

@Getter
@Setter
public class IpOtherMessage extends BungeeCordMessage {
    private String player;

    public IpOtherMessage() {}

    public IpOtherMessage(@NotNull BMessage message) {
        super(message);
        player = message.readString();
    }

    @NotNull
    @Override
    public BMessage save() {
        BMessage message = new BMessage();
        message.writeString("IpOther");
        message.writeString(Objects.requireNonNull(player));
        return message;
    }
}
