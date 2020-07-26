package de.paul2708.signature;

import de.paul2708.signature.command.SignCommand;
import de.paul2708.signature.configuration.PluginConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class SignaturePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginConfiguration configuration = new PluginConfiguration(this);
        configuration.load();

        getCommand("sign").setExecutor(new SignCommand("sign", "no_permission",
                configuration, new ItemSigner(List.of("test1", "test2"))));
    }
}