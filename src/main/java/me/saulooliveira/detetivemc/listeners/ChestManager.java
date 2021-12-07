package me.saulooliveira.detetivemc.listeners;

import me.saulooliveira.detetivemc.storages.LootItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ChestManager implements Listener {

    private final Set<Location> openedChests = new HashSet<>();
    private final List<LootItem> lootItems = new ArrayList<>();

    public ChestManager(FileConfiguration lootConfig) {
        ConfigurationSection itemsSection = lootConfig.getConfigurationSection("lootItems");

        if (itemsSection == null) {
            Bukkit.getLogger().severe("ALERTA - Favor configurar corretamente seu lootItems no config.yml!");
        } else {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection section = itemsSection.getConfigurationSection(key);
                lootItems.add(new LootItem(section));
            }
        }
    }

    @EventHandler
    private void onChestOpen(InventoryOpenEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;
            if (hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fill(chest.getBlockInventory());
        } else if (holder instanceof DoubleChest) {
            DoubleChest chest = (DoubleChest) holder;
            if (hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fill(chest.getInventory());
        }

    }

    public void fill(Inventory inventory) {
        inventory.clear();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<LootItem> used = new HashSet<>();

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            LootItem randomItem = lootItems.get(
                    random.nextInt(lootItems.size())
            );
            if (used.contains(randomItem)) continue;
            used.add(randomItem);

            if (randomItem.shoulFill(random)) {
                ItemStack itemStack = randomItem.make(random);
                inventory.setItem(slotIndex, itemStack);
            }
        }
    }

    public void markAsOpened(Location location) {
        openedChests.add(location);
    }

    public boolean hasBeenOpened(Location location) {
        return openedChests.contains(location);
    }

    public void resetChests() {
        openedChests.clear();
    }

}
