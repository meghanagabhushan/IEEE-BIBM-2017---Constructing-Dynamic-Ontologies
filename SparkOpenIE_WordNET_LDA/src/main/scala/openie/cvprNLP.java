package openie;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Quadruple;
import org.apache.commons.lang.WordUtils;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Megha Nagabhushan on 11/1/2017.
 */
public class cvprNLP {
    public static String returnTriplets(String sentence) throws IOException {
        Document doc = new Document(sentence);


        String triplet="";


        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
            Collection<Quadruple<String, String, String, Double>> l = sent.openie();//.iterator();


            for (Quadruple x : l) {

                String subject = (String) x.first();
                String predicate = (String) x.second();
                String object = (String) x.third();

                triplet = formatWords(subject) + "\t"+formatWords(predicate) + "\t"+ formatWords(object);

            }
        }

        return triplet;
    }

    public static String formatWords(String word){
        String str = WordUtils.capitalize(word);
        return str.replaceAll(" ","");
    }

}
