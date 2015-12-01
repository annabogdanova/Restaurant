package com.epam.restaurant.shared;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class hash {

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		map.put("sa_aa", "value");
		map.put("sa_aa", "value");
		map.put("ss_ab", "value");
		map.put("sb_aa", "value");
		
		System.out.println(map.size());
		
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			if (it.next().startsWith("ss")) {
				it.remove();
			}
		}
		
		System.out.println(map.size());
		System.out.println(map.toString());
		
	}
	
	
	
	
}
