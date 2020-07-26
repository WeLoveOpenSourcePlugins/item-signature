package de.paul2708.signature;

import de.paul2708.signature.command.AbstractCommand;
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

        AbstractCommand signCommand = new SignCommand(
                configuration.getPermission("sign"),
                configuration.getMessage("no_permission"),
                configuration, signer);
        AbstractCommand unsignCommand = new UnsignCommand(
                configuration.getPermission("unsign"),
                configuration.getMessage("no_permission"),
                configuration, signer);

        getCommand("sign").setExecutor(signCommand);
        getCommand("unsign").setExecutor(unsignCommand);
    }
}