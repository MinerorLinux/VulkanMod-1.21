package net.vulkanmod.vulkan.framebuffer;

import net.vulkanmod.vulkan.memory.MemoryManager;

public interface FrameOp extends Runnable {
    void execute(MemoryManager memoryManager);

    @Override
    default void run() {
        execute(MemoryManager.getInstance());
    }
}
