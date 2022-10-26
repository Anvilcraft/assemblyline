package assemblyline.client.model;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelHelper {
    private static int gTexWidth = 64;
    private static int gTexHeight = 32;
    private static int sTexWidth = 64;
    private static int sTexHeight = 32;
    private static int texOffsetX = 0;
    private static int texOffsetY = 0;
    private static float texScale = 16.0f;
    private static boolean clip = false;

    private static void drawQuadRaw(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, ForgeDirection side) {
        float yMax;
        float xMax;
        GL11.glBegin((int)7);
        float quadWidth = 1.0f;
        float quadHeight = 1.0f;
        float subWidth = (float)sTexWidth / (float)gTexWidth;
        float subHeight = (float)sTexHeight / (float)gTexHeight;
        float xMin = (float)texOffsetX / (float)sTexWidth * subWidth;
        float yMin = (float)texOffsetY / (float)sTexHeight * subHeight;
        float subSqX = 0.0f;
        float subSqY = 0.0f;
        float subSqWidth = 0.25f * ((float)sTexWidth / (float)gTexWidth);
        float subSqHeight = 0.5f * ((float)sTexHeight / (float)gTexHeight);
        switch (side) {
            case UP: {
                subSqX = 2.0f * subSqWidth;
                subSqY = 0.0f;
                quadWidth = (float)Math.abs(v2.xCoord - v1.xCoord);
                quadHeight = (float)Math.abs(v4.zCoord - v1.zCoord);
                break;
            }
            case DOWN: {
                subSqX = 1.0f * subSqWidth;
                subSqY = 0.0f;
                quadWidth = (float)Math.abs(v2.xCoord - v1.xCoord);
                quadHeight = (float)Math.abs(v4.zCoord - v1.zCoord);
                break;
            }
            case EAST: {
                subSqX = 0.0f;
                subSqY = subSqHeight;
                quadWidth = (float)Math.abs(v2.zCoord - v1.zCoord);
                quadHeight = (float)Math.abs(v4.yCoord - v1.yCoord);
                break;
            }
            case WEST: {
                subSqX = 2.0f * subSqWidth;
                subSqY = subSqHeight;
                quadWidth = (float)Math.abs(v2.zCoord - v1.zCoord);
                quadHeight = (float)Math.abs(v4.yCoord - v1.yCoord);
                break;
            }
            case SOUTH: {
                subSqX = subSqWidth;
                subSqY = subSqHeight;
                quadWidth = (float)Math.abs(v2.xCoord - v1.xCoord);
                quadHeight = (float)Math.abs(v4.yCoord - v1.yCoord);
                break;
            }
            case NORTH: {
                subSqX = 3.0f * subSqWidth;
                subSqY = subSqHeight;
                quadWidth = (float)Math.abs(v2.xCoord - v1.xCoord);
                quadHeight = (float)Math.abs(v4.yCoord - v1.yCoord);
                break;
            }
        }
        xMin += subSqX;
        yMin += subSqY;
        if (clip) {
            xMax = (xMin += (1.0f - quadWidth) * subSqWidth) + subSqWidth * quadWidth;
            yMax = (yMin += (1.0f - quadHeight) * subSqHeight) + subSqHeight * quadHeight;
        } else {
            xMax = xMin + subSqWidth;
            yMax = yMin + subSqHeight;
        }
        GL11.glTexCoord2f((float)xMin, (float)yMin);
        GL11.glVertex3d((double)v1.xCoord, (double)v1.yCoord, (double)v1.zCoord);
        GL11.glTexCoord2f((float)xMax, (float)yMin);
        GL11.glVertex3d((double)v2.xCoord, (double)v2.yCoord, (double)v2.zCoord);
        GL11.glTexCoord2f((float)xMax, (float)yMax);
        GL11.glVertex3d((double)v3.xCoord, (double)v3.yCoord, (double)v3.zCoord);
        GL11.glTexCoord2f((float)xMin, (float)yMax);
        GL11.glVertex3d((double)v4.xCoord, (double)v4.yCoord, (double)v4.zCoord);
        GL11.glEnd();
    }

    private static void drawCuboidRaw(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, Vec3 v5, Vec3 v6, Vec3 v7, Vec3 v8) {
        ModelHelper.drawQuadRaw(v1, v2, v3, v4, ForgeDirection.UP);
        ModelHelper.drawQuadRaw(v7, v6, v3, v2, ForgeDirection.EAST);
        ModelHelper.drawQuadRaw(v5, v6, v7, v8, ForgeDirection.DOWN);
        ModelHelper.drawQuadRaw(v5, v8, v1, v4, ForgeDirection.WEST);
        ModelHelper.drawQuadRaw(v6, v5, v4, v3, ForgeDirection.NORTH);
        ModelHelper.drawQuadRaw(v8, v7, v2, v1, ForgeDirection.SOUTH);
    }

    public static void drawCuboid(float xOffset, float yOffset, float zOffset, float xSize, float ySize, float zSize) {
        float x = xOffset;
        float y = yOffset;
        float z = zOffset;
        float x2 = x + xSize;
        float y2 = y + ySize;
        float z2 = z + zSize;
        Vec3 v1 = Vec3.createVectorHelper((double)x, (double)y2, (double)z2);
        Vec3 v2 = Vec3.createVectorHelper((double)x2, (double)y2, (double)z2);
        Vec3 v3 = Vec3.createVectorHelper((double)x2, (double)y2, (double)z);
        Vec3 v4 = Vec3.createVectorHelper((double)x, (double)y2, (double)z);
        Vec3 v5 = Vec3.createVectorHelper((double)x, (double)y, (double)z);
        Vec3 v6 = Vec3.createVectorHelper((double)x2, (double)y, (double)z);
        Vec3 v7 = Vec3.createVectorHelper((double)x2, (double)y, (double)z2);
        Vec3 v8 = Vec3.createVectorHelper((double)x, (double)y, (double)z2);
        ModelHelper.drawCuboidRaw(v1, v2, v3, v4, v5, v6, v7, v8);
    }

    public static void setTextureOffset(int xOffset, int yOffset) {
        texOffsetX = xOffset;
        texOffsetY = yOffset;
    }

    public static void setGlobalTextureResolution(int width, int height) {
        gTexWidth = width;
        gTexHeight = height;
    }

    public static void setTextureSubResolution(int width, int height) {
        sTexWidth = width;
        sTexHeight = height;
    }

    public static void setTextureClip(boolean clip) {
        ModelHelper.clip = clip;
    }
}

