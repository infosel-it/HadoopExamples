package com.del;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class Map extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println("key :" + key);
		Configuration conf = context.getConfiguration();
		int m = Integer.parseInt(conf.get("m"));
		int p = Integer.parseInt(conf.get("p"));
		String line = value.toString();
		System.out.println("line :" + line);
		String[] indicesAndValue = line.split(",");
		System.out.println("indicesAndValue :" + indicesAndValue);
		Text outputKey = new Text();
		Text outputValue = new Text();
		if (indicesAndValue[0].equals("M")) {
			for (int k = 0; k < p; k++) {
				System.out.println("----------------------------");
				System.out.println("k:" + k);
				System.out.println(indicesAndValue[1] + "," + k);
				outputKey.set(indicesAndValue[1] + "," + k);
				System.out.println(indicesAndValue[0] + "," + indicesAndValue[2] + "," + indicesAndValue[3]);
				outputValue.set(indicesAndValue[0] + "," + indicesAndValue[2] + "," + indicesAndValue[3]);
				System.out.println("outputKey :" + outputKey + "outputValue:" + outputValue);
				context.write(outputKey, outputValue);
				System.out.println("----------------------------");
			}
		} else {
			for (int i = 0; i < m; i++) {
				System.out.println("----------------------------");
				System.out.println("i:" + i);
				System.out.println(i + "," + indicesAndValue[2]);
				outputKey.set(i + "," + indicesAndValue[2]);
				outputValue.set("N," + indicesAndValue[1] + "," + indicesAndValue[3]);
				System.out.println("N," + indicesAndValue[1] + "," + indicesAndValue[3]);
				System.out.println("outputKey :" + outputKey + "outputValue:" + outputValue);
				context.write(outputKey, outputValue);
				System.out.println("----------------------------");
			}
		}
	}
}