package cloud.simps.simps.listeners;

import cloud.simps.simps.SIMPSPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class VanillaListener implements Listener {
    private final SIMPSPlugin plugin;

    public VanillaListener(SIMPSPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        // A ban is really just a kick and adding the player to the ban list
        if (!player.isBanned()) return;

        var logMsg = String.format("%s was banned, distributing to SIMPS", player.getName());
        plugin.getServer().broadcast(logMsg, "simps.notify");
        plugin.getLogger().info(logMsg);
        // TODO: actually send stuff to the SIMPS API
    }
}
