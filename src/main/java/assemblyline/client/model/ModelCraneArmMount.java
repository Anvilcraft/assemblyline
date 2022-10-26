package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCraneArmMount
extends ModelBase {
    private ModelRenderer RailGuard1;
    private ModelRenderer ArmMount;
    private ModelRenderer RailGuard2;
    private ModelRenderer Base;
    private ModelRenderer WheelMount1;
    private ModelRenderer Wheel2;
    private ModelRenderer WheelMount2;
    private ModelRenderer Wheel1;

    public ModelCraneArmMount() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.RailGuard1 = new ModelRenderer((ModelBase)this, 0, 20);
        this.RailGuard1.addBox(0.0f, 0.0f, 0.0f, 16, 8, 2);
        this.RailGuard1.setRotationPoint(-8.0f, 12.0f, 4.0f);
        this.RailGuard1.setTextureSize(64, 32);
        this.RailGuard1.mirror = true;
        this.setRotation(this.RailGuard1, 0.0f, 0.0f, 0.0f);
        this.ArmMount = new ModelRenderer((ModelBase)this, 36, 26);
        this.ArmMount.addBox(0.0f, 0.0f, 0.0f, 8, 4, 2);
        this.ArmMount.setRotationPoint(-4.0f, 4.0f, -8.0f);
        this.ArmMount.setTextureSize(64, 32);
        this.ArmMount.mirror = true;
        this.setRotation(this.ArmMount, 0.0f, 0.0f, 0.0f);
        this.RailGuard2 = new ModelRenderer((ModelBase)this, 0, 20);
        this.RailGuard2.addBox(0.0f, 0.0f, 0.0f, 16, 8, 2);
        this.RailGuard2.setRotationPoint(-8.0f, 12.0f, -6.0f);
        this.RailGuard2.setTextureSize(64, 32);
        this.RailGuard2.mirror = true;
        this.setRotation(this.RailGuard2, 0.0f, 0.0f, 0.0f);
        this.Base = new ModelRenderer((ModelBase)this, 0, 0);
        this.Base.addBox(0.0f, 0.0f, 0.0f, 16, 4, 16);
        this.Base.setRotationPoint(-8.0f, 8.0f, -8.0f);
        this.Base.setTextureSize(64, 32);
        this.Base.mirror = true;
        this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
        this.WheelMount1 = new ModelRenderer((ModelBase)this, 0, 30);
        this.WheelMount1.addBox(0.0f, 0.0f, 0.0f, 14, 4, 4);
        this.WheelMount1.setRotationPoint(-7.0f, 4.0f, 2.0f);
        this.WheelMount1.setTextureSize(64, 32);
        this.WheelMount1.mirror = true;
        this.setRotation(this.WheelMount1, 0.0f, 0.0f, 0.0f);
        this.Wheel2 = new ModelRenderer((ModelBase)this, 36, 20);
        this.Wheel2.addBox(0.0f, 0.0f, 0.0f, 5, 4, 2);
        this.Wheel2.setRotationPoint(1.0f, 6.0f, -1.0f);
        this.Wheel2.setTextureSize(64, 32);
        this.Wheel2.mirror = true;
        this.setRotation(this.Wheel2, 0.0f, 0.0f, 0.0f);
        this.WheelMount2 = new ModelRenderer((ModelBase)this, 0, 30);
        this.WheelMount2.addBox(0.0f, 0.0f, 0.0f, 14, 4, 4);
        this.WheelMount2.setRotationPoint(-7.0f, 4.0f, -6.0f);
        this.WheelMount2.setTextureSize(64, 32);
        this.WheelMount2.mirror = true;
        this.setRotation(this.WheelMount2, 0.0f, 0.0f, 0.0f);
        this.Wheel1 = new ModelRenderer((ModelBase)this, 36, 20);
        this.Wheel1.addBox(0.0f, 0.0f, 0.0f, 5, 4, 2);
        this.Wheel1.setRotationPoint(-6.0f, 6.0f, -1.0f);
        this.Wheel1.setTextureSize(64, 32);
        this.Wheel1.mirror = true;
        this.setRotation(this.Wheel1, 0.0f, 0.0f, 0.0f);
    }

    public void render(float scale) {
        this.RailGuard1.render(scale);
        this.ArmMount.render(scale);
        this.RailGuard2.render(scale);
        this.Base.render(scale);
        this.WheelMount1.render(scale);
        this.Wheel2.render(scale);
        this.WheelMount2.render(scale);
        this.Wheel1.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

