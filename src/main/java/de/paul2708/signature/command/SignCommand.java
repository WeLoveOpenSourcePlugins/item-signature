package de.paul2708.signature.command;

import de.paul2708.signature.ItemSigner;
import de.paul2708.signature.configuration.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SignCommand extends AbstractCommand {

    private final PluginConfiguration configuration;
    private final ItemSigner signer;

    public SignCommand(String permission, String noPermissionMessage, PluginConfiguration configuration,
                       ItemSigner signer) {
        super(permission, noPermissionMessage);

        this.configuration = configuration;
        this.signer = signer;
    }

    @Override
    protected void run(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!signer.isValid(item)) {
            player.sendMessage(configuration.getMessage("no_item_in_hand"));
            return;
        }
        if (!configuration.isAllowed("resign")) {
            player.sendMessage(configuration.getMessage("resign_not_allowed"));
            return;
        }

        if (signer.isSigned(item)) {
            signer.unsign(item);
        }

        signer.sign(player, item);
        player.sendMessage(configuration.getMessage("successfully_signed"));
    }
}