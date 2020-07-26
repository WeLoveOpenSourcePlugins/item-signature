package de.paul2708.signature;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class ItemSigner {

    private final List<String> signature;

    public ItemSigner(List<String> signature) {
        this.signature = signature;
    }

    public boolean isValid(ItemStack item) {
        return item != null && !item.getType().isAir();
    }

    public void sign(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());

        if (meta.hasLore()) {
            List<String> lore = new LinkedList<>(meta.getLore());
            lore.addAll(signature);
            meta.setLore(lore);
        } else {
            meta.setLore(signature);
        }

        item.setItemMeta(meta);
    }
}