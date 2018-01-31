package openie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Megha Nagabhushan on 1/26/2018.
 */
public class corrLDAFiltering {

    public static void main(String[] args) throws IOException{
        processing();
    }

    public static void processing()throws IOException{
        String ldaValues = "data/glda";
        String tripletFile = "data/Triplets/rec.sport.hockey";
        BufferedReader ldaReader = new BufferedReader(new FileReader(ldaValues));
        BufferedReader tripletReader = new BufferedReader(new FileReader(tripletFile));
        ArrayList<String> lda = new ArrayList<String>();
        String line;
        while ((line = ldaReader.readLine()) != null) {
            lda.add(line);
        }
        while ((line = tripletReader.readLine()) != null) {

                String[] linearray = line.split(",");


                String subject = linearray[0];
                String predicate = linearray[1];
                String object = linearray[2];


                subject = subject.toLowerCase();
                object = object.toLowerCase();
                for(String str : lda){
                    str = str.toLowerCase();
                    if(subject.equals(str)){
                        for(String str1 : lda){
                            if(object.equals(str1) && !object.equals(str)){
                                System.out.println(line);
                            }
                        }

                    }
                }



        }
        ldaReader.close();
        tripletReader.close();
    }
}

