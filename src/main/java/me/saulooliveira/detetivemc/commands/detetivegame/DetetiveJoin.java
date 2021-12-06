package me.saulooliveira.detetivemc.commands.detetivegame;

import me.saulooliveira.detetivemc.detetivegame.DetetiveGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DetetiveJoin implements CommandExecutor {

    DetetiveGame lobby = new DetetiveGame();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("entrar")) {

                    if (!lobby.getLobby().isEmpty()) {
                        if (lobby.getLobby().containsKey(player)) {
                            player.sendMessage("Você já está no lobby.");
                        }
                    } else {
                        lobby.getLobby().put(player, lobby.randomizeRole());
                        player.sendMessage("Você entrou na sala do detetive!");
                    }
                }

                if (args[0].equalsIgnoreCase("listar")) {
                    player.sendMessage(lobby.getLobby().toString());
                }

            } else {
                player.sendMessage("Você precisa passar um argumento para este comando");
                player.sendMessage("/detetive [argumento]");
            }
        }

        return true;
    }
}
