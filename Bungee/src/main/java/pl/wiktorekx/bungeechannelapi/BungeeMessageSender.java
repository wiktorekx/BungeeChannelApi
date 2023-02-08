package pl.wiktorekx.bungeechannelapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.Connection;

@RequiredArgsConstructor
@Getter
public class BungeeMessageSender {
    private final Connection connection;
}
