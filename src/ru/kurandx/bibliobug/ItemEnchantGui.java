package ru.kurandx.bibliobug;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemEnchantGui
	extends GuiScreen
	implements IreturnGui {
	public ArrayList<Integer> enchlevels;
	private IreturnGui returnclazz;
	private GuiButton give;
	private GuiButton enchant;
	private GuiTextField count;
	private GuiTextField metta;
	private GuiTextField name;
	private int icount = 1;
	private int imetta = 0;
	private String sname = "";
	private ItemStack is;
	
	public ItemEnchantGui(ItemStack is) {
		this.imetta = -1;
		this.icount = -1;
		this.is = is.copy();
		int i;
		
		int l = BiblioBugMod.enchantmanager.enchantlist.size();
		
		this.enchlevels = new ArrayList(l);
		for (i = 0; i < l; i++) {
			this.enchlevels.add(Integer.valueOf(0));
		}
		NBTTagList ench = this.is.getEnchantmentTagList();
		if (ench != null) {
			l = ench.tagCount();
			for (i = 0; i < l; i++) {
				NBTTagCompound tag = ench.getCompoundTagAt(i);
				if (tag != null) {
					int id = tag.getShort("id");
					int lvl = tag.getShort("lvl");
					int listid = ((Integer)BiblioBugMod.enchantmanager.map.get(Integer.valueOf(id))).intValue();
					System.out.println("Reading enchant ID " + id + " lvl: " + lvl + " li id " + listid);
					if ((listid > -1) && (listid < BiblioBugMod.enchantmanager.enchantlist.size())) {
						this.enchlevels.set(listid, Integer.valueOf(lvl));
					}
				}
			}
		}
		if (this.is.stackTagCompound != null) {
			if (this.is.stackTagCompound.hasKey("ench")) {
				this.is.stackTagCompound.removeTag("ench");
			}
		}
	}
	
	public ItemEnchantGui(ArrayList<Integer> enchlevels, EnchantGuiSave data) {
		this.enchlevels = enchlevels;
		this.is = data.is.copy();
		this.icount = data.count;
		this.imetta = data.metta;
		this.sname = data.name;
	}
	
	protected void keyTyped(char par1, int par2) {
		this.metta.textboxKeyTyped(par1, par2);
		this.count.textboxKeyTyped(par1, par2);
		this.name.textboxKeyTyped(par1, par2);
		if (par2 == 1) {
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		}
	}
	
	public void onGuiClosed() {
		super.onGuiClosed();
	}
	
	public void initGui() {
		super.initGui();
		
		this.buttonList.add(this.give = new GuiButton(0, this.width / 10 * 2 - 60, this.height - 40, 120, 20, "Set"));
		this.buttonList.add(this.enchant = new GuiButton(1, this.width / 10 * 8 - 60, this.height - 40, 120, 20, "Enchant"));
		
		this.metta = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 1, 60, 20);
		this.metta.setText(Integer.valueOf(this.imetta).toString());
		this.metta.setMaxStringLength(6);
		
		this.count = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 2, 60, 20);
		this.count.setText(Integer.valueOf(this.icount).toString());
		this.count.setMaxStringLength(3);
		
		this.name = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 3, 60, 20);
		this.name.setText(this.sname);
		this.name.setMaxStringLength(300);
	}
	
	protected void actionPerformed(GuiButton click) {
		switch (click.id) {
		case 0: 
			ItemStack is = this.is.copy();
			if (is.stackTagCompound == null) {
				is.setTagCompound(new NBTTagCompound());
			}
			int l = BiblioBugMod.enchantmanager.enchantlist.size();
			NBTTagList nbttaglist = new NBTTagList();
			boolean flag = false;
			for (int i = 0; i < l; i++) {
				int lvl = ((Integer)this.enchlevels.get(i)).intValue();
				if (lvl > 0) {
					flag = true;
					EnchantmentElement el = (EnchantmentElement)BiblioBugMod.enchantmanager.enchantlist.get(i);
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setShort("id", (short)el.effectId);
					nbttagcompound.setShort("lvl", (short)lvl);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
			if (flag) {
				is.stackTagCompound.setTag("ench", nbttaglist);
			} else {
				is.stackTagCompound.removeTag("ench");
			}
			if (!this.sname.equals("")) {
				String n = this.sname;
				n = n.replaceAll("&", "§");
				is.setStackDisplayName(n);
			}
			if (this.imetta > -1) {
				is.setItemDamage(this.imetta);
			}
			if (this.icount > -1) {
				is.stackSize = this.icount;
			}
			BiblioBugMod.give_itemstack(is);
			break;
		case 1: 
			Minecraft.getMinecraft().displayGuiScreen(new GuiEnchant(this, this.enchlevels, new EnchantGuiSave(this.is, this.icount, this.imetta, this.sname)));
		}
	}
	
	public GuiScreen retusn_result(ArrayList<Integer> enchlevels, Object data) {
		return new ItemEnchantGui(enchlevels, (EnchantGuiSave)data);
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.metta.mouseClicked(par1, par2, par3);
		this.count.mouseClicked(par1, par2, par3);
		this.name.mouseClicked(par1, par2, par3);
	}
	
	private boolean drawErrors() {
		boolean flag = true;
		String mettas = this.metta.getText();
		String counts = this.count.getText();
		this.sname = this.name.getText();
		if ((mettas.equals("")) || (counts.equals(""))) {
			drawString(this.mc.fontRenderer, "Field is Empty", this.width / 2, this.height / 10 * 5, 16711680);
			
			flag = false;
		}
		else {
			try {
				this.imetta = Integer.parseInt(mettas);
				this.icount = Integer.parseInt(counts);
				if ((this.imetta < -1) || (this.icount < -1) || (this.icount > 127)) {
					drawString(this.mc.fontRenderer, "Invalid number", this.width / 2, this.height / 10 * 5, 16711680);
					
					flag = false;
				}
			}
			catch (NumberFormatException e) {
				drawString(this.mc.fontRenderer, "Fieldcontent is not a number", this.width / 2 * 5, this.height / 10 * 2, 16711680);
				
				flag = false;
			}
		}
		return flag;
	}
	
	private void drawInfo() {
		drawString(this.mc.fontRenderer, "Metta", 40, this.height / 10 * 1 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Count", 40, this.height / 10 * 2 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Name(null to default)", 40, this.height / 10 * 3 + 5, 16777215);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		this.metta.drawTextBox();
		this.count.drawTextBox();
		this.name.drawTextBox();
		this.give.enabled = drawErrors();
		drawInfo();
		super.drawScreen(par1, par2, par3);
	}
}
