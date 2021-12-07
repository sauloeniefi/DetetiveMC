package me.saulooliveira.detetivemc.detetivegame;

import me.saulooliveira.detetivemc.enums.Papel;

import java.util.*;

public class DetetiveGame {

    public static final DetetiveGame INSTANCE = new DetetiveGame();

    public static DetetiveGame getInstance() {
        return INSTANCE;
    }

    private Map<UUID, Papel> playersInLobby = new HashMap<>();

    public Map<UUID, Papel> getLobby() {
        return playersInLobby;
    }

    public Papel randomizeRole() {
        Random random = new Random();
        Papel papelSorteado = Papel.fromValue(random.nextInt(3) + 1);
        if (!playersInLobby.isEmpty()) {
            if (playersInLobby.containsValue(Papel.TRAIDOR) ||
                    playersInLobby.containsValue(Papel.DETETIVE)) {
                return Papel.SUSPEITO;
            }
        }

        return papelSorteado;
    }

    public List<UUID> listarPlayers() {
        List<UUID> players = new ArrayList();
        for (Map.Entry<UUID, Papel> entry : getLobby().entrySet()) {
            if (entry.getKey() != null) {
                players.add(entry.getKey());
            }
        }

        return players;
    }



}
