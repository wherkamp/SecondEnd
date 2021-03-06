package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.requirements.RWRequirement;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Objects;

public class ResourceWorld {
    private final String name;
    private final int resetTime;
    private final List<RWRequirement> requirementsList;
    private final long seed;
    private final int cost;
    private final boolean regenOnStart;
    private final boolean generateStructures;
    private final WorldType worldType;
    private final World.Environment environment;
    private final String generator;
    private long lastReset = System.currentTimeMillis();
    private final ConfigurationSection propertiesSection;

    public ResourceWorld(String name, int resetTime, List<RWRequirement> requirementsList, long seed, int cost, boolean regenOnStart, boolean generateStructures, WorldType worldType, World.Environment environment, String generator, ConfigurationSection propertiesSection) {
        this.name = name;
        this.resetTime = resetTime;
        this.requirementsList = requirementsList;
        this.seed = seed;
        this.cost = cost;
        this.regenOnStart = regenOnStart;
        this.generateStructures = generateStructures;
        this.worldType = worldType;
        this.environment = environment;
        this.generator = generator;
        this.propertiesSection = propertiesSection;
    }

    public String getName() {
        return name;
    }

    public int getResetTime() {
        return resetTime;
    }

    public List<RWRequirement> getRequirementsList() {
        return requirementsList;
    }

    public long getSeed() {
        return seed;
    }

    public int getCost() {
        return cost;
    }

    public boolean isRegenOnStart() {
        return regenOnStart;
    }

    public boolean isGenerateStructures() {
        return generateStructures;
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public World.Environment getEnvironment() {
        return environment;
    }

    public String getGenerator() {
        return generator;
    }

    public ConfigurationSection getPropertiesSection() {
        return propertiesSection;
    }

    public String getID() {
        return propertiesSection.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceWorld that = (ResourceWorld) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "ResourceWorld{" +
                "name='" + name + '\'' +
                ", resetTime=" + resetTime +
                ", requirementsList=" + requirementsList +
                ", seed=" + seed +
                ", cost=" + cost +
                ", regenOnStart=" + regenOnStart +
                ", generateStructures=" + generateStructures +
                ", worldType=" + worldType +
                ", environment=" + environment +
                ", generator='" + generator + '\'' +
                '}';
    }

    public long getLastReset() {
        return lastReset;
    }

    public void setLastReset(long lastReset) {
        this.lastReset = lastReset;
    }

    public WorldCreator createCreator() {
        WorldCreator worldCreator = WorldCreator.name(getID()).seed(seed).environment(environment).type(worldType).generateStructures(generateStructures);
        if (generator != null) {
            worldCreator = worldCreator.generator(generator);
        }
        return worldCreator;
    }
}
