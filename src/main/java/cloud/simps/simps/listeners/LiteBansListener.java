package cloud.simps.simps.listeners;

import cloud.simps.simps.SIMPSPlugin;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.entity.Player;

public class LiteBansListener extends Events.Listener {
    private final SIMPSPlugin plugin;

    public LiteBansListener(SIMPSPlugin plugin) { this.plugin = plugin; }

    @Override
    public void entryAdded(Entry entry) {
        Player player = plugin.getServer().getPlayer(entry.getUuid());

        var logMsg = String.format("%s was banned, distributing to SIMPS", player.getName());
        plugin.getServer().broadcast(logMsg, "simps.notify");
        plugin.getLogger().info(logMsg);
        // TODO: actually send stuff to the SIMPS API
    }

    public static void register(SIMPSPlugin plugin) {
        Events.get().register(new LiteBansListener(plugin));
    }
}
