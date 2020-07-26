package de.paul2708.signature;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ItemSigner {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private final List<String> signature;

    public ItemSigner(List<String> signature) {
        this.signature = signature;
    }

    public boolean isValid(ItemStack item) {
        return item != null && !item.getType().isAir();
    }

    public void sign(Player player, ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        List<String> realSignature = replacePlaceholder(player);

        if (meta.hasLore()) {
            List<String> lore = new LinkedList<>(meta.getLore());
            lore.addAll(realSignature);
            meta.setLore(lore);
        } else {
            meta.setLore(realSignature);
        }

        item.setItemMeta(meta);
    }

    public boolean isSigned(ItemStack item) {
        if (!isValid(item) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
            return false;
        }

        int counter = 0;
        for (String regex : toRegex()) {
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.matches(regex)) {
                    counter++;
                }
            }
        }

        return counter == signature.size();
    }

    public void unsign(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> list = new ArrayList<>(meta.getLore());

        for (String regex : toRegex()) {
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.matches(regex)) {
                    list.remove(lore);
                }
            }
        }

        meta.setLore(list);
        item.setItemMeta(meta);
    }

    private List<String> replacePlaceholder(Player player) {
        List<String> list = new LinkedList<>();
        Date date = new Date(System.currentTimeMillis());

        for (String line : signature) {
            String replaced = line.replace("%NAME%", player.getName())
                    .replace("%DATUM%", DATE_FORMAT.format(date))
                    .replace("%UHRZEIT%", TIME_FORMAT.format(date));
            list.add(replaced);
        }

        return list;
    }

    private List<String> toRegex() {
        List<String> list = new LinkedList<>();

        for (String line : signature) {
            String replaced = line.replace("%NAME%", ".+")
                    .replace("%DATUM%", ".+")
                    .replace("%UHRZEIT%", ".+");
            list.add(replaced);
        }

        return list;
    }
}