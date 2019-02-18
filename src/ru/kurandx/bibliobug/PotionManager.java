package ru.kurandx.bibliobug;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;

public class PotionManager {
	public final HashMap<Integer, Integer> map = new HashMap();
	public final ArrayList<PotionElement> potionlist = new ArrayList();
	
	public PotionManager() {
		int l = Potion.potionTypes.length;
		
		int id = 0;
		for (int i = 0; i < l; i++) {
			Potion potion = Potion.potionTypes[i];
			if (potion != null) {
				this.map.put(Integer.valueOf(potion.getId()), Integer.valueOf(id));
				this.potionlist.add(new PotionElement(StatCollector.translateToLocal(potion.getName()), potion.getId()));
				id++;
			}
		}
		System.out.println("==================================================");
		System.out.println("Detected " + this.potionlist.size() + " Potions Effect.");
		System.out.println("==================================================");
	}
}
