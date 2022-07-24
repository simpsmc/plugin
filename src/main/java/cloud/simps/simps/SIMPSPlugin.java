package cloud.simps.simps;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SIMPSPlugin extends JavaPlugin {
    private OkHttpClient httpClient;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // Make sure all requests use api key if directed to our API
        httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request ogReq = chain.request();
            String url = this.getConfig().getString("apiUrl");
            if (!ogReq.url().host().equals(url)) return chain.proceed(ogReq);
            Request req = ogReq.newBuilder().addHeader("Authorization", "API_KEY").build();
            return chain.proceed(req);
        }).build();

        if (this.getConfig().getBoolean("checkApiAvailability")) {
            // Test to make sure the API is available
            // Otherwise disable the plugin
            Request testRequest = new Request.Builder().url("https://api.simps.cloud/ping").build();

            try (Response res = httpClient.newCall(testRequest).execute()) {
                if (!res.isSuccessful()) throw new IOException("Unexpected non 200 code");
            } catch (Exception e) {
                this.getLogger().severe("Could not connect to SIMPS API, disabling SIMPS");
                this.setEnabled(false);
                return;
            }
        } else {
            getLogger().severe("API availability check has been disabled, unless you know what this entails, you should set 'checkApiAvailability' to true in the config");
        }

        getServer().getPluginManager().registerEvents(new BanCommandListener(this), this);
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
