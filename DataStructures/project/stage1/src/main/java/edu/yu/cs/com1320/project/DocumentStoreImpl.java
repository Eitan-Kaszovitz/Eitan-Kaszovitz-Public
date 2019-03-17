package edu.yu.cs.com1320.project;

import sun.misc.IOUtils;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

class MyDocumentStore implements DocumentStore {

    MyHashTable<URI, Document> store;

    public DocumentStoreImpl() {
        this.store = new MyHashTable<>(4);
    }

    public void setDefaultCompressionFormat(CompressionFormat format) {

    }

    public int putDocument(InputStream input, URI uri) {
    }

    public int putDocument(InputStream input, URI uri, CompressionFormat format) {
        String docString = IOUtils.toString(input, StandardCharsets.UTF_8);
        if (store.get(uri) != null) {
            if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {
                return null;
            }
            else {
                break;
            }
        }
        else {
            //compress//
        }

    }

    public String getDocument(URI uri) {
        return store.get(uri);
    }

    public byte[] getCompressedDocument(URI uri) {

    }

    public boolean deleteDocument(URI uri) {

    }

}
