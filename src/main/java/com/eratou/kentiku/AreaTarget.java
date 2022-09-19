package com.eratou.kentiku;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AreaTarget{
	private int[] poss=new int[3];
	private BlockState target;
	private BlockPos base;
	private int startX,endX,startY,endY,startZ,endZ;
	public AreaTarget(World w,BlockPos core) {
		base=core;
		target=w.getBlockState(core);
		boolean hit=true;
		int min=Integer.MAX_VALUE;
		for(int i=-64;i<128;i++) {
			BlockPos pos=core.add(i,0,0);
			if(pos.equals(core))continue;
			BlockState st=w.getBlockState(pos);
			if(target.getBlock()==st.getBlock()) {
				int sa=Math.abs(pos.getX()-core.getX());
				//System.out.print(sa+" ");
				if(sa<min) {
					min=sa;
					poss[0]=pos.getX();
				}
				hit=false;
			}
		}
		//System.out.println(min);
		if(hit)poss[0]=core.getX();
		hit=true;
		min=Integer.MAX_VALUE;
		for(int i=-64;i<128;i++) {
			BlockPos pos=core.add(0,i,0);
			if(pos.equals(core))continue;
			BlockState st=w.getBlockState(pos);
			if(target.getBlock()==st.getBlock()) {
				int sa=Math.abs(pos.getY()-core.getY());
				//System.out.print(sa+" ");
				if(sa<min) {
					min=sa;
					poss[1]=pos.getY();
				}
				hit=false;
			}
		}
		//System.out.println(min);
		if(hit)poss[1]=core.getY();
		hit=true;
		min=Integer.MAX_VALUE;
		for(int i=-64;i<128;i++) {
			BlockPos pos=core.add(0,0,i);
			if(pos.equals(core))continue;
			BlockState st=w.getBlockState(pos);
			if(target.getBlock()==st.getBlock()) {
				int sa=Math.abs(pos.getZ()-core.getZ());
				//System.out.print(sa+" ");
				if(sa<min) {
					min=sa;
					poss[2]=pos.getZ();
				}
				hit=false;
			}
		}
		//System.out.println(min);
		if(hit)poss[2]=core.getZ();
	}
	private void pos() {
		if(base.getX()<poss[0]) {
			startX=base.getX();
			endX=poss[0];
		}else {
			endX=base.getX();
			startX=poss[0];
		}
		if(base.getY()<poss[1]) {
			startY=base.getY();
			endY=poss[1];
		}else {
			endY=base.getY();
			startY=poss[1];
		}
		if(base.getZ()<poss[2]) {
			startZ=base.getZ();
			endZ=poss[2];
		}else {
			endZ=base.getZ();
			startZ=poss[2];
		}
	}
	public void fill(Action a) {
		pos();
		/*
		System.out.println("base x"+base.getX()+"y"+base.getY()+"z"+base.getZ());
		System.out.println("pos x"+poss[0]+"y"+poss[1]+"z"+poss[2]);
		System.out.println("x"+startX+"<"+endX);
		System.out.println("y"+startY+"<"+endY);
		System.out.println("z"+startZ+"<"+endZ);
		*/
		for(int x=startX;x<endX+1;x++) {
			for(int y=startY;y<endY+1;y++) {
				for(int z=startZ;z<endZ+1;z++) {
					a.run(x,y,z);
				}
			}
		}
	}
	public static interface Action{
		public void run(int x,int y,int z);
	}
}
