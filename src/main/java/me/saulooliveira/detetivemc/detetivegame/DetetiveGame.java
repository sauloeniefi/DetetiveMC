package me.saulooliveira.detetivemc.detetivegame;

import me.saulooliveira.detetivemc.enums.Papel;

import java.util.*;

public class DetetiveGame {

    public static final DetetiveGame INSTANCE = new DetetiveGame();

    public static DetetiveGame getInstance() {
        return INSTANCE;
    }

    private Map<UUID, Papel> playersInLobby = new HashMap<>();
    private Map<UUID, Boolean> playersVivos = new HashMap<>();

    public Map<UUID, Papel> getLobby() {
        return playersInLobby;
    }

    public Map<UUID, Boolean> getPlayersVivos() {
        return playersVivos;
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


    public boolean verificarCondicaoDeVitoriaInocentes() {
        return !playersInLobby.containsValue(Papel.TRAIDOR);
    }

    public boolean verificarCondicaoDeVitoriaTraidor() {
        return playersInLobby.size() == 1 && playersInLobby.containsValue(Papel.TRAIDOR);
    }


}
