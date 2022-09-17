package com.eratou.kentiku;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SmokerEntity extends BlockEntity{

	public SmokerEntity(BlockPos p_155301_,BlockState p_155302_){
		super(Kentiku.SMOKER_ENTITY.get(),p_155301_,p_155302_);
	}

}
