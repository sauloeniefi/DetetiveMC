package me.saulooliveira.detetivemc.events;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reputacao implements Listener {


    DetetiveGame detetiveGame = DetetiveGame.INSTANCE;

    @EventHandler
    public void onKillPlayer(EntityDeathEvent event) {
        Player assassinado = event.getEntity().getKiller();
        LivingEntity assassino = event.getEntity();

        if (assassino instanceof Player && assassinado != null) {

            System.out.println("ALGUEM MORREU");
            Map<Player, Papel> mapaAssassino = new HashMap<>();
            Map<Player, Papel> mapaAssassinado = new HashMap<>();

            System.out.println(detetiveGame.getLobby());

            for (Map.Entry<Player, Papel> entry : detetiveGame.getLobby().entrySet()) {
                if (entry.getKey().equals(assassino)) {
                    mapaAssassinado.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Player, Papel> entry : detetiveGame.getLobby().entrySet()) {
                if (entry.getKey().equals(assassinado)) {
                    mapaAssassino.put(entry.getKey(), entry.getValue());
                }
            }

            // ASSASSINO MATA
            if (mapaAssassino.containsValue(Papel.TRAIDOR)) {
                if (mapaAssassinado.containsValue(Papel.DETETIVE)) {
                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(20);
                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou o Detetive! +20 de reputação!");
                } else {
                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(5);
                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você fez uma vítima! +5 de reputação!");
                }
            }

            // SUSPEITO MATA TRAIDOR
            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
            }

            // SUSPEITO MATA SUSPEITO
            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um suspeito! -5 de reputação!");
            }

            // SUSPEITO MATA DETETIVE
            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.DETETIVE)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-20);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um detetive! -20 de reputação!");
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Preste mais atenção...");
            }

            // DETETIVE MATA SUSPEITO
            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um inocente! -5 de reputação!");
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Ainda há traídores à solta...");
            }

            // DETETIVE MATA TRAIDOR
            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
            }
        }
    }
}
