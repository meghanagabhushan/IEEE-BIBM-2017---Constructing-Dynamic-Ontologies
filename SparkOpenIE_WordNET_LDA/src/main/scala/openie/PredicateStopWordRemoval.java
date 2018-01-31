package openie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Megha Nagabhushan on 10/23/2017.
 */
public class PredicateStopWordRemoval {
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("data/Predicates/alt.atheism"));
        BufferedReader reader2 = new BufferedReader(new FileReader("data/stopwords.txt"));
        String line;
        String line2;
        while((line = reader.readLine()) != null){
            while((line2 = reader2.readLine()) != null){
                if(line.contains(line2)){
                    line.replaceAll(line2,"");
                }
            }
        }
    }
}
