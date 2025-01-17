package it.unisa.diem.javaadv24.group01.mysimpleirtool.components;

import it.unisa.diem.javaadv24.group01.mysimpleirtool.annotations.Status;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p></p>
 *
 * @author Giura Alessio Donato
 * @version 1.0
 */
@Status(status = "done")
public sealed class TextUnit implements Serializable permits Document, Query {
    private Map<String, Integer> textContent;

    @Status(status = "done")
    public TextUnit(){
        this.textContent = new HashMap<>();
    }

    public void setTextContent(Stream<String> words, Stream<String> stopwords){
        List<String> wordsList = words.collect(Collectors.toList());
        Set<String> stopwordsSet = stopwords.collect(Collectors.toSet());
        for(String word : wordsList) {
            if (!stopwordsSet.contains(word)) {
                if (textContent.containsKey(word))
                    textContent.put(word, textContent.get(word) + 1);
                else
                    textContent.put(word, 1);
            }
        }
    }

    public Map<String, Integer> getTextContent() {
        return textContent;
    }

    /**
     * Consente di costruire la vista vettoriale del contenuto del file.
     *
     * @param vocabulary il vocabolario da cui ricavare l'indice della parola
     * @return il contenuto del file in forma di vettore
     */
    @Status(status = "done")
    public ArrayList<Integer> mapToArray(Vocabulary vocabulary){
        // costruisco un array n-dimensionale con le frequenze messe tutte a zero
        ArrayList<Integer> vector = new ArrayList<>(textContent.values().size());
        for(Integer i : vector) vector.add(i, 0);

        // ottengo la lista delle parole
        String[] words = (String[]) textContent.keySet().toArray();
        for(String s : words){
            // aggiungo la frequenza della parola i-esima nella i-esima posizione del vettore
            vector.add(vocabulary.getWordIndex(s), textContent.get(s));
        }
        return vector;
    }
}
