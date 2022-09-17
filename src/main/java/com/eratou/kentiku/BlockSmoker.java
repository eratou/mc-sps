package com.eratou.kentiku;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockSmoker extends BaseEntityBlock{
	public BlockSmoker(){
		super(BlockBehaviour.Properties.of(Material.WOOD,MaterialColor.PODZOL).strength(2.0F).sound(SoundType.WOOD)
				.lightLevel(a -> 15).noOcclusion());
	}
	@Override
	public void animateTick(BlockState p_220918_,Level p_220919_,BlockPos p_220920_,RandomSource p_220921_){
		if(p_220921_.nextInt(10)==0){
			p_220919_.playLocalSound((double) p_220920_.getX()+0.5D,(double) p_220920_.getY()+0.5D,
					(double) p_220920_.getZ()+0.5D,SoundEvents.CAMPFIRE_CRACKLE,SoundSource.BLOCKS,
					0.5F+p_220921_.nextFloat(),p_220921_.nextFloat()*0.7F+0.6F,false);
		}
	}
	@Override
	public BlockEntity newBlockEntity(BlockPos p_152759_,BlockState p_152760_){
		return new SmokerEntity(p_152759_,p_152760_);
	}
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_,BlockState p_152756_,
			BlockEntityType<T> p_152757_){
		if(p_152755_.isClientSide){
			return createTickerHelper(p_152757_,Kentiku.SMOKER_ENTITY.get(),(a,b,c,d)->{
				if(a.random.nextFloat()<0.2F){
					particle(a,b);
				}
			});
		}else{
			return null;
		}
	}
	public static void particle(Level p_155319_,BlockPos p_155320_) {
		double x=(double) p_155320_.getX()+0.5D;
		double y=(double) p_155320_.getY()+1.5D;
		double z=(double) p_155320_.getZ()+0.5D;
		for(int k=0;k<4;++k){
			x+=(p_155319_.random.nextFloat()-0.5)/3F;
			z+=(p_155319_.random.nextFloat()-0.5)/3F;
			float s=p_155319_.random.nextFloat();
			p_155319_.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,x,y,z,0D,s/20D+0.04D,0D);
			//p_155319_.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,d0,d1,d2,0D,s/20D+0.05D,0D);//0.0005
		}
	}
}
