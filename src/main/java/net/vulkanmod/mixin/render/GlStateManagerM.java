package net.vulkanmod.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.vulkanmod.gl.GlBuffer;
import net.vulkanmod.gl.GlFramebuffer;
import net.vulkanmod.gl.GlRenderbuffer;
import net.vulkanmod.gl.GlTexture;
import net.vulkanmod.vulkan.Renderer;
import net.vulkanmod.vulkan.VRenderSystem;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(GlStateManager.class)
public class GlStateManagerM {

    /**
     * @reason Replaces OpenGL texture binding with Vulkan texture binding.
     * @author
     */
    @Overwrite(remap = false)
    public static void _bindTexture(int i) {
        GlTexture.bindTexture(i);
    }

    /**
     * @reason Replaces OpenGL blend disable with Vulkan blend disable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableBlend() {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.disableBlend();
    }

    /**
     * @reason Replaces OpenGL blend enable with Vulkan blend enable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableBlend() {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.enableBlend();
    }

    /**
     * @reason Replaces OpenGL blend function with Vulkan blend function.
     * @author
     */
    @Overwrite(remap = false)
    public static void _blendFunc(int i, int j) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.blendFunc(i, j);

    }

    /**
     * @reason Replaces OpenGL blend function separate with Vulkan blend function separate.
     * @author
     */
    @Overwrite(remap = false)
    public static void _blendFuncSeparate(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.blendFuncSeparate(i, j, k, l);

    }

    /**
     * @reason Replaces OpenGL scissor test disable with Vulkan scissor test disable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableScissorTest() {
        Renderer.resetScissor();
    }

    /**
     * @reason Placeholder for enabling scissor test in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableScissorTest() {}

    /**
     * @reason Replaces OpenGL cull enable with Vulkan cull enable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableCull() {
        VRenderSystem.enableCull();
    }

    /**
     * @reason Replaces OpenGL cull disable with Vulkan cull disable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableCull() {
        VRenderSystem.disableCull();
    }

    /**
     * @reason Replaces OpenGL viewport setting with Vulkan viewport setting.
     * @author
     */
    @Overwrite(remap = false)
    public static void _viewport(int x, int y, int width, int height) {
        Renderer.setViewport(x, y, width, height);
    }

    /**
     * @reason Replaces OpenGL scissor box setting with Vulkan scissor box setting.
     * @author
     */
    @Overwrite(remap = false)
    public static void _scissorBox(int x, int y, int width, int height) {
        Renderer.setScissor(x, y, width, height);
    }

    /**
     * @reason Placeholder for getting error in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static int _getError() {
        return 0;
    }

    /**
     * @reason Replaces OpenGL texture image 2D with Vulkan texture image 2D.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, @Nullable IntBuffer pixels) {
        GlTexture.texImage2D(target, level, internalFormat, width, height, border, format, type, pixels != null ? MemoryUtil.memByteBuffer(pixels) : null);
    }

    /**
     * @reason Placeholder for texture sub-image 2D in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texSubImage2D(int target, int level, int offsetX, int offsetY, int width, int height, int format, int type, long pixels) {

    }

    /**
     * @reason Replaces OpenGL active texture with Vulkan active texture.
     * @author
     */
    @Overwrite(remap = false)
    public static void _activeTexture(int i) {
        GlTexture.activeTexture(i);
    }

    /**
     * @reason Replaces OpenGL texture parameter with Vulkan texture parameter.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texParameter(int i, int j, int k) {
        GlTexture.texParameteri(i, j, k);
    }

    /**
     * @reason Placeholder for texture parameter in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texParameter(int i, int j, float k) {
        //TODO
    }

    /**
     * @reason Replaces OpenGL texture level parameter with Vulkan texture level parameter.
     * @author
     */
    @Overwrite(remap = false)
    public static int _getTexLevelParameter(int i, int j, int k) {
        return GlTexture.getTexLevelParameter(i, j, k);
    }

    /**
     * @reason Placeholder for pixel store in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _pixelStore(int pname, int param) {
        //Used during upload to set copy offsets
    }

    /**
     * @reason Replaces OpenGL texture generation with Vulkan texture generation.
     * @author
     */
    @Overwrite(remap = false)
    public static int _genTexture() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlTexture.genTextureId();
    }

    /**
     * @reason Replaces OpenGL texture deletion with Vulkan texture deletion.
     * @author
     */
    @Overwrite(remap = false)
    public static void _deleteTexture(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlTexture.glDeleteTextures(i);
    }

    /**
     * @reason Replaces OpenGL color mask with Vulkan color mask.
     * @author
     */
    @Overwrite(remap = false)
    public static void _colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.colorMask(red, green, blue, alpha);
    }

    /**
     * @reason Replaces OpenGL depth function with Vulkan depth function.
     * @author
     */
    @Overwrite(remap = false)
    public static void _depthFunc(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.depthFunc(i);
    }

    /**
     * @reason Replaces OpenGL clear color with Vulkan clear color.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clearColor(float f, float g, float h, float i) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.setClearColor(f, g, h, i);
    }

    /**
     * @reason Placeholder for clear depth in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clearDepth(double d) {}

    /**
     * @reason Replaces OpenGL clear with Vulkan clear.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clear(int mask, boolean bl) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.clear(mask);
    }

    /**
     * @reason Placeholder for using program in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glUseProgram(int i) {}

    /**
     * @reason Replaces OpenGL depth test disable with Vulkan depth test disable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableDepthTest() {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.disableDepthTest();
    }

    /**
     * @reason Replaces OpenGL depth test enable with Vulkan depth test enable.
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableDepthTest() {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.enableDepthTest();
    }

    /**
     * @reason Replaces OpenGL depth mask with Vulkan depth mask.
     * @author
     */
    @Overwrite(remap = false)
    public static void _depthMask(boolean bl) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.depthMask(bl);

    }

    /**
     * @reason Replaces OpenGL framebuffer generation with Vulkan framebuffer generation.
     * @author
     */
    @Overwrite(remap = false)
    public static int glGenFramebuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlFramebuffer.genFramebufferId();
    }

    /**
     * @reason Replaces OpenGL renderbuffer generation with Vulkan renderbuffer generation.
     * @author
     */
    @Overwrite(remap = false)
    public static int glGenRenderbuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlRenderbuffer.genId();
    }

    /**
     * @reason Replaces OpenGL framebuffer binding with Vulkan framebuffer binding.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindFramebuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.bindFramebuffer(i, j);
    }

    /**
     * @reason Replaces OpenGL framebuffer texture 2D with Vulkan framebuffer texture 2D.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glFramebufferTexture2D(int i, int j, int k, int l, int m) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.framebufferTexture2D(i, j, k, l, m);
    }

    /**
     * @reason Replaces OpenGL renderbuffer binding with Vulkan renderbuffer binding.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindRenderbuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlRenderbuffer.bindRenderbuffer(i, j);
    }

    /**
     * @reason Replaces OpenGL framebuffer renderbuffer with Vulkan framebuffer renderbuffer.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glFramebufferRenderbuffer(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.framebufferRenderbuffer(i, j, k, l);
    }

    /**
     * @reason Replaces OpenGL renderbuffer storage with Vulkan renderbuffer storage.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glRenderbufferStorage(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlRenderbuffer.renderbufferStorage(i, j, k, l);
    }

    /**
     * @reason Replaces OpenGL framebuffer status check with Vulkan framebuffer status check.
     * @author
     */
    @Overwrite(remap = false)
    public static int glCheckFramebufferStatus(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlFramebuffer.glCheckFramebufferStatus(i);
    }

    /**
     * @reason Replaces OpenGL buffer generation with Vulkan buffer generation.
     * @author
     */
    @Overwrite(remap = false)
    public static int _glGenBuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlBuffer.glGenBuffers();
    }

    /**
     * @reason Replaces OpenGL buffer binding with Vulkan buffer binding.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindBuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBindBuffer(i, j);
    }

    /**
     * @reason Replaces OpenGL buffer data with Vulkan buffer data.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBufferData(int i, ByteBuffer byteBuffer, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBufferData(i, byteBuffer, j);
    }

    /**
     * @reason Replaces OpenGL buffer data with Vulkan buffer data.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBufferData(int i, long l, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBufferData(i, l, j);
    }

    /**
     * @reason Replaces OpenGL buffer mapping with Vulkan buffer mapping.
     * @author
     */
    @Overwrite(remap = false)
    @Nullable
    public static ByteBuffer _glMapBuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlBuffer.glMapBuffer(i, j);
    }

    /**
     * @reason Replaces OpenGL buffer unmapping with Vulkan buffer unmapping.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glUnmapBuffer(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glUnmapBuffer(i);
    }

    /**
     * @reason Replaces OpenGL buffer deletion with Vulkan buffer deletion.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glDeleteBuffers(int i) {
        RenderSystem.assertOnRenderThread();
        GlBuffer.glDeleteBuffers(i);
    }

    /**
     * @reason Placeholder for disabling vertex attribute array in Vulkan.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableVertexAttribArray(int i) {}
}
