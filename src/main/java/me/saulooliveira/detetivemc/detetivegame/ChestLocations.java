package me.saulooliveira.detetivemc.detetivegame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ChestLocations {

    private static final String worldName = "world";

    private final List<Location> chestSpawns = new ArrayList<>();

    public ChestLocations() {
        setSpawnsLocation();
    }

    public List<Location> getChestSpawns() {
        return chestSpawns;
    }

    public void inserirBausNoMundo() {
        for (Location chestSpawn : chestSpawns) {
            chestSpawn.getBlock().setType(Material.CHEST);
        }
    }

    private void setSpawnsLocation() {
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -42, 78, 22));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -40, 78, 7));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -20, 78, -13));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 2, 82, 1));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 34, 78, 26));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 32, 81, -10));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 16, 80, 13));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 38, 79, 47));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -1, 75, 54));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -36, 82, 89));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -50, 78, 84));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -24, 75, 86));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -21, 75, 73));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), -4, 75, 88));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 10, 78, 75));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 11, 81, 92));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 15, 78, 93));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 23, 78, 63));
        chestSpawns.add(new Location(Bukkit.getWorld(worldName), 36, 80, 102));
    }
}
