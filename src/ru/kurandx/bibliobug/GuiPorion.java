package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiPorion
	extends GuiScreen {
	public ArrayList<PotionData> potions;
	public GuiPotionSlot potionslot;
	public PotionGuiSave data;
	public GuiTextField level;
	public GuiTextField time;
	public GuiSlider levelslider;
	public GuiSlider timeslider;
	
	public GuiPorion(ArrayList<PotionData> potions, PotionGuiSave data) {
		this.potions = potions;
		this.data = data;
	}
	
	protected void keyTyped(char par1, int par2) {
		this.level.textboxKeyTyped(par1, par2);
		this.time.textboxKeyTyped(par1, par2);
		int index = this.potionslot.getSelectIndex();
		boolean info = index > -1;
		
		this.levelslider.enabled = info;
		this.level.setEnabled(info);
		this.timeslider.enabled = info;
		this.time.setEnabled(info);
		if (info) {
			PotionData data = (PotionData)this.potions.get(index);
			try {
				int level = Integer.parseInt(this.level.getText());
				if (level > 127) {
					level = 127;
					this.level.setText("127");
				}
				if (level < 0) {
					level = 0;
					this.level.setText("0");
				}
				this.levelslider.setValue(level / 127.0D);
				data.Amplifier = level;
			}
			catch (NumberFormatException localNumberFormatException) {}
			try {
				int time = Integer.parseInt(this.time.getText());
				if (time > 75000) {
					time = 75000;
					this.time.setText("75000");
				}
				if (time < 0) {
					time = 0;
					this.time.setText("0");
				}
				this.timeslider.setValue(time / 75000.0D);
				data.Duration = time;
			}
			catch (NumberFormatException localNumberFormatException1) {}
		}
		if (par2 == 1) {
			Minecraft.getMinecraft().displayGuiScreen(new GivePotionGui(this.potions, this.data));
		}
	}
	
	public void initGui() {
		super.initGui();
		this.potionslot = new GuiPotionSlot(Minecraft.getMinecraft(), this.width, this.height, 25, this.height - 30, 20, this);
		
		this.level = new GuiTextField(this.mc.fontRenderer, this.width / 2 - 50, this.height - 25, 50, 20);
		this.level.setEnabled(false);
		this.level.setMaxStringLength(3);
		this.time = new GuiTextField(this.mc.fontRenderer, this.width - 55, this.height - 25, 50, 20);
		this.time.setEnabled(false);
		this.time.setMaxStringLength(5);
		
		this.buttonList.add(this.levelslider = new GuiSlider(0, 5, this.height - 25, this.width / 2 - 60, 20, "", "", 0.0D, 1.0D, 0.0D, false, false, new PitionSliderHandler(this)));
		this.buttonList.add(this.timeslider = new GuiSlider(1, this.width / 2 + 5, this.height - 25, this.width / 2 - 65, 20, "", "", 0.0D, 1.0D, 0.0D, false, false, new PitionSliderHandler(this)));
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.level.mouseClicked(par1, par2, par3);
		this.time.mouseClicked(par1, par2, par3);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		this.potionslot.drawScreen(par1, par2, par3);
		this.level.drawTextBox();
		this.time.drawTextBox();
		
		this.levelslider.drawButton(this.mc, par1, par2);
		this.timeslider.drawButton(this.mc, par1, par2);
	}
	
	public void updateScreen() {
		this.level.updateCursorCounter();
		this.time.updateCursorCounter();
	}
}
