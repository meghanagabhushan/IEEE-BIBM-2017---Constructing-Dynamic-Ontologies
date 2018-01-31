package openie;

import java.io.*;
import java.util.*;

/**
 * Created by Megha Nagabhushan on 10/22/2017.
 */
public class PredicateExtractor {
    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new FileReader("data/TripletStopword/alt.atheism"));) {
            Map<Integer, String> mapHash = new HashMap<>();

            int i = 0;
            String st;

            while ((st = in.readLine()) != null) {
                st = st.trim();
                StringTokenizer tokenizer = new StringTokenizer(st);
                int j = 0;
                while (tokenizer.hasMoreTokens()) {
                    if (j == 1) {
                        mapHash.put(i, tokenizer.nextToken());
                        break;
                    } else {
                        tokenizer.nextToken();
                        j++;
                    }
                }
                //store the lexicon with position in the hashmap
                i++;
            }
            //System.out.println(mapHash);
            Collection<String> list = mapHash.values();
            for(Iterator<String> itr = list.iterator(); itr.hasNext();)
            {
                if(Collections.frequency(list, itr.next())>1)
                {
                    itr.remove();
                }
            }
            //System.out.println(list);
            ArrayList<String> predicates = new ArrayList<>();
            Iterator<String> crunchifyIterator = list.iterator();
            while (crunchifyIterator.hasNext()) {
                predicates.add(crunchifyIterator.next().replaceAll(
                        String.format("%s|%s|%s",
                                "(?<=[A-Z])(?=[A-Z][a-z])",
                                "(?<=[^A-Z])(?=[A-Z])",
                                "(?<=[A-Za-z])(?=[^A-Za-z])"
                        ),
                        " "
                ).toLowerCase());
            }
            BufferedWriter tripwriter = new BufferedWriter(new FileWriter("data/Predicates/alt.atheism"));
            for(String predicate : predicates){
                tripwriter.write(predicate+"\n");
            }

            ArrayList<String> stopRemovedPredicates = new ArrayList<>();
           /* Iterator<String> predicateIterator = list.iterator();
            while (predicateIterator.hasNext()) {
                String process = CoreNLP.returnLemma(predicateIterator.next());
                stopRemovedPredicates.add(process);
            }
            */
           /*String line;
            BufferedReader reader = new BufferedReader(new FileReader("data/english.stop"));
            while ((line = reader.readLine()) != null) {
                for(String str2 : predicates){
                    String lowercaseLine = str2.toLowerCase();
                    boolean value =  !str2.contains(line);
                    if(value == true){
                    *//*tripwriter.write(line+",Obj\n");*//*
                        stopRemovedPredicates.add(str2);
                    }
                }
            }
            System.out.println(stopRemovedPredicates);*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
