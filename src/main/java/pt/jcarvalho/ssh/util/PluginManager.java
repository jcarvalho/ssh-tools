package pt.jcarvalho.ssh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginManager {

    private static Map<Class<?>, Object> implementations = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getPluginFor(Class<? extends T> interfaceClass) {
	Object implementation = implementations.get(interfaceClass);
	if (implementation == null)
	    throw new RuntimeException("Error, no implementation was provided for the given plugin! " + interfaceClass.getName());

	return (T) implementation;
    }

    public static <T> void registerPlugin(Class<?> plugin) {
	try {
	    List<Class<?>> pluggables = new ArrayList<>();
	    findPluggables(plugin, pluggables);
	    Object instance = plugin.newInstance();
	    for (Class<?> pluggable : pluggables) {
		implementations.put(pluggable, instance);
	    }
	} catch (Throwable e) {
	    throw new RuntimeException("Error while registering plugin!", e);
	}
    }

    private static void findPluggables(Class<?> plugin, List<Class<?>> pluggables) {
	if (plugin.equals(Object.class))
	    return;

	for (Class<?> interfaces : plugin.getInterfaces()) {
	    if (interfaces.isAnnotationPresent(Pluggable.class))
		pluggables.add(interfaces);
	}
	findPluggables(plugin.getSuperclass(), pluggables);
    }
}
