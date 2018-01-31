package openie

/**
  * Created by Megha Nagabhushan on 9/9/2017.
  */
import java.io.{File, PrintWriter}

import org.apache.spark.{SparkConf, SparkContext}

object stopwordsubobj {

  def main(args: Array[String]): Unit = {

    val pw = new PrintWriter(new File("data/TripletStopword/rec.motorcycles" ))


    System.setProperty("hadoop.home.dir", "C:\\Users\\Megha Nagabhushan\\Documents\\BDAA\\big_data")

    val sparkConf = new SparkConf().setAppName("stopsubobj").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val stopWords = sc.textFile("data/stopwords.txt").collect()
    val stopWordsBroadCast = sc.broadcast(stopWords)

    //Reading the Text File
    val documents = sc.textFile("data/Triplets/rec.motorcycles")

    //Getting the Lemmatised form of the words in TextFile
    val documentseq = documents.map(f => {
      val splitString = f.split(",")
      //val splitString = f.split("/t")
      splitString.toSeq
    })

    val stopWordRemovedDF = documentseq.map(f => {
      //Filtered numeric and special characters out
      val filteredF = f.map(_.replaceAll("[^a-zA-Z]", ""))
        //Filter out the Stop Words
        .filter(ff => {
        if (stopWordsBroadCast.value.contains(ff.toLowerCase))
          false
        else
          true
      })
      filteredF
    })
    val data=stopWordRemovedDF.map(f=>
    {
      var triplet=" "
      if(f.mkString(" ").split(" ").length==3)
      {
        triplet= f.mkString(" ")
      }
      else{

      }
      triplet
    })
    pw.write(data.collect().mkString("\n"))
  }
}

