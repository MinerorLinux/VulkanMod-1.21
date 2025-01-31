package net.vulkanmod.render.chunk.buffer;

import net.vulkanmod.render.chunk.ChunkArea;
import net.vulkanmod.render.vertex.TerrainRenderType;

public class DrawParameters {

    private final ChunkArea chunkArea;
    private final TerrainRenderType renderType;

    public DrawParameters(ChunkArea chunkArea, TerrainRenderType renderType) {
        this.chunkArea = chunkArea;
        this.renderType = renderType;
    }

    public void reset(ChunkArea chunkArea, TerrainRenderType renderType) {
        // Reset draw parameters for the given chunk area and render type
        // Add implementation details based on your requirements
    }
}
