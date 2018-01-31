package openie;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Megha Nagabhushan on 9/9/2017.
 */
public class trimmingOpenIE {
    public static void main(String[] args) throws IOException{
        String filename = "data/CVPR-data/cvpr-newTriplets/triplets2";
        List<String> lines = FileUtils.readLines(new File(filename));

        Iterator<String> i = lines.iterator();
        while (i.hasNext())
        {
            String line = i.next();
            if (line.trim().isEmpty())
                i.remove();
        }

        FileUtils.writeLines(new File(filename), lines);
           // replacespaces(filename);


    }
    public static void replacespaces(String filename)throws IOException{

        /*BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String newLine = line;
            newLine.replaceAll("\\s+", "_");
        }*/
        List<String> lines = FileUtils.readLines(new File(filename));


        Iterator<String> i = lines.iterator();
        while (i.hasNext())
        {
            String line = i.next();
            line.replaceAll("\\s+", "_");
        }

        FileUtils.writeLines(new File(filename), lines);
    }
}
