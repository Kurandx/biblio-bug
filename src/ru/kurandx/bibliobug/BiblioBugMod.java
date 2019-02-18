package ru.kurandx.bibliobug;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jds.bibliocraft.BiblioCraft;
import net.minecraft.item.ItemStack;

@Mod(modid="KurandxBiblioBug", name="Biblio Bug by Kurandx", version="1.0.0.5")
public class MainModClass {
	@Mod.Instance("BiblioBug")
	public static MainModClass instance;
	public static EnchantManager enchantmanager;
	public static PotionManager potionmanager;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {}
	
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		enchantmanager = new EnchantManager();
		potionmanager = new PotionManager();
	}
	
	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent event) {}
	
	public static void give_itemstack(ItemStack is) {
		ByteBuf buffer = Unpooled.buffer();
		ByteBufUtils.writeItemStack(buffer, is);
		BiblioCraft.ch_BiblioClipboard.sendToServer(new FMLProxyPacket(buffer, "BiblioUpdateInv"));
	}
}
