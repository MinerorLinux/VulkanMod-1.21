package net.vulkanmod.vulkan.memory;

import net.vulkanmod.vulkan.device.DeviceManager;
import net.vulkanmod.vulkan.memory.MemoryType; // Add this import

import static net.vulkanmod.vulkan.util.VUtil.align;
import static org.lwjgl.vulkan.VK10.VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT;

public class UniformBuffer extends Buffer {

    private final static int minOffset = (int) DeviceManager.deviceProperties.limits().minUniformBufferOffsetAlignment();

    public static int getAlignedSize(int uploadSize) {
        return align(uploadSize, minOffset);
    }
    
    private MemoryType memoryType; // Change the type to MemoryType
    
    public UniformBuffer(int size, MemoryType memoryType) {
        super(VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT, memoryType);
        this.memoryType = memoryType;
        this.createBuffer(size);
    }
    
    public void checkCapacity(int size) {
        if (size > this.bufferSize - this.usedBytes) {
            resizeBuffer((this.bufferSize + size) * 2);
        }
    }
    
    public void updateOffset(int alignedSize) {
        usedBytes += alignedSize;
    }
    
    private void resizeBuffer(int newSize) {
        MemoryManager.getInstance().addToFreeable(this);
        createBuffer(newSize);
    }
    
    public long getPointer() {
        return this.data.get(0) + usedBytes;
    }
    
    public boolean isMappable() {
        return this.memoryType == MemoryType.HOST_VISIBLE;
    }
}
