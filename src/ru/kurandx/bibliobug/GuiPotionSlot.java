package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;

public class GuiPotionSlot
	extends GuiSlot {
	public GuiPorion owner;
	private int selectedIndex = -1;
	
	public GuiPotionSlot(Minecraft par1Minecraft, int width, int height, int top, int bottom, int slotHeight, GuiPorion owner) {
		super(par1Minecraft, width, height, top, bottom, slotHeight);
		this.owner = owner;
	}
	
	public int getSelectIndex() {
		return this.selectedIndex;
	}
	
	protected int getSize() {
		return BiblioBugMod.potionmanager.potionlist.size();
	}
	
	protected void elementClicked(int i, boolean p_148144_2_, int cx, int cy) {
		this.selectedIndex = i;
		if (i > -1) {
			PotionData data = (PotionData)this.owner.potions.get(i);
			this.owner.level.setEnabled(true);
			this.owner.level.setText(Integer.valueOf(data.Amplifier).toString());
			this.owner.levelslider.setValue(data.Amplifier / 127.0D);
			
			this.owner.time.setEnabled(true);
			this.owner.time.setText(Integer.valueOf(data.Duration).toString());
			this.owner.timeslider.setValue(data.Duration / 75000.0D);
		}
		else {
			this.owner.level.setEnabled(false);
			this.owner.time.setEnabled(false);
		}
	}
	
	protected boolean isSelected(int i) {
		return i == this.selectedIndex;
	}
	
	protected void drawBackground() {}
	
	protected void drawSlot(int i, int x, int y, int l, Tessellator tessellator, int j, int k) {
		PotionElement slot = (PotionElement)BiblioBugMod.potionmanager.potionlist.get(i);
		PotionData data = (PotionData)this.owner.potions.get(i);
		this.owner.drawString(Minecraft.getMinecraft().fontRenderer, slot.Name, x + 2, y + 5, 16777215);
		this.owner.drawString(Minecraft.getMinecraft().fontRenderer, "EL " + data.Amplifier + " TM " + data.Duration, x - 80, y + 5, 16777215);
	}
}
