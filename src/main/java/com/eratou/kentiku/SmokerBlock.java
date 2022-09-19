package com.eratou.kentiku;

import java.util.Random;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokerBlock extends ContainerBlock{

	public SmokerBlock(Properties properties){
		super(properties);
		/*
		BlockState st=this.stateContainer.getBaseState();
		st.with(LIT, Boolean.valueOf(true));
		st.with(SIGNAL_FIRE, Boolean.valueOf(false));
		st.with(WATERLOGGED, Boolean.valueOf(false));
		st.with(FACING, Direction.NORTH);
		this.setDefaultState(st);
		*/
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
		//smoke(stateIn, worldIn, pos, rand);
	}
	public int tickRate(IWorldReader worldIn) {
		return 10;
	}
	public static void smoke(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(!worldIn.isRemote)return;
		//Random random=rand;
		for(int k = 0; k < 4; ++k) {
			double x=pos.getX()+(1-worldIn.getRandom().nextDouble());
			double y=pos.getY()+1+(1-worldIn.getRandom().nextDouble());
			double z=pos.getZ()+(1-worldIn.getRandom().nextDouble());
			worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,false,x,y,z, 0.0D, 0.07D, 0.0D);
			//IParticleData basicparticletype=ParticleTypes.CAMPFIRE_COSY_SMOKE;
			//double x= (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1);
			//double y=(double)pos.getY() + random.nextDouble() + random.nextDouble();
			//double z= (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1);
			//worldIn.func_217404_b(basicparticletype, true,x, y,z, 0.0D, 0.07D, 0.0D);
		}
	}
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileSmokerBlock();
	}
}
