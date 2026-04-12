package com.mordd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mordd.block.BlockLoader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.CS.BlocksGT;
import gregapi.data.CS.ItemsGT;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.render.TextureSet;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public class Utils {
	public static List<OreDictMaterial> fluxCrystalMaterials = new ArrayList<OreDictMaterial>(); 
	public static List<Aspect> primalAspects;
	public static Map<ItemStack,ItemStack> rc_recipe = new HashMap<ItemStack,ItemStack>();
	static{
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,201),new ItemStack(BlocksGT.Marble,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,202),new ItemStack(BlocksGT.Limestone,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,203),new ItemStack(BlocksGT.Basalt,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,204),new ItemStack(BlocksGT.Kimberlite,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,205),new ItemStack(BlocksGT.Diorite,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,206),new ItemStack(BlocksGT.Komatiite,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,207),new ItemStack(BlocksGT.Quartzite,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,210),new ItemStack(BlocksGT.Andesite,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,212),new ItemStack(BlocksGT.GraniteRed,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,213),new ItemStack(BlocksGT.GraniteBlack,1,1));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,208),new ItemStack(BlocksGT.RockOres,1,2));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,209),new ItemStack(BlocksGT.RockOres,1,4));
		rc_recipe.put(new ItemStack(BlockLoader.multiBlock,1,211),new ItemStack(BlocksGT.RockOres,1,0));
		
		if(isTCLoaded()) primalAspects = Aspect.getPrimalAspects();
		fluxCrystalMaterials.add(MT.Empty);
	}
	private static int currentId = 32001 ;
	public static void replaceMaterial(ItemStack stack,OreDictMaterial org,OreDictMaterial tar) {
		List<OreDictMaterialStack> temp = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		for(OreDictMaterialStack t : temp) {
			if(t.mMaterial == org) t.mMaterial = tar;
		}
	}
	public static void replaceMaterial(ItemStack stack,OreDictMaterial org,OreDictMaterial tar,long replaced) {
		List<OreDictMaterialStack> temp = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		boolean add = false;
		for(OreDictMaterialStack t : temp) {
			if(t.mMaterial == org) {
				if(replaced > t.mAmount) {
					t.mAmount = replaced;
					t.mMaterial = tar;
				}
				else {
					t.mAmount -= replaced;
					add = true;
				}
			} 
		}
		if(add) {
			temp.add(new OreDictMaterialStack(tar,replaced));
			OreDictMaterialStack org2 = temp.get(0);
			temp.remove(0);
			OreDictItemData data = new OreDictItemData(org2,temp.toArray(new OreDictMaterialStack[temp.size()]));
			OreDictManager.INSTANCE.setItemData(stack,data);
		} 
	}
	public static void clearMaterial(ItemStack stack) {
		OreDictManager.INSTANCE.setItemData(stack,new OreDictItemData());
	}
	public static void addMaterial(ItemStack stack,OreDictMaterial mat,long cnt) {
		List<OreDictMaterialStack> materials = OreDictManager.INSTANCE.getItemData(stack, CS.F).getAllMaterialStacks();
		OreDictItemData data = new OreDictItemData(new OreDictMaterialStack(mat,cnt),materials.toArray(new OreDictMaterialStack[materials.size()]));
		OreDictManager.INSTANCE.setItemData(stack,data);
	} 
	/**
	 *  Register a New Material
	 */
	public static OreDictMaterial registerMaterial(String aNameOreDict, String aLocalName,int MeltingPoint,int BoilingPoint, int PlasmaPoint, double density, int color_ARGB,TextureSet... aSets) {
		if(currentId > 32760) throw new IndexOutOfBoundsException("You can only register at most 760 materials for Modpack use!");
		OreDictMaterial ret = OreDictMaterial.createMaterial(currentId, aNameOreDict, aLocalName);
		ret.heat(MeltingPoint, BoilingPoint, PlasmaPoint);
		ret.setDensity(density / 111.111111);
		int A = (color_ARGB & 0xFF000000) >> 24;
		int R = (color_ARGB & 0xFF0000) >> 16;
		int G = (color_ARGB & 0xFF00) >> 8;
		int B = color_ARGB & 0xFF;
		ret.setRGBa(R, G, B, A);
		ret.setTextures(TextureSet.SET_METALLIC);
		currentId++;
		return ret;
	}
	public static OreDictMaterial registerMaterial(String aNameOreDict, String aLocalName,int MeltingPoint,int BoilingPoint, double density, int color_ARGB,TextureSet... aSets) {
		if(currentId > 32760) throw new IndexOutOfBoundsException("You can only register at most 760 materials for Modpack use!");
		OreDictMaterial ret = OreDictMaterial.createMaterial(currentId, aNameOreDict, aLocalName);
		ret.heat(MeltingPoint, BoilingPoint);
		ret.setDensity(density / 111.111111);
		int A = (color_ARGB & 0xFF000000) >> 24;
		int R = (color_ARGB & 0xFF0000) >> 16;
		int G = (color_ARGB & 0xFF00) >> 8;
		int B = color_ARGB & 0xFF;
		ret.setRGBa(R, G, B, A);
		ret.setTextures(TextureSet.SET_METALLIC);
		currentId++;
		return ret;
	}
	
	
	public static boolean isTCLoaded() {
		return Loader.isModLoaded("Thaumcraft") && Loader.isModLoaded("thaumicenergistics");
	}
	public static boolean isSameItem(ItemStack stack,ItemStack stack2) {
		if(stack == null || stack2 == null) return false; 
		if(stack.getItem() != stack2.getItem()) return false;
		if(!stack.getHasSubtypes()) return true;
		if(stack.getItemDamage() == stack2.getItemDamage()) return true;
		return false;
	}
	public static boolean xor(boolean b1,boolean b2) {
		return !(b1 && b2) && (b1 || b2);
	}

}
