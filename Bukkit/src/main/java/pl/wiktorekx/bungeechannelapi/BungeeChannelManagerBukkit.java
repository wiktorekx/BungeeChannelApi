package pl.wiktorekx.bungeechannelapi;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.wiktorekx.bungechannelapi.IBungeeChannelManager;
import pl.wiktorekx.bungechannelapi.annotation.MessageReceiver;
import pl.wiktorekx.bungechannelapi.channel.BMessage;
import pl.wiktorekx.bungechannelapi.channel.BungeeChannelUtils;
import pl.wiktorekx.bungechannelapi.channel.IChannelConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class BungeeChannelManagerBukkit implements IBungeeChannelManager {
    private final Map<String, Object> channelListeners = new HashMap<>();
    private final Map<String, BukkitChannelConnection> channelConnections = new HashMap<>();
    private final Plugin plugin;

    public static BungeeChannelManagerBukkit create(@NotNull Plugin plugin) {
        return new BungeeChannelManagerBukkit(Objects.requireNonNull(plugin));
    }

    @Override
    @SneakyThrows
    public @NotNull IBungeeChannelManager registerChannelListener(@NotNull Class<?> listener) {
        registerChannelListener(Objects.requireNonNull(listener).newInstance());
        return this;
    }

    @Override
    public @NotNull IBungeeChannelManager registerChannelListener(@NotNull Object listener) {
        String channel = BungeeChannelUtils.registerListener(this, Objects.requireNonNull(listener));
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, (ch, player, message) -> {
            if(channel.equals(ch)) {
                Map<Class<?>, Object> map = new HashMap<>();
                map.put(IChannelConnection.class, getChannelConnection(channel));
                map.put(BMessage.class, new BMessage(message));
                map.put(Player.class, player);
                BungeeChannelUtils.invokeAnnotatedMethod(listener, MessageReceiver.class, map);
            }
        });
        channelListeners.put(channel, listener);
        return this;
    }

    @Override
    public @Nullable Object getChannelListener(@NotNull String channel) {
        return channelListeners.get(Objects.requireNonNull(channel));
    }

    @Override
    public @NotNull BukkitChannelConnection getChannelConnection(@NotNull String channel) {
        Objects.requireNonNull(channel);
        if (channelConnections.containsKey(channel)) {
            return channelConnections.get(channel);
        } else {
            BukkitChannelConnection channelConnection = new BukkitChannelConnection(plugin, channel);
            Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
            channelConnections.put(channel, channelConnection);
            return channelConnection;
        }
    }
}
