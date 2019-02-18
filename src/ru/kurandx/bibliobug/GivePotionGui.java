package ru.kurandx.bibliobug;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GivePotionGui
	extends GuiScreen {
	private GuiTextField count;
	private GuiTextField name;
	private ArrayList<PotionData> potions;
	private int icount = 1;
	private String sname = "";
	private boolean isSplash = false;
	private GuiButton give;
	private GuiButton potion;
	private GuiButton splash;
	
	public GivePotionGui() {
		int l = BiblioBugMod.potionmanager.potionlist.size();
		this.potions = new ArrayList(l);
		for (int i = 0; i < l; i++) {
			this.potions.add(new PotionData(((PotionElement)BiblioBugMod.potionmanager.potionlist.get(i)).effectId, 0, 0));
		}
	}
	
	public GivePotionGui(ArrayList<PotionData> potions, PotionGuiSave data) {
		this.potions = potions;
		this.icount = data.count;
		this.sname = data.name;
	}
	
	public void initGui() {
		super.initGui();
		
		this.buttonList.add(this.give = new GuiButton(0, this.width / 10 * 2 - 60, this.height - 40, 120, 20, "Give"));
		this.buttonList.add(this.splash = new GuiButton(2, this.width / 10 * 5 - 60, this.height - 40, 120, 20, "Is splash: false"));
		this.buttonList.add(this.potion = new GuiButton(1, this.width / 10 * 8 - 60, this.height - 40, 120, 20, "Effects"));
		
		this.count = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 1, 60, 20);
		this.count.setText(Integer.valueOf(this.icount).toString());
		this.count.setMaxStringLength(3);
		
		this.name = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 2, 60, 20);
		this.name.setText(this.sname);
		this.name.setMaxStringLength(300);
	}
	
	private boolean drawErrors() {
		boolean flag = true;
		String counts = this.count.getText();
		this.sname = this.name.getText();
		if (counts.equals("")) {
			drawString(this.mc.fontRenderer, "Field is Empty", this.width / 2, this.height / 10 * 3, 16711680);
			
			flag = false;
		}
		else {
			try {
				this.icount = Integer.parseInt(counts);
				if ((this.icount < 0) || (this.icount > 127)) {
					drawString(this.mc.fontRenderer, "Invalid number", this.width / 2, this.height / 10 * 3, 16711680);
					
					flag = false;
				}
			}
			catch (NumberFormatException e) {
				drawString(this.mc.fontRenderer, "Fieldcontent is not a number", this.width / 2 * 5, this.height / 10 * 3, 16711680);
				
				flag = false;
			}
		}
		return flag;
	}
	
	private void drawInfo() {
		drawString(this.mc.fontRenderer, "Count", 40, this.height / 10 * 1 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Name(null to default)", 40, this.height / 10 * 2 + 5, 16777215);
	}
	
	public void updateScreen() {
		this.count.updateCursorCounter();
	}
	
	protected void keyTyped(char par1, int par2) {
		this.count.textboxKeyTyped(par1, par2);
		this.name.textboxKeyTyped(par1, par2);
		if (par2 == 1) {
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.count.mouseClicked(par1, par2, par3);
		this.name.mouseClicked(par1, par2, par3);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		this.count.drawTextBox();
		this.name.drawTextBox();
		this.give.enabled = drawErrors();
		drawInfo();
		super.drawScreen(par1, par2, par3);
	}
	
	protected void actionPerformed(GuiButton click) {
		switch (click.id) {
		case 0: 
			ItemStack is = new ItemStack(Items.potionitem, this.icount, this.isSplash ? 16385 : 1);
			if (is.stackTagCompound == null) {
				is.setTagCompound(new NBTTagCompound());
			}
			int l = BiblioBugMod.potionmanager.potionlist.size();
			NBTTagList nbttaglist = new NBTTagList();
			boolean flag = false;
			for (int i = 0; i < l; i++) {
				PotionData potion = (PotionData)this.potions.get(i);
				if ((potion.Amplifier > 0) || (potion.Duration > 0)) {
					PotionElement el = (PotionElement)BiblioBugMod.potionmanager.potionlist.get(i);
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Id", (byte)el.effectId);
					nbttagcompound.setByte("Amplifier", (byte)potion.Amplifier);
					nbttagcompound.setInteger("Duration", potion.Duration);
					nbttagcompound.setBoolean("Ambient", false);
					nbttaglist.appendTag(nbttagcompound);
					flag = true;
				}
			}
			if (flag) {
				is.stackTagCompound.setTag("CustomPotionEffects", nbttaglist);
			}
			if (!this.sname.equals("")) {
				String n = this.sname;
				n = n.replaceAll("&", "§");
				is.setStackDisplayName(n);
			}
			BiblioBugMod.give_itemstack(is);
			this.mc.thePlayer.inventory.changeCurrentItem(-1);
			break;
		case 1: 
			Minecraft.getMinecraft().displayGuiScreen(new GuiPorion(this.potions, new PotionGuiSave(this.icount, this.sname, this.isSplash)));
			break;
		case 2: 
			this.isSplash = (!this.isSplash);
			this.splash.displayString = (this.isSplash ? "Is splash: true" : "Is splash: false");
		}
	}
	
	public void onGuiClosed() {
		super.onGuiClosed();
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
}
