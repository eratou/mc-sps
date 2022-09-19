package com.eratou.kentiku;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemFillTool extends Item{
	public static final int REMOVE=-1,KEEP=0,REPLACE=1,FILLER=2;
	public ItemFillTool(Properties properties){
		super(properties);
	}
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		if(stack==null)return super.getDisplayName(stack);
		int v=stack.getDamage()+1;
		v=v*2+1;
		String message=v+"x"+v;
		String key=getTranslationKey(stack);
		CompoundNBT tag=stack.getTag();
		if(tag!=null) {
			if(tag.getBoolean("foreground")) {
				message+="fg";
			}
			switch(tag.getShort("type")) {
				case KEEP:
					key+="_keep";
					break;
				case REPLACE:
					key+="_replace";
					break;
				case REMOVE:
					key+="_remove";
					break;
				case FILLER:
					key+="_filler";
					break;
				default:
					key+="_unknown";
			}
		}
		return new TranslationTextComponent(key,message);
	}
	private ItemStack tagStack(int type,boolean fg) {
		ItemStack st=new ItemStack(this);
		CompoundNBT nbt=new CompoundNBT();
		nbt.putBoolean("foreground",fg);
		nbt.putShort("type",(short) type);
		st.setTag(nbt);
		return st;
	}
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(tagStack(KEEP,false));
			items.add(tagStack(KEEP,true));
			items.add(tagStack(REPLACE,false));
			items.add(tagStack(REPLACE,true));
			items.add(tagStack(REMOVE,false));
			items.add(tagStack(REMOVE,true));
			//items.add(tagStack(FILLER,false));
		}
	}
	public ActionResultType onItemUse(ItemUseContext context) {
		World world=context.getWorld();
		BlockPos blockpos=context.getPos();
		BlockState blockstate1;
		int type=0;
		if(context.getItem()!=null) {
			CompoundNBT tag=context.getItem().getTag();
			if(tag!=null) {
				type=tag.getShort("type");
			}
		}
		if(type==FILLER) {
			AreaTarget at=new AreaTarget(world, blockpos);
			at.fill(null);
			return ActionResultType.SUCCESS;
		}
		if(type==REMOVE)blockstate1=Blocks.VOID_AIR.getDefaultState();
		else blockstate1=world.getBlockState(blockpos);
		PlayerEntity playerentity=context.getPlayer();
		//if(!world.isRemote) {
			//String message="Face="+context.getFace();
			//playerentity.sendMessage(ITextComponent.Serializer.fromJson("[\"\",{\"text\":\""+message+"\"}]"));
		//}
		int sneek=0;
		if(playerentity.isSneaking()) {
			switch(context.getFace()) {
				case DOWN:
				case WEST:
				case NORTH:
					sneek=1;
					break;
				default:
					sneek=-1;
					break;
			}
		}
		boolean suc=set(context, blockstate1, sneek);
		if(!suc) {
			ItemStack item=context.getItem();
			CompoundNBT tag=item==null?null:item.getTag();
			if(tag!=null&&tag.getBoolean("foreground")) {
				switch(context.getFace()) {
					case DOWN:
					case WEST:
					case NORTH:
						sneek=-1;
						break;
					default:
						sneek=1;
						break;
				}
				suc=set(context, blockstate1, sneek);
			}
		}
		if(suc) {
			SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
			world.playSound(playerentity, blockpos,blockstate1.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			return ActionResultType.SUCCESS;
		}else {
			return ActionResultType.FAIL;
		}
	}
	public boolean set(ItemUseContext context,BlockState blockstate1,int d) {
		PlayerEntity playerentity=context.getPlayer();
		BlockPos blockpos=context.getPos();
		World world=context.getWorld();
		Block block=blockstate1.getBlock();
		boolean suc=false;
		int size=1;
		int type=0;
		if(context.getItem()!=null) {
			CompoundNBT tag=context.getItem().getTag();
			if(tag!=null) {
				type=tag.getShort("type");
			}
			size+=context.getItem().getDamage();
		}
		switch(context.getFace()) {
			case DOWN:
			case UP:
				for(int x=-size;x<size+1;x++)for(int z=-size;z<size+1;z++){
					BlockPos add=blockpos.add(x,d,z);
					//BlockPos add=blockpos.offset(d,1);
					if(type==KEEP&&!world.isAirBlock(add))continue;
					if(world.setBlockState(add,blockstate1))suc=true;
					block.onBlockPlacedBy(world, add, blockstate1, playerentity, new ItemStack(block.asItem()));
				}
				break;
			case WEST:
			case EAST:
				for(int y=-size;y<size+1;y++)for(int z=-size;z<size+1;z++){
					BlockPos add=blockpos.add(d,y,z);
					//BlockPos add=blockpos.offset(d,1);
					if(type==KEEP&&!world.isAirBlock(add))continue;
					if(world.setBlockState(add,blockstate1))suc=true;
					block.onBlockPlacedBy(world, add, blockstate1, playerentity, new ItemStack(block.asItem()));
				}
				break;
			case NORTH:
			case SOUTH:
				for(int x=-size;x<size+1;x++)for(int y=-size;y<size+1;y++){
					BlockPos add=blockpos.add(x,y,d);
					//BlockPos add=blockpos.offset(d,1);
					if(type==KEEP&&!world.isAirBlock(add))continue;
					if(world.setBlockState(add,blockstate1))suc=true;
					block.onBlockPlacedBy(world, add, blockstate1, playerentity, new ItemStack(block.asItem()));
				}
				break;
		}
		return suc;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		//System.out.println("a");
		return true;
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(playerIn.isSneaking()) {
			int nv=itemstack.getDamage()-1;
			if(nv>=0)itemstack.setDamage(nv);
		}else {
			int nv=itemstack.getDamage()+1;
			if(nv<10)itemstack.setDamage(nv);
		}
		if(!worldIn.isRemote) {
			int v=itemstack.getDamage()+1;
			v=v*2+1;
			String message=v+"x"+v;
			playerIn.sendMessage(ITextComponent.Serializer.fromJson("[\"\",{\"text\":\""+message+"\"}]"));
		}
		return super.onItemRightClick(worldIn,playerIn,handIn);
	}
}