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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(GlStateManager.class)
public class GlStateManagerM {

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _bindTexture(int i) {
        GlTexture.bindTexture(i);
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableBlend() {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.disableBlend();
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableBlend() {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.enableBlend();
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _blendFunc(int i, int j) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.blendFunc(i, j);

    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _blendFuncSeparate(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.blendFuncSeparate(i, j, k, l);

    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableScissorTest() {
        Renderer.resetScissor();
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableScissorTest() {}

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableCull() {
        VRenderSystem.enableCull();
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableCull() {
        VRenderSystem.disableCull();
    }

    /**
     * @author
     */
    @Redirect(method = "_viewport", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glViewport(IIII)V"), remap = false)
    private static void _viewport(int x, int y, int width, int height) {
        Renderer.setViewport(x, y, width, height);
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _scissorBox(int x, int y, int width, int height) {
        Renderer.setScissor(x, y, width, height);
    }

    //TODO
    /**
     * @author
     */
    @Overwrite(remap = false)
    public static int _getError() {
        return 0;
    }

    /**
     * @author
     */
    @Overwrite(remap = false)
    public static void _texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, @Nullable IntBuffer pixels) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlTexture.texImage2D(target, level, internalFormat, width, height, border, format, type, pixels != null ? MemoryUtil.memByteBuffer(pixels) : null);
    }

    /**
     * @reason Replace OpenGL texture sub-image functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texSubImage2D(int target, int level, int offsetX, int offsetY, int width, int height, int format, int type, long pixels) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlTexture.texSubImage2D(target, level, offsetX, offsetY, width, height, format, type, pixels);
    }

    /**
     * @reason Replace OpenGL active texture functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _activeTexture(int i) {
        GlTexture.activeTexture(i);
    }

    /**
     * @reason Replace OpenGL texture parameter functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texParameter(int i, int j, int k) {
        GlTexture.texParameteri(i, j, k);
    }

    /**
     * @reason Replace OpenGL texture parameter functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _texParameter(int i, int j, float k) {
        //TODO
    }

    /**
     * @reason Replace OpenGL texture level parameter functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int _getTexLevelParameter(int i, int j, int k) {
        return GlTexture.getTexLevelParameter(i, j, k);
    }

    /**
     * @reason Replace OpenGL pixel store functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _pixelStore(int pname, int param) {
        //Used during upload to set copy offsets
        RenderSystem.assertOnRenderThreadOrInit();
        GlTexture.pixelStoreI(pname, param);
    }

    /**
     * @reason Replace OpenGL texture generation functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int _genTexture() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlTexture.genTextureId();
    }

    /**
     * @reason Replace OpenGL texture deletion functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _deleteTexture(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlTexture.glDeleteTextures(i);
    }

    /**
     * @reason Replace OpenGL color mask functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.colorMask(red, green, blue, alpha);
    }

    /**
     * @reason Replace OpenGL depth function functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _depthFunc(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.depthFunc(i);
    }

    /**
     * @reason Replace OpenGL clear color functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clearColor(float f, float g, float h, float i) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.setClearColor(f, g, h, i);
    }

    /**
     * @reason Replace OpenGL clear depth functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clearDepth(double d) {}

    /**
     * @reason Replace OpenGL clear functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _clear(int mask, boolean bl) {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.clear(mask);
    }

    /**
     * @reason Replace OpenGL use program functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glUseProgram(int i) {}

    /**
     * @reason Replace OpenGL disable depth test functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableDepthTest() {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.disableDepthTest();
    }

    /**
     * @reason Replace OpenGL enable depth test functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _enableDepthTest() {
        RenderSystem.assertOnRenderThreadOrInit();
        VRenderSystem.enableDepthTest();
    }

    /**
     * @reason Replace OpenGL depth mask functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _depthMask(boolean bl) {
        RenderSystem.assertOnRenderThread();
        VRenderSystem.depthMask(bl);

    }

    /**
     * @reason Replace OpenGL framebuffer generation functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int glGenFramebuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlFramebuffer.genFramebufferId();
    }

    /**
     * @reason Replace OpenGL renderbuffer generation functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int glGenRenderbuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlRenderbuffer.genId();
    }

    /**
     * @reason Replace OpenGL bind framebuffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindFramebuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.bindFramebuffer(i, j);
    }

    /**
     * @reason Replace OpenGL framebuffer texture functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glFramebufferTexture2D(int i, int j, int k, int l, int m) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.framebufferTexture2D(i, j, k, l, m);
    }

    /**
     * @reason Replace OpenGL bind renderbuffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindRenderbuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlRenderbuffer.bindRenderbuffer(i, j);
    }

    /**
     * @reason Replace OpenGL framebuffer renderbuffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glFramebufferRenderbuffer(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlFramebuffer.framebufferRenderbuffer(i, j, k, l);
    }

    /**
     * @reason Replace OpenGL renderbuffer storage functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glRenderbufferStorage(int i, int j, int k, int l) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlRenderbuffer.renderbufferStorage(i, j, k, l);
    }

    /**
     * @reason Replace OpenGL framebuffer status check functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int glCheckFramebufferStatus(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlFramebuffer.glCheckFramebufferStatus(i);
    }

    /**
     * @reason Replace OpenGL buffer generation functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static int _glGenBuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlBuffer.glGenBuffers();
    }

    /**
     * @reason Replace OpenGL bind buffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBindBuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBindBuffer(i, j);
    }

    /**
     * @reason Replace OpenGL buffer data functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBufferData(int i, ByteBuffer byteBuffer, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBufferData(i, byteBuffer, j);
    }

    /**
     * @reason Replace OpenGL buffer data functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glBufferData(int i, long l, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glBufferData(i, l, j);
    }

    /**
     * @reason Replace OpenGL map buffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    @Nullable
    public static ByteBuffer _glMapBuffer(int i, int j) {
        RenderSystem.assertOnRenderThreadOrInit();
        return GlBuffer.glMapBuffer(i, j);
    }

    /**
     * @reason Replace OpenGL unmap buffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glUnmapBuffer(int i) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlBuffer.glUnmapBuffer(i);
    }

    /**
     * @reason Replace OpenGL delete buffer functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _glDeleteBuffers(int i) {
        RenderSystem.assertOnRenderThread();
        GlBuffer.glDeleteBuffers(i);
    }

    /**
     * @reason Replace OpenGL disable vertex attribute array functionality with Vulkan equivalent.
     * @author
     */
    @Overwrite(remap = false)
    public static void _disableVertexAttribArray(int i) {}
}
