package com.eratou.kentiku;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class KentikuItemGroup extends ItemGroup{

	public KentikuItemGroup(){
		super("kentiku");
	}

	@Override
	public ItemStack createIcon(){
		return new ItemStack(Kentiku.MarkerBlock);
	}

}
