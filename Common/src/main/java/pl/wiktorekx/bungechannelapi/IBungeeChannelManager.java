package pl.wiktorekx.bungechannelapi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.wiktorekx.bungechannelapi.channel.IChannelConnection;

public interface IBungeeChannelManager {
    @NotNull IBungeeChannelManager registerChannelListener(@NotNull Class<?> listener);

    @NotNull IBungeeChannelManager registerChannelListener(@NotNull Object listener);

    @Nullable Object getChannelListener(@NotNull String channel);

    @NotNull IChannelConnection getChannelConnection(@NotNull String channel);
}