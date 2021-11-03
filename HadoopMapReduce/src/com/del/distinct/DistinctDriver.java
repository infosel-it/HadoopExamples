package com.del.distinct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DistinctDriver extends Configured implements Tool{

	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		args = new GenericOptionsParser(conf, args).getRemainingArgs();
		String input = args[0];
		String output = args[1];
				
		Job job = new Job(conf, "Avg");
		job.setJarByClass(DistinctDriver.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(DistinctIntMap.class);
		//job.setMapOutputKeyClass(IntWritable.class);
		//job.setMapOutputValueClass(IntWritable.class);
		job.setNumReduceTasks(2);
		
		//job.setCombinerClass(Combiner.class);
		job.setReducerClass(DistinctReducer.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(input));
		Path outPath = new Path(output);
		FileOutputFormat.setOutputPath(job, outPath);
		outPath.getFileSystem(conf).delete(outPath, true);
		
		job.waitForCompletion(true);
		return (job.waitForCompletion(true) ? 0 : 1);
	}
	
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new DistinctDriver(), args);
        System.exit(exitCode);
    }
}
