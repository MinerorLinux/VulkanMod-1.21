package net.vulkanmod.mixin.debug;

import com.mojang.blaze3d.platform.GlUtil;
import net.vulkanmod.vulkan.SystemInfo;
import net.vulkanmod.vulkan.Vulkan;
import net.vulkanmod.vulkan.device.Device;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GlUtil.class)
public class GlDebugInfoM {

    /**
     * @reason Replace OpenGL vendor info with Vulkan vendor info
     * @author
     */
    @Overwrite
    public static String getVendor() {
        return Vulkan.getDevice() != null ? Vulkan.getDevice().vendorIdString : "n/a";
    }

    /**
     * @reason Replace OpenGL renderer info with Vulkan renderer info
     * @author
     */
    @Overwrite
    public static String getRenderer() {
        return Vulkan.getDevice() != null ? Vulkan.getDevice().deviceName : "n/a";
    }

    /**
     * @reason Replace OpenGL version info with Vulkan driver version info
     * @author
     */
    @Overwrite
    public static String getOpenGLVersion() {
        return Vulkan.getDevice() != null ? Vulkan.getDevice().driverVersion : "n/a";
    }

    /**
     * @reason Provide CPU information from SystemInfo
     * @author
     */
    @Overwrite
    public static String getCpuInfo() {
        return SystemInfo.cpuInfo;
    }
}
