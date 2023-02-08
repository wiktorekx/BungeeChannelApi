package pl.wiktorekx.bungeechannelapi;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungechannelapi.channel.BMessage;
import pl.wiktorekx.bungechannelapi.channel.IChannelConnection;

import java.util.Objects;

@RequiredArgsConstructor
public class BungeeChannelConnection implements IChannelConnection {
    private final String channel;

    @Override
    public @NotNull String getChannel() {
        return channel;
    }

    @Override
    public void sendMessage(@NotNull BMessage message) {
        for(ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            sendServerMessage(serverInfo, Objects.requireNonNull(message));
        }
    }

    public void sendServerMessage(@NotNull ServerInfo serverInfo, @NotNull BMessage message) {
        Objects.requireNonNull(serverInfo).sendData(channel, Objects.requireNonNull(message).toByteArray());
    }

    public void sendPlayerMessage(@NotNull ProxiedPlayer player, @NotNull BMessage message){
        Objects.requireNonNull(player).sendData(channel, Objects.requireNonNull(message).toByteArray());
    }
}
