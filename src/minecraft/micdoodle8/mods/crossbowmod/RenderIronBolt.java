package micdoodle8.mods.crossbowmod;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import net.minecraft.src.*;
import net.minecraft.src.*;

public class RenderIronBolt extends Render
{
    public RenderIronBolt()
    {
    }

    public void renderArrow(EntityIronBolt entityironbolt, double d, double d1, double d2, float f, float f1)
    {
        if (entityironbolt.prevRotationYaw == 0.0F && entityironbolt.prevRotationPitch == 0.0F)
        {
            return;
        }

        loadTexture("/Mic'sMods/CrossbowMod/items/ironbolt.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(entityironbolt.prevRotationYaw + (entityironbolt.rotationYaw - entityironbolt.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entityironbolt.prevRotationPitch + (entityironbolt.rotationPitch - entityironbolt.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float)(0 + i * 10) / 32F;
        float f5 = (float)(5 + i * 10) / 32F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(5 + i * 10) / 32F;
        float f9 = (float)(10 + i * 10) / 32F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f11 = (float) entityironbolt.arrowShake - f1;

        if (f11 > 0.0F)
        {
            float f12 = -MathHelper.sin(f11 * 3F) * f11;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-4D, -2D, -2D, f6, f8);
        tessellator.addVertexWithUV(-4D, -2D, 2D, f7, f8);
        tessellator.addVertexWithUV(-4D, 2D, 2D, f7, f9);
        tessellator.addVertexWithUV(-4D, 2D, -2D, f6, f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-4D, 2D, -2D, f6, f8);
        tessellator.addVertexWithUV(-4D, 2D, 2D, f7, f8);
        tessellator.addVertexWithUV(-4D, -2D, 2D, f7, f9);
        tessellator.addVertexWithUV(-4D, -2D, -2D, f6, f9);
        tessellator.draw();

        for (int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-5D, -2D, 0.0D, f2, f4);
            tessellator.addVertexWithUV(5D, -2D, 0.0D, f3, f4);
            tessellator.addVertexWithUV(5D, 2D, 0.0D, f3, f5);
            tessellator.addVertexWithUV(-5D, 2D, 0.0D, f2, f5);
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        renderArrow((EntityIronBolt)entity, d, d1, d2, f, f1);
    }
}
