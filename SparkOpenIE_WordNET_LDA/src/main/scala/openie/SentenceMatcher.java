package openie;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SentenceMatcher {

    public static void main(String args[]){

        //This is the target URL
        URL targetUrl;
        try {
            targetUrl = new URL("https://rxnlp-core.p.mashape.com/computeSimilarity");

            //First set the headers
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("X-Mashape-Key", "<GET_YOUR_MASHAPE_KEY>");

            String str1="Accumbens,Activated,CentralbasolateralNuclei";
            String str2="ParabrachialNucleus,IsWith,NotableStainingInSeptum";

            //Then set input
            String input = "{\"text1\":\""+str1
                    +"\",\"text2\":\""+str2
                    +"\",\"clean\":\"true\"}";

            //Next, process output
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            //Throw exception on error
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));

            //Printing output from server (you can use a json parser here instead)
            String output;
            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }

            //disconnect from server
            httpConnection.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
