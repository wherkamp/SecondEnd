package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.events.ResourceWorldResetEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class WorldRunnable implements Runnable {
    private ResourceWorlds resourceWorlds;

    public WorldRunnable(ResourceWorlds resourceWorlds) {
        this.resourceWorlds = resourceWorlds;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        for (ResourceWorld resourceWorld : resourceWorlds.getResourceWorlds()) {
            long lastReset = resourceWorld.getLastReset();
            long resetTime = TimeUnit.SECONDS.toMillis(resourceWorld.getResetTime());
            if (currentTime >= (lastReset + resetTime)) {
                ResourceWorldResetEvent resourceWorldResetEvent = new ResourceWorldResetEvent(resourceWorld);
                Bukkit.getServer().getPluginManager().callEvent(resourceWorldResetEvent);
                deleteWorld(resourceWorld);
                createWorld(resourceWorld);
            }
        }
    }

    public void createWorld(ResourceWorld world) {
        WorldCreator worldCreator = world.createCreator();
        resourceWorlds.getRwWorldManager().createWorld(worldCreator);
    }

    public void deleteWorld(ResourceWorld world) {
        if (resourceWorlds.getRwWorldManager().worldExists(world.getName())) {
            Bukkit.broadcastMessage(Locale.RESETTING_END_ANNOUNCEMENT.color());
            World bWorld = resourceWorlds.getRwWorldManager().getWorld(world.getName());
            String returnWorldName = resourceWorlds.getConfig().getString("return-world", "world");
            if (returnWorldName == null) return;
            World returnWorld = Bukkit.getWorld(returnWorldName);
            if (returnWorld == null) return;
            for (Player player : bWorld.getPlayers()) player.teleport(returnWorld.getSpawnLocation());
            resourceWorlds.getRwWorldManager().deleteWorld(world.getName());
        }
    }
}