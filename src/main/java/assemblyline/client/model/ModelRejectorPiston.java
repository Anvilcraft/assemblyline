package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRejectorPiston
extends ModelBase {
    ModelRenderer BodyBottom;
    ModelRenderer PistonFace2;
    ModelRenderer PistonShaft;
    ModelRenderer Piston;
    ModelRenderer H1;
    ModelRenderer H2;
    ModelRenderer H3;
    ModelRenderer PistonFace;
    ModelRenderer WireCCRight;
    ModelRenderer WireCCLeft;
    ModelRenderer BodyP2;
    ModelRenderer BodyP1;
    ModelRenderer LeftSide;
    ModelRenderer RightSide;
    ModelRenderer PistonBack;
    ModelRenderer WireCCFront;

    public ModelRejectorPiston() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.BodyBottom = new ModelRenderer((ModelBase)this, 29, 69);
        this.BodyBottom.addBox(-5.0f, 0.0f, -2.0f, 10, 5, 15);
        this.BodyBottom.setRotationPoint(0.0f, 19.0f, -6.0f);
        this.BodyBottom.setTextureSize(128, 128);
        this.BodyBottom.mirror = true;
        this.setRotation(this.BodyBottom, 0.0f, 0.0f, 0.0f);
        this.PistonFace2 = new ModelRenderer((ModelBase)this, 0, 53);
        this.PistonFace2.addBox(-2.0f, -2.0f, -1.0f, 4, 6, 1);
        this.PistonFace2.setRotationPoint(0.0f, 15.0f, -6.0f);
        this.PistonFace2.setTextureSize(128, 128);
        this.PistonFace2.mirror = true;
        this.setRotation(this.PistonFace2, 0.0f, 0.0f, 0.0f);
        this.PistonShaft = new ModelRenderer((ModelBase)this, 0, 75);
        this.PistonShaft.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 10);
        this.PistonShaft.setRotationPoint(0.0f, 16.0f, -6.0f);
        this.PistonShaft.setTextureSize(128, 128);
        this.PistonShaft.mirror = true;
        this.setRotation(this.PistonShaft, 0.0f, 0.0f, 0.0f);
        this.Piston = new ModelRenderer((ModelBase)this, 0, 22);
        this.Piston.addBox(-3.0f, -1.0f, 0.0f, 6, 6, 10);
        this.Piston.setRotationPoint(0.0f, 14.0f, -5.0f);
        this.Piston.setTextureSize(128, 128);
        this.Piston.mirror = true;
        this.setRotation(this.Piston, 0.0f, 0.0f, 0.0f);
        this.H1 = new ModelRenderer((ModelBase)this, 33, 23);
        this.H1.addBox(-1.5f, 0.0f, 0.0f, 3, 1, 8);
        this.H1.setRotationPoint(0.0f, 12.0f, -4.0f);
        this.H1.setTextureSize(128, 128);
        this.H1.mirror = true;
        this.setRotation(this.H1, 0.0f, 0.0f, 0.0f);
        this.H2 = new ModelRenderer((ModelBase)this, 90, 23);
        this.H2.addBox(0.0f, -1.5f, 0.0f, 1, 3, 8);
        this.H2.setRotationPoint(-4.0f, 16.0f, -4.0f);
        this.H2.setTextureSize(128, 128);
        this.H2.mirror = true;
        this.setRotation(this.H2, 0.0f, 0.0f, 0.0f);
        this.H3 = new ModelRenderer((ModelBase)this, 70, 23);
        this.H3.addBox(0.0f, -1.5f, 0.0f, 1, 3, 8);
        this.H3.setRotationPoint(3.0f, 16.0f, -4.0f);
        this.H3.setTextureSize(128, 128);
        this.H3.mirror = true;
        this.setRotation(this.H3, 0.0f, 0.0f, 0.0f);
        this.PistonFace = new ModelRenderer((ModelBase)this, 0, 62);
        this.PistonFace.addBox(-3.0f, -2.0f, -1.0f, 6, 4, 1);
        this.PistonFace.setRotationPoint(0.0f, 16.0f, -6.0f);
        this.PistonFace.setTextureSize(128, 128);
        this.PistonFace.mirror = true;
        this.setRotation(this.PistonFace, 0.0f, 0.0f, 0.0f);
        this.WireCCRight = new ModelRenderer((ModelBase)this, 69, 52);
        this.WireCCRight.addBox(-3.0f, -3.0f, 0.0f, 6, 11, 1);
        this.WireCCRight.setRotationPoint(0.0f, 16.0f, 7.0f);
        this.WireCCRight.setTextureSize(128, 128);
        this.WireCCRight.mirror = true;
        this.setRotation(this.WireCCRight, 0.0f, 0.0f, 0.0f);
        this.WireCCLeft = new ModelRenderer((ModelBase)this, 54, 37);
        this.WireCCLeft.addBox(0.0f, -3.0f, -3.0f, 1, 8, 6);
        this.WireCCLeft.setRotationPoint(7.0f, 16.0f, 0.0f);
        this.WireCCLeft.setTextureSize(128, 128);
        this.WireCCLeft.mirror = true;
        this.setRotation(this.WireCCLeft, 0.0f, 0.0f, 0.0f);
        this.BodyP2 = new ModelRenderer((ModelBase)this, 100, 60);
        this.BodyP2.addBox(1.0f, -2.0f, -2.0f, 2, 7, 4);
        this.BodyP2.setRotationPoint(-8.0f, 16.0f, 0.0f);
        this.BodyP2.setTextureSize(128, 128);
        this.BodyP2.mirror = true;
        this.setRotation(this.BodyP2, 0.0f, 0.0f, 0.0f);
        this.BodyP1 = new ModelRenderer((ModelBase)this, 87, 60);
        this.BodyP1.addBox(3.0f, -2.0f, -2.0f, 2, 7, 4);
        this.BodyP1.setRotationPoint(2.0f, 16.0f, 0.0f);
        this.BodyP1.setTextureSize(128, 128);
        this.BodyP1.mirror = true;
        this.setRotation(this.BodyP1, 0.0f, 0.0f, 0.0f);
        this.LeftSide = new ModelRenderer((ModelBase)this, 29, 91);
        this.LeftSide.addBox(0.0f, 0.0f, -2.0f, 3, 3, 14);
        this.LeftSide.setRotationPoint(5.0f, 21.0f, -5.0f);
        this.LeftSide.setTextureSize(128, 128);
        this.LeftSide.mirror = true;
        this.setRotation(this.LeftSide, 0.0f, 0.0f, 0.0f);
        this.RightSide = new ModelRenderer((ModelBase)this, 64, 91);
        this.RightSide.addBox(0.0f, 0.0f, -2.0f, 3, 3, 14);
        this.RightSide.setRotationPoint(-8.0f, 21.0f, -5.0f);
        this.RightSide.setTextureSize(128, 128);
        this.RightSide.mirror = true;
        this.setRotation(this.RightSide, 0.0f, 0.0f, 0.0f);
        this.PistonBack = new ModelRenderer((ModelBase)this, 0, 12);
        this.PistonBack.addBox(-2.5f, -2.5f, -1.0f, 5, 5, 2);
        this.PistonBack.setRotationPoint(0.0f, 16.0f, 6.0f);
        this.PistonBack.setTextureSize(128, 128);
        this.PistonBack.mirror = true;
        this.setRotation(this.PistonBack, 0.0f, 0.0f, 0.0f);
        this.WireCCFront = new ModelRenderer((ModelBase)this, 69, 37);
        this.WireCCFront.addBox(0.0f, -3.0f, -3.0f, 1, 8, 6);
        this.WireCCFront.setRotationPoint(-8.0f, 16.0f, 0.0f);
        this.WireCCFront.setTextureSize(128, 128);
        this.WireCCFront.mirror = true;
        this.setRotation(this.WireCCFront, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f5) {
        this.BodyBottom.render(f5);
        this.Piston.render(f5);
        this.H1.render(f5);
        this.H2.render(f5);
        this.H3.render(f5);
        this.WireCCRight.render(f5);
        this.WireCCLeft.render(f5);
        this.BodyP2.render(f5);
        this.BodyP1.render(f5);
        this.LeftSide.render(f5);
        this.RightSide.render(f5);
        this.PistonBack.render(f5);
        this.WireCCFront.render(f5);
    }

    public void renderPiston(float f5, int p) {
        this.PistonFace2.setRotationPoint(0.0f, 15.0f, -6.0f - (float)p);
        this.PistonShaft.setRotationPoint(0.0f, 16.0f, -6.0f - (float)p);
        this.PistonFace.setRotationPoint(0.0f, 16.0f, -6.0f - (float)p);
        this.PistonFace2.render(f5);
        this.PistonShaft.render(f5);
        this.PistonFace.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

