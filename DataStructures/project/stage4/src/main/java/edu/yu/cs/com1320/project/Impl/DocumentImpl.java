package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.Document;
import edu.yu.cs.com1320.project.DocumentStore;

import java.net.URI;

public class DocumentImpl implements Document {

    private URI key;
    private DocumentStore.CompressionFormat compressionFormat;
    private int docHashcode;
    private byte[] compressedDoc;
    private String string;
    private HashTableImpl<String, Integer> wordCountTable;
    private String[] wordList;
    private long lastUsedTime;

    public DocumentImpl(URI k, DocumentStore.CompressionFormat f, String s, byte[] b) {
        this.key = k;
        this.compressionFormat = f;
        this.docHashcode = s.hashCode();
        this.compressedDoc = b;
        this.string = s;
        this.wordList = s.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().split("\\s+");
        this.wordCountTable = new HashTableImpl(50);
        this.createWordTable();
        this.lastUsedTime = 0;
    }


    public byte[] getDocument() {

        return compressedDoc;
    }

    public int getDocumentHashCode() {

        return docHashcode;
    }

    public URI getKey() {

        return key;
    }

    public DocumentStore.CompressionFormat getCompressionFormat() {

        return compressionFormat;
    }


    protected void createWordTable() {
        for (String current : this.wordList) {
            if (wordCountTable.get(current) != null) {
                wordCountTable.put(current, wordCountTable.get(current) + 1);
            }
            else {
                wordCountTable.put(current, 1);
            }
        }
    }

    public int wordCount(String word) {
        return wordCountTable.get(word);
    }

    public long getLastUseTime() {
        return this.lastUsedTime;
    }

    public void setLastUseTime(long timeInMilliseconds) {
        this.lastUsedTime = timeInMilliseconds;
    }

    @Override
    public String toString() {
        return this.string;
    }

    protected String[] getWords() {
        return this.wordList;
    }

    @Override
    public int compareTo(Document o) {
        if (this.getLastUseTime() > o.getLastUseTime()) {
            return 1;
        }
        else if (this.getLastUseTime() < o.getLastUseTime()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
