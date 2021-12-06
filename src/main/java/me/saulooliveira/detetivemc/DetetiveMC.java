package me.saulooliveira.detetivemc;

import me.saulooliveira.detetivemc.commands.Detetive;
import me.saulooliveira.detetivemc.commands.Reputacao;
import me.saulooliveira.detetivemc.events.ReputacaoEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DetetiveMC extends JavaPlugin {

    private static DetetiveMC plugin;

    @Override
    public void onEnable() {
        plugin = this;

        System.out.println("Meu primeiro plugin foi Iniciado!!!!!!!!!!!!!!!!!!!!!");

        getCommand("detetive").setExecutor(new Detetive());
        getCommand("reputacao").setExecutor(new Reputacao());
        getServer().getPluginManager().registerEvents(new ReputacaoEvent(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Meu primeiro plugin foi Parado!");

    }
}
