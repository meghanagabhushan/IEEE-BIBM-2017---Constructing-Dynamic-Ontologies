package openie;

import com.clearspring.analytics.util.Lists;
import com.google.common.base.Splitter;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Megha Nagabhushan on 9/8/2017.
 */
public class openIEwithTFIDF {

    public static void main(String[] args) throws IOException{
        ArrayList<String> tfidf = new ArrayList<String>();
        Scanner s = new Scanner(new File("data/20NGtfidf/comp.windows.x"));
        while (s.hasNext()){
            tfidf.add(s.next());
        }
        s.close();
        String filename = "data/TripletStopword/comp.windows.x";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        FileWriter fwTriplets = new FileWriter("data/Triplets-tfidf/comp.windows.x",true);
        ArrayList<String> importantTrip = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            for (String str:
                 tfidf) {
                    String lineLower = line.toLowerCase();
                    String strLower =  str.toLowerCase();
                    //String[] lineArray = lineLower.split(",");
                List<String> l = Lists.newArrayList(Splitter.on(" ").trimResults().split(lineLower));
                for (String string:
                     l) {
                    if(string.equals(strLower)){
                        //fwTriplets.write(line + "\n");
                        //fwTriplets.write(line + "\n");
                        importantTrip.add(line);
                    }

                }


            }

        }
        System.out.println(importantTrip);
        for(String str: importantTrip) {
            fwTriplets.write(str+"\n");
        }
        fwTriplets.close();
        //fwTriplets.close();

    }
}
