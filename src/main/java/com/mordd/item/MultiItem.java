package com.mordd.item; 

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import com.mordd.VoidDwellerCore;
import com.mordd.block.MultiBlock;

import gregtech.tileentity.multiblocks.MultiTileEntityBedrockDrill;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MultiItem extends Item {
	public MultiItem() {
		this.setHasSubtypes(true);

	}
	public static HashMap<Integer,String> registeredItem = new HashMap<Integer,String>();
	public static HashMap<Integer,IIcon> icons = new HashMap<Integer,IIcon>(); 
	public static void registerMultiItem(int meta,String name) {
		registeredItem.put(meta,name);
	}
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String postFix = registeredItem.get(stack.getItemDamage());
		if(postFix != null) {
			return "vd_core.multiitem"+"."+postFix;
		}
		return "vd_core.multiitem"+"."+stack.getItemDamage();
	}
	@Override
	public void getSubItems(Item item,CreativeTabs tabs,List list) {
		for(int meta : registeredItem.keySet()) {
			list.add(new ItemStack(this,1,meta));
		}
	}
	public IIcon getIconFromDamage(int meta) {
		return icons.get(meta);
	}
	public void registerIcons(IIconRegister register) {
		for(int meta : registeredItem.keySet()) {
			IIcon icon = register.registerIcon("vd_core:"+registeredItem.get(meta));
			icons.put(meta,icon);
		}
	}
	@Override
	 public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(stack.getItemDamage() == 0) {   	
        	TileEntity tile = world.getTileEntity(x, y, z);
        	if(tile != null && tile instanceof MultiTileEntityBedrockDrill) {
        		MultiTileEntityBedrockDrill drill = (MultiTileEntityBedrockDrill)tile;
        		drill.mStructureOkay = true;
        		 Class c = tile.getClass();
        		 try {
					Field f = c.getField("mOnlyStructureOkay");
					boolean out = (Boolean) f.get(tile);
					VoidDwellerCore.logger.fatal(out ? "Okay" : "Not Okay");
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	return false;
        }
        return false;
    }
	
	
}
