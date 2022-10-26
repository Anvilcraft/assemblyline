package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelConveyorBelt
extends ModelBase {
    ModelRenderer bBELTLong;
    ModelRenderer FBELT;
    ModelRenderer BacBELT;
    ModelRenderer BBelt;
    ModelRenderer FRL;
    ModelRenderer MRL;
    ModelRenderer FLL;
    ModelRenderer BLL;
    ModelRenderer MRoller;
    ModelRenderer BRoller;
    ModelRenderer tBELT;
    ModelRenderer FRoller;
    ModelRenderer BRL;
    ModelRenderer BML;
    ModelRenderer tBELTLong;
    ModelRenderer tBELT15;
    ModelRenderer bBELT15;
    ModelRenderer c4;
    ModelRenderer c3;
    ModelRenderer c2;
    ModelRenderer c1;

    public ModelConveyorBelt() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bBELTLong = new ModelRenderer((ModelBase)this, 0, 66);
        this.bBELTLong.addBox(0.0f, 0.0f, 0.0f, 14, 1, 16);
        this.bBELTLong.setRotationPoint(-7.0f, 22.0f, -8.0f);
        this.bBELTLong.setTextureSize(128, 128);
        this.bBELTLong.mirror = true;
        this.setRotation(this.bBELTLong, 0.0f, 0.0f, 0.0f);
        this.FBELT = new ModelRenderer((ModelBase)this, 0, 16);
        this.FBELT.addBox(0.0f, 0.0f, 0.0f, 14, 2, 1);
        this.FBELT.setRotationPoint(-7.0f, 20.0f, -8.0f);
        this.FBELT.setTextureSize(128, 128);
        this.FBELT.mirror = true;
        this.setRotation(this.FBELT, 0.0f, 0.0f, 0.0f);
        this.BacBELT = new ModelRenderer((ModelBase)this, 0, 16);
        this.BacBELT.addBox(0.0f, 0.0f, 0.0f, 14, 2, 1);
        this.BacBELT.setRotationPoint(-7.0f, 20.0f, 7.0f);
        this.BacBELT.setTextureSize(128, 128);
        this.BacBELT.mirror = true;
        this.setRotation(this.BacBELT, 0.0f, 0.0f, 0.0f);
        this.BBelt = new ModelRenderer((ModelBase)this, 0, 31);
        this.BBelt.addBox(0.0f, 0.0f, 0.0f, 14, 1, 14);
        this.BBelt.setRotationPoint(-7.0f, 22.0f, -7.0f);
        this.BBelt.setTextureSize(128, 128);
        this.BBelt.mirror = true;
        this.setRotation(this.BBelt, 0.0f, 0.0f, 0.0f);
        this.FRL = new ModelRenderer((ModelBase)this, 0, 20);
        this.FRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.FRL.setRotationPoint(-8.0f, 21.0f, -6.0f);
        this.FRL.setTextureSize(128, 128);
        this.FRL.mirror = true;
        this.setRotation(this.FRL, 0.0f, 0.0f, 0.0f);
        this.MRL = new ModelRenderer((ModelBase)this, 0, 20);
        this.MRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.MRL.setRotationPoint(-8.0f, 21.0f, -1.0f);
        this.MRL.setTextureSize(128, 128);
        this.MRL.mirror = true;
        this.setRotation(this.MRL, 0.0f, 0.0f, 0.0f);
        this.FLL = new ModelRenderer((ModelBase)this, 0, 20);
        this.FLL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.FLL.setRotationPoint(7.0f, 21.0f, -6.0f);
        this.FLL.setTextureSize(128, 128);
        this.FLL.mirror = true;
        this.setRotation(this.FLL, 0.0f, 0.0f, 0.0f);
        this.BLL = new ModelRenderer((ModelBase)this, 0, 20);
        this.BLL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BLL.setRotationPoint(7.0f, 21.0f, 4.0f);
        this.BLL.setTextureSize(128, 128);
        this.BLL.mirror = true;
        this.setRotation(this.BLL, 0.0f, 0.0f, 0.0f);
        this.MRoller = new ModelRenderer((ModelBase)this, 0, 26);
        this.MRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.MRoller.setRotationPoint(0.0f, 21.0f, 0.0f);
        this.MRoller.setTextureSize(128, 128);
        this.MRoller.mirror = true;
        this.setRotation(this.MRoller, 0.0f, 0.0f, 0.0f);
        this.BRoller = new ModelRenderer((ModelBase)this, 0, 26);
        this.BRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.BRoller.setRotationPoint(0.0f, 21.0f, 5.0f);
        this.BRoller.setTextureSize(128, 128);
        this.BRoller.mirror = true;
        this.setRotation(this.BRoller, 0.0f, 0.0f, 0.0f);
        this.tBELT = new ModelRenderer((ModelBase)this, 0, 0);
        this.tBELT.addBox(0.0f, 0.0f, 0.0f, 14, 1, 14);
        this.tBELT.setRotationPoint(-7.0f, 19.0f, -7.0f);
        this.tBELT.setTextureSize(128, 128);
        this.tBELT.mirror = true;
        this.setRotation(this.tBELT, 0.0f, 0.0f, 0.0f);
        this.FRoller = new ModelRenderer((ModelBase)this, 0, 26);
        this.FRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.FRoller.setRotationPoint(0.0f, 21.0f, -5.0f);
        this.FRoller.setTextureSize(128, 128);
        this.FRoller.mirror = true;
        this.setRotation(this.FRoller, 0.0f, 0.0f, 0.0f);
        this.BRL = new ModelRenderer((ModelBase)this, 0, 20);
        this.BRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BRL.setRotationPoint(-8.0f, 21.0f, 4.0f);
        this.BRL.setTextureSize(128, 128);
        this.BRL.mirror = true;
        this.setRotation(this.BRL, 0.0f, 0.0f, 0.0f);
        this.BML = new ModelRenderer((ModelBase)this, 0, 20);
        this.BML.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BML.setRotationPoint(7.0f, 21.0f, -1.0f);
        this.BML.setTextureSize(128, 128);
        this.BML.mirror = true;
        this.setRotation(this.BML, 0.0f, 0.0f, 0.0f);
        this.tBELTLong = new ModelRenderer((ModelBase)this, 0, 48);
        this.tBELTLong.addBox(0.0f, 0.0f, 0.0f, 14, 1, 16);
        this.tBELTLong.setRotationPoint(-7.0f, 19.0f, -8.0f);
        this.tBELTLong.setTextureSize(128, 128);
        this.tBELTLong.mirror = true;
        this.setRotation(this.tBELTLong, 0.0f, 0.0f, 0.0f);
        this.tBELT15 = new ModelRenderer((ModelBase)this, 0, 84);
        this.tBELT15.addBox(0.0f, 0.0f, 0.0f, 14, 1, 15);
        this.tBELT15.setRotationPoint(-7.0f, 19.0f, -8.0f);
        this.tBELT15.setTextureSize(128, 128);
        this.tBELT15.mirror = true;
        this.setRotation(this.tBELT15, 0.0f, 0.0f, 0.0f);
        this.bBELT15 = new ModelRenderer((ModelBase)this, 0, 84);
        this.bBELT15.addBox(0.0f, 0.0f, 0.0f, 14, 1, 15);
        this.bBELT15.setRotationPoint(-7.0f, 22.0f, -8.0f);
        this.bBELT15.setTextureSize(128, 128);
        this.bBELT15.mirror = true;
        this.setRotation(this.bBELT15, 0.0f, 0.0f, 0.0f);
        this.c4 = new ModelRenderer((ModelBase)this, 60, 20);
        this.c4.addBox(0.0f, 0.0f, 0.0f, 1, 16, 1);
        this.c4.setRotationPoint(7.0f, 8.0f, 7.0f);
        this.c4.setTextureSize(128, 128);
        this.c4.mirror = true;
        this.setRotation(this.c4, 0.0f, 0.0f, 0.0f);
        this.c3 = new ModelRenderer((ModelBase)this, 60, 20);
        this.c3.addBox(0.0f, 0.0f, 0.0f, 1, 16, 1);
        this.c3.setRotationPoint(7.0f, 8.0f, -8.0f);
        this.c3.setTextureSize(128, 128);
        this.c3.mirror = true;
        this.setRotation(this.c3, 0.0f, 0.0f, 0.0f);
        this.c2 = new ModelRenderer((ModelBase)this, 60, 20);
        this.c2.addBox(0.0f, 0.0f, 0.0f, 1, 16, 1);
        this.c2.setRotationPoint(-8.0f, 8.0f, 7.0f);
        this.c2.setTextureSize(128, 128);
        this.c2.mirror = true;
        this.setRotation(this.c2, 0.0f, 0.0f, 0.0f);
        this.c1 = new ModelRenderer((ModelBase)this, 60, 20);
        this.c1.addBox(0.0f, 0.0f, 0.0f, 1, 16, 1);
        this.c1.setRotationPoint(-8.0f, 8.0f, -8.0f);
        this.c1.setTextureSize(128, 128);
        this.c1.mirror = true;
        this.setRotation(this.c1, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f5, float radians, boolean front, boolean back, boolean above, boolean legs) {
        boolean rightCap;
        boolean mid = front && back;
        boolean leftCap = !front && back;
        boolean bl = rightCap = front && !back;
        if (back || front) {
            if (leftCap) {
                this.FBELT.render(f5);
                this.tBELT15.setRotationPoint(-7.0f, 19.0f, -7.0f);
                this.bBELT15.setRotationPoint(-7.0f, 22.0f, -7.0f);
                this.tBELT15.render(f5);
                this.bBELT15.render(f5);
            } else if (rightCap) {
                this.BacBELT.render(f5);
                this.tBELT15.setRotationPoint(-7.0f, 19.0f, -8.0f);
                this.bBELT15.setRotationPoint(-7.0f, 22.0f, -8.0f);
                this.tBELT15.render(f5);
                this.bBELT15.render(f5);
            } else {
                this.bBELTLong.render(f5);
                this.tBELTLong.render(f5);
            }
        } else {
            this.FBELT.render(f5);
            this.BacBELT.render(f5);
            this.BBelt.render(f5);
            this.tBELT.render(f5);
        }
        if (above) {
            this.c1.render(f5);
            this.c2.render(f5);
            this.c3.render(f5);
            this.c4.render(f5);
        }
        this.MRoller.rotateAngleX = radians;
        this.BRoller.rotateAngleX = radians;
        this.FRoller.rotateAngleX = radians;
        this.MRoller.render(f5);
        this.BRoller.render(f5);
        this.FRoller.render(f5);
        if (legs) {
            this.BRL.render(f5);
            this.BML.render(f5);
            this.FLL.render(f5);
            this.BLL.render(f5);
            this.FRL.render(f5);
            this.MRL.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

