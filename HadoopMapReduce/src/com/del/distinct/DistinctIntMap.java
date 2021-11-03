package com.del.distinct;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistinctIntMap extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  protected void map(final LongWritable key, final Text value,
    final Context context) throws IOException, InterruptedException {
   context.write(new Text("MAPPER"), value);
   
   System.out.println("MAPPER Value :" +value);
  };
 }