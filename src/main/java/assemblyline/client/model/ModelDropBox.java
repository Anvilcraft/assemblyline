package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelDropBox extends ModelBase {
    ModelRenderer Left;
    ModelRenderer Top;
    ModelRenderer Front;
    ModelRenderer LeftB;
    ModelRenderer Back;
    ModelRenderer Right;
    ModelRenderer RightB;
    ModelRenderer Bottom;
    ModelRenderer Back3;
    ModelRenderer Back2;

    public ModelDropBox() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Left = new ModelRenderer((ModelBase) this, 33, 43);
        this.Left.addBox(7.0f, -10.0f, -8.0f, 1, 7, 15);
        this.Left.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Left.setTextureSize(128, 128);
        this.Left.mirror = true;
        this.setRotation(this.Left, 0.0f, 0.0f, 0.0f);
        this.Top = new ModelRenderer((ModelBase) this, 0, 65);
        this.Top.addBox(-7.0f, -11.0f, -7.0f, 14, 1, 14);
        this.Top.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Top.setTextureSize(128, 128);
        this.Top.mirror = true;
        this.setRotation(this.Top, 0.0f, 0.0f, 0.0f);
        this.Front = new ModelRenderer((ModelBase) this, 0, 33);
        this.Front.addBox(-8.0f, -4.0f, -7.5f, 16, 5, 1);
        this.Front.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Front.setTextureSize(128, 128);
        this.Front.mirror = true;
        this.setRotation(this.Front, 0.5235988f, 0.0f, 0.0f);
        this.LeftB = new ModelRenderer((ModelBase) this, 36, 21);
        this.LeftB.addBox(7.0f, -3.0f, -6.0f, 1, 8, 13);
        this.LeftB.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.LeftB.setTextureSize(128, 128);
        this.LeftB.mirror = true;
        this.setRotation(this.LeftB, 0.0f, 0.0f, 0.0f);
        this.Back = new ModelRenderer((ModelBase) this, 0, 96);
        this.Back.addBox(-5.0f, -8.0f, 4.0f, 10, 10, 4);
        this.Back.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Back.setTextureSize(128, 128);
        this.Back.mirror = true;
        this.setRotation(this.Back, 0.0f, 0.0f, 0.0f);
        this.Right = new ModelRenderer((ModelBase) this, 0, 42);
        this.Right.addBox(-8.0f, -10.0f, -8.0f, 1, 7, 15);
        this.Right.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Right.setTextureSize(128, 128);
        this.Right.mirror = true;
        this.setRotation(this.Right, 0.0f, 0.0f, 0.0f);
        this.RightB = new ModelRenderer((ModelBase) this, 36, 0);
        this.RightB.addBox(-8.0f, -3.0f, -6.0f, 1, 8, 13);
        this.RightB.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.RightB.setTextureSize(128, 128);
        this.RightB.mirror = true;
        this.setRotation(this.RightB, 0.0f, 0.0f, 0.0f);
        this.Bottom = new ModelRenderer((ModelBase) this, 0, 81);
        this.Bottom.addBox(-7.0f, 4.0f, -6.0f, 14, 1, 13);
        this.Bottom.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Bottom.setTextureSize(128, 128);
        this.Bottom.mirror = true;
        this.setRotation(this.Bottom, 0.0f, 0.0f, 0.0f);
        this.Back3 = new ModelRenderer((ModelBase) this, 0, 23);
        this.Back3.addBox(-7.0f, -10.0f, -9.0f, 14, 8, 1);
        this.Back3.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Back3.setTextureSize(128, 128);
        this.Back3.mirror = true;
        this.setRotation(this.Back3, -0.1919862f, 0.0f, 0.0f);
        this.Back2 = new ModelRenderer((ModelBase) this, 0, 7);
        this.Back2.addBox(-7.0f, -10.0f, 3.0f, 14, 14, 1);
        this.Back2.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.Back2.setTextureSize(128, 128);
        this.Back2.mirror = true;
        this.setRotation(this.Back2, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f5) {
        this.Left.render(f5);
        this.Top.render(f5);
        this.Front.render(f5);
        this.LeftB.render(f5);
        this.Back.render(f5);
        this.Right.render(f5);
        this.RightB.render(f5);
        this.Bottom.render(f5);
        this.Back.render(f5);
        this.Back.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
