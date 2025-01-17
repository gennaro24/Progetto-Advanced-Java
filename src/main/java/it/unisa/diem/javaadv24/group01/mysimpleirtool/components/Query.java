package it.unisa.diem.javaadv24.group01.mysimpleirtool.components;

import it.unisa.diem.javaadv24.group01.mysimpleirtool.annotations.NeedsUpgrade;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * <p></p>
 *
 * @author Giura Alessio Donato
 * @version 1.0
 */
public final class Query extends TextUnit  {
    private String queryText;

    public Query(String queryText, Stream<String> stopwords){
        super();
        this.queryText = queryText;
        super.setTextContent(Arrays.stream(queryText.split(" ")), stopwords);
    }

    /**
     *
     *
     * @return
     */
    public double getScore(Document document){
        double score = 0.0;

        return score;
    }
}
