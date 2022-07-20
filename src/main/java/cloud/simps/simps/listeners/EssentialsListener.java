package cloud.simps.simps.listeners;

import cloud.simps.simps.SIMPSPlugin;
import com.earth2me.essentials.User;
import com.earth2me.essentials.craftbukkit.BanLookup;
import net.ess3.api.IEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.Plugin;

public class EssentialsListener implements Listener {
    private final SIMPSPlugin plugin;
    private final IEssentials essentials;

    public EssentialsListener(SIMPSPlugin plugin, Plugin essentials) {
        this.plugin = plugin;
        this.essentials = (IEssentials) essentials;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        User essentialsUser = this.essentials.getUser(player);

        if (!BanLookup.isBanned(essentials, essentialsUser)) return;

        var logMsg = String.format("%s was banned, distributing to SIMPS", player.getName());
        plugin.getServer().broadcast(logMsg, "simps.notify");
        plugin.getLogger().info(logMsg);
        // TODO: actually send stuff to the SIMPS API
    }
}
