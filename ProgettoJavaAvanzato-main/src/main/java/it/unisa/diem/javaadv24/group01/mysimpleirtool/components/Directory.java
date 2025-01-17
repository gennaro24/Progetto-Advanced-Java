package it.unisa.diem.javaadv24.group01.mysimpleirtool.components;

import it.unisa.diem.javaadv24.group01.mysimpleirtool.annotations.NeedsUpgrade;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;


/**
 * <p>Rappresentazione, mediante astrazione, di una directory.</p>
 * 
 * <p>Si rappresenta la directory in termini dei documenti che contiene</p>
 * 
 * @author Giura Alessio Donato
 * @version 1.0
 */
public class Directory implements Serializable {
    private HashSet<Document> directory;
    private String path;
    private Vocabulary vocabulary;

    public Directory(String path) throws IOException, NoSuchAlgorithmException{
        this.directory = directoryInitializer();

        // TODO: sto path rende tutto più difficile...
        this.path = path;

        this.vocabulary = new Vocabulary();
    }

    // TODO: filtering per files .txt => filefilter ha metodo accept() [interfacce funzionali]
    // TODO: c'è la necessità di passare al documento la lista delle stopwords
    // TODO: [intermedio] MIME-Types per individuare i files testuali anziché escluderli solo per l'estensione.
    @NeedsUpgrade(upgrades = {"Lambdas", "Stream API"})
    private HashSet<Document> directoryInitializer() throws IOException, NoSuchAlgorithmException {
        HashSet<Document> files = new HashSet<>();
        File[] filesList = new File(path).listFiles(); 
        for(File f : filesList){
            directory.add(new Document(f, vocabulary.getStopwordsStream()));
        }
        return files;
    }
    
    public void directoryUpdater() throws IOException, NoSuchAlgorithmException {
        File[] filesArray = new File(this.path).listFiles();
        List<Document> documents = directory.stream().toList();
        HashMap<Integer, Document> documentMapWithSignatures = new HashMap<>();
        documents.forEach((document) -> documentMapWithSignatures.put(document.hashCode(), document));

        for(File f : filesArray){
            if(this.directory.contains(f.getName().hashCode())){
                if(!documentMapWithSignatures.get(f.hashCode()).getHash().equals(calculateHash(f))) {
                    documentMapWithSignatures.get(f.hashCode()).updateFile(vocabulary.getStopwordsStream());
                }
            } else {
                this.directory.add(new Document(f, this.vocabulary.getStopwordsStream()));
            }
        }
        obsoleteFilesCleanup(filesArray);
    }

    private void obsoleteFilesCleanup(File[] filesArray){
        HashSet<Integer> currentFiles = new HashSet<>();
        Iterator<Document> iterator = this.directory.iterator();
        Document d = iterator.next();

        for(File f : filesArray){
            currentFiles.add(f.getName().hashCode());
        }

        while(iterator.hasNext()){
            if(!currentFiles.contains(d.hashCode()))
                directory.remove(d);
        }

        iterator.next();
    }

    private String calculateHash(File f) throws NoSuchAlgorithmException, IOException{
        MessageDigest digest = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(f.getAbsolutePath());
        byte[] byteArray = new byte[10240];
        int bytesRead = 0;

        while((bytesRead = fis.read(byteArray)) != -1){
            digest.update(byteArray, 0, bytesRead);
        }

        fis.close();

        return digest.digest().toString();
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public static Directory readIndexingStatus(String path) throws IOException, NoSuchAlgorithmException , ClassNotFoundException{

        ObjectInputStream obj = new ObjectInputStream(new FileInputStream(path));
        Directory directory = (Directory) obj.readObject();
        obj.close();
        return directory;

    }
    
    /**
     * 
     */
    public void writeIndexingStatus(String path) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(path));
        obj.writeObject(this);
        obj.close();
    }
}
