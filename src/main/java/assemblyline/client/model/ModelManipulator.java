package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelManipulator extends ModelBase {
    ModelRenderer bBELTLong;
    ModelRenderer FBELT;
    ModelRenderer BacPanel;
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
    ModelRenderer RPanel;
    ModelRenderer LPanel;
    ModelRenderer TopPanel;
    ModelRenderer RCPanel;
    ModelRenderer LCPanel;

    public ModelManipulator() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bBELTLong = new ModelRenderer((ModelBase) this, 0, 66);
        this.bBELTLong.addBox(0.0f, 0.0f, 0.0f, 14, 1, 16);
        this.bBELTLong.setRotationPoint(-7.0f, 22.0f, -8.0f);
        this.bBELTLong.setTextureSize(128, 128);
        this.bBELTLong.mirror = true;
        this.setRotation(this.bBELTLong, 0.0f, 0.0f, 0.0f);
        this.FBELT = new ModelRenderer((ModelBase) this, 0, 16);
        this.FBELT.addBox(0.0f, 0.0f, 0.0f, 14, 2, 1);
        this.FBELT.setRotationPoint(-7.0f, 20.0f, -8.0f);
        this.FBELT.setTextureSize(128, 128);
        this.FBELT.mirror = true;
        this.setRotation(this.FBELT, 0.0f, 0.0f, 0.0f);
        this.BacPanel = new ModelRenderer((ModelBase) this, 0, 86);
        this.BacPanel.addBox(0.0f, -12.0f, 0.0f, 14, 12, 1);
        this.BacPanel.setRotationPoint(-7.0f, 24.0f, 7.0f);
        this.BacPanel.setTextureSize(128, 128);
        this.BacPanel.mirror = true;
        this.setRotation(this.BacPanel, 0.0f, 0.0f, 0.0f);
        this.BBelt = new ModelRenderer((ModelBase) this, 0, 31);
        this.BBelt.addBox(0.0f, 0.0f, 0.0f, 14, 1, 14);
        this.BBelt.setRotationPoint(-7.0f, 22.0f, -7.0f);
        this.BBelt.setTextureSize(128, 128);
        this.BBelt.mirror = true;
        this.setRotation(this.BBelt, 0.0f, 0.0f, 0.0f);
        this.FRL = new ModelRenderer((ModelBase) this, 0, 20);
        this.FRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.FRL.setRotationPoint(-8.0f, 21.0f, -6.0f);
        this.FRL.setTextureSize(128, 128);
        this.FRL.mirror = true;
        this.setRotation(this.FRL, 0.0f, 0.0f, 0.0f);
        this.MRL = new ModelRenderer((ModelBase) this, 0, 20);
        this.MRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.MRL.setRotationPoint(-8.0f, 21.0f, -1.0f);
        this.MRL.setTextureSize(128, 128);
        this.MRL.mirror = true;
        this.setRotation(this.MRL, 0.0f, 0.0f, 0.0f);
        this.FLL = new ModelRenderer((ModelBase) this, 0, 20);
        this.FLL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.FLL.setRotationPoint(7.0f, 21.0f, -6.0f);
        this.FLL.setTextureSize(128, 128);
        this.FLL.mirror = true;
        this.setRotation(this.FLL, 0.0f, 0.0f, 0.0f);
        this.BLL = new ModelRenderer((ModelBase) this, 0, 20);
        this.BLL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BLL.setRotationPoint(7.0f, 21.0f, 4.0f);
        this.BLL.setTextureSize(128, 128);
        this.BLL.mirror = true;
        this.setRotation(this.BLL, 0.0f, 0.0f, 0.0f);
        this.MRoller = new ModelRenderer((ModelBase) this, 0, 26);
        this.MRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.MRoller.setRotationPoint(0.0f, 21.0f, 0.0f);
        this.MRoller.setTextureSize(128, 128);
        this.MRoller.mirror = true;
        this.setRotation(this.MRoller, 0.0f, 0.0f, 0.0f);
        this.BRoller = new ModelRenderer((ModelBase) this, 0, 26);
        this.BRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.BRoller.setRotationPoint(0.0f, 21.0f, 5.0f);
        this.BRoller.setTextureSize(128, 128);
        this.BRoller.mirror = true;
        this.setRotation(this.BRoller, 0.0f, 0.0f, 0.0f);
        this.tBELT = new ModelRenderer((ModelBase) this, 0, 0);
        this.tBELT.addBox(0.0f, 0.0f, 0.0f, 14, 1, 14);
        this.tBELT.setRotationPoint(-7.0f, 19.0f, -7.0f);
        this.tBELT.setTextureSize(128, 128);
        this.tBELT.mirror = true;
        this.setRotation(this.tBELT, 0.0f, 0.0f, 0.0f);
        this.FRoller = new ModelRenderer((ModelBase) this, 0, 26);
        this.FRoller.addBox(-7.0f, -1.0f, -1.0f, 14, 2, 2);
        this.FRoller.setRotationPoint(0.0f, 21.0f, -5.0f);
        this.FRoller.setTextureSize(128, 128);
        this.FRoller.mirror = true;
        this.setRotation(this.FRoller, 0.0f, 0.0f, 0.0f);
        this.BRL = new ModelRenderer((ModelBase) this, 0, 20);
        this.BRL.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BRL.setRotationPoint(-8.0f, 21.0f, 4.0f);
        this.BRL.setTextureSize(128, 128);
        this.BRL.mirror = true;
        this.setRotation(this.BRL, 0.0f, 0.0f, 0.0f);
        this.BML = new ModelRenderer((ModelBase) this, 0, 20);
        this.BML.addBox(0.0f, 0.0f, 0.0f, 1, 3, 2);
        this.BML.setRotationPoint(7.0f, 21.0f, -1.0f);
        this.BML.setTextureSize(128, 128);
        this.BML.mirror = true;
        this.setRotation(this.BML, 0.0f, 0.0f, 0.0f);
        this.tBELTLong = new ModelRenderer((ModelBase) this, 0, 48);
        this.tBELTLong.addBox(0.0f, 0.0f, 0.0f, 14, 1, 16);
        this.tBELTLong.setRotationPoint(-7.0f, 19.0f, -8.0f);
        this.tBELTLong.setTextureSize(128, 128);
        this.tBELTLong.mirror = true;
        this.setRotation(this.tBELTLong, 0.0f, 0.0f, 0.0f);
        this.RPanel = new ModelRenderer((ModelBase) this, 65, 41);
        this.RPanel.addBox(0.0f, -2.0f, -8.0f, 1, 4, 16);
        this.RPanel.setRotationPoint(-8.0f, 19.0f, 0.0f);
        this.RPanel.setTextureSize(128, 128);
        this.RPanel.mirror = true;
        this.setRotation(this.RPanel, 0.0f, 0.0f, 0.0f);
        this.LPanel = new ModelRenderer((ModelBase) this, 65, 20);
        this.LPanel.addBox(0.0f, -2.0f, -8.0f, 1, 4, 16);
        this.LPanel.setRotationPoint(7.0f, 19.0f, 0.0f);
        this.LPanel.setTextureSize(128, 128);
        this.LPanel.mirror = true;
        this.setRotation(this.LPanel, 0.0f, 0.0f, 0.0f);
        this.TopPanel = new ModelRenderer((ModelBase) this, 0, 105);
        this.TopPanel.addBox(0.0f, 0.0f, 0.0f, 14, 2, 10);
        this.TopPanel.setRotationPoint(-7.0f, 12.0f, -3.0f);
        this.TopPanel.setTextureSize(128, 128);
        this.TopPanel.mirror = true;
        this.setRotation(this.TopPanel, 0.0f, 0.0f, 0.0f);
        this.RCPanel = new ModelRenderer((ModelBase) this, 50, 105);
        this.RCPanel.addBox(-1.0f, 0.0f, 0.0f, 2, 5, 10);
        this.RCPanel.setRotationPoint(-7.0f, 14.0f, -3.0f);
        this.RCPanel.setTextureSize(128, 128);
        this.RCPanel.mirror = true;
        this.setRotation(this.RCPanel, 0.0f, 0.0f, 0.0f);
        this.LCPanel = new ModelRenderer((ModelBase) this, 76, 105);
        this.LCPanel.addBox(0.0f, 0.0f, 0.0f, 2, 5, 10);
        this.LCPanel.setRotationPoint(6.0f, 14.0f, -3.0f);
        this.LCPanel.setTextureSize(128, 128);
        this.LCPanel.mirror = true;
        this.setRotation(this.LCPanel, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f5, boolean isLongBelt, int radians) {
        this.BacPanel.render(f5);
        this.RPanel.render(f5);
        this.LPanel.render(f5);
        this.TopPanel.render(f5);
        this.RCPanel.render(f5);
        this.LCPanel.render(f5);
        this.FRL.render(f5);
        this.MRL.render(f5);
        this.FLL.render(f5);
        this.BLL.render(f5);
        this.BRL.render(f5);
        this.BML.render(f5);
        this.MRoller.rotateAngleX = radians;
        this.BRoller.rotateAngleX = radians;
        this.FRoller.rotateAngleX = radians;
        this.MRoller.render(f5);
        this.BRoller.render(f5);
        this.FRoller.render(f5);
        if (isLongBelt) {
            this.tBELTLong.render(f5);
            this.bBELTLong.render(f5);
        } else {
            this.FBELT.render(f5);
            this.tBELT.render(f5);
            this.BBelt.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
