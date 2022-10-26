package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCraneController
extends ModelBase {
    ModelRenderer Base2;
    ModelRenderer Base;
    ModelRenderer ConnectorFront;
    ModelRenderer Decoration1;
    ModelRenderer Decoration2;
    ModelRenderer Decoration3;
    ModelRenderer Decoration4;
    ModelRenderer ConnectorTop;
    ModelRenderer ConnectorRight;

    public ModelCraneController() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Base2 = new ModelRenderer((ModelBase)this, 0, 24);
        this.Base2.addBox(0.0f, 0.0f, 0.0f, 12, 4, 12);
        this.Base2.setRotationPoint(-6.0f, 12.0f, -6.0f);
        this.Base2.setTextureSize(128, 64);
        this.Base2.mirror = true;
        this.setRotation(this.Base2, 0.0f, 0.0f, 0.0f);
        this.Base = new ModelRenderer((ModelBase)this, 0, 0);
        this.Base.addBox(0.0f, 0.0f, 0.0f, 16, 8, 16);
        this.Base.setRotationPoint(-8.0f, 16.0f, -8.0f);
        this.Base.setTextureSize(128, 64);
        this.Base.mirror = true;
        this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
        this.ConnectorFront = new ModelRenderer((ModelBase)this, 64, 0);
        this.ConnectorFront.addBox(0.0f, 0.0f, 0.0f, 8, 8, 8);
        this.ConnectorFront.setRotationPoint(-4.0f, 12.0f, 0.0f);
        this.ConnectorFront.setTextureSize(128, 64);
        this.ConnectorFront.mirror = true;
        this.setRotation(this.ConnectorFront, 0.0f, 1.570796f, 0.0f);
        this.Decoration1 = new ModelRenderer((ModelBase)this, 54, 24);
        this.Decoration1.addBox(0.0f, 0.0f, 0.0f, 2, 1, 1);
        this.Decoration1.setRotationPoint(2.0f, 15.0f, 6.0f);
        this.Decoration1.setTextureSize(128, 64);
        this.Decoration1.mirror = true;
        this.setRotation(this.Decoration1, 0.0f, 0.0f, 0.0f);
        this.Decoration2 = new ModelRenderer((ModelBase)this, 54, 24);
        this.Decoration2.addBox(0.0f, 0.0f, 0.0f, 2, 1, 1);
        this.Decoration2.setRotationPoint(-4.0f, 15.0f, 6.0f);
        this.Decoration2.setTextureSize(128, 64);
        this.Decoration2.mirror = true;
        this.setRotation(this.Decoration2, 0.0f, 0.0f, 0.0f);
        this.Decoration3 = new ModelRenderer((ModelBase)this, 48, 24);
        this.Decoration3.addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
        this.Decoration3.setRotationPoint(-7.0f, 15.0f, 2.0f);
        this.Decoration3.setTextureSize(128, 64);
        this.Decoration3.mirror = true;
        this.setRotation(this.Decoration3, 0.0f, 0.0f, 0.0f);
        this.Decoration4 = new ModelRenderer((ModelBase)this, 48, 24);
        this.Decoration4.addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
        this.Decoration4.setRotationPoint(-7.0f, 15.0f, -4.0f);
        this.Decoration4.setTextureSize(128, 64);
        this.Decoration4.mirror = true;
        this.setRotation(this.Decoration4, 0.0f, 0.0f, 0.0f);
        this.ConnectorTop = new ModelRenderer((ModelBase)this, 64, 0);
        this.ConnectorTop.addBox(0.0f, 0.0f, 0.0f, 8, 8, 8);
        this.ConnectorTop.setRotationPoint(-4.0f, 16.0f, -4.0f);
        this.ConnectorTop.setTextureSize(128, 64);
        this.ConnectorTop.mirror = true;
        this.setRotation(this.ConnectorTop, 0.0f, 0.0f, -1.570796f);
        this.ConnectorRight = new ModelRenderer((ModelBase)this, 64, 0);
        this.ConnectorRight.addBox(0.0f, 0.0f, 0.0f, 8, 8, 8);
        this.ConnectorRight.setRotationPoint(0.0f, 12.0f, -4.0f);
        this.ConnectorRight.setTextureSize(128, 64);
        this.ConnectorRight.mirror = true;
        this.setRotation(this.ConnectorRight, 0.0f, 0.0f, 0.0f);
    }

    public void render(float scale, boolean connectEast, boolean connectNorth) {
        this.Base2.setRotationPoint(-6.0f, 12.0f, -6.0f);
        this.setRotation(this.Base2, 0.0f, 0.0f, 0.0f);
        this.Base.setRotationPoint(-8.0f, 16.0f, -8.0f);
        this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
        this.Decoration1.setRotationPoint(2.0f, 15.0f, 6.0f);
        this.setRotation(this.Decoration1, 0.0f, 0.0f, 0.0f);
        this.Decoration2.setRotationPoint(-4.0f, 15.0f, 6.0f);
        this.setRotation(this.Decoration2, 0.0f, 0.0f, 0.0f);
        this.Decoration3.setRotationPoint(-7.0f, 15.0f, 2.0f);
        this.setRotation(this.Decoration3, 0.0f, 0.0f, 0.0f);
        this.Decoration4.setRotationPoint(-7.0f, 15.0f, -4.0f);
        this.setRotation(this.Decoration4, 0.0f, 0.0f, 0.0f);
        this.ConnectorTop.setRotationPoint(-4.0f, 16.0f, -4.0f);
        this.setRotation(this.ConnectorTop, 0.0f, 0.0f, -1.570796f);
        this.ConnectorFront.setRotationPoint(-4.0f, 11.99f, -0.01f);
        this.setRotation(this.ConnectorFront, 0.0f, 1.570796f, 0.0f);
        this.ConnectorRight.setRotationPoint(0.01f, 11.99f, -4.0f);
        this.setRotation(this.ConnectorRight, 0.0f, 0.0f, 0.0f);
        this.Base2.render(scale);
        this.Base.render(scale);
        this.ConnectorTop.render(scale);
        if (connectEast) {
            this.ConnectorFront.render(scale);
            this.Decoration1.render(scale);
            this.Decoration2.render(scale);
        }
        if (connectNorth) {
            this.ConnectorRight.render(scale);
            this.Decoration3.render(scale);
            this.Decoration4.render(scale);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

