package ru.kurandx.bibliobug;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GiveItemGui extends GuiScreen implements IreturnGui {
	private GuiTextField count;
	private GuiTextField metta;
	private GuiTextField ID;
	private GuiTextField name;
	private ArrayList<Integer> enchlevels;
	private int icount = 1;
	private int imetta = 0;
	private int iid = 0;
	private String sname = "";
	private GuiButton give;
	private GuiButton enchant;
	public NBTTagCompound itemnbt;
	
	public GiveItemGui() {
		int l = BiblioBugMod.enchantmanager.enchantlist.size();
		this.enchlevels = new ArrayList(l);
		for (int i = 0; i < l; i++) {
			this.enchlevels.add(Integer.valueOf(0));
		}
	}
	
	public GiveItemGui(ArrayList<Integer> enchlevels, TextDataSave data) {
		this.enchlevels = enchlevels;
		this.iid = data.ID;
		this.icount = data.count;
		this.imetta = data.metta;
		this.sname = data.name;
	}
	
	public void initGui() {
		super.initGui();
		
		this.buttonList.add(this.give = new GuiButton(0, this.width / 10 * 2 - 60, this.height - 40, 120, 20, "Give"));
		this.buttonList.add(this.enchant = new GuiButton(1, this.width / 10 * 8 - 60, this.height - 40, 120, 20, "Enchant"));
		
		this.ID = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10, 60, 20);
		this.ID.setText(Integer.valueOf(this.iid).toString());
		this.ID.setMaxStringLength(6);
		
		this.metta = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 2, 60, 20);
		this.metta.setText(Integer.valueOf(this.imetta).toString());
		this.metta.setMaxStringLength(6);
		
		this.count = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 3, 60, 20);
		this.count.setText(Integer.valueOf(this.icount).toString());
		this.count.setMaxStringLength(3);
		
		this.name = new GuiTextField(this.mc.fontRenderer, this.width / 2, this.height / 10 * 4, 60, 20);
		this.name.setText(this.sname);
		this.name.setMaxStringLength(300);
	}
	
	private boolean drawErrors() {
		boolean flag = true;
		String ids = this.ID.getText();
		String mettas = this.metta.getText();
		String counts = this.count.getText();
		this.sname = this.name.getText();
		if ((ids.equals("")) || (mettas.equals("")) || (counts.equals(""))) {
			drawString(this.mc.fontRenderer, "Field is Empty", this.width / 2, this.height / 10 * 5, 16711680);
			
			flag = false;
		}
		else {
			try {
				this.iid = Integer.parseInt(ids);
				this.imetta = Integer.parseInt(mettas);
				this.icount = Integer.parseInt(counts);
				if ((this.iid <= 0) || (this.imetta < 0) || (this.icount < 0) || (this.icount > 127)) {
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
		drawString(this.mc.fontRenderer, "ID", 40, this.height / 10 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Metta", 40, this.height / 10 * 2 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Count", 40, this.height / 10 * 3 + 5, 16777215);
		drawString(this.mc.fontRenderer, "Name(null to default)", 40, this.height / 10 * 4 + 5, 16777215);
	}
	
	public void updateScreen() {
		this.ID.updateCursorCounter();
		this.metta.updateCursorCounter();
		this.count.updateCursorCounter();
	}
	
	protected void keyTyped(char par1, int par2) {
		this.ID.textboxKeyTyped(par1, par2);
		this.metta.textboxKeyTyped(par1, par2);
		this.count.textboxKeyTyped(par1, par2);
		this.name.textboxKeyTyped(par1, par2);
		if (par2 == 1) {
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		}
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.ID.mouseClicked(par1, par2, par3);
		this.metta.mouseClicked(par1, par2, par3);
		this.count.mouseClicked(par1, par2, par3);
		this.name.mouseClicked(par1, par2, par3);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		this.ID.drawTextBox();
		this.metta.drawTextBox();
		this.count.drawTextBox();
		this.name.drawTextBox();
		this.give.enabled = drawErrors();
		drawInfo();
		super.drawScreen(par1, par2, par3);
	}
	
	protected void actionPerformed(GuiButton click) {
		switch (click.id) {
		case 0: 
			Item item = Item.getItemById(this.iid);
			if (item == null) {
				Block block = Block.getBlockById(this.iid);
				if (block == null) {
					return;
				}
				item = Item.getItemFromBlock(block);
			}
			ItemStack is = new ItemStack(item, this.icount, this.imetta);
			is.setTagCompound(this.itemnbt);
			if (is.stackTagCompound == null) {
				is.setTagCompound(new NBTTagCompound());
			}
			int l = BiblioBugMod.enchantmanager.enchantlist.size();
			NBTTagList nbttaglist = new NBTTagList();
			boolean flag = false;
			for (int i = 0; i < l; i++) {
				int lvl = ((Integer)this.enchlevels.get(i)).intValue();
				if (lvl > 0) {
					EnchantmentElement el = (EnchantmentElement)BiblioBugMod.enchantmanager.enchantlist.get(i);
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setShort("id", (short)el.effectId);
					nbttagcompound.setShort("lvl", (short)lvl);
					nbttaglist.appendTag(nbttagcompound);
					flag = true;
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
			BiblioBugMod.give_itemstack(is);
			this.mc.thePlayer.inventory.changeCurrentItem(-1);
			break;
		case 1: 
			Minecraft.getMinecraft().displayGuiScreen(new GuiEnchant(this, this.enchlevels, new TextDataSave(this.iid, this.icount, this.imetta, this.sname)));
		}
	}
	
	public void onGuiClosed() {
		super.onGuiClosed();
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public GuiScreen retusn_result(ArrayList<Integer> enchlevels, Object data) {
		return new GiveItemGui(enchlevels, (TextDataSave)data);
	}
}
