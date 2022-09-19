package com.eratou.kentiku;

import java.util.Random;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class TileSmokerBlock extends TileEntity implements ITickableTileEntity{

	public TileSmokerBlock(){
		super(Kentiku.SMOKER);
	}
	@Override
	public void tick(){
		Random rand=getWorld().getRandom();
		if(rand.nextInt(10) == 0) {
			if (rand.nextInt(50) == 0) {
				//Thread.dumpStack();
				getWorld().playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
			}
			SmokerBlock.smoke(getBlockState(),getWorld(),getPos(),rand);
		}
	}
}
