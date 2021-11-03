package com.del.avg;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageReduce extends Reducer<Text, Text, Text, Text> {
  @Override
  protected void reduce(final Text key, final Iterable<Text> values,
    final Context context) throws IOException, InterruptedException {
   Double sum = 0D;
   Integer totalcount = 0;
   final Iterator<Text> itr = values.iterator();
   while (itr.hasNext()) {
    final String text = itr.next().toString();
    System.out.println("text :" +text);
    final String[] tokens = text.split("_");
    System.out.println("tokens :" +tokens);
    final Double average = Double.parseDouble(tokens[0]);
    final Integer count = Integer.parseInt(tokens[1]);
    System.out.println("average :" +average);
    System.out.println("count :" +count);
    sum += (average * count);
    System.out.println("sum :" +sum);
    totalcount += count;
    System.out.println("totalcount :" +totalcount);
   }

   System.out.println(" Final sum :" +sum + " Final total Count :" +totalcount);
   final Double average = sum / totalcount;
   System.out.println("average :" +average);

   context.write(new Text("AVERAGE"), new Text(average.toString()));
  };
 }