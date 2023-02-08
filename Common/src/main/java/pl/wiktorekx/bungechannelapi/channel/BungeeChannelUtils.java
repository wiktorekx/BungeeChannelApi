package pl.wiktorekx.bungechannelapi.channel;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import pl.wiktorekx.bungechannelapi.IBungeeChannelManager;
import pl.wiktorekx.bungechannelapi.annotation.ChannelListener;
import pl.wiktorekx.bungechannelapi.annotation.ChannelRegisterAction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public final class BungeeChannelUtils {

    private BungeeChannelUtils() {}

    @SneakyThrows
    public static String registerListener(@NotNull IBungeeChannelManager channelManager, @NotNull Object listener) {
        Objects.requireNonNull(channelManager);
        Objects.requireNonNull(listener);
        Class<?> listenerClass = listener.getClass();
        if(!listenerClass.isAnnotationPresent(ChannelListener.class)) throw new IllegalArgumentException("This class is not a channel listener");
        String channel = listenerClass.getAnnotation(ChannelListener.class).value();
        if(channel == null || channel.isEmpty()) throw new IllegalArgumentException("Channel in channel listener is empty");
        IChannelConnection channelConnection = channelManager.getChannelConnection(channel);

        invokeAnnotatedMethod(listener, ChannelRegisterAction.class, Collections.singletonMap(IChannelConnection.class, channelConnection));

        return channel;
    }

    @SneakyThrows
    public static void invokeAnnotatedMethod(@NotNull Object instance, @NotNull Class<? extends Annotation> annotation, @NotNull Map<Class<?>, Object> parameters) {
        Objects.requireNonNull(instance);
        Objects.requireNonNull(annotation);
        Objects.requireNonNull(parameters);
        for(Method method : instance.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(annotation)) {
                List<Object> parametersValues = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
                    Object parameterValue = parameters.get(parameter.getType());
                    Objects.requireNonNull(parameterValue, "Parameter type " + parameter.getType() + " is not instance");
                    if (!parameter.getType().isInstance(parameterValue)) {
                        throw new ClassCastException("Parameter except type " + parameter.getType() + " give instance is " + parameterValue);
                    }
                    parametersValues.add(parameterValue);
                }
                if(!method.isAccessible()) method.setAccessible(true);
                method.invoke(instance, parametersValues.toArray(new Object[0]));
            }
        }
    }
}
