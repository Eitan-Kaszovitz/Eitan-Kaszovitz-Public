package edu.yu.cs.com1320.project.Impl;


import edu.yu.cs.com1320.project.DocumentStore;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Stage4Test {

    public static void main (String[] args) {

        //Document Tests//

        DocumentStoreImpl mystore = new DocumentStoreImpl();
        mystore.setMaxDocumentCount(3);

        //////////////////////
        String str1 = "folder/stuff";
        URI uri1 = URI.create(str1);
        String source1 = "String 1";
        ByteArrayInputStream in1 = new ByteArrayInputStream(source1.getBytes(StandardCharsets.UTF_8));

        int code1 = mystore.putDocument(in1, uri1, DocumentStore.CompressionFormat.GZIP);

        /////////////////////
        String str2 = "folder2/things";
        URI uri2 = URI.create(str2);
        String source2 = "String 2";
        ByteArrayInputStream in2 = new ByteArrayInputStream(source2.getBytes(StandardCharsets.UTF_8));

        int code2 = mystore.putDocument(in2, uri2, DocumentStore.CompressionFormat.BZIP2);


        /////////////////////
        String str3 = "folder3/morestuff";
        URI uri3 = URI.create(str3);
        String source3 = "String 3";
        ByteArrayInputStream in3 = new ByteArrayInputStream(source3.getBytes(StandardCharsets.UTF_8));

        int code3 = mystore.putDocument(in3, uri3, DocumentStore.CompressionFormat.SEVENZ);


        String str4 = "http://folder4/morethings";
        URI uri4 = URI.create(str4);
        String source4 = "String 4- Apples taste sour, so apples are bad.";
        ByteArrayInputStream in4 = new ByteArrayInputStream(source4.getBytes(StandardCharsets.UTF_8));

        int code4 = mystore.putDocument(in4, uri4, DocumentStore.CompressionFormat.BZIP2);

        String source5 = "String 1 new";
        ByteArrayInputStream in5 = new ByteArrayInputStream(source5.getBytes(StandardCharsets.UTF_8));

        int code5 = mystore.putDocument(in5, uri1, DocumentStore.CompressionFormat.GZIP);

        mystore.printdocStore();
        System.out.println();

        mystore.undo();
        mystore.printdocStore();

















    }



}
