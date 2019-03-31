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

    public DocumentImpl(URI k, DocumentStore.CompressionFormat f, String s, byte[] b) {
        this.key = k;
        this.compressionFormat = f;
        this.docHashcode = s.hashCode();
        this.compressedDoc = b;
        this.string = s;
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

    @Override
    public String toString() {
        return this.string;
    }


}
