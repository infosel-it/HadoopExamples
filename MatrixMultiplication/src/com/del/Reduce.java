package com.del;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;

public class Reduce
  extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                        throws IOException, InterruptedException {
                String[] value;
                HashMap<Integer, Float> hashA = new HashMap<Integer, Float>();
                HashMap<Integer, Float> hashB = new HashMap<Integer, Float>();
                System.out.println("hashA :" +hashA);
                System.out.println("hashB :" +hashB);
                
                for (Text val : values) {
                        value = val.toString().split(",");
                        System.out.println("value :" +value);
                        if (value[0].equals("M")) {
                                hashA.put(Integer.parseInt(value[1]), Float.parseFloat(value[2]));
                        } else {
                                hashB.put(Integer.parseInt(value[1]), Float.parseFloat(value[2]));
                        }
                        
                }
                int n = Integer.parseInt(context.getConfiguration().get("n"));
                System.out.println("n :" +n);
                float result = 0.0f;
                float m_ij;
                float n_jk;
                for (int j = 0; j < n; j++) {
                		System.out.println("----------------------------");
                		System.out.println("j:"+j);
                		System.out.println("hashA.containsKey(j) ? hashA.get(j) : 0.0f :" + (hashA.containsKey(j) ? hashA.get(j) : 0.0f));
                        m_ij = hashA.containsKey(j) ? hashA.get(j) : 0.0f;
                        System.out.println("m_ij :" + (m_ij));
                        System.out.println("hashB.containsKey(j) ? hashB.get(j) : 0.0f:" + (hashB.containsKey(j) ? hashB.get(j) : 0.0f));
                        n_jk = hashB.containsKey(j) ? hashB.get(j) : 0.0f;
                        System.out.println("n_jk :" + (n_jk));
                        result += m_ij * n_jk;
                        System.out.println("result :" + (result));
                        System.out.println("----------------------------");
                }
                if (result != 0.0f) {
                        context.write(null,
                                        new Text(key.toString() + "," + Float.toString(result)));
                        
                        System.out.println(key.toString() + "," + Float.toString(result));
                }
        }
}
