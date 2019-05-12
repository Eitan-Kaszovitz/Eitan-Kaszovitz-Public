package edu.yu.cs.com1320.project.Impl;


import edu.yu.cs.com1320.project.DocumentStore;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Stage4Test {

    public static void main (String[] args) {

        //Document Tests//

        DocumentStoreImpl mystore = new DocumentStoreImpl();

        //////////////////////
        String str1 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039682";
        URI uri1 = URI.create(str1);
        String source1 = "String 1 Apples taste great. Apples are my favorite food. Apples are red.";
        ByteArrayInputStream in1 = new ByteArrayInputStream(source1.getBytes(StandardCharsets.UTF_8));

        int code1 = mystore.putDocument(in1, uri1, DocumentStore.CompressionFormat.GZIP);


        /////////////////////
        String str2 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039683";
        URI uri2 = URI.create(str2);
        String source2 = "String2- no message here.";
        ByteArrayInputStream in2 = new ByteArrayInputStream(source2.getBytes(StandardCharsets.UTF_8));

        int code2 = mystore.putDocument(in2, uri2, DocumentStore.CompressionFormat.BZIP2);


        /////////////////////
        String str3 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039654";
        URI uri3 = URI.create(str3);
        String source3 = "String3- These apples are the best apples i've had in my apples career. Apples rock!";
        ByteArrayInputStream in3 = new ByteArrayInputStream(source3.getBytes(StandardCharsets.UTF_8));

        int code3 = mystore.putDocument(in3, uri3, DocumentStore.CompressionFormat.SEVENZ);



        String str4 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri4 = URI.create(str4);
        String source4 = "String4- Apples taste sour, so apples are bad.";
        ByteArrayInputStream in4 = new ByteArrayInputStream(source4.getBytes(StandardCharsets.UTF_8));

        int code4 = mystore.putDocument(in4, uri4, DocumentStore.CompressionFormat.BZIP2);


        String str5 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networewewng+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri5 = URI.create(str5);
        String source5 = "String5- no message here either.";
        ByteArrayInputStream in5 = new ByteArrayInputStream(source5.getBytes(StandardCharsets.UTF_8));

        int code5 = mystore.putDocument(in5, uri5, DocumentStore.CompressionFormat.BZIP2);

        mystore.printdocStore();
        System.out.println();

        mystore.getDocument(uri4);
        System.out.println(mystore.search("apples"));

        mystore.undo(uri5);
        mystore.printdocStore();
        System.out.println();

        mystore.setMaxDocumentCount(4);


        String str6 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=netwong+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri6 = URI.create(str6);
        String source6 = "String 6";
        ByteArrayInputStream in6 = new ByteArrayInputStream(source6.getBytes(StandardCharsets.UTF_8));

        int code6 = mystore.putDocument(in6, uri6, DocumentStore.CompressionFormat.JAR);

        mystore.printdocStore();
        System.out.println();


        String str7 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=netwoeitanewng+in+java+geeksforgeeks"+""
                +"&spf=149691805767674683";
        URI uri7 = URI.create(str7);
        String source7 = "String 7";
        ByteArrayInputStream in7 = new ByteArrayInputStream(source7.getBytes(StandardCharsets.UTF_8));

        int code7 = mystore.putDocument(in7, uri7, DocumentStore.CompressionFormat.GZIP);

        mystore.printdocStore();
        System.out.println();





        /*String source8 = "String 8";
        ByteArrayInputStream in8 = new ByteArrayInputStream(source8.getBytes(StandardCharsets.UTF_8));

        int code8 = mystore.putDocument(in8, uri7, DocumentStore.CompressionFormat.GZIP);
        String doc8 = mystore.getDocument(uri7);*/













    }



}
