package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiEnchant
	extends GuiScreen {
	public GuiEnchantSlot enchanmentslot;
	public ArrayList<Integer> enchlevels;
	public GuiSlider level;
	public GuiTextField level_t;
	private Object data;
	private IreturnGui returnclazz;
	
	public GuiEnchant(IreturnGui returnclazz, ArrayList<Integer> enchlevels, Object data) {
		this.enchlevels = enchlevels;
		this.data = data;
		this.returnclazz = returnclazz;
	}
	
	public void updateScreen() {
		this.level_t.updateCursorCounter();
	}
	
	protected void keyTyped(char par1, int par2) {
		this.level_t.textboxKeyTyped(par1, par2);
		try {
			int pos = Integer.parseInt(this.level_t.getText());
			int index = this.enchanmentslot.getSelectIndex();
			if (index > -1) {
				if (pos > 32767) {
					pos = 32767;
					this.level_t.setText("32767");
				}
				this.enchlevels.set(index, Integer.valueOf(pos));
				this.level.setValue(pos / 32767);
			}
			else {
				this.level.enabled = false;
				this.level_t.setEnabled(false);
			}
		}
		catch (NumberFormatException localNumberFormatException) {}
		if (par2 == 1) {
			Minecraft.getMinecraft().displayGuiScreen(this.returnclazz.retusn_result(this.enchlevels, this.data));
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.level_t.mouseClicked(par1, par2, par3);
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.add(this.level = new GuiSlider(0, 5, this.height - 25, this.width - 65, 20, "", "", 0.0D, 1.0D, 0.0D, false, false, new EnchantSliderEvent(this)));
		this.level.enabled = false;
		this.level_t = new GuiTextField(this.mc.fontRenderer, this.width - 55, this.height - 25, 50, 20);
		this.level_t.setEnabled(false);
		this.level_t.setMaxStringLength(5);
		this.enchanmentslot = new GuiEnchantSlot(Minecraft.getMinecraft(), this.width, this.height, 25, this.height - 30, 20, this);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		this.enchanmentslot.drawScreen(par1, par2, par3);
		this.level_t.drawTextBox();
		this.level.drawButton(this.mc, par1, par2);
	}
	
	public void onGuiClosed() {
		super.onGuiClosed();
	}
}
