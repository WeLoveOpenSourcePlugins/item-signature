package de.paul2708.signature;

import de.paul2708.signature.command.SignCommand;
import de.paul2708.signature.command.UnsignCommand;
import de.paul2708.signature.configuration.PluginConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignaturePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginConfiguration configuration = new PluginConfiguration(this);
        configuration.load();

        ItemSigner signer = new ItemSigner(configuration.getFormat());

        getCommand("sign").setExecutor(new SignCommand("sign", "no_permission",
                configuration, signer));
        getCommand("unsign").setExecutor(new UnsignCommand("unsign", "no_permission",
                configuration, signer));
    }
}