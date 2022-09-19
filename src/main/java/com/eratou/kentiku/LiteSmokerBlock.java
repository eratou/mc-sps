package com.eratou.kentiku;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LiteSmokerBlock extends Block{

	public LiteSmokerBlock(Properties properties){
		super(properties);
	}
	@OnlyIn(Dist.CLIENT)
	public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}
	public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
		return false;
	}
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	public boolean isSolid(BlockState bs){
		return false;
	}
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		//System.out.println("ani");
		if (rand.nextInt(50) == 0) {
			//Thread.dumpStack();
			worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
		}
		SmokerBlock.smoke(stateIn,worldIn,pos,rand);
	}
}
