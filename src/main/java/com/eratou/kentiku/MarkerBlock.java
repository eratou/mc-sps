package com.eratou.kentiku;

import com.eratou.kentiku.AreaTarget.Action;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class MarkerBlock extends Block{
	public MarkerBlock(final Properties properties) {
		super(properties);
	}
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack is=player.getHeldItem(handIn);
		if(is!=null) {
			Item item=is.getItem();
			Action ac;
			if(item instanceof BucketItem) {
				BucketItem bi=(BucketItem) item;
				ac=new LiquidAction(player, world, bi);
			}else {
				Block b=Block.getBlockFromItem(item);
				if(b==this)return false;
				BlockState bs=b.getDefaultState();
				BlockState ub=world.getBlockState(pos.up());
				boolean n=ub.getBlock()==b;
				ac=new AreaTarget.Action(){
					@Override
					public void run(int x,int y,int z){
						BlockPos pos=new BlockPos(x,y,z);
						//if(world.isAirBlock(pos)) {
							world.setBlockState(pos,n?ub:bs);
						//}//else break;
					}
				};
			}
			AreaTarget at=new AreaTarget(world, pos);
			at.fill(ac);
		}
		return true;
	}
	private class LiquidAction implements Action{
		private PlayerEntity player;
		private World world;
		private BucketItem bi;
		private LiquidAction(PlayerEntity p,World w,BucketItem b){
			player=p;
			world=w;
			bi=b;
		}
		@Override
		public void run(int x,int y,int z){
			BlockPos pos=new BlockPos(x,y,z);
			if(!world.isAirBlock(pos))world.setBlockState(pos,Blocks.AIR.getDefaultState());
			bi.tryPlaceContainedLiquid(player, world, pos, null);
		}
	}
}
