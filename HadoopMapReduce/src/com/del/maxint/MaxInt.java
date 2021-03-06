package com.del.maxint;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class MaxInt {
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable k, Text v, Context con) throws IOException, InterruptedException {
			String line = v.toString();
			String[] w = line.split(",");
			int max = Integer.parseInt(w[1]);
			con.write(new Text("largest integer"), new IntWritable(max));
		}
	}

	public static class Red extends Reducer<Text, IntWritable, IntWritable, Text> {
		public void reduce(Text k, Iterable<IntWritable> vlist, Context con) throws IOException, InterruptedException {
			int max = 0;
			for (IntWritable v : vlist) {
				max = Math.max(max, v.get());
			}

			con.write(new IntWritable(max), new Text(k));
		}
	}

	// main function
	public static void main(String[] args) throws Exception {
		Configuration c = new Configuration();
		Job j = new Job(c, "Find largest integer ");
		j.setJarByClass(MaxInt.class); // set the jar class
		j.setMapperClass(Map.class); // set the mapper class
		j.setReducerClass(Red.class); // set the reducer class
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

}
