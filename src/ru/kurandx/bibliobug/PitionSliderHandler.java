package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiTextField;

public class PitionSliderHandler
	implements GuiSlider.ISlider {
	private GuiPorion owner;
	
	public PitionSliderHandler(GuiPorion owner) {
		this.owner = owner;
	}
	
	public void onChangeSliderValue(GuiSlider slider) {
		int level = (int)(this.owner.levelslider.getValue() * 127.0D);
		int time = (int)(this.owner.timeslider.getValue() * 72000.0D);
		int i = this.owner.potionslot.getSelectIndex();
		if (i > -1) {
			this.owner.level.setEnabled(true);
			this.owner.time.setEnabled(true);
			
			PotionData data = (PotionData)this.owner.potions.get(i);
			data.Amplifier = level;
			data.Duration = time;
			
			this.owner.level.setText(Integer.valueOf(level).toString());
			this.owner.time.setText(Integer.valueOf(time).toString());
		}
		else {
			this.owner.levelslider.enabled = false;
			this.owner.level.setEnabled(false);
			this.owner.timeslider.enabled = false;
			this.owner.time.setEnabled(false);
		}
	}
}
