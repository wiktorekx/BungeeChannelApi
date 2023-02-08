package pl.wiktorekx.bungeechannelapi;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.wiktorekx.bungechannelapi.IBungeeChannelManager;
import pl.wiktorekx.bungechannelapi.channel.BungeeChannelUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class BungeeChannelManagerBungee implements IBungeeChannelManager {
    private final Map<String, Object> channelListeners = new HashMap<>();
    private final Map<String, BungeeChannelConnection> channelConnections = new HashMap<>();
    private final Plugin plugin;

    public static BungeeChannelManagerBungee create(@NotNull Plugin plugin) {
        return new BungeeChannelManagerBungee(Objects.requireNonNull(plugin));
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
        registerChannel(channel);
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, new BungeeChannelMessageListener(this, channel, listener));
        channelListeners.put(channel, listener);
        return this;
    }

    @Override
    public @Nullable Object getChannelListener(@NotNull String channel) {
        return channelListeners.get(Objects.requireNonNull(channel));
    }

    @Override
    public @NotNull BungeeChannelConnection getChannelConnection(@NotNull String channel) {
        Objects.requireNonNull(channel);
        if (channelConnections.containsKey(channel)) {
            return channelConnections.get(channel);
        } else {
            BungeeChannelConnection channelConnection = new BungeeChannelConnection(channel);
            registerChannel(channel);
            channelConnections.put(channel, channelConnection);
            return channelConnection;
        }
    }

    private void registerChannel(String channel) {
        if(!ProxyServer.getInstance().getChannels().contains(channel)){
            ProxyServer.getInstance().registerChannel(channel);
        }
    }
}
