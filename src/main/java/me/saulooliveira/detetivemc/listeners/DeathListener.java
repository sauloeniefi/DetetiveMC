package me.saulooliveira.detetivemc.listeners;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player playerMorto = event.getEntity();
        playerMorto.sendMessage("TU TA MORTO BRO");
    }

    private void spawnDeadBody(Player player) {
        EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();
    }
}
