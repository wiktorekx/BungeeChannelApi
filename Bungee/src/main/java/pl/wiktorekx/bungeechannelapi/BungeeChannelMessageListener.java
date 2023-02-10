package pl.wiktorekx.bungeechannelapi;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.wiktorekx.bungeechannelapi.annotation.MessageReceiver;
import pl.wiktorekx.bungeechannelapi.channel.BMessage;
import pl.wiktorekx.bungeechannelapi.channel.BungeeChannelUtils;
import pl.wiktorekx.bungeechannelapi.channel.IChannelConnection;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BungeeChannelMessageListener implements Listener {
    private final IBungeeChannelManager channelManager;
    private final String channel;
    private final Object listener;

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event){
        if(!event.getTag().equals(channel)) return;
        Map<Class<?>, Object> map = new HashMap<>();
        map.put(IChannelConnection.class, channelManager.getChannelConnection(channel));
        map.put(BMessage.class, new BMessage(event.getData()));
        map.put(BungeeMessageSender.class, new BungeeMessageSender(event.getSender()));
        map.put(BungeeMessageReceiver.class, new BungeeMessageReceiver(event.getReceiver()));
        BungeeChannelUtils.invokeAnnotatedMethod(listener, MessageReceiver.class, map);
    }
}
