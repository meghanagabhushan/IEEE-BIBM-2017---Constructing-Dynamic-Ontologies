package openie;

import java.io.*;

/**
 * Created by Megha Nagabhushan on 9/5/2017.
 */
public class stopWordsRemoval {
    public static void main(String[] args) throws IOException{
        InputStream is = new FileInputStream("data/Triplets/rec.motorcycles");
        PrintWriter out = new PrintWriter("data/TripletStopword/rec.motorcycles");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
            String fileAsString = sb.toString();


        String alpha = fileAsString.replaceAll("[^a-zA-Z\n\\t' ']", " ");
        out.println(alpha);

    }

}
