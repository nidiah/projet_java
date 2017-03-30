package lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * Cette classe crée un index Apache Lucene dans le chemin indiqué par l'utilisateur
 * et permet de faire des requêtes booleenes sur cet index
 */
public class ProjetLucene {
	MyIndexer idx;
	Searcher searcher;
	/**
	 * Constructeur 
	 * @param cheminindex Chemin vers le répertoire où sera crée l'index
	 * @throws IOException
	 */
	public ProjetLucene(String cheminindex) throws IOException {
		//initialisation de l'index et du searcher
    	 idx = new MyIndexer(cheminindex);
    	 searcher = new Searcher(idx.getIndexPath());
	}
	
	/**
	 * Crée l'index du corpus passé en paramètre
	 * @param chemincorpus Chemin vers le repertoire où se trouve le corpus
	 * @throws IOException
	 */
	public void createIndex(String chemincorpus) throws IOException{
		System.out.println("Indexation du corpus "+chemincorpus+"\n");
		idx.IndexerCorpus(chemincorpus);
	}
	
	/**
	 * Permet de faire des requêtes sur le contenu des fichiers du corpus
	 * @param query Requête
	 * @throws IOException
	 * @throws ParseException
	 */
	public void search(String query) throws IOException, ParseException{
		System.out.println("Requête : "+query);
		searcher.searchContent(query);
	}
	
	/**
	 * Permet de modifier le nombre des résultats affichés
	 * @param resultnum Nombre de résultats désirés
	 */
	public void setResultNumber(int resultnum){
		searcher.nbresultats = resultnum;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		//determiner le repertoire où sera créé l'index
		ProjetLucene pl = new ProjetLucene("/Users/nidiahernandez/Desktop/index");
		//donner le chemin du corpus à interroger
		pl.createIndex("/Users/nidiahernandez/Desktop/TP-FS/_txt/2013");
		//fixer nombre de resultats (sinon 10 par défaut)
		pl.setResultNumber(5);
		//interroger le corpus
		pl.search("France");
	}

}
