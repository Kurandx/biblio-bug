package ru.kurandx.bibliobug;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class KeyHandler {
	public KeyBinding keyG = new KeyBinding("Open give gui", 34, "key.categories.misc");
	public KeyBinding keyH = new KeyBinding("Open enchant gui", 35, "key.categories.misc");
	public KeyBinding keyP = new KeyBinding("Open give potion gui", 25, "key.categories.misc");
	
	public KeyHandler() {
		ClientRegistry.registerKeyBinding(this.keyG);
		ClientRegistry.registerKeyBinding(this.keyH);
		ClientRegistry.registerKeyBinding(this.keyP);
	}
	
	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		if (event.side == Side.SERVER) {
			return;
		}
		if (event.phase == TickEvent.Phase.START) {
			if (this.keyG.getIsKeyPressed()) {
				if (FMLClientHandler.instance().getClient().inGameHasFocus) {
					EntityPlayer player = event.player;
					if (player != null) {
						Minecraft.getMinecraft().displayGuiScreen(new GiveItemGui());
					}
				}
			}
			if (this.keyH.getIsKeyPressed()) {
				if (FMLClientHandler.instance().getClient().inGameHasFocus) {
					EntityPlayer player = event.player;
					if (player != null) {
						ItemStack is = player.inventory.getCurrentItem();
						if (is != null) {
							Minecraft.getMinecraft().displayGuiScreen(new ItemEnchantGui(is));
						}
					}
				}
			}
			if (this.keyP.getIsKeyPressed()) {
				if (FMLClientHandler.instance().getClient().inGameHasFocus) {
					EntityPlayer player = event.player;
					if (player != null) {
						Minecraft.getMinecraft().displayGuiScreen(new GivePotionGui());
					}
				}
			}
		}
	}
}
