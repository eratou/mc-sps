package com.eratou.kentiku;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockLiteSmoker extends AbstractGlassBlock{

	public BlockLiteSmoker(){
		super(BlockBehaviour.Properties.of(Material.WOOD,MaterialColor.PODZOL).strength(2.0F).sound(SoundType.WOOD)
				.lightLevel(a -> 0).noOcclusion());
	}
	@Override
	public boolean skipRendering(BlockState p_53972_,BlockState p_53973_,Direction p_53974_){
		return true;
	}
	@Override
	public float getShadeBrightness(BlockState p_48731_,BlockGetter p_48732_,BlockPos p_48733_){
		return 0.0F;
	}
	@Override
	public void animateTick(BlockState p_220918_,Level p_220919_,BlockPos p_220920_,RandomSource p_220921_){
		BlockSmoker.particle(p_220919_,p_220920_);
	}
}
