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

    UUID assassinoUUID = UUID.randomUUID();
    UUID assassinadoUUID = UUID.randomUUID();

//    @EventHandler
//    public void onKillPlayer(EntityDeathEvent event) {
//        Player assassinado = event.getEntity().getKiller();
//        LivingEntity assassino = event.getEntity();
//
//        if (assassino instanceof Player && assassinado != null) {
//
//            Map<UUID, Papel> mapaAssassino = new HashMap<>();
//            Map<UUID, Papel> mapaAssassinado = new HashMap<>();
//
//            for (Map.Entry<UUID, Papel> entry : detetiveGame.getLobby().entrySet()) {
//                if (entry.getKey().equals(assassino)) {
//                    mapaAssassinado.put(entry.getKey(), entry.getValue());
//                }
//            }
//
//            for (Map.Entry<UUID, Papel> entry : detetiveGame.getLobby().entrySet()) {
//                if (entry.getKey().equals(assassinado)) {
//                    mapaAssassino.put(entry.getKey(), entry.getValue());
//                }
//            }
//
//            if (mapaAssassino.containsValue(Papel.TRAIDOR)) {
//                if (mapaAssassinado.containsValue(Papel.DETETIVE)) {
//                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(20);
//                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou o Detetive! +20 de reputação!");
//                    assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//                    assassinado.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
//                } else {
//                    Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(5);
//                    assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você fez uma vítima! +5 de reputação!");
//                    assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//
//                }
//            }
//
//            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
//                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
//                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
//                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
//            }
//
//            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
//                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
//                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um suspeito! -5 de reputação!");
//                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
//            }
//
//            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.DETETIVE)) {
//                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-20);
//                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um detetive! -20 de reputação!");
//                assassinado.getPlayer().sendMessage(ChatColor.RED + "Preste mais atenção...");
//                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
//                assassinado.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
//            }
//
//            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
//                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(-5);
//                assassinado.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um inocente! -5 de reputação!");
//                assassinado.getPlayer().sendMessage(ChatColor.RED + "Ainda há traídores à solta...");
//                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
//            }
//
//            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
//                Objects.requireNonNull(assassinado.getPlayer()).giveExpLevels(10);
//                assassinado.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
//                assassinado.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//            }
//        }
//    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {
            if (event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                System.out.println(Bukkit.getPlayer(assassinoUUID) + " matou " + Bukkit.getPlayer(assassinadoUUID));
                Player player = (Player) event.getEntity();
                DeadBodyEntity.execute(player);

                player.setHealth(20);
                player.setGameMode(GameMode.SPECTATOR);
                player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1));

                EntityDamageEvent eventoDano = player.getLastDamageCause();
                if (eventoDano!=null && !eventoDano.isCancelled() && eventoDano instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) eventoDano).getDamager();
                    if(damager instanceof Player) {
                        calcularReputacao(damager.getUniqueId(), player.getUniqueId());
                    }
                }

                detetiveGame.getPlayersVivos().remove(player.getUniqueId());
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
                    assassino.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou o Detetive! +20 de reputação!");
                    assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    assassino.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
                } else {
                    Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(5);
                    assassino.getPlayer().sendMessage(ChatColor.GREEN + "Você fez uma vítima! +5 de reputação!");
                    assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                }
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(10);
                assassino.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-5);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um suspeito! -5 de reputação!");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.SUSPEITO) && mapaAssassinado.containsValue(Papel.DETETIVE)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-20);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um detetive! -20 de reputação!");
                assassino.getPlayer().sendMessage(ChatColor.RED + "Preste mais atenção...");
                assassino.getPlayer().playSound(assassino.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                assassino.getWorld().getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_RED + "Um Detetive foi eliminado!"));
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.SUSPEITO)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(-5);
                assassino.getPlayer().sendMessage(ChatColor.RED + "Você eliminou um inocente! -5 de reputação!");
                assassino.getPlayer().sendMessage(ChatColor.RED + "Ainda há traídores à solta...");
                assassino.getPlayer().playSound(assassinado.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 1);
            }

            if (mapaAssassino.containsValue(Papel.DETETIVE) && mapaAssassinado.containsValue(Papel.TRAIDOR)) {
                Objects.requireNonNull(assassino.getPlayer()).giveExpLevels(10);
                assassino.getPlayer().sendMessage(ChatColor.GREEN + "Você eliminou um traídor! +10 de reputação!");
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
        if (vitoriaTraidores && !vitoriaInocentes) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say Os traidores venceram!");
            //try {
                //Thread.sleep(5000);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
          //  } catch (InterruptedException e) {
               // Thread.currentThread().interrupt();
            }//


        if (vitoriaInocentes) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say Os inocentes venceram!");
            //try {
                //Thread.sleep(5000);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            //} catch (InterruptedException e) {
              //  Thread.currentThread().interrupt();
            //}
        }

    }
}
