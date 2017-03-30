package lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 * Indexation de fichiers contenus dans un répertoire
 * @author nidiahernandez
 */
public class MyIndexer {
	private IndexWriter w;
	private String indexpath;
	
	/**
	 * Constructeur
	 * @param indexdir Repertoire où sera crée l'index 
	 * @throws IOException
	 */
	public MyIndexer(String indexdir) throws IOException { 
		//Creation de l'index
		indexpath = indexdir;
		FSDirectory dir = FSDirectory.open( Paths.get(indexpath)); //repertoire de l'index
		StandardAnalyzer analyzer = new StandardAnalyzer(); //Definition de l'analyseur pour tokenisation
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
	    w = new IndexWriter(dir, config);
	}
	
	/**
	 * Indexe les fichiers contenus dans un répertoire
	 * @param corpusdir Chemin vers le corpus
	 * @throws IOException
	 */
	public void IndexerCorpus(String corpusdir) throws IOException {
	    //AJout des fichiers à l'index
	    File f = new File(corpusdir);
		File filelist [] = f.listFiles();
		int docnum = 0;
		for (File fi : filelist) {
			Document doc = new Document();
			FileReader fr = new FileReader(fi);
			//définition des champs à indexer
            doc.add(new TextField("content", fr)); //texte de la recette
            doc.add(new StringField("path", fi.getPath(), Field.Store.YES)); //chemin complet
            doc.add(new StringField("filename", fi.getName(), Field.Store.YES)); //nom du fichier 
            w.addDocument(doc);
            docnum++;
            System.out.println(fi.getName()+" a été ajouté");   
            fr.close();
		}
		w.close();
		System.out.println("\n"+docnum+" documents indexés\n");
	}
	
	/**
	 * Rend le chemin où l'index a été crée
	 * @return
	 */
	public String getIndexPath(){
		return indexpath;
	}

   
	
    public static void main(String[] args) throws IOException {
    	MyIndexer indexer = new MyIndexer("/Users/nidiahernandez/Desktop/projet_java/index");
    	indexer.IndexerCorpus("/Users/nidiahernandez/Desktop/projet_java/corpus/recettes-utf-8");
  
		  }
}
