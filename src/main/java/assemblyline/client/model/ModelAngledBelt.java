package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelAngledBelt extends ModelBase {
    ModelRenderer MRoller1;
    ModelRenderer bBELT;
    ModelRenderer MRoller2;
    ModelRenderer tBELT;
    ModelRenderer MRoller3;

    public ModelAngledBelt() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.MRoller1 = new ModelRenderer((ModelBase) this, 0, 26);
        this.MRoller1.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.MRoller1.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.MRoller1.setTextureSize(128, 128);
        this.MRoller1.mirror = true;
        this.setRotation(this.MRoller1, 0.7853982f, 0.0f, 0.0f);
        this.bBELT = new ModelRenderer((ModelBase) this, 0, 0);
        this.bBELT.addBox(0.0f, 0.0f, 0.0f, 14, 1, 23);
        this.bBELT.setRotationPoint(-7.0f, 23.5f, -8.0f);
        this.bBELT.setTextureSize(128, 128);
        this.bBELT.mirror = true;
        this.setRotation(this.bBELT, 0.7853982f, 0.0f, 0.0f);
        this.MRoller2 = new ModelRenderer((ModelBase) this, 0, 26);
        this.MRoller2.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.MRoller2.setRotationPoint(0.0f, 9.0f, 5.0f);
        this.MRoller2.setTextureSize(128, 128);
        this.MRoller2.mirror = true;
        this.setRotation(this.MRoller2, 0.7853982f, 0.0f, 0.0f);
        this.tBELT = new ModelRenderer((ModelBase) this, 0, 0);
        this.tBELT.addBox(0.0f, 0.0f, 0.0f, 14, 1, 23);
        this.tBELT.setRotationPoint(-7.0f, 19.0f, -8.0f);
        this.tBELT.setTextureSize(128, 128);
        this.tBELT.mirror = true;
        this.setRotation(this.tBELT, 0.7853982f, 0.0f, 0.0f);
        this.MRoller3 = new ModelRenderer((ModelBase) this, 0, 26);
        this.MRoller3.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.MRoller3.setRotationPoint(0.0f, 19.0f, -5.0f);
        this.MRoller3.setTextureSize(128, 128);
        this.MRoller3.mirror = true;
        this.setRotation(this.MRoller3, 0.7853982f, 0.0f, 0.0f);
    }

    public void render(float f5, boolean slantAdjust) {
        if (slantAdjust) {
            this.bBELT.setRotationPoint(-7.0f, 21.5f, -7.0f);
        } else {
            this.bBELT.setRotationPoint(-7.0f, 23.5f, -8.0f);
        }
        this.MRoller1.render(f5);
        this.bBELT.render(f5);
        this.MRoller2.render(f5);
        this.tBELT.render(f5);
        this.MRoller3.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
