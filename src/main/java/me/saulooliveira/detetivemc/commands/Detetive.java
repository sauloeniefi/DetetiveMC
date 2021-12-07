package me.saulooliveira.detetivemc.commands;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import me.saulooliveira.detetivemc.detetivegame.SpawnLocations;
import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Detetive implements CommandExecutor {

    DetetiveGame lobby = DetetiveGame.INSTANCE;
    SpawnLocations spawnLocations = new SpawnLocations();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location spawnLobby = player.getWorld().getSpawnLocation();

            if (args.length > 0) {

                switch (args[0]) {

                    case "entrar":
                        if (!lobby.getLobby().isEmpty()) {
                            if (lobby.getLobby().containsKey(player)) {
                                player.sendMessage("Você já está no lobby.");
                            } else {
                                lobby.getLobby().put(player.getUniqueId(), lobby.randomizeRole());
                                player.sendMessage("Você entrou na sala do detetive!");
                                player.teleport(spawnLobby);
                                player.setGameMode(GameMode.SURVIVAL);
                                player.setInvulnerable(false);
                            }
                        } else {
                            lobby.getLobby().put(player.getUniqueId(), lobby.randomizeRole());
                            player.sendMessage("Você entrou na sala do detetive!");
                            player.teleport(spawnLobby);
                            player.setGameMode(GameMode.SURVIVAL);
//                            player.setInvulnerable(true);
                            player.setInvulnerable(false);

                        }
                        break;

                    case "listar":
                        player.sendMessage(lobby.getLobby().toString());
                        break;

                    case "iniciar":
                        if (verificarInicioLobby(lobby.getLobby())) {

                            for (int i = 0; i < lobby.listarPlayers().size(); i++) {
                                Player playerTeleportado = (Player) Bukkit.getPlayer(lobby.listarPlayers().get(i));
                                playerTeleportado.teleport(spawnLocations.getListaSpawns().get(i));
                            }

                            commandSender.sendMessage("Iniciou o jogo!");

                        } else {
                            player.sendMessage(ChatColor.YELLOW + "Impossível iniciar o minigame, jogadores insuficientes.");
                        }
                        break;

                    case "sair":
                        for (Map.Entry<UUID, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer().getUniqueId())) {
                                lobby.getLobby().remove(entry.getKey());
                            }
                        }
                        break;

                    case "traidor":
                        for (Map.Entry<UUID, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer().getUniqueId())) {
                                lobby.getLobby().replace(entry.getKey(), Papel.TRAIDOR);
                            }
                        }
                        break;

                    case "detetive":
                        for (Map.Entry<UUID, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer().getUniqueId())) {
                                lobby.getLobby().replace(entry.getKey(), Papel.DETETIVE);
                            }
                        }
                        break;

                    default:
                        player.sendMessage("Você precisa passar um argumento para este comando");
                        player.sendMessage("/detetive [argumento]");
                        break;
                }

            }
        }
        return true;
    }

    private boolean verificarInicioLobby(Map<UUID, Papel> mapaLobby) {
        boolean temDetetive = mapaLobby.containsValue(Papel.DETETIVE);
        boolean temTraidor = mapaLobby.containsValue(Papel.TRAIDOR);

        return temDetetive && temTraidor && mapaLobby.size() >= 5;
    }
}
