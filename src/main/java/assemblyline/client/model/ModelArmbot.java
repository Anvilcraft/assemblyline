package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelArmbot
extends ModelBase {
    ModelRenderer baseTop;
    ModelRenderer base;
    ModelRenderer armMountRight;
    ModelRenderer armMountLeft;
    ModelRenderer armLower;
    ModelRenderer armLower2;
    ModelRenderer armLower3;
    ModelRenderer armUpper;
    ModelRenderer baseRotation;
    ModelRenderer clampBody;
    ModelRenderer clampBody2;
    ModelRenderer clampClawLower;
    ModelRenderer clampClawLower2;
    ModelRenderer clampClawLower3;

    public ModelArmbot() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.baseTop = new ModelRenderer((ModelBase)this, 0, 94);
        this.baseTop.addBox(-6.0f, 0.0f, -6.0f, 12, 3, 12);
        this.baseTop.setRotationPoint(0.0f, 18.0f, 0.0f);
        this.baseTop.setTextureSize(64, 32);
        this.baseTop.mirror = true;
        this.setRotation(this.baseTop, 0.0f, 0.0f, 0.0f);
        this.base = new ModelRenderer((ModelBase)this, 0, 109);
        this.base.addBox(-8.0f, 0.0f, -8.0f, 16, 3, 16);
        this.base.setRotationPoint(0.0f, 21.0f, 0.0f);
        this.base.setTextureSize(64, 32);
        this.base.mirror = true;
        this.setRotation(this.base, 0.0f, 0.0f, 0.0f);
        this.armMountRight = new ModelRenderer((ModelBase)this, 24, 85);
        this.armMountRight.addBox(-3.8f, -5.0f, -2.0f, 4, 5, 4);
        this.armMountRight.setRotationPoint(0.0f, 17.0f, 0.0f);
        this.armMountRight.setTextureSize(128, 128);
        this.armMountRight.mirror = true;
        this.setRotation(this.armMountRight, 0.0f, 0.0f, 0.0f);
        this.armMountLeft = new ModelRenderer((ModelBase)this, 0, 85);
        this.armMountLeft.addBox(2.0f, -5.0f, -2.0f, 2, 5, 4);
        this.armMountLeft.setRotationPoint(0.0f, 17.0f, 0.0f);
        this.armMountLeft.setTextureSize(64, 32);
        this.armMountLeft.mirror = true;
        this.setRotation(this.armMountLeft, 0.0f, 0.0f, 0.0f);
        this.armLower = new ModelRenderer((ModelBase)this, 116, 0);
        this.armLower.addBox(0.3f, -15.0f, -1.5f, 2, 16, 4);
        this.armLower.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.armLower.setTextureSize(64, 32);
        this.armLower.mirror = true;
        this.setRotation(this.armLower, 0.5235988f, 0.0f, 0.0f);
        this.armLower2 = new ModelRenderer((ModelBase)this, 104, 0);
        this.armLower2.addBox(-2.3f, -15.0f, -1.5f, 2, 16, 4);
        this.armLower2.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.armLower2.setTextureSize(64, 32);
        this.armLower2.mirror = true;
        this.setRotation(this.armLower2, 0.5235988f, 0.0f, 0.0f);
        this.armLower3 = new ModelRenderer((ModelBase)this, 92, 0);
        this.armLower3.addBox(-1.0f, -14.0f, -2.0f, 2, 14, 4);
        this.armLower3.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.armLower3.setTextureSize(64, 32);
        this.armLower3.mirror = true;
        this.setRotation(this.armLower3, 0.5235988f, 0.0f, 0.0f);
        this.armUpper = new ModelRenderer((ModelBase)this, 0, 70);
        this.armUpper.addBox(-1.0f, -10.0f, -1.5f, 2, 12, 3);
        this.armUpper.setRotationPoint(0.0f, 2.0f, -7.0f);
        this.armUpper.setTextureSize(64, 32);
        this.armUpper.mirror = true;
        this.setRotation(this.armUpper, 2.513274f, 0.0f, 0.0f);
        this.baseRotation = new ModelRenderer((ModelBase)this, 0, 60);
        this.baseRotation.addBox(-4.5f, 0.0f, -4.5f, 9, 1, 9);
        this.baseRotation.setRotationPoint(0.0f, 17.0f, 0.0f);
        this.baseRotation.setTextureSize(64, 32);
        this.baseRotation.mirror = true;
        this.setRotation(this.baseRotation, 0.0f, 0.0f, 0.0f);
        this.clampBody = new ModelRenderer((ModelBase)this, 0, 7);
        this.clampBody.addBox(-1.5f, -12.0f, -2.5f, 3, 2, 5);
        this.clampBody.setRotationPoint(0.0f, 2.0f, -7.0f);
        this.clampBody.setTextureSize(64, 32);
        this.clampBody.mirror = true;
        this.setRotation(this.clampBody, 2.513274f, 0.0f, 0.0f);
        this.clampBody2 = new ModelRenderer((ModelBase)this, 0, 56);
        this.clampBody2.addBox(-1.0f, -14.0f, -1.0f, 2, 2, 2);
        this.clampBody2.setRotationPoint(0.0f, 2.0f, -7.0f);
        this.clampBody2.setTextureSize(64, 32);
        this.clampBody2.mirror = true;
        this.setRotation(this.clampBody2, 2.513274f, 0.0f, 0.0f);
        this.clampClawLower = new ModelRenderer((ModelBase)this, 0, 25);
        this.clampClawLower.addBox(-1.0f, -4.0f, -1.0f, 2, 5, 1);
        this.clampClawLower.setRotationPoint(0.0f, 13.0f, -15.0f);
        this.clampClawLower.setTextureSize(64, 32);
        this.clampClawLower.mirror = true;
        this.setRotation(this.clampClawLower, 2.9147f, 0.0f, 0.0f);
        this.clampClawLower2 = new ModelRenderer((ModelBase)this, 0, 31);
        this.clampClawLower2.addBox(-1.2f, -3.5f, 0.0f, 1, 6, 1);
        this.clampClawLower2.setRotationPoint(0.0f, 14.0f, -16.0f);
        this.clampClawLower2.setTextureSize(64, 32);
        this.clampClawLower2.mirror = true;
        this.setRotation(this.clampClawLower2, 2.897247f, 0.0f, 0.0f);
        this.clampClawLower3 = new ModelRenderer((ModelBase)this, 0, 0);
        this.clampClawLower3.addBox(0.2f, -3.5f, 0.0f, 1, 6, 1);
        this.clampClawLower3.setRotationPoint(0.0f, 14.0f, -16.0f);
        this.clampClawLower3.setTextureSize(64, 32);
        this.clampClawLower3.mirror = true;
        this.setRotation(this.clampClawLower3, 2.897247f, 0.0f, 0.0f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.render(f5, entity.rotationYaw, entity.rotationPitch);
    }

    public void render(float f5, float rotationYaw, float rotationPitch) {
        this.baseTop.render(f5);
        this.base.render(f5);
        GL11.glPushMatrix();
        GL11.glRotatef((float)rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
        this.armMountRight.render(f5);
        this.armMountLeft.render(f5);
        this.baseRotation.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef((float)rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)0.9f, (float)0.0f);
        GL11.glRotatef((float)(-rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)-0.9f, (float)0.0f);
        this.armLower.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.1f, (float)-0.35f);
        GL11.glRotatef((float)(-rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)-0.05f, (float)0.35f);
        this.armUpper.render(f5);
        this.clampBody.render(f5);
        this.clampBody2.render(f5);
        this.clampClawLower.render(f5);
        this.clampClawLower2.render(f5);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

