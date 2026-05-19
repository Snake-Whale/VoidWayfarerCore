package com.mordd.tileentity;

import gregapi.block.MaterialMachines;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.util.UT;
import gregtech.tileentity.multiblocks.MultiTileEntityLargeHeatExchanger;
import gregtech.tileentity.multiblocks.MultiTileEntityLargeTurbineGas;
import net.minecraft.block.Block;

import static gregapi.data.CS.*;

public class GT_TileEntityLoader {
	public static MultiTileEntityRegistry registry,gtRegistry;

	public static void load() {
		registry = MultiTileEntityRegistry.getRegistry("vd.multitileentity");
		gtRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
		MultiTileEntityBlock aMachine = MultiTileEntityBlock.getOrCreate(
				MD.GT.mID,
				"machine"      ,
				MaterialMachines.instance ,
				Block.soundTypeMetal,
				TOOL_wrench ,
				0,
				0,
				15,
				F,
				F);
		registry.add(
				"Large Heat Exchanger(Adamantium)",
				"Multiblock Machines",
				17996,
				17101,
				MultiTileEntityHugeHeatExchanger.class,
				MT.Ad.mToolQuality,
				16,
				aMachine,
				UT.NBT.make(
						NBT_MATERIAL,
						MT.Ad,
						NBT_HARDNESS,
						6.0F,
						NBT_RESISTANCE,
						6.0F,
						NBT_TEXTURE,
						"largeheatexchanger"      ,
						NBT_OUTPUT,
						131072,
						NBT_FUELMAP,
						FM.Hot,
						NBT_ENERGY_EMITTED,
						TD.Energy.HU
				),
				"DDD", "PMP", "DDD",
				'M',
				gtRegistry.getItem(18025),
				'D',
				OP.plateDense.dat(MT.AnnealedCopper),
				'P', OP.pipeHuge.dat(MT.Draconium));
		registry.add("Crystal Battery (IV)", "Batteries", 18000, 17110, MultiTileEntityBatteryEU8192.class, 0, 16, aMachine, UT.NBT.make(NBT_HARDNESS, 0.5F, NBT_RESISTANCE, 3.0F, NBT_COLOR, DYES_INT[DYE_INDEX_White], NBT_INPUT, V[5], NBT_CAPACITY, V[5]*8000, NBT_ENERGY_ACCEPTED, TD.Energy.EU), "WPW", "UMU", "DUD", 'M', gtRegistry.getItem(14505), 'P', IL.Processor_Crystal_Empty, 'U', IL.Circuit_Ultimate, 'D', OP.plateDense.dat(MT.BatteryAlloy), 'W', gtRegistry.getItem(10045));
		registry.add("Crystal Battery (LuV)", "Batteries", 18001, 17110, MultiTileEntityBatteryEU32768.class, 0, 16, aMachine, UT.NBT.make(NBT_HARDNESS, 0.5F, NBT_RESISTANCE, 3.0F, NBT_COLOR, DYES_INT[DYE_INDEX_White], NBT_INPUT, V[6], NBT_CAPACITY, V[5]*8000, NBT_ENERGY_ACCEPTED, TD.Energy.EU), "WPW", "UMU", "DPD", 'M', gtRegistry.getItem(14505), 'P', IL.Processor_Crystal_Empty, 'U', IL.Circuit_Ultimate, 'D', OP.plateDense.dat(MT.BatteryAlloy), 'W', gtRegistry.getItem(10046));
		registry.add("Crystal Battery (ZPM)", "Batteries", 18002, 17110, MultiTileEntityBatteryEU131072.class, 0, 16, aMachine, UT.NBT.make(NBT_HARDNESS, 0.5F, NBT_RESISTANCE, 3.0F, NBT_COLOR, DYES_INT[DYE_INDEX_White], NBT_INPUT, V[7], NBT_CAPACITY, V[5]*8000, NBT_ENERGY_ACCEPTED, TD.Energy.EU), "WPW", "PMP", "DPD", 'M', gtRegistry.getItem(14505), 'P', IL.Processor_Crystal_Empty, 'D', OP.plateDense.dat(MT.BatteryAlloy), 'W', gtRegistry.getItem(10047));

		registry.add("Magnalium Liquid-Fuel Gas Turbine Main Housing", "Multiblock Machines", 17991, 17101, MultiTileEntityLargeMotorLiquid.class, MT.StainlessSteel.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, MT.StainlessSteel, NBT_HARDNESS, 6.0F, NBT_RESISTANCE, 6.0F, NBT_TEXTURE, "gasturbine", NBT_DESIGN, 18022, NBT_INPUT, 6144, NBT_OUTPUT, 4096, NBT_WASTE_ENERGY, F, NBT_LIMIT_CONSUMPTION, T, NBT_ENERGY_EMITTED, TD.Energy.RU, NBT_FUELMAP, FM.Engine), "PUP", "BMC", "PEP", 'M', gtRegistry.getItem(17231), 'U', OP.pipeNonuple.dat(MT.StainlessSteel), 'B', gtRegistry.getItem(32748), 'C', OD_CIRCUITS[6], 'E', IL.PUMPS[1], 'P', OP.plateDense.dat(MT.StainlessSteel));
		registry.add("Trinitanium Liquid-Fuel Gas Turbine Main Housing", "Multiblock Machines", 17992, 17101, MultiTileEntityLargeMotorLiquid.class, MT.Ti.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, MT.Ti, NBT_HARDNESS, 9.0F, NBT_RESISTANCE, 9.0F, NBT_TEXTURE, "gasturbine", NBT_DESIGN, 18026, NBT_INPUT, 12288, NBT_OUTPUT, 8192, NBT_WASTE_ENERGY, F, NBT_LIMIT_CONSUMPTION, T, NBT_ENERGY_EMITTED, TD.Energy.RU, NBT_FUELMAP, FM.Engine), "PUP", "BMC", "PEP", 'M', gtRegistry.getItem(17232), 'U', OP.pipeNonuple.dat(MT.Ir), 'B', gtRegistry.getItem(32749), 'C', OD_CIRCUITS[6], 'E', IL.PUMPS[2], 'P', OP.plateDense.dat(MT.Ti));
		registry.add("Graphene Liquid-Fuel Gas Turbine Main Housing", "Multiblock Machines", 17993, 17101, MultiTileEntityLargeMotorLiquid.class, MT.TungstenSteel.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, MT.TungstenSteel, NBT_HARDNESS, 12.5F, NBT_RESISTANCE, 12.5F, NBT_TEXTURE, "gasturbine", NBT_DESIGN, 18023, NBT_INPUT, 24576, NBT_OUTPUT, 16384, NBT_WASTE_ENERGY, F, NBT_LIMIT_CONSUMPTION, T, NBT_ENERGY_EMITTED, TD.Energy.RU, NBT_FUELMAP, FM.Engine), "PUP", "BMC", "PEP", 'M', gtRegistry.getItem(17233), 'U', OP.pipeNonuple.dat(MT.W), 'B', gtRegistry.getItem(32079), 'C', OD_CIRCUITS[6], 'E', IL.PUMPS[3], 'P', OP.plateDense.dat(MT.TungstenSteel));
		registry.add("Vibramantium Liquid-Fuel Gas Turbine Main Housing", "Multiblock Machines", 17994, 17101, MultiTileEntityLargeMotorLiquid.class, MT.Ad.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, MT.Ad, NBT_HARDNESS, 9.0F, NBT_RESISTANCE, 9.0F, NBT_TEXTURE, "gasturbine", NBT_DESIGN, 18025, NBT_INPUT, 196608, NBT_OUTPUT, 131072, NBT_WASTE_ENERGY, F, NBT_LIMIT_CONSUMPTION, T, NBT_ENERGY_EMITTED, TD.Energy.RU, NBT_FUELMAP, FM.Engine), "PUP", "BMC", "PEP", 'M', gtRegistry.getItem(17234), 'U', OP.pipeNonuple.dat(MT.Ad), 'B', gtRegistry.getItem(32750), 'C', OD_CIRCUITS[6], 'E', IL.PUMPS[4], 'P', OP.plateDense.dat(MT.Ad));
	}
}
