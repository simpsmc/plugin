package cloud.simps.simps;

import cloud.simps.simps.listeners.EssentialsListener;
import cloud.simps.simps.listeners.LiteBansListener;
import cloud.simps.simps.listeners.VanillaListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SIMPSPlugin extends JavaPlugin {
    private OkHttpClient httpClient;

    @Override
    public void onEnable() {
        // Make sure all requests use api key if directed to our API
        httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request ogReq = chain.request();
            String url = this.getConfig().getString("apiUrl");
            if (!ogReq.url().host().equals(url)) return chain.proceed(ogReq);
            Request req = ogReq.newBuilder().addHeader("Authorization", "API_KEY").build();
            return chain.proceed(req);
        }).build();

        // Test to make sure the API is available
        // Otherwise disable the plugin
        Request testRequest = new Request.Builder().url("https://api.simps.cloud/ping").build();

        try (Response res = httpClient.newCall(testRequest).execute()){
            if (!res.isSuccessful()) throw new IOException("Unexpected non 200 code");
        } catch (Exception e) {
            this.getLogger().severe("Could not connect to SIMPS API, disabling SIMPS");
            this.setEnabled(false);
            return;
        }

        Plugin essentials = getServer().getPluginManager().getPlugin("Essentials");
        Plugin litebans = getServer().getPluginManager().getPlugin("LiteBans");

        // Check if litebans or essentialsx is being used
        if (litebans != null || essentials != null) {
            Plugin banHandler = getServer().getPluginCommand("ban").getPlugin();

            if (banHandler.equals(essentials)) {
                getServer().getPluginManager().registerEvents(new EssentialsListener(this, essentials), this);
            } else if (banHandler.equals(litebans)) {
                LiteBansListener.register(this);
            } else {
                // Litebans or essentials is found, but neither handle the ban command?
                // Something funky is going on, disable the plugin
                this.getLogger().severe("Neither litebans nor essentials is the register for the ban command, unsure how to proceed, disabling");
                this.getLogger().severe("Contact the developer if this doesn't seem right");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        } else {
            // If neither essentials nor litebans is found, we default to listening for vanilla bans
            getServer().getPluginManager().registerEvents(new VanillaListener(this), this);
        }

        this.saveDefaultConfig();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
