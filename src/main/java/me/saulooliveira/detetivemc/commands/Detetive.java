package me.saulooliveira.detetivemc.commands;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Detetive implements CommandExecutor {

    DetetiveGame lobby = DetetiveGame.INSTANCE;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length > 0) {

                switch (args[0]) {

                    case "entrar":
                        if (!lobby.getLobby().isEmpty()) {
                            if (lobby.getLobby().containsKey(player)) {
                                player.sendMessage("Você já está no lobby.");
                            } else {
                                lobby.getLobby().put(player, lobby.randomizeRole());
                                player.sendMessage("Você entrou na sala do detetive!");
                                player.setGameMode(GameMode.SURVIVAL);
                            }
                        } else {
                            lobby.getLobby().put(player, lobby.randomizeRole());
                            player.sendMessage("Você entrou na sala do detetive!");
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        break;

                    case "listar":
                        player.sendMessage(lobby.getLobby().toString());
                        break;

                    case "iniciar":
                        commandSender.sendMessage("Iniciou o jogo!");
                        break;

                    case "sair":
                        for (Map.Entry<Player, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer())) {
                                lobby.getLobby().remove(entry.getKey());
                            }
                        }
                        break;

                    case "traidor":
                        for (Map.Entry<Player, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer())) {
                                lobby.getLobby().replace(entry.getKey(), Papel.TRAIDOR);
                            }
                        }
                        break;

                    case "detetive":
                        for (Map.Entry<Player, Papel> entry : lobby.getLobby().entrySet()) {
                            if (entry.getKey().equals(((Player) commandSender).getPlayer())) {
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
}
