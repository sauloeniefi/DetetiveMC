package me.saulooliveira.detetivemc.detetivegame;

import me.saulooliveira.detetivemc.enums.Papel;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DetetiveGame {

    private Map<Player, Papel> playersInLobby = new HashMap<>();

    public Map<Player, Papel> getLobby() {
        return playersInLobby;
    }

    public Papel randomizeRole() {
        Random random = new Random();
        Papel papelSorteado = Papel.fromValue(random.nextInt(3) + 1);
        if (!playersInLobby.isEmpty()) {
            if (playersInLobby.containsValue(Papel.TRAIDOR) ||
                    playersInLobby.containsValue(Papel.DETETIVE)) {
                randomizeRole();
            }
        }

        return papelSorteado;
    }


}
