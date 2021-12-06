package me.saulooliveira.detetivemc;

import me.saulooliveira.detetivemc.commands.Detetive;
import org.bukkit.plugin.java.JavaPlugin;

public final class DetetiveMC extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Meu primeiro plugin foi Iniciado!");

        getCommand("detetive").setExecutor(new Detetive());
    }

    @Override
    public void onDisable() {
        System.out.println("Meu primeiro plugin foi Parado!");

    }
}
