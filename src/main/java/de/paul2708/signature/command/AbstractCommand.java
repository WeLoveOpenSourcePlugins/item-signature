package de.paul2708.signature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    private final String permission;
    private final String noPermissionMessage;

    public AbstractCommand(String permission, String noPermissionMessage) {
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[Sign] Der Befehl ist nur für Spieler.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            player.sendMessage(noPermissionMessage);
            return true;
        }

        run(player, args);
        return true;
    }

    protected abstract void run(Player player, String[] args);
}