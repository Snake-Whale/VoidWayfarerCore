package com.mordd.tileentity;

import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.CS;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase11MultiBlockConverter;
import gregapi.util.UT;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;
import static gregapi.data.CS.F;
import static gregapi.data.CS.NI;
import static gregapi.data.CS.SERVER_TIME;
import static gregapi.data.CS.TOOL_plunger;
import static gregapi.data.CS.ZL_IS;

public class MultiTileEntityLargeMotorLiquid  extends TileEntityBase11MultiBlockConverter implements IMultiBlockFluidHandler, IFluidHandler, ITileEntitySwitchableOnOff {
    public short mTurbineWalls = 18022;
    public static final IIconContainer mTextureActive = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine_active");
    public static final IIconContainer mTextureInactive = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine");
    public ITileEntityUnloadable mEmitter = null;

    public FluidTankGT mInputTank = new FluidTankGT(), mTanksOutput[] = new FluidTankGT[] {new FluidTankGT(), new FluidTankGT(), new FluidTankGT()};
    public FluidTankGT[] mTanks = new FluidTankGT[] {mInputTank, mTanksOutput[0], mTanksOutput[1], mTanksOutput[2]};
    public Recipe.RecipeMap mRecipes = FM.Engine;
    public Recipe mLastRecipe = null;

    public MultiTileEntityLargeMotorLiquid() {
    }

    public static boolean checkAndSetTargetModified(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart)tTileEntity).getMultiTileEntityID() == aRegistryMeta){ //&& ((MultiTileEntityMultiBlockPart)tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart)tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null && tTarget.isInsideStructure(aX, aY, aZ)) {
                return false;
            } else {
                ((MultiTileEntityMultiBlockPart)tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else {
            return false;
        }
    }

    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey("gt.design")) {
            this.mTurbineWalls = aNBT.getShort("gt.design");
        }

        if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
        for (int i = 0; i < mTanksOutput.length; i++)
            mTanksOutput[i].readFromNBT(aNBT, NBT_TANK+"."+i).setCapacity(mEnergyIN.mMax*16);
        mInputTank.readFromNBT(aNBT, NBT_TANK).setCapacity(mEnergyIN.mMax*4);
    }

    public boolean checkStructure2() {
        int tMinX = this.xCoord - (4 == this.mFacing ? 0 : (5 == this.mFacing ? 3 : 1));
        int tMinY = this.yCoord - (0 == this.mFacing ? 0 : (1 == this.mFacing ? 3 : 1));
        int tMinZ = this.zCoord - (2 == this.mFacing ? 0 : (3 == this.mFacing ? 3 : 1));
        int tMaxX = this.xCoord + (5 == this.mFacing ? 0 : (4 == this.mFacing ? 3 : 1));
        int tMaxY = this.yCoord + (1 == this.mFacing ? 0 : (0 == this.mFacing ? 3 : 1));
        int tMaxZ = this.zCoord + (3 == this.mFacing ? 0 : (2 == this.mFacing ? 3 : 1));
        int tOutX = this.getOffsetXN(this.mFacing, 3);
        int tOutY = this.getOffsetYN(this.mFacing, 3);
        int tOutZ = this.getOffsetZN(this.mFacing, 3);
        if (this.worldObj.blockExists(tMinX, tMinY, tMinZ) && this.worldObj.blockExists(tMaxX, tMaxY, tMaxZ)) {
            this.mEmitter = null;
            boolean tSuccess = true;
            boolean tFlag = false;//temp

            for(int tX = tMinX; tX <= tMaxX; ++tX) {
                for(int tY = tMinY; tY <= tMaxY; ++tY) {
                    for(int tZ = tMinZ; tZ <= tMaxZ; ++tZ) {
                        int tBits = 0;
                        if (tX == tOutX && tY == tOutY && tZ == tOutZ) {
                            tBits = -2;
                        } else if (CS.SIDES_AXIS_X[this.mFacing] && tX == this.xCoord || CS.SIDES_AXIS_Y[this.mFacing] && tY == this.yCoord || CS.SIDES_AXIS_Z[this.mFacing] && tZ == this.zCoord) {
                            tBits = tY == tMinY ? -61 : -41;
                        } else {
                            tBits = tY == tMinY ? -21 : -1;
                        }

                        if (!checkAndSetTargetModified(
                                this, tX, tY, tZ, this.mTurbineWalls, this.getMultiTileEntityRegistryID(), tX == tOutX && tY == tOutY && tZ == tOutZ ? 3 : 0, tBits)) {
                            tSuccess = false;
                        }
                    }
                }
            }

            return tSuccess;
        } else {
            return this.mStructureOkay;
        }
    }

    public boolean allowCovers(byte aSide) {
        return aSide != this.mFacing;
    }

    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return aX >= this.xCoord - (4 == this.mFacing ? 0 : (5 == this.mFacing ? 3 : 1)) && aY >= this.yCoord - (0 == this.mFacing ? 0 : (1 == this.mFacing ? 3 : 1)) && aZ >= this.zCoord - (2 == this.mFacing ? 0 : (3 == this.mFacing ? 3 : 1)) && aX <= this.xCoord + (5 == this.mFacing ? 0 : (4 == this.mFacing ? 3 : 1)) && aY <= this.yCoord + (1 == this.mFacing ? 0 : (0 == this.mFacing ? 3 : 1)) && aZ <= this.zCoord + (3 == this.mFacing ? 0 : (2 == this.mFacing ? 3 : 1));
    }

    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return this.mStructureOkay ? 2 : 1;
    }

    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        if (aRenderPass == 1) {
            switch (this.mFacing) {
                case 0:
                case 1:
                    return this.box(aBlock, -0.999, -0.001, -0.999, 1.999, 1.001, 1.999);
                case 2:
                case 3:
                    return this.box(aBlock, -0.999, -0.999, -0.001, 1.999, 1.999, 1.001);
                case 4:
                case 5:
                    return this.box(aBlock, -0.001, -0.999, -0.999, 1.001, 1.999, 1.999);
            }
        }

        return false;
    }

    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return (ITexture)(aRenderPass == 0 ? super.getTexture2(aBlock, aRenderPass, aSide, aShouldSideBeRendered) : (aSide != this.mFacing ? null : BlockTextureDefault.get(this.mActivity.mState > 0 ? mTextureActive : mTextureInactive)));
    }

    public TileEntity getEmittingTileEntity() {
        if (this.mEmitter == null || this.mEmitter.isDead()) {
            this.mEmitter = null;
            TileEntity tTileEntity = this.getTileEntityAtSideAndDistance(CS.OPOS[this.mFacing], 3);
            if (tTileEntity instanceof ITileEntityUnloadable) {
                this.mEmitter = (ITileEntityUnloadable)tTileEntity;
            }
        }

        return (TileEntity)(this.mEmitter == null ? this : (TileEntity)this.mEmitter);
    }

    public byte getEmittingSide() {
        return CS.OPOS[this.mFacing];
    }

    public boolean isInput(byte aSide) {
        return aSide == this.mFacing;
    }

    public boolean isOutput(byte aSide) {
        return aSide == CS.OPOS[this.mFacing];
    }

    public byte getDefaultSide() {
        return 3;
    }

    public boolean[] getValidSides() {
        return CS.SIDES_VALID;
    }

    public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && this.mEnergyOUT.isType(aEnergyType);
    }

    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return false;
    }

    public boolean canDrop(int aInventorySlot) {
        return false;
    }

    // aa

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        for (int i = 0; i < mTanksOutput.length; i++)
            mTanksOutput[i].writeToNBT(aNBT, NBT_TANK+"."+i);
        mInputTank.writeToNBT(aNBT, NBT_TANK);
    }

    static {
        LH.add("gt.tooltip.multiblock.gasturbine.1", "3x3x4 of 35 ");
        LH.add("gt.tooltip.multiblock.gasturbine.2", "Main centered on the 3x3 facing outwards");
        LH.add("gt.tooltip.multiblock.gasturbine.3", "Input only possible at frontal 3x3");
        LH.add("gt.tooltip.multiblock.gasturbine.4", "Exhaust Gas has to be removed!");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.1") + LH.get("gt.multitileentity" + "." + mTurbineWalls));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.2"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.3"));
        aList.add(LH.Chat.ORANGE   + LH.get("gt.tooltip.multiblock.gasturbine.4"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public void addToolTipsEnergy(List<String> aList, ItemStack aStack, boolean aF3_H) {
        mEnergyOUT.addToolTips(aList, aStack, aF3_H, null, T);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;

        if (isClientSide()) return 0;

        if (aTool.equals(TOOL_plunger)) {
            if (mTanksOutput[0].has()) return GarbageGT.trash(mTanksOutput[0]);
            if (mTanksOutput[1].has()) return GarbageGT.trash(mTanksOutput[1]);
            if (mTanksOutput[2].has()) return GarbageGT.trash(mTanksOutput[2]);
            return GarbageGT.trash(mInputTank);
        }

        return 0;
    }

    @Override
    public void doConversion(long aTimer) {
        if (mStorage.mEnergy >= mConverter.mEnergyIN.mMax) {
            // hacking my own code, lol
            long tEnergy = mStorage.mEnergy;
            mStorage.mEnergy = mConverter.mEnergyIN.mMax;
            super.doConversion(aTimer);
            mStorage.mEnergy = tEnergy - mConverter.mEnergyIN.mMax;
            return;
        }
        if (!mStopped && mInputTank.has() && mTanksOutput[0].underHalf() && mTanksOutput[1].underHalf() && mTanksOutput[2].underHalf()) {
            Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, mEnergyIN.mMax, NI, mInputTank.AS_ARRAY, ZL_IS);
            if (tRecipe != null) {
                mLastRecipe = tRecipe;
                if (tRecipe.mEUt < 0 && tRecipe.mDuration > 0) {
                    int tMax = UT.Code.bindInt(UT.Code.divup(mEnergyIN.mMax - mStorage.mEnergy, -tRecipe.mEUt * tRecipe.mDuration)), tParallel = tRecipe.isRecipeInputEqual(tMax, mInputTank.AS_ARRAY, ZL_IS);
                    if (tParallel < tMax) mInputTank.setEmpty();
                    if (tParallel > 0) {
                        mStorage.mEnergy -= tParallel * tRecipe.mEUt * tRecipe.mDuration;
                        for (int i = 0; i < tRecipe.mFluidOutputs.length && i < mTanksOutput.length; i++) {
                            if (!mTanksOutput[i].fillAll(tRecipe.mFluidOutputs[i], tParallel)) {
                                mStorage.mEnergy = 0;
                            }
                        }
                        super.doConversion(aTimer);
                        return;
                    }
                }
            }
        }
        mStorage.mEnergy -= mConverter.mEnergyIN.mMax;
        if (mStorage.mEnergy < 0) mStorage.mEnergy = 0;
        super.doConversion(aTimer);
    }

    @Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return !mStopped && mRecipes.containsInput(aFluidToFill, this, NI) ? mInputTank : null;}
    @Override protected IFluidTank[] getFluidTanks2(byte aSide) {return mTanks;}

    @Override
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        if (aFluidToDrain == null) {
            for (int i=0,j; i < mTanksOutput.length; i++) if (mTanksOutput[j = ((int)(SERVER_TIME/20)+i) % mTanksOutput.length].has()) return mTanksOutput[j];
        } else {
            for (int i = 0; i < mTanksOutput.length; i++) if (mTanksOutput[i].contains(aFluidToDrain)) return mTanksOutput[i];
        }
        return null;
    }

    @Override public String getTileEntityName() {return "vd.multitileentity.multiblock.motor_liquid";}
}