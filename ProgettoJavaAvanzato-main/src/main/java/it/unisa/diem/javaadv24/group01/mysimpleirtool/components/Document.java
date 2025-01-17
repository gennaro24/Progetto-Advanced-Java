package it.unisa.diem.javaadv24.group01.mysimpleirtool.components;

import it.unisa.diem.javaadv24.group01.mysimpleirtool.annotations.NeedsUpgrade;
import javafx.scene.text.Text;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;

/**
 * <p>La classe fornisce, mediante astrazione, la rappresentazione di un documento di testo.</p>
 * 
 * <p>Per questioni di efficienza, il contenuto del documento è rappresentato mediante una {@see java.util.HashMap} che usa come chiave il termine stesso
 * e, come valore, la sua frequenza all'interno del documento.</p>
 * 
 * <p>Rimane possibile ottenere una rappresentazione in forma di vettore dello stesso documento con il metodo {@see it.unisa.diem.javaadv24.group01.mysimpleirtool.components.Document#mapToArray()},
 * previa realizzazione della classe {@see it.unisa.diem.javaadv24.group01.mysimpleirtool.components.Vocabulary}.</p>
 * 
 * @author Giura Alessio Donato
 * @version 1.0
 */
@NeedsUpgrade(upgrades = "Ereditarietà")
public final class Document extends TextUnit implements Serializable {
    private File fileObject;
    private String hash;
    private String fileName;



// =====================        COSTRUTTORE             =====================
// TODO: passaggio al costruttore delle stopwords

    public Document(File file, Stream<String> stopwords) throws IOException, NoSuchAlgorithmException {
        super();
        this.fileObject = file;
        this.hash = calculateHash();
        this.fileName = file.getName();
        super.setTextContent(this.getFileContent(), stopwords);
    }

// =====================        GETTERS & SETTERS       =====================

    public String getFileName() {
        return fileObject.getName();
    }

    public String getHash() {
        return hash;
    }

    public void setHash() throws IOException, NoSuchAlgorithmException {
        this.hash = calculateHash();
    }

    public void updateFile(Stream<String> stopwords) throws IOException, NoSuchAlgorithmException{
        super.setTextContent(getFileContent(), stopwords);
        setHash();
    }

// =====================        METODI UTILITÀ          =====================

    private Stream<String> getFileContent() throws IOException {
        return new BufferedReader(new FileReader(fileObject)).lines();
    }

    /**
     * Consente di calcolare l'hash MD5 del file.
     * 
     * @return l'hash MD5 del file in formato String.
     * 
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String calculateHash() throws NoSuchAlgorithmException, IOException{
        MessageDigest digest = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(fileObject.getAbsolutePath());
        byte[] byteArray = new byte[10240];
        int bytesRead = 0;

        while((bytesRead = fis.read(byteArray)) != -1){
            digest.update(byteArray, 0, bytesRead);
        }

        fis.close();

        return digest.digest().toString();
    }

    /**
     * <p>Restituisce un hashCode basandosi unicamente sul nome del file.</p>
     * 
     * La scelta è motivata dal fatto che non esistono due file con lo stesso nome all'interno della stessa directory.
     */
    @Override
    public int hashCode() {
        return fileObject.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Document) || obj == null){
            return false;
        }
        else return hash.equals( ((Document) obj).getHash() ) && fileName.equals( ((Document) obj).getFileName() );
    }
}