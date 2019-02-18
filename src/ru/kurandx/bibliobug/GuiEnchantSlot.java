package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;

public class GuiEnchantSlot
	extends GuiSlot {
	private int selectedIndex = -1;
	private GuiEnchant owner;
	
	public GuiEnchantSlot(Minecraft par1Minecraft, int width, int height, int top, int bottom, int slotHeight, GuiEnchant owner) {
		super(par1Minecraft, width, height, top, bottom, slotHeight);
		this.owner = owner;
	}
	
	public int getSelectIndex() {
		return this.selectedIndex;
	}
	
	protected int getSize() {
		return BiblioBugMod.enchantmanager.enchantlist.size();
	}
	
	protected void elementClicked(int i, boolean p_148144_2_, int cx, int cy) {
		this.selectedIndex = i;
		if (i > -1) {
			this.owner.level.enabled = true;
			this.owner.level_t.setEnabled(true);
			int v = ((Integer)this.owner.enchlevels.get(i)).intValue();
			this.owner.level_t.setText(Integer.valueOf(v).toString());
			this.owner.level.setValue(v / 32767.0D);
		}
		else {
			this.owner.level.enabled = false;
			this.owner.level_t.setEnabled(false);
		}
	}
	
	protected boolean isSelected(int i) {
		return i == this.selectedIndex;
	}
	
	protected void drawBackground() {}
	
	protected void drawSlot(int i, int x, int y, int l, Tessellator tessellator, int j, int k) {
		EnchantmentElement slot = (EnchantmentElement)BiblioBugMod.enchantmanager.enchantlist.get(i);
		int level = ((Integer)this.owner.enchlevels.get(i)).intValue();
		this.owner.drawString(Minecraft.getMinecraft().fontRenderer, slot.Name, x + 2, y + 5, 16777215);
		this.owner.drawString(Minecraft.getMinecraft().fontRenderer, "LVL " + level, x - 60, y + 5, 16777215);
	}
}
