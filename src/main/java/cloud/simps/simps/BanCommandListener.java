package cloud.simps.simps;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class BanCommandListener implements Listener {
    private final SIMPSPlugin plugin;

    public BanCommandListener(SIMPSPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void handlePlayerCommandEvent(PlayerCommandPreprocessEvent event) {
        parseCommandContent(event.getMessage().substring(1));
    }

    @EventHandler
    public void handleServerCommandEvent(ServerCommandEvent event) {
        parseCommandContent(event.getCommand());
    }

    private void parseCommandContent(String command) {
        String[] args = command.split(" ");

        if (args[0].equals("ban")) {
            String bannedUser = args[1];
            String banReason = null;
            if (args.length < 2) banReason = args[2];

            // TODO: Send parsed info to main logic
        }
    }
}
