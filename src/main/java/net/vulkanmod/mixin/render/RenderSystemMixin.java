package net.vulkanmod.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.vulkanmod.gl.GlTexture;
import net.vulkanmod.vulkan.Renderer;
import net.vulkanmod.vulkan.VRenderSystem;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

import static com.mojang.blaze3d.systems.RenderSystem.*;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    @Shadow private static Matrix4f projectionMatrix;
    @Shadow private static Matrix4f savedProjectionMatrix;
    @Shadow @Final private static Matrix4fStack modelViewStack;
    @Shadow private static Matrix4f modelViewMatrix;
    @Shadow private static Matrix4f textureMatrix;

    @Shadow @Final private static float[] shaderColor;
    @Shadow @Final private static Vector3f[] shaderLightDirections;
    @Shadow @Final private static float[] shaderFogColor;

    @Shadow private static @Nullable Thread renderThread;

    @Shadow public static VertexSorting vertexSorting;
    @Shadow private static VertexSorting savedVertexSorting;

    @Shadow
    public static void assertOnRenderThread() {
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void initRenderer(int debugVerbosity, boolean debugSync) {
        VRenderSystem.initRenderer();

        renderThread.setPriority(Thread.NORM_PRIORITY + 2);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void setupDefaultState(int x, int y, int width, int height) { }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enableColorLogicOp() {
        assertOnRenderThread();
        VRenderSystem.enableColorLogicOp();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disableColorLogicOp() {
        assertOnRenderThread();
        VRenderSystem.disableColorLogicOp();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite
    public static void logicOp(GlStateManager.LogicOp op) {
        assertOnRenderThread();
        VRenderSystem.logicOp(op);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void activeTexture(int texture) {
        GlTexture.activeTexture(texture);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void glGenBuffers(Consumer<Integer> consumer) {}

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void glGenVertexArrays(Consumer<Integer> consumer) {}

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static int maxSupportedTextureSize() {
        return VRenderSystem.maxSupportedTextureSize();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void clear(int mask, boolean getError) {
        VRenderSystem.clear(mask);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void clearColor(float r, float g, float b, float a) {
        VRenderSystem.setClearColor(r, g, b, a);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void clearDepth(double d) {
        VRenderSystem.clearDepth(d);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enableScissor(int x, int y, int width, int height) {
        Renderer.setScissor(x, y, width, height);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disableScissor() {
        Renderer.resetScissor();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disableDepthTest() {
        assertOnRenderThread();
        //GlStateManager._disableDepthTest();
        VRenderSystem.disableDepthTest();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enableDepthTest() {
        assertOnRenderThreadOrInit();
        VRenderSystem.enableDepthTest();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void depthFunc(int i) {
        assertOnRenderThread();
        VRenderSystem.depthFunc(i);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void depthMask(boolean b) {
        assertOnRenderThread();
        VRenderSystem.depthMask(b);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        VRenderSystem.colorMask(red, green, blue, alpha);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void blendEquation(int i) {
        assertOnRenderThread();
        //TODO
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enableBlend() {
        VRenderSystem.enableBlend();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disableBlend() {
        VRenderSystem.disableBlend();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void blendFunc(GlStateManager.SourceFactor sourceFactor, GlStateManager.DestFactor destFactor) {
        VRenderSystem.blendFunc(sourceFactor, destFactor);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void blendFunc(int srcFactor, int dstFactor) {
        VRenderSystem.blendFunc(srcFactor, dstFactor);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void blendFuncSeparate(GlStateManager.SourceFactor p_69417_, GlStateManager.DestFactor p_69418_, GlStateManager.SourceFactor p_69419_, GlStateManager.DestFactor p_69420_) {
        VRenderSystem.blendFuncSeparate(p_69417_, p_69418_, p_69419_, p_69420_);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void blendFuncSeparate(int srcFactorRGB, int dstFactorRGB, int srcFactorAlpha, int dstFactorAlpha) {
        VRenderSystem.blendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enableCull() {
        assertOnRenderThread();
        VRenderSystem.enableCull();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disableCull() {
        assertOnRenderThread();
        VRenderSystem.disableCull();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void polygonMode(final int i, final int j) {
        assertOnRenderThread();
        VRenderSystem.setPolygonModeGL(i);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void enablePolygonOffset() {
        assertOnRenderThread();
        VRenderSystem.enablePolygonOffset();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void disablePolygonOffset() {
        assertOnRenderThread();
        VRenderSystem.disablePolygonOffset();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void polygonOffset(float p_69864_, float p_69865_) {
        assertOnRenderThread();
        VRenderSystem.polygonOffset(p_69864_, p_69865_);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void setShaderLights(Vector3f dir0, Vector3f dir1) {
        shaderLightDirections[0] = dir0;
        shaderLightDirections[1] = dir1;

        VRenderSystem.lightDirection0.buffer.putFloat(0, dir0.x());
        VRenderSystem.lightDirection0.buffer.putFloat(4, dir0.y());
        VRenderSystem.lightDirection0.buffer.putFloat(8, dir0.z());

        VRenderSystem.lightDirection1.buffer.putFloat(0, dir1.x());
        VRenderSystem.lightDirection1.buffer.putFloat(4, dir1.y());
        VRenderSystem.lightDirection1.buffer.putFloat(8, dir1.z());
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    private static void _setShaderColor(float r, float g, float b, float a) {
        shaderColor[0] = r;
        shaderColor[1] = g;
        shaderColor[2] = b;
        shaderColor[3] = a;

        VRenderSystem.setShaderColor(r, g, b, a);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void setShaderFogColor(float f, float g, float h, float i) {
        shaderFogColor[0] = f;
        shaderFogColor[1] = g;
        shaderFogColor[2] = h;
        shaderFogColor[3] = i;

        VRenderSystem.setShaderFogColor(f, g, h, i);
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void setProjectionMatrix(Matrix4f projectionMatrix, VertexSorting vertexSorting) {
        Matrix4f matrix4f = new Matrix4f(projectionMatrix);
        if (!isOnRenderThread()) {
            recordRenderCall(() -> {
                RenderSystemMixin.projectionMatrix = matrix4f;
                RenderSystem.vertexSorting = vertexSorting;

                VRenderSystem.applyProjectionMatrix(matrix4f);
                VRenderSystem.calculateMVP();
            });
        } else {
            RenderSystemMixin.projectionMatrix = matrix4f;
            RenderSystem.vertexSorting = vertexSorting;

            VRenderSystem.applyProjectionMatrix(matrix4f);
            VRenderSystem.calculateMVP();
        }

    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void setTextureMatrix(Matrix4f matrix4f) {
        Matrix4f matrix4f2 = new Matrix4f(matrix4f);
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
                textureMatrix = matrix4f2;
                VRenderSystem.setTextureMatrix(matrix4f);
            });
        } else {
            textureMatrix = matrix4f2;
            VRenderSystem.setTextureMatrix(matrix4f);
        }
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void resetTextureMatrix() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> textureMatrix.identity());
        } else {
            textureMatrix.identity();
            VRenderSystem.setTextureMatrix(textureMatrix);
        }
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void applyModelViewMatrix() {
        Matrix4f matrix4f = new Matrix4f(modelViewStack);
        if (!isOnRenderThread()) {
            recordRenderCall(() -> {
                modelViewMatrix = matrix4f;
                //Vulkan
                VRenderSystem.applyModelViewMatrix(matrix4f);
                VRenderSystem.calculateMVP();
            });
        } else {
            modelViewMatrix = matrix4f;

            VRenderSystem.applyModelViewMatrix(matrix4f);
            VRenderSystem.calculateMVP();
        }

    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    private static void _restoreProjectionMatrix() {
        projectionMatrix = savedProjectionMatrix;
        vertexSorting = savedVertexSorting;

        VRenderSystem.applyProjectionMatrix(projectionMatrix);
        VRenderSystem.calculateMVP();
    }

    /**
     * @reason Custom implementation for Vulkan
     * @author
     */
    @Overwrite(remap = false)
    public static void texParameter(int target, int pname, int param) {
        GlTexture.texParameteri(target, pname, param);
    }
}
