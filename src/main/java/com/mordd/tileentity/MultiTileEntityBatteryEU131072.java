package com.mordd.tileentity;

import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.TileEntityBase08Battery;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

import static gregapi.data.CS.*;
import static gregapi.data.CS.PX_N;
import static gregapi.data.CS.PX_P;
import static gregapi.data.CS.T;

public class MultiTileEntityBatteryEU131072 extends TileEntityBase08Battery {
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return SIDES_HORIZONTAL[aSide] ? aRenderPass == 1 ? BlockTextureDefault.get(sTextures[3], mRGBa) : BlockTextureDefault.get(sTextures[2]) : aRenderPass == 1 ? null : BlockTextureDefault.get(sTextures[FACES_TBS[aSide]]);
    }
    public static IIconContainer[] sTextures = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("vd_core:machines/batteries/eu/ultra/131072/bottom"),
            new Textures.BlockIcons.CustomIcon("vd_core:machines/batteries/eu/ultra/131072/top"),
            new Textures.BlockIcons.CustomIcon("vd_core:machines/batteries/eu/ultra/131072/sides"),
            new Textures.BlockIcons.CustomIcon("vd_core:machines/batteries/eu/ultra/131072/bar"),
    };
    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        if (aRenderPass == 1) {
            box(aBlock, PX_P[0]-0.002F, PX_P[1], PX_P[0]-0.002F, PX_N[0]+0.002F, PX_P[mDisplayedEnergy+1], PX_N[0]+0.002F);
            return T;
        }
        box(aBlock, PX_P[0], PX_P[0], PX_P[0], PX_N[0], PX_N[0], PX_N[0]);
        return T;
    }

    @Override public AxisAlignedBB getCollisionBoundingBoxFromPool() {return box(PX_P[0], PX_P[0], PX_P[0], PX_N[0], PX_N[0], PX_N[0]);}
    @Override public AxisAlignedBB getSelectedBoundingBoxFromPool () {return box(PX_P[0], PX_P[0], PX_P[0], PX_N[0], PX_N[0], PX_N[0]);}
    @Override public void setBlockBoundsBasedOnState(Block aBlock) {box(aBlock,  PX_P[0], PX_P[0], PX_P[0], PX_N[0], PX_N[0], PX_N[0]);}

    @Override public byte getDisplayScaleMax() {return 11;}
    @Override
    public String getTileEntityName() {
        return "vd.multitileentity.battery.eu.131072";
    }
}