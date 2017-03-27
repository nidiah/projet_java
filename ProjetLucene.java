package lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

public class ProjetLucene {
	MyIndexer idx;
	Searcher searcher;
	
	public ProjetLucene(String cheminindex) throws IOException {
		//initialisation de l'index
    	 idx = new MyIndexer(cheminindex);
	}
	
	public void createIndex(String chemincorpus) throws IOException{
		//création de l'index du corpus passé en paramètre
		System.out.println("Indexation du corpus "+chemincorpus+"\n");
		idx.IndexerCorpus(chemincorpus);
	}
	
	public void search(String query, int resultnum) throws IOException, ParseException{
		System.out.println("Requête : "+query);
		searcher = new Searcher(idx.getIndexPath());
		searcher.setResultNumber(resultnum);
		searcher.searchContent(query);
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		//determiner le repertoire où sera créé l'index
		ProjetLucene pl = new ProjetLucene("/Users/nidiahernandez/Desktop/index");
		//donner le chemin du corpus à interroger
		pl.createIndex("/Users/nidiahernandez/Desktop/TP-FS/_txt/2013");
		//interroger le corpus
		pl.search("France", 13);
	}

}
