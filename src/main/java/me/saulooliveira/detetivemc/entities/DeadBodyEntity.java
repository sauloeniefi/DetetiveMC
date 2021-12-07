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


        Property textures = (Property) craftPlayer.getProfile().getProperties().get("textures").toArray()[0];
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());
        gameProfile.getProperties().put("textures", new Property("textures", textures.getValue(), textures.getSignature()));


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
