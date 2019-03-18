package edu.yu.cs.com1320.project;


import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Stage1Test {

    public static void main (String[] args) {

        String str1 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039682";
        URI uri1 = URI.create(str1);
        String source1 = "Hey";
        ByteArrayInputStream in1 = new ByteArrayInputStream(source1.getBytes(StandardCharsets.UTF_8));

        DocumentStoreImpl mystore = new DocumentStoreImpl();
        int code1 = mystore.putDocument(in1, uri1, DocumentStore.CompressionFormat.GZIP);

        String doc1 = mystore.getDocument(uri1);


        String str2 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039683";
        URI uri2 = URI.create(str2);
        String source2 = "Hi";
        ByteArrayInputStream in2 = new ByteArrayInputStream(source2.getBytes(StandardCharsets.UTF_8));

        int code2 = mystore.putDocument(in2, uri2, DocumentStore.CompressionFormat.BZIP2);
        String doc2 = mystore.getDocument(uri2);


        String str3 = "https://www.google.co.in/?gws_rd=ssl#"+""
                + "q=networking+in+java+geeksforgeeks"+""
                +"&spf=1496918039654";
        URI uri3 = URI.create(str3);
        String source3 = "Sup";
        ByteArrayInputStream in3 = new ByteArrayInputStream(source3.getBytes(StandardCharsets.UTF_8));

        int code3 = mystore.putDocument(in3, uri3, DocumentStore.CompressionFormat.SEVENZ);
        String doc3 = mystore.getDocument(uri3);

        mystore.store.printTable();














        /*
        HashTableImpl<Integer, String> mylist = new HashTableImpl(4);
        mylist.put(003, "Reuven");
        mylist.put(174, "Shimon");
        mylist.put(987, "Reuven");
        mylist.put(385, "Levi");
        mylist.put(493, "Yehuda")
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

        System.out.println(mylist.get(5675));
        System.out.println(mylist.get(685));
        System.out.println(mylist.get(3653));
        */

    }



}
