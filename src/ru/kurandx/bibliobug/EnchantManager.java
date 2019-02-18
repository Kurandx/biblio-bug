package ru.kurandx.bibliobug;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.StatCollector;

public class EnchantManager {
	public final HashMap<Integer, Integer> map = new HashMap();
	public final ArrayList<EnchantmentElement> enchantlist = new ArrayList();
	
	public EnchantManager() {
		int l = Enchantment.enchantmentsList.length;
		
		int id = 0;
		for (int i = 0; i < l; i++) {
			Enchantment effect = Enchantment.enchantmentsList[i];
			if (effect != null) {
				this.map.put(Integer.valueOf(effect.effectId), Integer.valueOf(id));
				this.enchantlist.add(new EnchantmentElement(StatCollector.translateToLocal(effect.getName()), effect.effectId));
				id++;
			}
		}
		System.out.println("==================================================");
		System.out.println("Detected " + this.enchantlist.size() + " Enchantments.");
		System.out.println("==================================================");
	}
}
