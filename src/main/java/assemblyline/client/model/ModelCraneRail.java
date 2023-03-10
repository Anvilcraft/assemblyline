package assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCraneRail extends ModelBase {
    ModelRenderer SegmentUBLeft;
    ModelRenderer SegmentUFLeft;
    ModelRenderer SegmentBFLeft;
    ModelRenderer SegmentBBLeft;
    ModelRenderer SegmentBLBack;
    ModelRenderer SegmentLUBack;
    ModelRenderer SegmentRUBack;
    ModelRenderer SegmentBRBack;
    ModelRenderer SegmentBLFront;
    ModelRenderer SegmentLUFront;
    ModelRenderer SegmentRUFront;
    ModelRenderer SegmentBRFront;
    ModelRenderer SegmentLBUp;
    ModelRenderer SegmentRBUp;
    ModelRenderer SegmentLFUp;
    ModelRenderer SegmentRFUp;
    ModelRenderer SegmentUBRight;
    ModelRenderer SegmentBFRight;
    ModelRenderer SegmentUFRight;
    ModelRenderer SegmentBBRight;
    ModelRenderer SegmentLFDown;
    ModelRenderer SegmentLBDown;
    ModelRenderer SegmentRBDown;
    ModelRenderer SegmentRFDown;
    ModelRenderer SegmentBFMid;
    ModelRenderer SegmentUBMid;
    ModelRenderer SegmentBBMid;
    ModelRenderer SegmentUFMid;
    ModelRenderer SegmentLBMid;
    ModelRenderer SegmentLFMid;
    ModelRenderer SegmentRBMid;
    ModelRenderer SegmentRFMid;
    ModelRenderer SegmentRUMid;
    ModelRenderer SegmentBRMid;
    ModelRenderer SegmentBLMid;
    ModelRenderer SegmentLUMid;
    ModelRenderer SegmentMidDiag1;
    ModelRenderer SegmentMidDiag2;
    ModelRenderer SegmentMidDiag3;
    ModelRenderer SegmentMidDiag4;
    ModelRenderer SegmentMidDiag5;
    ModelRenderer SegmentMidDiag6;
    ModelRenderer SegmentMidDiag7;
    ModelRenderer SegmentMidDiag8;
    ModelRenderer FootBottom;
    ModelRenderer FootTop;

    public ModelCraneRail() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.SegmentBLBack = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentBLBack.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentBLBack.setRotationPoint(3.0f, 19.0f, 4.0f);
        this.SegmentBLBack.setTextureSize(64, 32);
        this.SegmentBLBack.mirror = true;
        this.setRotation(this.SegmentBLBack, 0.0f, 0.0f, 0.0f);
        this.SegmentLUBack = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentLUBack.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentLUBack.setRotationPoint(3.0f, 12.0f, 4.0f);
        this.SegmentLUBack.setTextureSize(64, 32);
        this.SegmentLUBack.mirror = true;
        this.setRotation(this.SegmentLUBack, 0.0f, 0.0f, 0.0f);
        this.SegmentRUBack = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentRUBack.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentRUBack.setRotationPoint(-4.0f, 12.0f, 4.0f);
        this.SegmentRUBack.setTextureSize(64, 32);
        this.SegmentRUBack.mirror = true;
        this.setRotation(this.SegmentRUBack, 0.0f, 0.0f, 0.0f);
        this.SegmentBRBack = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentBRBack.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentBRBack.setRotationPoint(-4.0f, 19.0f, 4.0f);
        this.SegmentBRBack.setTextureSize(64, 32);
        this.SegmentBRBack.mirror = true;
        this.setRotation(this.SegmentBRBack, 0.0f, 0.0f, 0.0f);
        this.SegmentBLFront = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentBLFront.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentBLFront.setRotationPoint(3.0f, 19.0f, -8.0f);
        this.SegmentBLFront.setTextureSize(64, 32);
        this.SegmentBLFront.mirror = true;
        this.setRotation(this.SegmentBLFront, 0.0f, 0.0f, 0.0f);
        this.SegmentLUFront = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentLUFront.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentLUFront.setRotationPoint(3.0f, 12.0f, -8.0f);
        this.SegmentLUFront.setTextureSize(64, 32);
        this.SegmentLUFront.mirror = true;
        this.setRotation(this.SegmentLUFront, 0.0f, 0.0f, 0.0f);
        this.SegmentRUFront = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentRUFront.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentRUFront.setRotationPoint(-4.0f, 12.0f, -8.0f);
        this.SegmentRUFront.setTextureSize(64, 32);
        this.SegmentRUFront.mirror = true;
        this.setRotation(this.SegmentRUFront, 0.0f, 0.0f, 0.0f);
        this.SegmentBRFront = new ModelRenderer((ModelBase) this, 10, 13);
        this.SegmentBRFront.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.SegmentBRFront.setRotationPoint(-4.0f, 19.0f, -8.0f);
        this.SegmentBRFront.setTextureSize(64, 32);
        this.SegmentBRFront.mirror = true;
        this.setRotation(this.SegmentBRFront, 0.0f, 0.0f, 0.0f);
        this.SegmentLBUp = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentLBUp.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentLBUp.setRotationPoint(3.0f, 8.0f, 3.0f);
        this.SegmentLBUp.setTextureSize(64, 32);
        this.SegmentLBUp.mirror = true;
        this.setRotation(this.SegmentLBUp, 0.0f, 0.0f, 0.0f);
        this.SegmentRBUp = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentRBUp.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentRBUp.setRotationPoint(-4.0f, 8.0f, 3.0f);
        this.SegmentRBUp.setTextureSize(64, 32);
        this.SegmentRBUp.mirror = true;
        this.setRotation(this.SegmentRBUp, 0.0f, 0.0f, 0.0f);
        this.SegmentLFUp = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentLFUp.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentLFUp.setRotationPoint(3.0f, 8.0f, -4.0f);
        this.SegmentLFUp.setTextureSize(64, 32);
        this.SegmentLFUp.mirror = true;
        this.setRotation(this.SegmentLFUp, 0.0f, 0.0f, 0.0f);
        this.SegmentRFUp = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentRFUp.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentRFUp.setRotationPoint(-4.0f, 8.0f, -4.0f);
        this.SegmentRFUp.setTextureSize(64, 32);
        this.SegmentRFUp.mirror = true;
        this.setRotation(this.SegmentRFUp, 0.0f, 0.0f, 0.0f);
        this.SegmentUBRight = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentUBRight.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentUBRight.setRotationPoint(-8.0f, 12.0f, 3.0f);
        this.SegmentUBRight.setTextureSize(64, 32);
        this.SegmentUBRight.mirror = true;
        this.setRotation(this.SegmentUBRight, 0.0f, 0.0f, 0.0f);
        this.SegmentBFRight = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentBFRight.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentBFRight.setRotationPoint(-8.0f, 19.0f, -4.0f);
        this.SegmentBFRight.setTextureSize(64, 32);
        this.SegmentBFRight.mirror = true;
        this.setRotation(this.SegmentBFRight, 0.0f, 0.0f, 0.0f);
        this.SegmentUFRight = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentUFRight.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentUFRight.setRotationPoint(-8.0f, 12.0f, -4.0f);
        this.SegmentUFRight.setTextureSize(64, 32);
        this.SegmentUFRight.mirror = true;
        this.setRotation(this.SegmentUFRight, 0.0f, 0.0f, 0.0f);
        this.SegmentBBRight = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentBBRight.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentBBRight.setRotationPoint(-8.0f, 19.0f, 3.0f);
        this.SegmentBBRight.setTextureSize(64, 32);
        this.SegmentBBRight.mirror = true;
        this.setRotation(this.SegmentBBRight, 0.0f, 0.0f, 0.0f);
        this.SegmentLFDown = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentLFDown.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentLFDown.setRotationPoint(3.0f, 20.0f, -4.0f);
        this.SegmentLFDown.setTextureSize(64, 32);
        this.SegmentLFDown.mirror = true;
        this.setRotation(this.SegmentLFDown, 0.0f, 0.0f, 0.0f);
        this.SegmentLBDown = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentLBDown.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentLBDown.setRotationPoint(3.0f, 20.0f, 3.0f);
        this.SegmentLBDown.setTextureSize(64, 32);
        this.SegmentLBDown.mirror = true;
        this.setRotation(this.SegmentLBDown, 0.0f, 0.0f, 0.0f);
        this.SegmentRBDown = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentRBDown.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentRBDown.setRotationPoint(-4.0f, 20.0f, 3.0f);
        this.SegmentRBDown.setTextureSize(64, 32);
        this.SegmentRBDown.mirror = true;
        this.setRotation(this.SegmentRBDown, 0.0f, 0.0f, 0.0f);
        this.FootTop = new ModelRenderer((ModelBase) this, 24, 0);
        this.FootTop.addBox(0.0f, 0.0f, 0.0f, 10, 2, 10);
        this.FootTop.setRotationPoint(-5.0f, 20.0f, -5.0f);
        this.FootTop.setTextureSize(64, 32);
        this.FootTop.mirror = true;
        this.setRotation(this.FootTop, 0.0f, 0.0f, 0.0f);
        this.SegmentRFDown = new ModelRenderer((ModelBase) this, 20, 13);
        this.SegmentRFDown.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.SegmentRFDown.setRotationPoint(-4.0f, 20.0f, -4.0f);
        this.SegmentRFDown.setTextureSize(64, 32);
        this.SegmentRFDown.mirror = true;
        this.setRotation(this.SegmentRFDown, 0.0f, 0.0f, 0.0f);
        this.FootBottom = new ModelRenderer((ModelBase) this, 0, 18);
        this.FootBottom.addBox(0.0f, 0.0f, 0.0f, 12, 2, 12);
        this.FootBottom.setRotationPoint(-6.0f, 22.0f, -6.0f);
        this.FootBottom.setTextureSize(64, 32);
        this.FootBottom.mirror = true;
        this.setRotation(this.FootBottom, 0.0f, 0.0f, 0.0f);
        this.SegmentBFMid = new ModelRenderer((ModelBase) this, 30, 12);
        this.SegmentBFMid.addBox(0.0f, 0.0f, 0.0f, 8, 1, 1);
        this.SegmentBFMid.setRotationPoint(-4.0f, 19.0f, -4.0f);
        this.SegmentBFMid.setTextureSize(64, 32);
        this.SegmentBFMid.mirror = true;
        this.setRotation(this.SegmentBFMid, 0.0f, 0.0f, 0.0f);
        this.SegmentUBMid = new ModelRenderer((ModelBase) this, 30, 12);
        this.SegmentUBMid.addBox(0.0f, 0.0f, 0.0f, 8, 1, 1);
        this.SegmentUBMid.setRotationPoint(-4.0f, 12.0f, 3.0f);
        this.SegmentUBMid.setTextureSize(64, 32);
        this.SegmentUBMid.mirror = true;
        this.setRotation(this.SegmentUBMid, 0.0f, 0.0f, 0.0f);
        this.SegmentBBMid = new ModelRenderer((ModelBase) this, 30, 12);
        this.SegmentBBMid.addBox(0.0f, 0.0f, 0.0f, 8, 1, 1);
        this.SegmentBBMid.setRotationPoint(-4.0f, 19.0f, 3.0f);
        this.SegmentBBMid.setTextureSize(64, 32);
        this.SegmentBBMid.mirror = true;
        this.setRotation(this.SegmentBBMid, 0.0f, 0.0f, 0.0f);
        this.SegmentUFMid = new ModelRenderer((ModelBase) this, 30, 12);
        this.SegmentUFMid.addBox(0.0f, 0.0f, 0.0f, 8, 1, 1);
        this.SegmentUFMid.setRotationPoint(-4.0f, 12.0f, -4.0f);
        this.SegmentUFMid.setTextureSize(64, 32);
        this.SegmentUFMid.mirror = true;
        this.setRotation(this.SegmentUFMid, 0.0f, 0.0f, 0.0f);
        this.SegmentLBMid = new ModelRenderer((ModelBase) this, 48, 19);
        this.SegmentLBMid.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1);
        this.SegmentLBMid.setRotationPoint(3.0f, 13.0f, 3.0f);
        this.SegmentLBMid.setTextureSize(64, 32);
        this.SegmentLBMid.mirror = true;
        this.setRotation(this.SegmentLBMid, 0.0f, 0.0f, 0.0f);
        this.SegmentLFMid = new ModelRenderer((ModelBase) this, 48, 19);
        this.SegmentLFMid.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1);
        this.SegmentLFMid.setRotationPoint(3.0f, 13.0f, -4.0f);
        this.SegmentLFMid.setTextureSize(64, 32);
        this.SegmentLFMid.mirror = true;
        this.setRotation(this.SegmentLFMid, 0.0f, 0.0f, 0.0f);
        this.SegmentRBMid = new ModelRenderer((ModelBase) this, 48, 19);
        this.SegmentRBMid.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1);
        this.SegmentRBMid.setRotationPoint(-4.0f, 13.0f, 3.0f);
        this.SegmentRBMid.setTextureSize(64, 32);
        this.SegmentRBMid.mirror = true;
        this.setRotation(this.SegmentRBMid, 0.0f, 0.0f, 0.0f);
        this.SegmentRFMid = new ModelRenderer((ModelBase) this, 48, 19);
        this.SegmentRFMid.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1);
        this.SegmentRFMid.setRotationPoint(-4.0f, 13.0f, -4.0f);
        this.SegmentRFMid.setTextureSize(64, 32);
        this.SegmentRFMid.mirror = true;
        this.setRotation(this.SegmentRFMid, 0.0f, 0.0f, 0.0f);
        this.SegmentMidDiag4 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag4.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag4.setRotationPoint(-3.99f, 12.0f, -3.0f);
        this.SegmentMidDiag4.setTextureSize(64, 32);
        this.SegmentMidDiag4.mirror = true;
        this.setRotation(this.SegmentMidDiag4, -0.7853982f, 0.0f, 0.0f);
        this.SegmentRUMid = new ModelRenderer((ModelBase) this, 48, 12);
        this.SegmentRUMid.addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
        this.SegmentRUMid.setRotationPoint(-4.0f, 12.0f, -3.0f);
        this.SegmentRUMid.setTextureSize(64, 32);
        this.SegmentRUMid.mirror = true;
        this.setRotation(this.SegmentRUMid, 0.0f, 0.0f, 0.0f);
        this.SegmentBRMid = new ModelRenderer((ModelBase) this, 48, 12);
        this.SegmentBRMid.addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
        this.SegmentBRMid.setRotationPoint(-4.0f, 19.0f, -3.0f);
        this.SegmentBRMid.setTextureSize(64, 32);
        this.SegmentBRMid.mirror = true;
        this.setRotation(this.SegmentBRMid, 0.0f, 0.0f, 0.0f);
        this.SegmentBLMid = new ModelRenderer((ModelBase) this, 48, 12);
        this.SegmentBLMid.addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
        this.SegmentBLMid.setRotationPoint(3.0f, 19.0f, -3.0f);
        this.SegmentBLMid.setTextureSize(64, 32);
        this.SegmentBLMid.mirror = true;
        this.setRotation(this.SegmentBLMid, 0.0f, 0.0f, 0.0f);
        this.SegmentLUMid = new ModelRenderer((ModelBase) this, 48, 12);
        this.SegmentLUMid.addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
        this.SegmentLUMid.setRotationPoint(3.0f, 12.0f, -3.0f);
        this.SegmentLUMid.setTextureSize(64, 32);
        this.SegmentLUMid.mirror = true;
        this.setRotation(this.SegmentLUMid, 0.0f, 0.0f, 0.0f);
        this.SegmentMidDiag3 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag3.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag3.setRotationPoint(-4.0f, 19.0f, -4.0f);
        this.SegmentMidDiag3.setTextureSize(64, 32);
        this.SegmentMidDiag3.mirror = true;
        this.setRotation(this.SegmentMidDiag3, 0.7853982f, 0.0f, 0.0f);
        this.SegmentMidDiag7 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag7.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag7.setRotationPoint(-2.99f, 12.0f, 4.0f);
        this.SegmentMidDiag7.setTextureSize(64, 32);
        this.SegmentMidDiag7.mirror = true;
        this.setRotation(this.SegmentMidDiag7, -0.7853982f, 1.570796f, 0.0f);
        this.SegmentMidDiag8 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag8.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag8.setRotationPoint(-4.0f, 19.0f, 4.0f);
        this.SegmentMidDiag8.setTextureSize(64, 32);
        this.SegmentMidDiag8.mirror = true;
        this.setRotation(this.SegmentMidDiag8, 0.7853982f, 1.570796f, 0.0f);
        this.SegmentMidDiag1 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag1.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag1.setRotationPoint(3.0f, 19.0f, -4.0f);
        this.SegmentMidDiag1.setTextureSize(64, 32);
        this.SegmentMidDiag1.mirror = true;
        this.setRotation(this.SegmentMidDiag1, 0.7853982f, 0.0f, 0.0f);
        this.SegmentMidDiag2 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag2.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag2.setRotationPoint(3.01f, 12.0f, -3.0f);
        this.SegmentMidDiag2.setTextureSize(64, 32);
        this.SegmentMidDiag2.mirror = true;
        this.setRotation(this.SegmentMidDiag2, -0.7853982f, 0.0f, 0.0f);
        this.SegmentMidDiag6 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag6.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag6.setRotationPoint(-4.0f, 19.0f, -3.0f);
        this.SegmentMidDiag6.setTextureSize(64, 32);
        this.SegmentMidDiag6.mirror = true;
        this.setRotation(this.SegmentMidDiag6, 0.7853982f, 1.570796f, 0.0f);
        this.SegmentMidDiag5 = new ModelRenderer((ModelBase) this, 0, 0);
        this.SegmentMidDiag5.addBox(0.0f, 0.0f, 0.0f, 1, 1, 10);
        this.SegmentMidDiag5.setRotationPoint(-2.99f, 12.0f, -3.0f);
        this.SegmentMidDiag5.setTextureSize(64, 32);
        this.SegmentMidDiag5.mirror = true;
        this.setRotation(this.SegmentMidDiag5, -0.7853982f, 1.570796f, 0.0f);
        this.SegmentUBLeft = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentUBLeft.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentUBLeft.setRotationPoint(4.0f, 12.0f, 3.0f);
        this.SegmentUBLeft.setTextureSize(64, 32);
        this.SegmentUBLeft.mirror = true;
        this.setRotation(this.SegmentUBLeft, 0.0f, 0.0f, 0.0f);
        this.SegmentUFLeft = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentUFLeft.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentUFLeft.setRotationPoint(4.0f, 12.0f, -4.0f);
        this.SegmentUFLeft.setTextureSize(64, 32);
        this.SegmentUFLeft.mirror = true;
        this.setRotation(this.SegmentUFLeft, 0.0f, 0.0f, 0.0f);
        this.SegmentBFLeft = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentBFLeft.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentBFLeft.setRotationPoint(4.0f, 19.0f, -4.0f);
        this.SegmentBFLeft.setTextureSize(64, 32);
        this.SegmentBFLeft.mirror = true;
        this.setRotation(this.SegmentBFLeft, 0.0f, 0.0f, 0.0f);
        this.SegmentBBLeft = new ModelRenderer((ModelBase) this, 0, 13);
        this.SegmentBBLeft.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.SegmentBBLeft.setRotationPoint(4.0f, 19.0f, 3.0f);
        this.SegmentBBLeft.setTextureSize(64, 32);
        this.SegmentBBLeft.mirror = true;
        this.setRotation(this.SegmentBBLeft, 0.0f, 0.0f, 0.0f);
        this.fixPositions();
    }

    public void fixPositions() {
        this.SegmentBLBack.setRotationPoint(3.0f, 19.0f, 4.0f);
        this.SegmentLUBack.setRotationPoint(3.0f, 12.0f, 4.0f);
        this.SegmentRUBack.setRotationPoint(-4.0f, 12.0f, 4.0f);
        this.SegmentBRBack.setRotationPoint(-4.0f, 19.0f, 4.0f);
        this.SegmentBLFront.setRotationPoint(3.0f, 19.0f, -8.0f);
        this.SegmentLUFront.setRotationPoint(3.0f, 12.0f, -8.0f);
        this.SegmentRUFront.setRotationPoint(-4.0f, 12.0f, -8.0f);
        this.SegmentBRFront.setRotationPoint(-4.0f, 19.0f, -8.0f);
        this.SegmentLBUp.setRotationPoint(3.0f, 8.0f, 3.0f);
        this.SegmentRBUp.setRotationPoint(-4.0f, 8.0f, 3.0f);
        this.SegmentLFUp.setRotationPoint(3.0f, 8.0f, -4.0f);
        this.SegmentRFUp.setRotationPoint(-4.0f, 8.0f, -4.0f);
        this.SegmentUBRight.setRotationPoint(-8.0f, 12.0f, 3.0f);
        this.SegmentBFRight.setRotationPoint(-8.0f, 19.0f, -4.0f);
        this.SegmentUFRight.setRotationPoint(-8.0f, 12.0f, -4.0f);
        this.SegmentBBRight.setRotationPoint(-8.0f, 19.0f, 3.0f);
        this.SegmentLFDown.setRotationPoint(3.0f, 20.0f, -4.0f);
        this.SegmentLBDown.setRotationPoint(3.0f, 20.0f, 3.0f);
        this.SegmentRBDown.setRotationPoint(-4.0f, 20.0f, 3.0f);
        this.SegmentRFDown.setRotationPoint(-4.0f, 20.0f, -4.0f);
        this.SegmentBFMid.setRotationPoint(-4.0f, 19.0f, -4.0f);
        this.SegmentUBMid.setRotationPoint(-4.0f, 12.0f, 3.0f);
        this.SegmentBBMid.setRotationPoint(-4.0f, 19.0f, 3.0f);
        this.SegmentUFMid.setRotationPoint(-4.0f, 12.0f, -4.0f);
        this.SegmentLBMid.setRotationPoint(3.0f, 13.0f, 3.0f);
        this.SegmentLFMid.setRotationPoint(3.0f, 13.0f, -4.0f);
        this.SegmentRBMid.setRotationPoint(-4.0f, 13.0f, 3.0f);
        this.SegmentRFMid.setRotationPoint(-4.0f, 13.0f, -4.0f);
        this.SegmentRUMid.setRotationPoint(-4.0f, 12.0f, -3.0f);
        this.SegmentBRMid.setRotationPoint(-4.0f, 19.0f, -3.0f);
        this.SegmentBLMid.setRotationPoint(3.0f, 19.0f, -3.0f);
        this.SegmentLUMid.setRotationPoint(3.0f, 12.0f, -3.0f);
        this.SegmentMidDiag1.setRotationPoint(2.99f, 19.1f, -4.0f);
        this.SegmentMidDiag2.setRotationPoint(2.99f, 12.0f, -3.1f);
        this.SegmentMidDiag3.setRotationPoint(-3.99f, 19.1f, -4.0f);
        this.SegmentMidDiag4.setRotationPoint(-3.99f, 12.0f, -3.1f);
        this.SegmentMidDiag5.setRotationPoint(-3.1f, 12.0f, -2.99f);
        this.SegmentMidDiag6.setRotationPoint(-4.0f, 19.1f, -2.99f);
        this.SegmentMidDiag7.setRotationPoint(-3.1f, 12.0f, 3.99f);
        this.SegmentMidDiag8.setRotationPoint(-4.0f, 19.0f, 3.99f);
        this.SegmentUBLeft.setRotationPoint(4.0f, 12.0f, 3.0f);
        this.SegmentUFLeft.setRotationPoint(4.0f, 12.0f, -4.0f);
        this.SegmentBFLeft.setRotationPoint(4.0f, 19.0f, -4.0f);
        this.SegmentBBLeft.setRotationPoint(4.0f, 19.0f, 3.0f);
    }

    public void render(
        boolean up,
        boolean down,
        boolean left,
        boolean right,
        boolean front,
        boolean back,
        boolean foot
    ) {
        float scale = 0.0625f;
        if (up) {
            this.SegmentLBUp.render(scale);
            this.SegmentRBUp.render(scale);
            this.SegmentLFUp.render(scale);
            this.SegmentRFUp.render(scale);
        }
        if (down) {
            this.SegmentLFDown.render(scale);
            this.SegmentLBDown.render(scale);
            this.SegmentRBDown.render(scale);
            this.SegmentRFDown.render(scale);
        }
        if (left) {
            this.SegmentUBLeft.render(scale);
            this.SegmentUFLeft.render(scale);
            this.SegmentBFLeft.render(scale);
            this.SegmentBBLeft.render(scale);
        }
        if (right) {
            this.SegmentUBRight.render(scale);
            this.SegmentBFRight.render(scale);
            this.SegmentUFRight.render(scale);
            this.SegmentBBRight.render(scale);
        }
        if (front) {
            this.SegmentBLFront.render(scale);
            this.SegmentLUFront.render(scale);
            this.SegmentRUFront.render(scale);
            this.SegmentBRFront.render(scale);
        }
        if (back) {
            this.SegmentBLBack.render(scale);
            this.SegmentLUBack.render(scale);
            this.SegmentRUBack.render(scale);
            this.SegmentBRBack.render(scale);
        }
        if (foot) {
            this.FootBottom.render(scale);
            this.FootTop.render(scale);
        }
        this.SegmentBFMid.render(scale);
        this.SegmentUBMid.render(scale);
        this.SegmentBBMid.render(scale);
        this.SegmentUFMid.render(scale);
        this.SegmentLBMid.render(scale);
        this.SegmentLFMid.render(scale);
        this.SegmentRBMid.render(scale);
        this.SegmentRFMid.render(scale);
        this.SegmentRUMid.render(scale);
        this.SegmentBRMid.render(scale);
        this.SegmentBLMid.render(scale);
        this.SegmentLUMid.render(scale);
        this.SegmentMidDiag1.render(scale);
        this.SegmentMidDiag2.render(scale);
        this.SegmentMidDiag3.render(scale);
        this.SegmentMidDiag4.render(scale);
        this.SegmentMidDiag5.render(scale);
        this.SegmentMidDiag6.render(scale);
        this.SegmentMidDiag7.render(scale);
        this.SegmentMidDiag8.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
