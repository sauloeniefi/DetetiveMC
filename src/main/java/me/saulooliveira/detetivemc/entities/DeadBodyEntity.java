package me.saulooliveira.detetivemc.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.saulooliveira.detetivemc.DetetiveMC;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class DeadBodyEntity {

    public static void execute(Player player) {

        EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());

        try {
            Property textures = (Property) craftPlayer.getProfile().getProperties().get("textures").toArray()[0];
            gameProfile.getProperties().put("textures", new Property("textures", textures.getValue(), textures.getSignature()));
        } catch (Exception e) {
            String signature = "pXKGKqY49hu3JPuLNVOYMam3MroCg3dCKjFvU2b/6EuNvtU4pwngCQMhAEDU0wXbk5MBY+tCqIEgeWFCpk6qA/BgR1fuWwF8Kx/UR8l0XFrCNhv0S2sTyM+RJlYB+Tigy6zJKp5WWWLdsOaFmBA9JYtFcUHE5BXYtkbUn9sFp1+Vsea1TFXpM9i5600d8eVng3y+tVxgSj3sZ7WYzdgqPXfyaDIReZHDG6PMuNPERhNVjgekbusI05WOFJ5e6tF83S6HioUKN4nqRk9S5BFxuZWzbf/qVuOkOpa91xPz7wbY0m4MxP5xVyA6Rdoef3qYDYzQSeCSo90oTC8lO0faif5bMiplL+7rA7kyBdBfAfG3FxHhOY1IZLgcZER88k1aIM5BWhVu0S7hu1Kv8K9jEE3oVWK6YNilADHtUZF/yehehgkR2JfPfwR3poIqHTrT0YeAxYdAtAskCsLXc7dphidwN3FUF/uqg5MgL7iECpfKyytackvE4PyWnD+SMiG367W4R3h2r38PV3EpvLxQez9y7LqWH2LqRvBMFAYo7o6tmdtiys4nEhMuRYxCz1P/yN5Mu0FDG3OzjkBTGCVK8U2bSEIa1ZTrfXKbDpZKhhlThWDgfEUTkuY+aOZxbmtAKAelq9DMw3rJsIvBmxwLvP1XJXiG4/CnxGkeIr2/oH0=";
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTAzMDUzNDI2MiwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjkwNjk2ZWJjNzRjZTdhOTAwZWM4YWJlZWMwZGMxY2NiMzUzNGMxYjhiYTZjYmQ5ZTgzYzVjZDdmMzgxZmI0OCIKICAgIH0KICB9Cn0=";
            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
        }

        EntityPlayer deadBody = new EntityPlayer(
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) player.getWorld()).getHandle(),
                gameProfile);

        deadBody.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());


        Location bed = player.getLocation().add(1, 0, 0);
        deadBody.e(new BlockPosition(bed.getX(), bed.getY(), bed.getZ()));

        ScoreboardTeam team = new ScoreboardTeam(
                ((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), player.getName());
        team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.b);
        PacketPlayOutScoreboardTeam score1 = PacketPlayOutScoreboardTeam.a(team);
        PacketPlayOutScoreboardTeam score2 = PacketPlayOutScoreboardTeam.a(team, true);
        PacketPlayOutScoreboardTeam score3 = PacketPlayOutScoreboardTeam.a(
                team, deadBody.getName(), PacketPlayOutScoreboardTeam.a.a);


        deadBody.setPose(EntityPose.c);
        DataWatcher watcher = deadBody.getDataWatcher();
        byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        watcher.set(DataWatcherRegistry.a.a(17), b);


        PacketPlayOutEntity.PacketPlayOutRelEntityMove move = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
                deadBody.getId(), (byte) 0, (byte) ((player.getLocation().getY() - 1.7 - player.getLocation().getY()) * 32),
                (byte) 0, false);


        for (Player on : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) on).getHandle().b;

            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, deadBody));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(deadBody));

            connection.sendPacket(score1);
            connection.sendPacket(score2);
            connection.sendPacket(score3);

            connection.sendPacket(new PacketPlayOutEntityMetadata(deadBody.getId(), watcher, true));
            connection.sendPacket(move);

            new BukkitRunnable() {
                public void run() {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, deadBody));
                }
            }.runTaskAsynchronously(DetetiveMC.getPlugin(DetetiveMC.class));
        }
    }

}
