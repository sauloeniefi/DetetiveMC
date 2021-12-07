package me.saulooliveira.detetivemc.events;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import me.saulooliveira.detetivemc.entities.DeadBodyEntity;
import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReputacaoEvent implements Listener {

    DetetiveGame detetiveGame = DetetiveGame.INSTANCE;

    @EventHandler
    public void onKillPlayer(EntityDeathEvent event) {
        Player assassinado = event.getEntity().getKiller();
        LivingEntity assassino = event.getEntity();

        if (assassino instanceof Player && assassinado != null) {

            Map<Player, Papel> mapaAssassino = new HashMap<>();
            Map<Player, Papel> mapaAssassinado = new HashMap<>();

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

            if (mapaAssassino.containsValue(Papel.TRAIDOR)) {
                if (mapaAssassinado.containsValue(Papel.DETETIVE)) {
                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(20);
                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou o Detetive! +20 de reputação!");
                    assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    assassinado.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
                } else {
                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(5);
                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você fez uma vítima! +5 de reputação!");
                    assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                }
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um suspeito! -5 de reputação!");
                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.DETETIVE)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-20);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um detetive! -20 de reputação!");
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Preste mais atenção...");
                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                assassinado.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um inocente! -5 de reputação!");
                assassinado.getPlayer().sendMessage(ChatColor.RED + "Ainda há traídores à solta...");
                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {
            if (event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                Player player = (Player) event.getEntity();
                DeadBodyEntity.execute(player);

                player.setHealth(20);
                player.setGameMode(GameMode.SPECTATOR);
                player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1));

                for (ItemStack itemStack : player.getInventory().getContents()) {
                    try {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                        player.getInventory().removeItem(itemStack);
                    } catch (Exception e) {

                    }
                }

                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setInvulnerable(false);
    }
}
