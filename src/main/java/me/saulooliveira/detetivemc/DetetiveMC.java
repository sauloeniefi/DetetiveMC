package me.saulooliveira.detetivemc;

import me.saulooliveira.detetivemc.commands.Detetive;
import me.saulooliveira.detetivemc.commands.Reputacao;
import me.saulooliveira.detetivemc.events.ReputacaoEvent;
import me.saulooliveira.detetivemc.listeners.ChestManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DetetiveMC extends JavaPlugin {

    private static DetetiveMC plugin;

    private ChestManager chestManager;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        chestManager = new ChestManager(getConfig());

        getCommand("detetive").setExecutor(new Detetive());
        getCommand("reputacao").setExecutor(new Reputacao());
        getServer().getPluginManager().registerEvents(new ReputacaoEvent(), plugin);
        getServer().getPluginManager().registerEvents(chestManager, plugin);
    }

    @Override
    public void onDisable() {
        System.out.println("Meu primeiro plugin foi Parado!");
    }
}
