package com.eratou.kentiku;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Kentiku.MODID)
public class Kentiku{
	public static final String MODID="eratou_kentiku";
	private static final Logger LOGGER=LogUtils.getLogger();
	public static DeferredRegister<Block> BLOCKS;
	public static DeferredRegister<Item> ITEMS;
	public static DeferredRegister<BlockEntityType<?>> TILE_ENTITYS;
	public static RegistryObject<BlockSmoker> SMOKER;
	public static RegistryObject<BlockLiteSmoker> LITE_SMOKER;
	public static RegistryObject<BlockEntityType<SmokerEntity>> SMOKER_ENTITY;

	public Kentiku(){
		IEventBus modEventBus=FMLJavaModLoadingContext.get().getModEventBus();

		BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
		ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
		TILE_ENTITYS=DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);

		modEventBus.addListener(this::commonSetup);

		SMOKER=BLOCKS.register("smoker_block",BlockSmoker::new);
		LITE_SMOKER=BLOCKS.register("lite_smoker_block",BlockLiteSmoker::new);
		ITEMS.register("smoker_block",()->new BlockItem(SMOKER.get(),new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
		ITEMS.register("lite_smoker_block",()->new BlockItem(LITE_SMOKER.get(),new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
		SMOKER_ENTITY=TILE_ENTITYS.register("jp.eratou.tile_smoker",() -> BlockEntityType.Builder.of(SmokerEntity::new,SMOKER.get()).build(null));

		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		TILE_ENTITYS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event){
		LOGGER.info("Eratou Kentiku COMMON SETUP");
		//LOGGER.info("DIRT BLOCK >> {}",ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
	}
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event){
		LOGGER.info("Eratou Kentiku server starting");
	}
	@Mod.EventBusSubscriber(modid=MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents{
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event){
			LOGGER.info("Eratou Kentiku CLIENT SETUP");
			//LOGGER.info("MINECRAFT NAME >> {}",Minecraft.getInstance().getUser().getName());
		}
	}
}
