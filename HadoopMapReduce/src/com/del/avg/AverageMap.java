package com.del.avg;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class AverageMap extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  protected void map(final LongWritable key, final Text value,
    final Context context) throws IOException, InterruptedException {
   context.write(new Text("MAPPER"), value);
   
   System.out.println("MAPPER Value :" +value);
  };
 }