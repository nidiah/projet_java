package lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

public class ProjetLucene {
	MyIndexer idx;
	Searcher searcher;
	
	public ProjetLucene(String cheminindex) throws IOException {
		//initialisation de l'index et du searcher
    	idx = new MyIndexer(cheminindex);
    	searcher = new Searcher(idx.getIndexPath());
	}
	
	public void createIndex(String chemincorpus) throws IOException{
		//création de l'index du corpus passé en paramètre
		System.out.println("Indexation du corpus "+chemincorpus+"\n");
		idx.IndexerCorpus(chemincorpus);
	}
	
	public void search(String query) throws IOException, ParseException{
		System.out.println("Requête : "+query);
		searcher.searchContent(query);
	}

	public void setResultNumber(int resultnum){
		searcher.nbresultats = resultnum;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		//determiner le repertoire où sera créé l'index
		ProjetLucene pl = new ProjetLucene("/Users/namelastname/Desktop/index");
		//donner le chemin du corpus à interroger
		pl.createIndex("/Users/namelastname/Desktop/corpus");
		//fixer nombre de resultats (sinon 10 par défaut)
		pl.setResultNumber(5);
		//interroger le corpus
		pl.search("Hello");
	}

}
