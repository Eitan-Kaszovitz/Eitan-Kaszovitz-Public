package edu.yu.cs.com1320.project.Impl;


import edu.yu.cs.com1320.project.DocumentStore;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Stage3Test {

    public static void main (String[] args) {

        //Document Tests//

        DocumentStoreImpl mystore = new DocumentStoreImpl();

        //////////////////////
        String str1 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039682";
        URI uri1 = URI.create(str1);
        String source1 = "I love to ride my bike";
        ByteArrayInputStream in1 = new ByteArrayInputStream(source1.getBytes(StandardCharsets.UTF_8));


        int code1 = mystore.putDocument(in1, uri1, DocumentStore.CompressionFormat.GZIP);
        String doc1 = mystore.getDocument(uri1);


        /////////////////////
        String str2 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039683";
        URI uri2 = URI.create(str2);
        String source2 = "You always play basketball";
        ByteArrayInputStream in2 = new ByteArrayInputStream(source2.getBytes(StandardCharsets.UTF_8));


        int code2 = mystore.putDocument(in2, uri2, DocumentStore.CompressionFormat.BZIP2);
        String doc2 = mystore.getDocument(uri2);


        /////////////////////
        String str3 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039654";
        URI uri3 = URI.create(str3);
        String source3 = "I live in Cedarhurst";
        ByteArrayInputStream in3 = new ByteArrayInputStream(source3.getBytes(StandardCharsets.UTF_8));


        int code3 = mystore.putDocument(in3, uri3, DocumentStore.CompressionFormat.SEVENZ);
        String doc3 = mystore.getDocument(uri3);



        String str4 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri4 = URI.create(str4);
        String source4 = "This is an apple computer";
        ByteArrayInputStream in4 = new ByteArrayInputStream(source4.getBytes(StandardCharsets.UTF_8));


        int code4 = mystore.putDocument(in4, uri4, DocumentStore.CompressionFormat.BZIP2);
        String doc4 = mystore.getDocument(uri4);


        String str5 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networewewng+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri5 = URI.create(str5);
        String source5 = "An apple is better than a pear because an apple is sweeter";
        ByteArrayInputStream in5 = new ByteArrayInputStream(source5.getBytes(StandardCharsets.UTF_8));


        int code5 = mystore.putDocument(in5, uri5, DocumentStore.CompressionFormat.BZIP2);
        String doc5 = mystore.getDocument(uri5);


        String str6 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=netwong+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri6 = URI.create(str6);
        String source6 = "Apple is better than PC. Apple has better apple computers and better apple phones.";
        ByteArrayInputStream in6 = new ByteArrayInputStream(source6.getBytes(StandardCharsets.UTF_8));


        int code6 = mystore.putDocument(in6, uri6, DocumentStore.CompressionFormat.JAR);
        String doc6 = mystore.getDocument(uri6);


        String str7 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=netwoeitanewng+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri7 = URI.create(str7);
        String source7 = "This is document 7";
        ByteArrayInputStream in7 = new ByteArrayInputStream(source7.getBytes(StandardCharsets.UTF_8));


        int code7 = mystore.putDocument(in7, uri7, DocumentStore.CompressionFormat.GZIP);
        String doc7 = mystore.getDocument(uri7);



        mystore.printdocStore();

        System.out.println(mystore.search("apple"));

        mystore.deleteDocument(uri4);
        mystore.deleteDocument(uri6);
        mystore.deleteDocument(uri1);
        mystore.undo(uri3);
        mystore.undo();



        System.out.println();
        System.out.println("Store without uri3, uri4, uri 6");
        mystore.printdocStore();
        System.out.println(mystore.search("apple"));









        //HashTable Tests//

        /*
        HashTableImpl<Integer, String> mylist = new HashTableImpl(4);
        mylist.put(003, "Reuven");
        mylist.put(174, "Shimon");
        mylist.put(987, "Reuven");
        mylist.put(385, "Levi");
        mylist.put(493, "Yehuda");
        mylist.put(685, "Yissachar");
        mylist.put(943, "Zevulen");
        mylist.put(4343, "Don");
        mylist.put(843, "Naftali");
        mylist.put(3232, "Gad");
        mylist.put(032, "Asher");
        mylist.put(434, "Yosef");
        mylist.put(754, "Benyamin");
        mylist.put(5343, "Avraham");
        mylist.put(043, "Yitzchak");
        mylist.put(5675, "Yaakov");
        mylist.put(4365, "Moshe");

        mylist.printTable();
        */


    }



}
