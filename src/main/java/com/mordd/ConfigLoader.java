package com.mordd;

import java.io.File;

import com.mordd.util.Utils;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader {
	public static String[] fluxMaterialList;
	
	
	public ConfigLoader(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "vd_core.cfg"));
		config.load();

		fluxMaterialList = config.getStringList("materials", "vd_core.materials.fluxCrystal",new String[] {
				MT.OREMATS.Magnetite.mNameInternal,
				MT.OREMATS.Tetrahedrite.mNameInternal,
				MT.OREMATS.Cassiterite.mNameInternal,
				MT.OREMATS.Chromite.mNameInternal,
				MT.OREMATS.Pyrolusite.mNameInternal,
				MT.OREMATS.Coltan.mNameInternal,
				MT.OREMATS.Sphalerite.mNameInternal,
				MT.Redstone.mNameInternal,
				MT.Diamond.mNameInternal,
				MT.Gold.mNameInternal,
				MT.Silver.mNameInternal,
				MT.OREMATS.Molybdenite.mNameInternal,
				MT.OREMATS.Wolframite.mNameInternal,
				MT.OREMATS.Stibnite.mNameInternal,
				MT.Monazite.mNameInternal,
				MT.Emerald.mNameInternal,
				MT.Lapis.mNameInternal,
				MT.OREMATS.Magnesite.mNameInternal,
				MT.OREMATS.Garnierite.mNameInternal,
				MT.OREMATS.Cobaltite.mNameInternal,
				MT.CertusQuartz.mNameInternal,
				MT.NetherQuartz.mNameInternal,
				MT.AncientDebris.mNameInternal,
				MT.Fluorite.mNameInternal,
				MT.Zircon.mNameInternal,
				MT.OREMATS.Galena.mNameInternal,
				MT.Niter.mNameInternal,
				MT.OREMATS.Sperrylite.mNameInternal,
				MT.Bedrock.mNameInternal,
				MT.OREMATS.Pitchblende.mNameInternal,
				MT.Naquadah.mNameInternal
		}, "");
		for(String s : fluxMaterialList) {
			OreDictMaterial m = OreDictMaterial.get(s);
			if(m != OreDictMaterial.NULL && m != null) Utils.fluxCrystalMaterials.add(m);
			
		}
		config.save();
		
	}
}
