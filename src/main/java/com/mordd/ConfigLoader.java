package com.mordd;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Field;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mordd.util.Utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader {
	public static String[] fluxMaterialList;
	
	
	public ConfigLoader(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "vd_core.cfg"));
		config.load();

		
		
		fluxMaterialList = config.getStringList("materials", "vd_core.materials.fluxCrystal",new String[] {MT.OREMATS.Magnetite.mNameInternal,MT.OREMATS.Tetrahedrite.mNameInternal}, "");
		for(String s : fluxMaterialList) {
			OreDictMaterial m = OreDictMaterial.get(s);
			if(m != OreDictMaterial.NULL && m != null) Utils.fluxCrystalMaterials.add(m);
		}
		JsonParser parser = new JsonParser();
		
		String[] list = config.getStringList("recipes", "vd_core.stone_maker", new String[] {"{\"base\":\"minecraft:stone_brick:0\",\"output\":\"minecraft:cobblestone:0\"}"}, "");
		for(String s : list) {
			JsonElement element = parser.parse(s);
			JsonObject json = element.getAsJsonObject();
			String base = json.get("base").getAsString();
			String info2[] = base.split(":");
			
			Block b = GameRegistry.findBlock(info2[0], info2[1]);
		
			if(b == null) continue;
			ItemStack stack2 = null;
			try {
				stack2 = new ItemStack(b,1,Integer.parseInt(info2[2]));
			}catch(Exception e) {}
			if(stack2 == null) continue;
			String output = json.get("output").getAsString();
			String info[] = output.split(":");
			if(!Loader.isModLoaded(info[0]) && info[0] != "minecraft") continue;
			Item item = GameRegistry.findItem(info[0], info[1]);
			if(item == null) continue;
			try {
				ItemStack stack = new ItemStack(item,0,Integer.parseInt(info[2]));
				if(Utils.rc_recipe.containsValue(stack2)) continue;
				else {
					Utils.rc_recipe.put(stack2,stack);
				}
			}
			catch(Exception e1) {
				continue;
			}
		}
	}
}
