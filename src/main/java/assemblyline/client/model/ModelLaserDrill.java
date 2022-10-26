package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLaserDrill
extends ModelBase {
    ModelRenderer Tip;
    ModelRenderer Upper_plating;
    ModelRenderer Middle_plating;
    ModelRenderer Shape3_1;
    ModelRenderer Shape3_2;
    ModelRenderer Shape3_3;
    ModelRenderer Shape3_4;
    ModelRenderer LowerPlating_1;
    ModelRenderer LowerPlating_2;
    ModelRenderer Bump_1;
    ModelRenderer Bump_2;
    ModelRenderer Bump_3;
    ModelRenderer Bump_4;
    ModelRenderer Cross;

    public ModelLaserDrill() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Tip = new ModelRenderer((ModelBase)this, 0, 8);
        this.Tip.addBox(-1.0f, 0.0f, -1.0f, 2, 16, 2);
        this.Tip.setRotationPoint(0.0f, 8.0f, 0.0f);
        this.Tip.setTextureSize(64, 32);
        this.Tip.mirror = true;
        this.setRotation(this.Tip, 0.0f, 0.0f, 0.0f);
        this.Upper_plating = new ModelRenderer((ModelBase)this, 0, 0);
        this.Upper_plating.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.Upper_plating.setRotationPoint(0.0f, 11.0f, 0.0f);
        this.Upper_plating.setTextureSize(64, 32);
        this.Upper_plating.mirror = true;
        this.setRotation(this.Upper_plating, 0.0f, 0.0f, 0.0f);
        this.Middle_plating = new ModelRenderer((ModelBase)this, 16, 0);
        this.Middle_plating.addBox(-3.0f, 0.0f, -3.0f, 6, 1, 6);
        this.Middle_plating.setRotationPoint(0.0f, 15.0f, 0.0f);
        this.Middle_plating.setTextureSize(64, 32);
        this.Middle_plating.mirror = true;
        this.setRotation(this.Middle_plating, 0.0f, 0.0f, 0.0f);
        this.Shape3_1 = new ModelRenderer((ModelBase)this, 8, 8);
        this.Shape3_1.addBox(2.0f, 0.0f, -1.0f, 1, 6, 2);
        this.Shape3_1.setRotationPoint(0.0f, 11.0f, 0.0f);
        this.Shape3_1.setTextureSize(64, 32);
        this.Shape3_1.mirror = true;
        this.setRotation(this.Shape3_1, 0.0f, -3.141593f, 0.0f);
        this.Shape3_2 = new ModelRenderer((ModelBase)this, 8, 8);
        this.Shape3_2.addBox(2.0f, 0.0f, -1.0f, 1, 6, 2);
        this.Shape3_2.setRotationPoint(0.0f, 11.0f, 0.0f);
        this.Shape3_2.setTextureSize(64, 32);
        this.Shape3_2.mirror = true;
        this.setRotation(this.Shape3_2, 0.0f, -1.570796f, 0.0f);
        this.Shape3_3 = new ModelRenderer((ModelBase)this, 8, 8);
        this.Shape3_3.addBox(2.0f, 0.0f, -1.0f, 1, 6, 2);
        this.Shape3_3.setRotationPoint(0.0f, 11.0f, 0.0f);
        this.Shape3_3.setTextureSize(64, 32);
        this.Shape3_3.mirror = true;
        this.setRotation(this.Shape3_3, 0.0f, 1.570796f, 0.0f);
        this.Shape3_4 = new ModelRenderer((ModelBase)this, 8, 8);
        this.Shape3_4.addBox(2.0f, 0.0f, -1.0f, 1, 6, 2);
        this.Shape3_4.setRotationPoint(0.0f, 11.0f, 0.0f);
        this.Shape3_4.setTextureSize(64, 32);
        this.Shape3_4.mirror = true;
        this.setRotation(this.Shape3_4, 0.0f, 0.0f, 0.0f);
        this.LowerPlating_1 = new ModelRenderer((ModelBase)this, 40, 0);
        this.LowerPlating_1.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.LowerPlating_1.setRotationPoint(0.0f, 18.0f, 0.0f);
        this.LowerPlating_1.setTextureSize(64, 32);
        this.LowerPlating_1.mirror = true;
        this.setRotation(this.LowerPlating_1, 0.0f, 0.0f, 0.0f);
        this.LowerPlating_2 = new ModelRenderer((ModelBase)this, 40, 0);
        this.LowerPlating_2.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.LowerPlating_2.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.LowerPlating_2.setTextureSize(64, 32);
        this.LowerPlating_2.mirror = true;
        this.setRotation(this.LowerPlating_2, 0.0f, 0.0f, 0.0f);
        this.Bump_1 = new ModelRenderer((ModelBase)this, 56, 0);
        this.Bump_1.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.Bump_1.setRotationPoint(0.0f, 12.0f, 3.0f);
        this.Bump_1.setTextureSize(64, 32);
        this.Bump_1.mirror = true;
        this.setRotation(this.Bump_1, 0.0f, 0.7853982f, 0.0f);
        this.Bump_2 = new ModelRenderer((ModelBase)this, 56, 0);
        this.Bump_2.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.Bump_2.setRotationPoint(3.0f, 12.0f, 0.0f);
        this.Bump_2.setTextureSize(64, 32);
        this.Bump_2.mirror = true;
        this.setRotation(this.Bump_2, 0.0f, 0.7853982f, 0.0f);
        this.Bump_3 = new ModelRenderer((ModelBase)this, 56, 0);
        this.Bump_3.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.Bump_3.setRotationPoint(0.0f, 12.0f, -3.0f);
        this.Bump_3.setTextureSize(64, 32);
        this.Bump_3.mirror = true;
        this.setRotation(this.Bump_3, 0.0f, 0.7853982f, 0.0f);
        this.Bump_4 = new ModelRenderer((ModelBase)this, 56, 0);
        this.Bump_4.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.Bump_4.setRotationPoint(-3.0f, 12.0f, 0.0f);
        this.Bump_4.setTextureSize(64, 32);
        this.Bump_4.mirror = true;
        this.setRotation(this.Bump_4, 0.0f, 0.7853982f, 0.0f);
        this.Cross = new ModelRenderer((ModelBase)this, 14, 8);
        this.Cross.addBox(-0.5f, -0.5f, -0.5f, 3, 1, 3);
        this.Cross.setRotationPoint(-1.5f, 11.0f, 0.0f);
        this.Cross.setTextureSize(64, 32);
        this.Cross.mirror = true;
        this.setRotation(this.Cross, 0.0f, 0.7853982f, 0.0f);
    }

    public void render(float rotation, float f5) {
        this.Tip.render(f5);
        this.Upper_plating.render(f5);
        this.Middle_plating.render(f5);
        this.Shape3_1.render(f5);
        this.Shape3_2.render(f5);
        this.Shape3_3.render(f5);
        this.Shape3_4.render(f5);
        this.LowerPlating_1.render(f5);
        this.LowerPlating_2.render(f5);
        this.Bump_1.render(f5);
        this.Bump_2.render(f5);
        this.Bump_3.render(f5);
        this.Bump_4.render(f5);
        this.Cross.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

