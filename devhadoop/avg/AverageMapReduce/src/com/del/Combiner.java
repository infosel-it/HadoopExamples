package com.del;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Combiner extends Reducer<Text, Text, Text, Text> {
	  @Override
	  protected void reduce(final Text key, final Iterable<Text> values,
	    final Context context) throws IOException, InterruptedException {
	
		System.out.println("key :" +key + ", values ;" +values.toString());
	   Integer count = 0;
	   Double sum = 0D;
	   final Iterator<Text> itr = values.iterator();
	   while (itr.hasNext()) {
	    final String text = itr.next().toString();
	    final Double value = Double.parseDouble(text);
	    System.out.println("text :"+text + ", value :" +value);
	    count++;
	    sum += value;
	    
	    System.out.println("count :"+count + ", sum :" +sum);
	   }

	   final Double average = sum / count;
	   System.out.println("average :"+average);
	   context.write(new Text("A_C"), new Text(average + "_" + count));
	  };
	 }