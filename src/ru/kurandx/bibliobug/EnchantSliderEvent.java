package ru.kurandx.bibliobug;

import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiTextField;

public class EnchantSliderEvent
	implements GuiSlider.ISlider {
	private GuiEnchant owner;
	
	public EnchantSliderEvent(GuiEnchant owner) {
		this.owner = owner;
	}
	
	public void onChangeSliderValue(GuiSlider slider) {
		int pos = (int)Math.round(this.owner.level.getValue() * 32767.0D);
		int index = this.owner.enchanmentslot.getSelectIndex();
		if (index > -1) {
			this.owner.enchlevels.set(index, Integer.valueOf(pos));
			this.owner.level_t.setText(Integer.valueOf(pos).toString());
			this.owner.level_t.setEnabled(true);
		}
		else {
			this.owner.level.enabled = false;
			this.owner.level_t.setEnabled(false);
		}
	}
}
