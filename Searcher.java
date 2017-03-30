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
		reader = DirectoryReader.open(FSDirectory.open(Paths.get(cheminindex))); //lecture du indice
	    searcher = new IndexSearcher(reader); // implemente le search pour l'IndexReader
	    analyzer = new StandardAnalyzer(); // construit un analysateur
	}
	
	public void searchContent(String requete) throws ParseException, IOException {
		QueryParser parser = new QueryParser("content", analyzer); // analisateur de la requete
	    Query q = parser.parse(requete); // recherche analisé par le QueryParser

	    // Calcul de similarité entre la query et les documents
	    TopDocs results = searcher.search(q, 300000);
	    ScoreDoc[] hits = results.scoreDocs;        
	    System.out.println("Total : " +results.totalHits+ " documents répondent à la requête");
	    
	    int afficher;
		if (nbresultats < results.totalHits ){
	    	afficher = nbresultats;
	    }else{
	    	afficher = results.totalHits;
	    }
	    
	    //Pour montrer les résultats
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

		
		// Différents syntaxes pour les recherches
        Searcher se = new Searcher("/home/janaina/Bureau/projet_java/index");
        se.searchContent("beurre");
        //se.searchContent("pomme OR sucre"); 
        //se.searchContent("+sel +oignon"); 
        //se.searchContent("pomme*");
        //se.searchContent("(pomme OR banane) AND sucre");
        //se.searchContent("pomme NOT sucre");
        //se.searchContent("carotte~10");
        //se.searchContent("sel~");
        //se.searchContent("title:'BABA-utf-8' AND beurre"); //ça ne marche pas 
        //se.searchContent("titre:crème"); //ça ne marche pas
        //se.searchContent("title:(+crevettes +frites)"); //ça ne marche pas
	    
		  


	}
}	

