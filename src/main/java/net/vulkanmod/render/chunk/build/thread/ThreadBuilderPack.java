package net.vulkanmod.render.chunk.build.thread;

import net.vulkanmod.render.vertex.TerrainBuilder;
import net.vulkanmod.render.vertex.TerrainRenderType;

import java.util.function.Function;
import java.util.EnumMap;
import java.util.Map;
import java.util.Arrays;

public class ThreadBuilderPack {
    private final Function<TerrainRenderType, TerrainBuilder> terrainBuilderConstructor;
    private final Map<TerrainRenderType, TerrainBuilder> builders;

    public ThreadBuilderPack(Function<TerrainRenderType, TerrainBuilder> terrainBuilderConstructor) {
        this.terrainBuilderConstructor = terrainBuilderConstructor;
        var map = new EnumMap<TerrainRenderType, TerrainBuilder>(TerrainRenderType.class);
        Arrays.stream(TerrainRenderType.values()).forEach(
                terrainRenderType -> map.put(terrainRenderType,
                        terrainBuilderConstructor.apply(terrainRenderType))
        );
        builders = map;
    }

    public ThreadBuilderPack() {
        TerrainRenderType defaultRenderType = TerrainRenderType.DEFAULT;
        TerrainBuilder defaultBuilder = new TerrainBuilder(defaultRenderType.getBufferSize());
        this.terrainBuilderConstructor = renderType -> new TerrainBuilder(TerrainRenderType.getRenderType(renderType).bufferSize());
        var map = new EnumMap<TerrainRenderType, TerrainBuilder>(TerrainRenderType.class);
        Arrays.stream(TerrainRenderType.values()).forEach(
                terrainRenderType -> map.put(terrainRenderType,
                        terrainBuilderConstructor.apply(terrainRenderType))
        );
        builders = map;
    }

    public TerrainBuilder builder(TerrainRenderType renderType) {
        return this.builders.get(renderType);
    }

    public void clearAll() {
        this.builders.values().forEach(TerrainBuilder::clear);
    }

    public void initDefaultTerrainBuilder() {
        TerrainRenderType defaultRenderType = TerrainRenderType.DEFAULT;
        TerrainBuilder defaultBuilder = new TerrainBuilder(defaultRenderType.getBufferSize());
        builders.put(defaultRenderType, defaultBuilder);
    }
}
