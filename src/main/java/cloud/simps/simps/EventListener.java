package cloud.simps.simps;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class EventListener implements Listener {
    private SIMPSPlugin plugin;

    public EventListener(SIMPSPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        // A ban is really just a kick and adding the player to the ban list
        if (!player.isBanned()) return;

        plugin.getServer().broadcast(String.format("%s was banned, distributing to SIMPS", player.getName()), "simps.notify");
        // TODO: actually send stuff to the SIMPS API
    }
}
