package com.mordd.item;

import gregapi.data.CS;
import gregapi.data.*;
import gregapi.item.multiitem.MultiItemRandom;
import gregapi.item.multiitem.behaviors.*;
import gregapi.oredict.OreDictItemData;
import gregtech.items.behaviors.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregapi.code.*;
import gregapi.util.ST;

import java.util.HashMap;

public class ToolCeramicBucket extends MultiItemRandom {
    public ToolCeramicBucket(String aModID, String aUnlocalized) {
        super(aModID, aUnlocalized);
        this.setUnlocalizedName("vd_core.ceramic_bucket");
    }
    @Override
    public void addItems() {
        OreDictItemData tData = new OreDictItemData(MT.Ceramic, CS.U*3);
        ItemStack zBucket = addItem(0, "Ceramic Bucket", "Empty", TC.stack(TC.TERRA, 2), TC.stack(TC.VACUOS, 2), Behavior_Bucket_Simple.INSTANCE, tData);
        CS.ItemsGT.addNEIRedirects(zBucket, addItem(1, "Ceramic Bucket", "Water", TC.stack(TC.TERRA, 2), TC.stack(TC.AQUA, 2), new Behavior_Bucket_Simple(ST.make(Items.water_bucket, 1, 0)), tData.copy(), FL.Water.make(1000), FL.DistW.make(1000), FL.River_Water.make(1000), FL.MnWtr.make(1000), FL.Mineralsoda.make(1000), FL.Soda.make(1000), FL.Water_Hot.make(1000), FL.Water_Boiling.make(1000), FL.Water_Geothermal.make(1000), OD.container1000water));
    }
    @Override
    public int getDefaultStackLimit(ItemStack aStack) {return 1;}
    @Override
    public ItemStack getContainerItem(ItemStack aStack) {
        short aMeta = ST.meta_(aStack);
        if (aMeta ==  0) return make(1);
        if (aMeta ==  1) return make(0);
        return super.getContainerItem(aStack);
    }
    public static HashMap<Integer,String> registeredItem = new HashMap<Integer,String>();
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String postFix = registeredItem.get(stack.getItemDamage());
        if(postFix != null) {
            return "vd_core.ceramic_bucket"+"."+postFix;
        }
        return "vd_core.ceramic_bucket"+"."+stack.getItemDamage();
    }
}