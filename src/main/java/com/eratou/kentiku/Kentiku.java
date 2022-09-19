package com.eratou.kentiku;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Kentiku.MOD_ID)
public class Kentiku{
	public static final String MOD_ID = "eratou_kentiku";
	public static MarkerBlock MarkerBlock;
	private static Block smoker,lite_smoker;
	private static Item FillTool,Item_MarkerBlock,ItemSmokerBlock,ItemSmokerBlockL;
	public static TileEntityType<TileSmokerBlock> SMOKER = null;
	public static KentikuItemGroup tab=new KentikuItemGroup();
	public Kentiku() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.register(this);
	}
	private void setup(final FMLCommonSetupEvent event){

	}
	private void doClientStuff(final FMLClientSetupEvent event) {

	}
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onRegisterTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
			SMOKER=TileEntityType.Builder.create(TileSmokerBlock::new, smoker).build(null);
			SMOKER.setRegistryName("jp.eratou.tile_smoker");
			event.getRegistry().register(SMOKER);
		}
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			MarkerBlock = new MarkerBlock(Block.Properties.create(Material.SAND)
					.hardnessAndResistance(0f,1.0f).lightValue(15)
					.sound(SoundType.WOOD));
			MarkerBlock.setRegistryName(new ResourceLocation(MOD_ID, "marker_block"));
			smoker = new SmokerBlock(Block.Properties.create(Material.GLASS)
					.hardnessAndResistance(0f,1.0f)
					.sound(SoundType.WOOD));
			smoker.setRegistryName(new ResourceLocation(MOD_ID, "smoker_block"));
			lite_smoker = new LiteSmokerBlock(Block.Properties.create(Material.GLASS)
					.hardnessAndResistance(0f,1.0f)
					.sound(SoundType.WOOD));
			lite_smoker.setRegistryName(new ResourceLocation(MOD_ID, "lite_smoker_block"));
			blockRegistryEvent.getRegistry().registerAll(MarkerBlock,smoker,lite_smoker);
		}
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
			FillTool = new ItemFillTool(new Item.Properties().group(tab))
			.setRegistryName(new ResourceLocation(MOD_ID, "fill_tool"));
			Item_MarkerBlock = new BlockItem(MarkerBlock, new Item.Properties().group(tab))
			.setRegistryName(MarkerBlock.getRegistryName());
			ItemSmokerBlock = new BlockItem(smoker, new Item.Properties().group(tab))
			.setRegistryName(smoker.getRegistryName());
			ItemSmokerBlockL = new BlockItem(lite_smoker, new Item.Properties().group(tab))
			.setRegistryName(lite_smoker.getRegistryName());
			itemRegistryEvent.getRegistry().registerAll(FillTool,Item_MarkerBlock,ItemSmokerBlock,ItemSmokerBlockL);
		}
	}
}