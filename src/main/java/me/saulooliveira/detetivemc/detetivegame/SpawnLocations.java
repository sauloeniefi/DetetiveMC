package me.saulooliveira.detetivemc.detetivegame;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class SpawnLocations {

    private static final String worldName = "world";

    private final List<Location> listaSpawns = new ArrayList<>();

    public SpawnLocations() {
        setSpawnsLocation();
    }

    public List<Location> getListaSpawns() {
        return listaSpawns;
    }

    private void setSpawnsLocation() {
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), 0, 81, -7));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), 27, 79, 10));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), 38, 79, 50));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), 28, 78, 80));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), 33, 79, 102));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), -7, 75, 83));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), -24, 78, 57));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), -29, 75, 88));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), -61, 81, 99));
        listaSpawns.add(new Location(Bukkit.getWorld(worldName), -66, 77, 33));
    }
}
