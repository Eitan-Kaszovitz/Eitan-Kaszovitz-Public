package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.DocumentStore;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DocumentStoreImplTest {


    DocumentStoreImpl mystore = new DocumentStoreImpl();

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



    @Test
    public void test1() {
        assertEquals(mystore.getDocument(uri1), "String 1");
    }

    @Test
    public void test2() {
        assertEquals(mystore.getDocument(uri2), "String 2");
    }

    @Test
    public void test3() {
        mystore.setMaxDocumentCount(2);
        assertEquals(mystore.getDocument(uri1), "String 1");
    }

    @Test
    public void test4() {
        mystore.setMaxDocumentCount(3);
        assertEquals(mystore.getDocument(uri4), "String 4- Apples taste sour, so apples are bad.");
    }

    @Test
    public void test5() {
        mystore.getDocument(uri1);
        mystore.getDocument(uri2);
        mystore.getDocument(uri3);

        mystore.setMaxDocumentCount(3);

        List<String> list = new ArrayList<String>();
        list.add(mystore.getDocument(uri4));

        assertEquals(mystore.search("Apples"), list);
    }








}