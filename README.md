<style>
@import "http://fonts.googleapis.com/css?family=Open Sans:regular,semibold,italic,italicsemibold|Inconsolata&amp;v2";
body {
  font-family: "Open Sans";
  margin: 6em 2em 2em 2em;
}
body:before {
  content: '';
  position: fixed;
  top: 2%;
  right: 3%;
  height: 100px;
  width: 100px;
  background: url(https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/ontology.jpg) no-repeat 50% 50%;
  background-size: contain;
  z-index: 10;
  opacity: 0.1;
}
h1, h2, h3, h4 {
  font-weight: 600;
}
h1 {
  position: fixed;
  background: -webkit-linear-gradient(top, white 65%, rgba(255,255,255,0));
  background: -moz-linear-gradient(top, white 65%, rgba(255,255,255,0));
  background: -ms-linear-gradient(top, white 65%, rgba(255,255,255,0));
  background: -o-linear-gradient(top, white 65%, rgba(255,255,255,0));
  width: 100%;
  height: 80px;
  padding: 10px 10px 10px 1em;
  left: 0;
  top: 0;
  margin: 0;
}
h1 img {
  height: 30px;
  vertical-align: middle;
  margin-bottom: 8px;
}
a { color: navy; }
pre {
  background: #eee;
  margin-left: 2em;
  padding: 5px;
  border-left: 3px solid #ccc;
}
</style>

<h1> Dynamic Ontologies </h1>

<h2>This repository contains the implementation IEEE International Conference on Bioinformatics and Biomedicine 2017 paper, <a href="https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/DynamicOntologyGeneration-Paper.pdf">“Constructing Dynamic Ontologies from Biomedical Publications” (B587)</a></h2>
<br>
<br>
<h3><u>Highlights</u></h3>
<p>
<ul>
<li>Unstructured data to assertion</li>
<li>Pipeline approach for knowledge discovery</li>
<li>Data reduced and made more readable</li>
<li>Semantic framework that automatically generates an ontology</li>
<li>Web based application</li>
</ul>
</p>
<hr>
<h3><u>Repository Contents</u></h3>
<p>
<ol>
<li><h4>SparkOpenIE_tf-idf_Word2Vec : </h4>This folder contains source code for retrieving Triplets<Subject,Predicate,Object> from the collected our Pubmed data [Our corpus is a collection of 30 abstracts collected from the Pubmed Repository. 10 abstracts for each of the categories : Heart Disease, Diabetes and Obesity were collected.]. NLP techniques were used on the corpus to extract tokens and remove stopwords. tf-idf vectorization was done on the tokens. The generated tf-idf tokens were mapped to the OpenIE subject and object values in order to retrieve the "Assertions" </li>
<li><h4>TripletClustering_LDA : </h4>This folder contains source code for clustering the Assertions and discovering the topic for each of these clusters using the LDA topic modelling technique.</li>
<li><h4>OntConstructor : </h4>This folder contains source code for generating Ontology using the OWL API.</li>
</ol>
</p>
<hr>
<h3><u>Implementation Pipeline</u></h3>
<p>
<ol>
<li><h4>Assertion Discovery</h4>
<img src = "https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/assertionDiscovery.PNG">
</li>
<li><h4>Clustering and Topic Discovery</h4>
<img src = "https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/clustering.PNG">
</li>
<li><h4>Ontology Generation</h4>
<img src = "https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/ontology-gen.PNG">
</li>
<li><h4>Web Application</h4>
<img src = "https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/WebApp.PNG">
</li>
</ol>
</p>
<hr>
<h3><u>Results</u></h3>
<h4>The implementation brings out those Assertions that are unique to each of the Categories and the Assertions that are coomon in all the categories.</h4>
<p>
<ol>
<li><b>Assertion Discovery Results :</b> Randomly selected 30 abstract papers in three categories(Diabetes, Obesity and Heart Disease) through the PubMed API
<img src="https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/assertionResults.PNG">
</li>
<li><b>NLP and Feature Discovery Results :</b> Among the tf-idf terms, 92%, 76% and 69% of them are unique to Diabetes, Obesity and Heart Disease domains respectively.
<img src="https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/nlpResults.PNG">
</li>
<li><b>Topic Discovery and Clustering Results :</b> The below table shows the results from the Topic Discovery Component for each cluster
<img src="https://github.com/meghanagabhushan/IEEE-BIBM-2017---Constructing-Dynamic-Ontologies/blob/master/Images/clusteringResults.PNG">
</li>
</ol>
</p>
<hr>
<h3><u>Future Work</u></h3>
<p>
<ul>
<li>Model for a large corpus of data</li>
<li>Domain expert evaluation</li>
<li>Ontology enhancement</li>
</ul>
</p>