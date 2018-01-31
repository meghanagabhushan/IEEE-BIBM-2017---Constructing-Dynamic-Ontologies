package openie;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Quadruple;
import org.apache.commons.lang.WordUtils;

import java.io.*;
import java.util.*;

/**
 * Created by Megha Nagabhushan on 7/23/2017.
 */
public class MainMedNLP {

    public static String returnTriplets(String sentence) throws IOException {

        Document doc = new Document(sentence);
        FileWriter fwTriplets = new FileWriter("MedTripletsWithoutObj",true);
        FileWriter fwClasses = new FileWriter("Classes",true);
        FileWriter fwIndividuals = new FileWriter("Individuals",true);
        FileWriter filener = new FileWriter("NER",true);
        ArrayList<String> tfidfvalues = new ArrayList<String>();
        String filename = "tf.idf.values";



        String triplet="";
        String fileTriplet="";
        ArrayList<String> patternList = new ArrayList<String>();
        patternList.add("density");
        patternList.add("Density");
        patternList.add("Mouse");
        patternList.add("mouse");
        patternList.add("pattern");
        patternList.add("Pattern");
        patternList.add("Neurons");
        patternList.add("neurons");
        patternList.add("Rat");
        patternList.add("rat");

        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
            Collection<Quadruple<String, String, String, Double>> l = sent.openie();//.iterator();


            for (Quadruple x : l) {

                String subject = (String) x.first();
                String predicate = (String) x.second();
                String object = (String) x.third();

                triplet = formatWords(subject)+formatWords(predicate)+formatWords(object);

                if(!subject.equalsIgnoreCase("those") && !subject.equalsIgnoreCase("that") && !subject.equalsIgnoreCase("we") && !object.equalsIgnoreCase("which") && !object.equalsIgnoreCase("present") && !object.equalsIgnoreCase("similar") && !object.equalsIgnoreCase("the")) {
                    fileTriplet = formatWords(subject).replaceAll("-","").replaceAll("/","") + "," + formatWords(predicate) + "," + formatWords(object).replaceAll("-","").replaceAll("/","");
                    fwTriplets.write(fileTriplet + "\n");
                }
                String subjectNER = returnNER(subject);
                String objectNER = returnNER(object);
                if(!subjectNER.equals("O"))  {
                    fwClasses.write(subjectNER);
                }
                if(!objectNER.equals("O")){
                    fwClasses.write(objectNER);
                }
               filener.write(subject+","+subjectNER+","+object+","+objectNER+"\n");
                for(String str : patternList){
                    if(subject.contains(str)){
                        fwIndividuals.write("Pattern,"+formatWords(subject).replaceAll("-","").replaceAll("/","")+"\n");
                    }
                }

                    if(subject.contains("fibers") || subject.contains("immunoreactivity") || subject.contains("immunoreativefibers")){
                        fwIndividuals.write("ImmunoReactivity,"+formatWords(subject).replaceAll("-","").replaceAll("/","")+"\n");
                    }

                if(subject.contains("endomorphins") || subject.contains("receptors")){
                    fwIndividuals.write("Receptors,"+formatWords(subject).replaceAll("-","").replaceAll("/","")+"\n");
                }

                if(subject.contains("EM")){
                    fwIndividuals.write("EM,"+formatWords(subject).replaceAll("-","").replaceAll("/","")+"\n");
                }



                if(!subjectNER.equals("O")){
                    fwIndividuals.write(subjectNER+","+subject.replaceAll(" .*$","").replaceAll(" .*$","")+"\n");
                }


                //checking if a object has NER and adding the NER values into the class set and adding them as intance in Indiviaduals File

                if(!objectNER.equals("O")){
                    fwIndividuals.write(objectNER+","+object.replaceAll(" .*$","")+"\n");
                }


                Set<String> lines = new HashSet<String>(10000); // maybe should be bigger



                // processing(subject,predicate,object);



            }

        }
        fwIndividuals.close();
        fwClasses.close();
        fwTriplets.close();
        filener.close();
        stripDuplicatesFromFile("MedTriplets");
        stripDuplicatesFromFile("Individuals");
        stripDuplicatesFromFile("Classes");
     return triplet;
    }




    public static String formatWords(String word){
        String str = WordUtils.capitalize(word);
        return str.replaceAll(" ","");
    }




    public static String returnNER(String word){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(word);
        pipeline.annotate(document);
        String stringNER = "";
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                stringNER = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            }
        }
        return stringNER;
    }

    public static void stripDuplicatesFromFile(String filename) throws  IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : lines) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }
    public static void processing(String subject,String predicate,String object) throws IOException{
        String filename = "tf.idf.values";
        String tripletFile = "newTriplets";
        BufferedWriter writer = new BufferedWriter(new FileWriter(tripletFile));
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
        String line;
        while ((line = reader.readLine()) != null) {
            if(subject.contains(line)){
                writer.write(formatWords(subject).replaceAll("-","").replaceAll("/","")+","+formatWords(predicate).replaceAll("-","").replaceAll("/","")+","+formatWords(object).replaceAll("-","").replaceAll("/","")+"\n");
            }
            if(object.contains(line)){
                writer.write(formatWords(subject).replaceAll("-","").replaceAll("/","")+","+formatWords(predicate).replaceAll("-","").replaceAll("/","")+","+formatWords(object).replaceAll("-","").replaceAll("/","")+"\n");
            }
        }
        reader.close();
        stripDuplicatesFromFile("newTriplets");
        writer.close();
    }
    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }
    public static void tripletProcessing()throws IOException{
        String filename = "MedTripletsWithoutObj";
        String filename2 = "tf.idf.values";
        String tripletFile = "newTriplets";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        BufferedReader reader2 = new BufferedReader(new FileReader(filename2));
        String line;
        String line2;
        ArrayList<String> oldTrips = new ArrayList<String>();
        ArrayList<String> tfidfvalues = new ArrayList<String>();
        BufferedWriter tripwriter = new BufferedWriter(new FileWriter(tripletFile));

        while ((line2 = reader2.readLine()) != null) {
            tfidfvalues.add(line2);
        }
        /*for(String s : tfidfvalues){
            System.out.println("tf-idf -  "+s+"\n");
        }*/
        while ((line = reader.readLine()) != null) {
            for(String str2 : tfidfvalues){
                String lowercaseLine = line.toLowerCase();
                boolean value =  lowercaseLine.contains(str2);
                if(value == true){
                    /*tripwriter.write(line+",Obj\n");*/
                    tripwriter.write(line+"\n");
                }
            }
        }
        /*for(String str : oldTrips){
            for(String str2 : tfidfvalues){
                boolean value =  str.contentEquals(str2);
                if(value == true){
                    tripwriter.write(oldTrips+"\n");
                }
            }
        }*/

        reader.close();
        reader2.close();
        tripwriter.close();
        stripDuplicatesFromFile("newTriplets");

    }
    public static void tfidfprocessing()throws IOException{
        String stopwordFile = "stopwords";
        String tfidffile ="tfidf.values";
        String tfidfwords = "tf.idf.words";
        String tfidfline;
        String stopword;
        BufferedReader stopwordReader = new BufferedReader(new FileReader(stopwordFile));
        BufferedReader tfidfreader = new BufferedReader(new FileReader(tfidffile));
        BufferedWriter tfidfwriter = new BufferedWriter(new FileWriter(tfidfwords));
        ArrayList<String> tfidf = new ArrayList<>();
        while ((tfidfline = tfidfreader.readLine()) != null) {
            /*tfidfline.replaceAll(",","");
            tfidfline.replaceAll(".","");*/
            tfidf.add(tfidfline);
        }
       /* while ((tfidfline = tfidfreader.readLine()) != null) {
            while((stopword = stopwordReader.readLine()) != null){
                if(!tfidfline.contains(stopword)){
                    tfidfwriter.write(tfidfline);
                }
            }
        }*/
       for(String str : tfidf){
           str.replaceAll(",","");
           str.replaceAll(".","");
       }
        System.out.println("tfidf words"+tfidf);
        stopwordReader.close();
        tfidfreader.close();
        tfidfwriter.close();

    }

}
