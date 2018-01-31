

import java.io.PrintStream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

import scala.collection.immutable.HashMap

/**
  * Created by Mayanka on 02-07-2017.
  */
object TripletClustering {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\Users\\Megha Nagabhushan\\Documents\\BDAA\\big_data")

    val conf = new SparkConf().setAppName(s"KMeansExample").setMaster("local[*]")
      .set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")

    val sc = new SparkContext(conf)

    val inputPath=Seq("data/Triplets-tfidf/alt.atheism")
    Logger.getRootLogger.setLevel(Level.WARN)

    val topic_output = new PrintStream("data/Triplets-tfidf/alt.atheism")
    // Load documents, and prepare them for KMeans.

    val preprocessStart = System.nanoTime()

    val (corpusVector, data, vocabSize) = preprocess(sc, inputPath)

    val actualCorpusSize = corpusVector.count()
    val actualVocabSize = vocabSize
    val preprocessElapsed = (System.nanoTime() - preprocessStart) / 1e9

    // Run KMeans.
    val startTime = System.nanoTime()

    val k= 10
    val numIterations=20

    val corpusKM=corpusVector.map(_._2)

    val model = KMeans.train(corpusKM, k, numIterations)

    val WSSSE = model.computeCost(corpusKM)

    println("Within Set Sum of Squared Errors = " + WSSSE)


    val elapsed = (System.nanoTime() - startTime) / 1e9

    val predictions = model.predict(corpusKM)

    val error = model.computeCost(corpusKM)

    println(error)

    val results = data.zip(predictions)

    val resultsA = results.collect()

    var hm = new HashMap[Int, Int]

    val cluster1= new PrintStream("data/cluster20NG-CorrLDA/cluster_1.txt")
    val cluster2= new PrintStream("data/cluster20NG-CorrLDA/cluster_2.txt")
    val cluster3= new PrintStream("data/cluster20NG-CorrLDA/cluster_3.txt")
    val cluster4= new PrintStream("data/cluster20NG-CorrLDA/cluster_4.txt")
    val cluster5= new PrintStream("data/cluster20NG-CorrLDA/cluster_5.txt")
    val cluster6= new PrintStream("data/cluster20NG-CorrLDA/cluster_6.txt")
    val cluster7= new PrintStream("data/cluster20NG-CorrLDA/cluster_7.txt")
    val cluster8= new PrintStream("data/cluster20NG-CorrLDA/cluster_8.txt")
    val cluster9= new PrintStream("data/cluster20NG-CorrLDA/cluster_9.txt")
    val cluster10= new PrintStream("data/cluster20NG-CorrLDA/cluster_10.txt")



    resultsA.foreach(f=>
     {
       if(f._2==0){
         cluster1.println(f._1._1)
       }
       else if(f._2==1){
         cluster2.println(f._1._1)
       }
       if(f._2==2){
         cluster3.println(f._1._1)
       }
       if(f._2==3){
         cluster4.println(f._1._1)
       }
       else if(f._2==4){
         cluster5.println(f._1._1)
       }
       if(f._2==5){
         cluster6.println(f._1._1)
       }
       if(f._2==6){
         cluster7.println(f._1._1)
       }
       else if(f._2==7){
         cluster8.println(f._1._1)
       }
       if(f._2==8){
         cluster9.println(f._1._1)
       }
       if(f._2==9){
         cluster10.println(f._1._1)
       }
     }
    )
    resultsA.foreach(f => {
      topic_output.println(f._1 +";" + f._2)
      if (hm.contains(f._2)) {
        var v = hm.get(f._2).get
        v = v + 1
        hm += f._2 -> v
      }
      else {
        hm += f._2 -> 1
      }
    })

    topic_output.close()
    sc.stop()

  }

  /**
    * Load documents, tokenize them, create vocabulary, and prepare documents as term count vectors.
    *
    * @return (corpus, vocabulary as array, total token count in corpus)
    */
  private def preprocess(sc: SparkContext, paths: Seq[String]): ( RDD[(Long,Vector)], RDD[(String,String)], Long) = {

    //Reading Stop Words file
    val stopWords=sc.textFile("data/stopwords.txt").collect()

    //broadcasting stopwords
    val stopWordsBroadCast=sc.broadcast(stopWords)


    val dataframe1= sc.textFile("data/CorrLDA-Triplets/rec.autos")
    //val path ="data/MedTriplets";
  //lemmatized form of text
    val df = dataframe1.map(f => {
     // val lemmatised=CoreNLP.returnLemma(f)
      val split = f.split(",")
      val lemmatised = f
      val splitString = lemmatised.split(",")
      (f,splitString)//file name, string
    })




    val dfseq=df.map(f => f._1.toSeq)

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF(df.count().toInt)  // VectorSize as the Size of the Vocab

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(dfseq)
    tf.cache()

    val idf = new IDF().fit(tf)
    //Creating Inverse Document Frequency
    val tfidf1 = idf.transform(tf)
    tfidf1.cache()


    var tfidf=tfidf1.zipWithIndex().map(_.swap)
    //tfidf = tfidf.map(_.swap)

    val dff= df.flatMap(f=>f._2)
    val vocab=dff.distinct().collect()
    tfidf.collect()

      val x = df.map(f=>
        (f._1,f._2.mkString(",")))
   // triplets

    (tfidf, x, dff.count()) // Vector, Data, total token count

  }
}

