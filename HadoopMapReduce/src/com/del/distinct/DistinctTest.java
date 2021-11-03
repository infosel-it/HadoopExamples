package com.del.distinct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DistinctTest
{
	
	public static void main(String[] args) {
		
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("10");
		arrayList.add("20");
		arrayList.add("30");
		arrayList.add("10");
		arrayList.add("20");
		arrayList.add("40");
		arrayList.add("50");
		
		final Iterator<String> itr = arrayList.iterator();
		HashMap<Double, Integer> hashmap = new HashMap<>();
		
		while (itr.hasNext()) {
			final String text = itr.next().toString();
			final Double value = Double.parseDouble(text);
			System.out.println("text :" + text + ", value :" + value);

			if (hashmap.containsKey(value)) {
				int rptCount = hashmap.get(value);
				hashmap.put(value, rptCount+1);
			} else {
				hashmap.put(value, 1);
			}
		}
		
		System.out.println(hashmap);
		
		int distinctCount = 0;
		
		  for (Map.Entry<Double, Integer> entry : hashmap.entrySet())
		  {
			  if(entry.getValue().equals(new Integer(1)))
			  {
				  System.out.println("Distinct Int :" + entry.getKey() );
				  distinctCount = distinctCount +1;
			  }
			  else
			  {
				  System.out.println("Repating Int :" + entry.getKey() );
			  }
			  
		  }
		
		System.out.println("distinctCount :" + distinctCount);
	}
	
}