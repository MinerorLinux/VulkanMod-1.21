package net.vulkanmod.mixin.render.vertex;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.renderer.ShaderInstance;
import net.vulkanmod.render.VBO;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VertexBuffer.class)
public class VertexBufferM {

    private VBO vbo;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constructor(VertexBuffer.Usage usage, CallbackInfo ci) {
        vbo = new VBO(usage);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glGenBuffers()I"))
    private int doNothing() {
        return 0;
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glGenVertexArrays()I"))
    private int doNothing2() {
        return 0;
    }

    /**
     * @reason Replace OpenGL buffer binding with Vulkan buffer binding
     * @author
     */
    @Overwrite
    public void bind() {}

    /**
     * @reason Replace OpenGL buffer unbinding with Vulkan buffer unbinding
     * @author
     */
    @Overwrite
    public static void unbind() {}

    /**
     * @reason Replace OpenGL buffer upload with Vulkan buffer upload
     * @author
     */
    @Overwrite
    public void upload(MeshData meshData) {
        vbo.upload(meshData);
    }

    /**
     * @reason Replace OpenGL index buffer upload with Vulkan index buffer upload
     * @author
     */
    @Overwrite
    public void uploadIndexBuffer(ByteBufferBuilder.Result result) {
        vbo.uploadIndexBuffer(result.byteBuffer());
    }

    /**
     * @reason Replace OpenGL drawing with Vulkan drawing
     * @author
     */
    @Overwrite
    public void drawWithShader(Matrix4f viewMatrix, Matrix4f projectionMatrix, ShaderInstance shader) {
        vbo.drawWithShader(viewMatrix, projectionMatrix, shader);
    }

    /**
     * @reason Replace OpenGL drawing with Vulkan drawing
     * @author
     */
    @Overwrite
    public void draw() {
        vbo.draw();
    }

    /**
     * @reason Replace OpenGL buffer closing with Vulkan buffer closing
     * @author
     */
    @Overwrite
    public void close() {
        vbo.close();
    }
}
