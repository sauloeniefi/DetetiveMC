package me.saulooliveira.detetivemc.commands;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                            }
                        } else {
                            lobby.getLobby().put(player, lobby.randomizeRole());
                            player.sendMessage("Você entrou na sala do detetive!");
                        }
                        break;

                    case "listar":
                        player.sendMessage(lobby.getLobby().toString());
                        break;

                    case "iniciar":
                        commandSender.sendMessage("Iniciou o jogo!");
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
