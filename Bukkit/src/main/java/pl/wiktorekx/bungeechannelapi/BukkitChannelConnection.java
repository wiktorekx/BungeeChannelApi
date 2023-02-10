package pl.wiktorekx.bungeechannelapi;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungeechannelapi.channel.BMessage;
import pl.wiktorekx.bungeechannelapi.channel.IChannelConnection;

import java.util.Objects;

@RequiredArgsConstructor
public class BukkitChannelConnection implements IChannelConnection {
    private final Plugin plugin;
    public final String channel;

    @Override
    public @NotNull String getChannel() {
        return channel;
    }

    @Override
    public void sendMessage(@NotNull BMessage message) {
        Bukkit.getOnlinePlayers().stream().findAny().ifPresent(player ->
                sendPlayerMessage(Objects.requireNonNull(player), Objects.requireNonNull(message))
        );
    }

    public void sendPlayerMessage(@NotNull Player player, @NotNull BMessage message) {
        Objects.requireNonNull(player).sendPluginMessage(plugin, channel, Objects.requireNonNull(message).toByteArray());
    }
}
