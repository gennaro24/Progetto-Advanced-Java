package it.unisa.diem.javaadv24.group01.mysimpleirtool.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>La classe fornisce una rappresentazione, mediante astrazione, del vocabolario dei termini.</p>
 * 
 * <p>L'implementazione prevede l'utilizzo di una HashMap per questioni di efficienza. La mappa utilizza, per chiave, la parola stessa e, per valore, l'indice della parola.</p>
 * 
 * @author Giura Alessio Donato
 * @version 1.0
 */
public class Vocabulary {
    private HashMap<String, Integer> vocabulary;
    private HashSet<String> stopwords;
    private int index;
    
// ==================           COSTRUTTORI             ==================             
// TODO: costruttore per caricare lista delle stopwords da una risorsa di rete

public Vocabulary() throws IOException {
    this.vocabulary = new HashMap<>();
    index = 0;
    this.stopwords = loadStopwords();
}

public Vocabulary(File stopwordsFile) throws IOException {
    this.vocabulary = new HashMap<>();
    index = 0;
    this.stopwords = loadStopwords(stopwordsFile);
}

// ==================           METODI DI UTILITÀ       ==================             

    /**
     * <p>Aggiunge una parola al vocabolario.</p>
     * 
     * <p>L'indice associato a ciascuna parola è incrementato sequenzialmente in base all'ordine con cui queste appaiono nei documenti.</p>
     * 
     * @param word la parola da aggiungere al vocabolario
     */
    public void addWordToVocabulary(String word){
        if(!vocabulary.containsKey(word)){
            vocabulary.put(word, index);
            index++;
        } else 
            return;
    }

    /**
     * Consente di ottenere l'indice associato a ciascuna parola per la costruzione della vista vettoriale del documento.
     * 
     * @param word la parola di cui si desidera l'indice
     * @return l'indice della parola
     */
    public int getWordIndex(String word){
        if(vocabulary.containsKey(word))
            return vocabulary.get(word);
        else 
            return -1;
    }

    /**
     * Carica la lista delle stopwords di default.
     */
    public HashSet<String> loadStopwords() throws IOException {
        return loadStopwordsResource(Files.lines(new File("static-it_IT-stopwords").toPath()));
    }
    
    /**
     * Carica la lista delle stopwords da un file di testo locale.
     *  
     * @param stopwordsFile file delle stopwords
     */
    public HashSet<String> loadStopwords(File stopwordsFile) throws IOException {
        return loadStopwordsResource(Files.lines(stopwordsFile.toPath()));
    }

    private HashSet<String> loadStopwordsResource(Stream<String> stream){
        HashSet<String> stopwords = new HashSet<>();
        List<String> stopwordsList = stream.collect(Collectors.toList());
        for(String s : stopwordsList)
            stopwords.add(s);
        return stopwords;
    }

    public Stream<String> getStopwordsStream(){
        return stopwords.stream();
    }

    // TODO: [intermedio] Metodo per caricare la lista delle stopwords da una risorsa di rete.
    // public HashSet<String> loadStopwords(String url){}

    /**
     * Controlla se una parola è una stopword.
     * 
     * @param s la parola da controllare
     * @return true se la parola è una stopword, false altrimenti
     */
    public boolean isStopWord(String s){
        return stopwords.contains(s);
    }

}
