package me.saulooliveira.detetivemc.events;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import me.saulooliveira.detetivemc.entities.DeadBodyEntity;
import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ReputacaoEvent implements Listener {

    DetetiveGame detetiveGame = DetetiveGame.INSTANCE;

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

                EntityDamageEvent eventoDano = player.getLastDamageCause();
                if (eventoDano != null && !eventoDano.isCancelled() && eventoDano instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) eventoDano).getDamager();
                    if (damager instanceof Player) {
                        calcularReputacao(damager.getUniqueId(), player.getUniqueId());
                    }
                }

                detetiveGame.getLobby().remove(player.getUniqueId());
                verificarVencedor();

                for (ItemStack itemStack : player.getInventory().getContents()) {
                    try {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                        player.getInventory().removeItem(itemStack);
                        player.getInventory().setArmorContents(null);
                    } catch (Exception e) {

                    }
                }

                event.setCancelled(true);
            }
        }

    }

    private void calcularReputacao(UUID assassinoUUID, UUID assassinadoUUID) {
        if (Bukkit.getPlayer(assassinoUUID) != null && Bukkit.getPlayer(assassinadoUUID) != null) {

            Map<UUID, Papel> mapaAssassino = new HashMap<>();
            Map<UUID, Papel> mapaAssassinado = new HashMap<>();

            Player assassino = Bukkit.getPlayer(assassinoUUID);
            Player assassinado = Bukkit.getPlayer(assassinadoUUID);

            for (Map.Entry<UUID, Papel> entry : detetiveGame.getLobby().entrySet()) {
                if (entry.getKey().equals(assassino.getUniqueId())) {
                    mapaAssassino.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<UUID, Papel> entry : detetiveGame.getLobby().entrySet()) {
                if (entry.getKey().equals(assassinado.getUniqueId())) {
                    mapaAssassinado.put(entry.getKey(), entry.getValue());
                }
            }

            if (mapaAssassino.containsValue(Papel.TRAIDOR)) {
                if (mapaAssassinado.containsValue(Papel.DETETIVE)) {
                    Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(20);
                    assassino.getPlayer().sendMessage(ChatColor.GREEN + "Voc?? eliminou o Detetive! +20 de reputa????o!");
                    assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    assassino.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
                } else {
                    Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(5);
                    assassino.getPlayer().sendMessage(ChatColor.GREEN + "Voc?? fez uma v??tima! +5 de reputa????o!");
                    assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                }
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(10);
                assassino.getPlayer().sendMessage(ChatColor.GREEN + "Voc?? eliminou um tra??dor! +10 de reputa????o!");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-5);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Voc?? eliminou um suspeito! -5 de reputa????o!");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.DETETIVE)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-20);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Voc?? eliminou um detetive! -20 de reputa????o!");
                assassino.getPlayer().sendMessage(ChatColor.RED + "Preste mais aten????o...");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                assassino.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-5);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Voc?? eliminou um inocente! -5 de reputa????o!");
                assassino.getPlayer().sendMessage(ChatColor.RED + "Ainda h?? tra??dores ?? solta...");
                assassino.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(10);
                assassino.getPlayer().sendMessage(ChatColor.GREEN + "Voc?? eliminou um tra??dor! +10 de reputa????o!");
                assassino.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setInvulnerable(false);
    }

    private void verificarVencedor() {
        boolean vitoriaTraidores = detetiveGame.verificarCondicaoDeVitoriaTraidor();
        boolean vitoriaInocentes = detetiveGame.verificarCondicaoDeVitoriaInocentes();


        if (vitoriaInocentes) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "Os inocentes venceram");
        } else if (vitoriaTraidores) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "O Tra??dor venceu");
        }


    }
}
