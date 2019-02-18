package ru.kurandx.bibliobug;

import net.minecraft.item.ItemStack;

public class EnchantGuiSave {
	public ItemStack is;
	public int count;
	public int metta;
	public String name;
	
	public EnchantGuiSave(ItemStack is, int count, int metta, String name) {
		this.is = is.copy();
		this.count = count;
		this.metta = metta;
		this.name = name;
	}
}
