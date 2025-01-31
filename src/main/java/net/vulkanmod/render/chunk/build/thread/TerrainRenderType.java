package net.vulkanmod.render.chunk.build.thread;

public enum TerrainRenderType {
    DEFAULT;

    public int bufferSize() {
        return 1024; // Example buffer size, adjust as needed
    }
}
