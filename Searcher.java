package lucene;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;

public class Searcher {
	IndexReader reader;
	IndexSearcher searcher;
	Analyzer analyzer;
	int nbresultats = 10;//nombre de resultats par défaut
	
	public Searcher(String cheminindex) throws IOException {
		reader = DirectoryReader.open(FSDirectory.open(Paths.get(cheminindex)));
	    searcher = new IndexSearcher(reader);
	    analyzer = new StandardAnalyzer();
	}
	
	public void searchContent(String requete) throws ParseException, IOException {
		QueryParser parser = new QueryParser("content", analyzer);
	    Query q = parser.parse(requete);

	    TopDocs results = searcher.search(q, 300000);
	    ScoreDoc[] hits = results.scoreDocs;        
	    System.out.println("Total : " +results.totalHits+ " documents répondent à la requête");
	    
	    int afficher;
		if (nbresultats < results.totalHits ){
	    	afficher = nbresultats;
	    }else{
	    	afficher = results.totalHits;
	    }
	    
	    for (int i=0;i<afficher;i++) {    
	    	Document doc = searcher.doc(hits[i].doc);
	        if (doc != null) {
	        	System.out.println((i+1) + ".");
	        	System.out.println(doc.get("filename"));
	        	System.out.println(doc.get("path"));
	        	System.out.println("score : "+hits[i].score);
	        }
	    }
	    }
	
	// public void setResultNumber(int n){
	// 	this.nbresultats = n;
	// }
	
	public static void main(String[] args) throws IOException, ParseException {
		
		Searcher se = new Searcher("/Users/nidiahernandez/Desktop/projet_java/index");
	    //se.setResultNumber(4);
		se.searchContent("sel");
	    
		  


	}
}	
