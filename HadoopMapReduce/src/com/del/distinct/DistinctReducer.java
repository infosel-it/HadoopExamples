package com.del.distinct;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DistinctReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(final Text key, final Iterable<Text> values, final Context context)
			throws IOException, InterruptedException {

		System.out.println("key :" + key + ", values ;" + values.toString());
		Integer count = 0;
		Double sum = 0D;
		final Iterator<Text> itr = values.iterator();
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

		context.write(new Text("Distinct Int Count"), new Text(distinctCount + ""));
	};
}