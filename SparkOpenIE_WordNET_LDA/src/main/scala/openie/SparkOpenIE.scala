package openie

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}

import scala.io.Source;
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.HashMap

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sparkContext = new SparkContext(conf)


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    /*val outputFile = "tfidf.values";
    val tfidfFile = "tf.idf.words";
    val stopWordsFile = "data/stopwords.txt";
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))
    val tfidfWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tfidfFile)))
*/

    /*val fileToRead = sparkContext.textFile("altsample.txt")
    val stopWords=sparkContext.textFile("data/stopwords.txt").collect()
    val stopWordsBroadCast=sparkContext.broadcast(stopWords)

    val documentSequence = fileToRead.map(f => {
     // val lemmatisedWords = CoreNLP.returnLemma(f)
      val splitString = f.split(" ")
      //lemmatized.split or f.split
      splitString.toSeq
    })
    val hashingTF = new HashingTF()

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(documentSequence)
    tf.cache()


    val idf = new IDF().fit(tf)

    //Creating Inverse Document Frequency
    val tfidf = idf.transform(tf)

    val tfidfvalues = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val values = ff(2).replace("]", "").replace(")", "").split(",")
      values
    })

    val tfidfindex = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val indices = ff(1).replace("]", "").replace(")", "").split(",")
      indices
    })

    tfidf.foreach(f => println(f))

    val tfidfData = tfidfindex.zip(tfidfvalues)

    var hm = new HashMap[String, Double]

    tfidfData.collect().foreach(f => {
      hm += f._1 -> f._2.toDouble
    })

    val mapp = sparkContext.broadcast(hm)

    val documentData = documentSequence.flatMap(_.toList)
    val dd = documentData.map(f => {
      val i = hashingTF.indexOf(f)
      val h = mapp.value
      (f, h(i.toString))
    })

    val dd1 = dd.distinct().sortBy(_._2, false)


    dd1.take(50).foreach(f => {
      println(f)
      writer.write(f._1 + "\n")
    })
    writer.close()
*/
    val ipfile = sparkContext.textFile("data/extra").map(s => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val output = cvprNLP.returnTriplets(s)
      output
    })//should un comment

    /*for (line <- Source.fromFile(outputFile).getLines) {
      for(stopword <- Source.fromFile(stopWordsFile)){
        if(!line.contains(stopword)){
          tfidfWriter.write(line)
        }

      }
    }*/
    //MainMedNLP.tfidfprocessing() //shouls un comment
    //println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
  println(ipfile.collect().mkString("\n")) //should un comment

  // MainMedNLP.tripletProcessing()



  }

}
