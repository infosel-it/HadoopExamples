package com.del.iwc;

import java.io.*;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.Reducer.Context;
//import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class InputWC {
	public static class MultipleMapA extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		String s = "girl";
		Text t1 = new Text(s);

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				if (itr.nextToken().equals("girl")) {
					context.write(word, one);
				}
			}
		}
	}

	public static class MultipleMapB extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		String s = "girl";
		Text t1 = new Text(s);

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				if (itr.nextToken().equals("girl")) {
					context.write(word, one);
				}
			}
		}
	}

	public static class MultipleReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage :<inputlocation1> <inputlocation2> <outputlocation>");
			System.exit(0);
		}
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path p1 = new Path(files[0]);
		Path p2 = new Path(files[1]);
		Path p3 = new Path(files[2]);
		FileSystem fs = FileSystem.get(c);
		if (fs.exists(p3)) {
			fs.delete(p3, true);
		}
		Job job = Job.getInstance(c, "InputWC");
		job.setJarByClass(InputWC.class);
		MultipleInputs.addInputPath(job, p1, TextInputFormat.class, MultipleMapA.class);
		MultipleInputs.addInputPath(job, p2, TextInputFormat.class, MultipleMapB.class);
		job.setReducerClass(MultipleReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileOutputFormat.setOutputPath(job, p3);
		if (!job.waitForCompletion(true)) {
			System.exit(1);
		}
	}
}