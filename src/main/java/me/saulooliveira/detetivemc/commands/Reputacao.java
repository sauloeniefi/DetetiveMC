package me.saulooliveira.detetivemc.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reputacao implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length > 0) {
                switch (args[0]) {
                    case "exibir":
                        player.sendMessage(ChatColor.GREEN + "Sua reputação é: " + player.getLevel());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1,1);
                        break;
                }
            }
        }

        return true;
    }
}
