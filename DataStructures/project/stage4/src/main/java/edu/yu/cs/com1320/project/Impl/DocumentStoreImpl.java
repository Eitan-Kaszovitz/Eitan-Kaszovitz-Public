package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.Document;
import edu.yu.cs.com1320.project.DocumentStore;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;


public class DocumentStoreImpl implements DocumentStore {

    private HashTableImpl<URI, DocumentImpl> store;
    private CompressionFormat defaultCompressionFormat = CompressionFormat.ZIP;
    private StackImpl commandStack;
    private TrieImpl<DocumentImpl> wordTrie;
    private String compareString;
    private DocComparator dc;
    private int maxDocCount;
    private int maxDocBytes;
    private MinHeapImpl docHeap;
    private int totalBytes;

    public DocumentStoreImpl() {
        this.store = new HashTableImpl<>(4);
        this.commandStack = new StackImpl(20);
        this.dc = new DocComparator();
        this.wordTrie = new TrieImpl(this.dc);
        this.maxDocCount = 10000;
        this.maxDocBytes = 10000;
        this.docHeap = new MinHeapImpl();
        this.totalBytes = 0;
    }

    protected class DocComparator implements Comparator {
        String word;
        protected DocComparator () {
            this.word = null;
        }

        @Override
        public int compare(Object o1, Object o2) {
            DocumentImpl d1 = (DocumentImpl)o1;
            DocumentImpl d2 = (DocumentImpl)o2;
            int d1Count = d1.wordCount(this.word);
            int d2Count = d2.wordCount(this.word);
            if (d1Count > d2Count) {
                return -1;
            }
            else if (d1Count < d2Count) {
                return 1;
            }
            else {
                return 0;
            }
        }

        protected void setWord(String string) {
            this.word = string;
        }
    }

    public List<String> search(String keyword) {
        String lowerCase = keyword.toLowerCase();
        this.dc.setWord(lowerCase);
        List<DocumentImpl> docList = this.wordTrie.getAllSorted(lowerCase);

        //if word has no docs with it, return null
        if (docList == null) {
            return null;
        }

        //if word has docs with it, get a list of those docs' strings
        else {
            List<String> stringList = new ArrayList<>();
            long timeUpdate = System.currentTimeMillis();
            for (DocumentImpl current : docList) {
                current.setLastUseTime(timeUpdate); ///time stamp
                this.docHeap.reHeapify(current);
                stringList.add(current.toString());
            }
            return stringList;
        }
    }


    public List<byte[]> searchCompressed(String keyword) {
        String lowerCase = keyword.toLowerCase();
        this.dc.setWord(lowerCase);
        List<DocumentImpl> docList = this.wordTrie.getAllSorted(lowerCase);

        //if word has no docs with it, return null
        if (docList == null) {
            return null;
        }

        //if word has docs with it, get a list of those docs' compressed forms
        else {
            List<byte[]> compressedDocList = new ArrayList<>();
            long timeUpdate = System.currentTimeMillis();
            for (DocumentImpl current : docList) {
                current.setLastUseTime(timeUpdate);  ///time stamp
                this.docHeap.reHeapify(current);
                compressedDocList.add(current.getDocument());
            }
            return compressedDocList;
        }
    }

    public void setDefaultCompressionFormat(CompressionFormat format) {
        this.defaultCompressionFormat = format;
    }

    @Override
    public CompressionFormat getDefaultCompressionFormat() {
        return this.defaultCompressionFormat;
    }

    public int putDocument(InputStream input, URI uri) {
        try {
            if (input == null) {
                deleteDocument(uri);
                return -1;
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
            if (store.get(uri) != null) {
                if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {
                    store.get(uri).setLastUseTime(System.currentTimeMillis()); ///time stamp
                    this.docHeap.reHeapify(store.get(uri));
                    return docString.hashCode();
                }
            }
            else {
                this.compressionSwitch(this.defaultCompressionFormat, docString, uri);
            }
        }
        catch (IOException e) {
            return -1;
        }
        return -1;
    }

    public int putDocument(InputStream input, URI uri, CompressionFormat format) {
        try {
            if (input == null) {
                deleteDocument(uri);
                return -1;
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
            if ((store.get(uri) != null) && (store.get(uri).getDocumentHashCode() == docString.hashCode())) {
                store.get(uri).setLastUseTime(System.currentTimeMillis()); ///time stamp
                this.docHeap.reHeapify(store.get(uri));
                return docString.hashCode();
            }
            else {
                this.compressionSwitch(format, docString, uri);
            }

        }
        catch (IOException e) {
            return -1;
        }
        return -1;
    }

    protected int compressionSwitch(CompressionFormat format, String docString, URI uri) {
        switch (format) {
            case JAR:
                return this.compressJar(docString, format, uri, 0);
            case ZIP:
                try {
                    return this.compressZip(docString, format, uri, 0);
                } catch (ArchiveException e) {
                    e.printStackTrace();
                }
            case GZIP:
                try {
                    return this.compressGzip(docString, format, uri, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case SEVENZ:
                return this.compressSevenz(docString, format, uri, 0);
            case BZIP2:
                return this.compressBzip2(docString, format, uri, 0);
        }
        return -1;
    }

    public int putDocumentUndoVersion(String docString, URI uri) {
        try {
            if (store.get(uri) != null) {
                if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {
                    return docString.hashCode();
                }
            }
            else {
                switch (this.defaultCompressionFormat) {
                    case JAR:
                        return this.compressJar(docString, this.defaultCompressionFormat, uri, 1);
                    case ZIP:
                        return this.compressZip(docString, this.defaultCompressionFormat, uri, 1);
                    case GZIP:
                        return this.compressGzip(docString, this.defaultCompressionFormat, uri, 1);
                    case SEVENZ:
                        return this.compressSevenz(docString, this.defaultCompressionFormat, uri,1);
                    case BZIP2:
                        return this.compressBzip2(docString, this.defaultCompressionFormat, uri,1);
                }
            }
        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
        return -1;
    }

    public int putDocumentUndoVersion(String docString, URI uri, CompressionFormat format) {
        try {
            if (store.get(uri) != null) {
                if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {

                    return docString.hashCode();
                }
            }
            else {
                switch (format) {
                    case JAR:
                        return this.compressJar(docString, format, uri, 1);
                    case ZIP:
                        return this.compressZip(docString, format, uri, 1);
                    case GZIP:
                        return this.compressGzip(docString, format, uri, 1);
                    case SEVENZ:
                        return this.compressSevenz(docString, format, uri, 1);
                    case BZIP2:
                        return this.compressBzip2(docString, format, uri, 1);
                }
            }

        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
        return -1;
    }

    public String getDocument(URI uri) {
        if (store.get(uri) != null) {
            store.get(uri).setLastUseTime(System.currentTimeMillis());  ///time stamp
            this.docHeap.reHeapify(store.get(uri));
            switch (store.get(uri).getCompressionFormat()) {
                case JAR:
                    return this.decompressJar(store.get(uri).getDocument());
                case ZIP:
                    return this.decompressZip(store.get(uri).getDocument());
                case GZIP:
                    return this.decompressGzip(store.get(uri).getDocument());
                case SEVENZ:
                    return this.decompressSevenz(store.get(uri).getDocument());
                case BZIP2:
                    return this.decompressBzip2(store.get(uri).getDocument());
            }
        }
        else {
            return null;
        }
        return null;
    }

    public String getDocumentNoTimeStamp(URI uri) {
        if (store.get(uri) != null) {
            switch (store.get(uri).getCompressionFormat()) {
                case JAR:
                    return this.decompressJar(store.get(uri).getDocument());
                case ZIP:
                    return this.decompressZip(store.get(uri).getDocument());
                case GZIP:
                    return this.decompressGzip(store.get(uri).getDocument());
                case SEVENZ:
                    return this.decompressSevenz(store.get(uri).getDocument());
                case BZIP2:
                    return this.decompressBzip2(store.get(uri).getDocument());
            }
        }
        else {
            return null;
        }
        return null;
    }



    public byte[] getCompressedDocument(URI uri) {
        store.get(uri).setLastUseTime(System.currentTimeMillis());  ///time stamp
        this.docHeap.reHeapify(store.get(uri));
        return store.get(uri).getDocument();
    }

    public boolean deleteDocument(URI uri) {
        if (store.get(uri) == null) {
            return false;
        }
        else {           //creates command object if delete is successful
            String s = getDocumentNoTimeStamp(uri);
            CompressionFormat format = store.get(uri).getCompressionFormat();
            Long putTime = store.get(uri).getLastUseTime();
            Function deleteUndo = this.deleteUndoFunction(format, s, putTime);
            Function deleteRedo = this.deleteRedoFunction();
            Command putCommand = new Command(uri, deleteUndo, deleteRedo);
            this.commandStack.push(putCommand);
        }
        this.deleteWords(uri);
        this.docHeap.delete(store.get(uri));
        this.totalBytes -= store.get(uri).getDocument().length;
        boolean isDeleted = store.deleteObject(uri);
        return isDeleted;
    }

    protected Function<URI, Boolean> deleteUndoFunction(CompressionFormat c, String string, Long putTime) {
        Function<URI, Boolean> deleteUndo = (uri1) -> {
            if (c.equals(this.defaultCompressionFormat)) {
                this.putDocumentUndoVersion(string, uri1);
                this.store.get(uri1).setLastUseTime(putTime);
                this.addWords(this.store.get(uri1));
                this.docHeap.insert(this.store.get(uri1));
                this.totalBytes += this.store.get(uri1).getDocument().length;
                return true;
            } else {
                this.putDocumentUndoVersion(string, uri1, c);
                this.store.get(uri1).setLastUseTime(putTime);
                this.addWords(this.store.get(uri1));
                this.docHeap.insert(this.store.get(uri1));
                this.totalBytes += this.store.get(uri1).getDocument().length;
                return true;
            }
        };
        return deleteUndo;
    }

    protected Function<URI, Boolean> deleteRedoFunction() {
        Function<URI, Boolean> deleteRedo = (uri2) -> {
            this.deleteWords(uri2);
            this.docHeap.delete(store.get(uri2));
            this.totalBytes -= store.get(uri2).getDocument().length;
            this.deleteDocumentUndoVersion(uri2);
            return true;
        };
        return deleteRedo;
    }

        public boolean deleteDocumentUndoVersion(URI uri) {    //deletes a document as an undo function: no command object created
        return store.deleteObject(uri);
    }

    public boolean undo() throws IllegalStateException {
        if (commandStack.size() == 0) {
            throw new IllegalStateException("stack empty: no action done previously");
        }
        Command thisCommand = (Command) commandStack.pop();
        return thisCommand.undo();
    }

    public boolean undo(URI uri) throws IllegalStateException {
        if (commandStack.size() == 0) {
            throw new IllegalStateException("error - stack empty: no action done previously");
        }
        int stackMax = commandStack.getMax();
        StackImpl temp = new StackImpl(stackMax);   ///Create temp stack to hold the taken out commands
        Command current = (Command) commandStack.peek();
        while ((commandStack.size() != 0) && (!current.getUri().equals(uri))) {
            Command thisCommand = (Command) commandStack.pop();
            thisCommand.undo();
            temp.push(thisCommand);
            current = (Command) commandStack.peek();
        }
        if (commandStack.size() == 0) {
            throw new IllegalStateException("error - URI not found in Command Stack");
        }
        Command peekPopped = (Command) commandStack.pop();
        Boolean undoCommand = peekPopped.undo();
        while (temp.size() > 0) {
            Command thatCommand = (Command) temp.pop();
            thatCommand.redo();
            commandStack.push(thatCommand);
        }
        return undoCommand;
    }

    public void setMaxDocumentCount(int limit) {
        this.maxDocCount = limit;
        if (this.store.getN() > limit) {
            this.makeSpace();
        }
    }

    public void setMaxDocumentBytes(int limit) {
        this.maxDocBytes = limit;
        if (this.totalBytes > limit) {
            this.makeSpace();
        }
    }

    ////////// Compression ///////////

    protected int compressJar(String s, CompressionFormat format, URI uri, int undoTest) {
        try {
            JarArchiveEntry zai = new JarArchiveEntry("this");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.JAR, bos);
            os.putArchiveEntry(zai);
            os.write(s.getBytes());
            os.closeArchiveEntry();
            os.close();
            bos.close();
            byte[] compressed = bos.toByteArray();
            if (undoTest == 0) {
                createDocument(s, compressed, uri, format);
            }
            if (undoTest == 1){
                createDocumentUndoVerion(s, compressed, uri, format);
            }
            return store.get(uri).getDocumentHashCode();

        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
    }

    protected int compressZip(String s, CompressionFormat format, URI uri, int undoTest) throws ArchiveException {
        try {
            ZipArchiveEntry zai = new ZipArchiveEntry("this");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, bos);
            os.putArchiveEntry(zai);
            os.write(s.getBytes());
            os.closeArchiveEntry();
            os.close();
            byte[] compressed = bos.toByteArray();
            if (undoTest == 0) {
                createDocument(s, compressed, uri, format);
            }
            if (undoTest == 1){
                createDocumentUndoVerion(s, compressed, uri, format);
            }
            return store.get(uri).getDocumentHashCode();
        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
    }

    protected int compressGzip(String s, CompressionFormat format, URI uri, int undoTest) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GzipCompressorOutputStream gzout = new GzipCompressorOutputStream(bos);
            gzout.write(s.getBytes());
            gzout.close();
            byte[] compressed = bos.toByteArray();
            if (undoTest == 0) {
                createDocument(s, compressed, uri, format);
            }
            if (undoTest == 1){
                createDocumentUndoVerion(s, compressed, uri, format);
            }
            return store.get(uri).getDocumentHashCode();
        }
        catch (IOException e) {
            return -1;
        }
    }

    protected int compressSevenz(String s, CompressionFormat format, URI uri, int undoTest) {
        try {
            SeekableInMemoryByteChannel sc = new SeekableInMemoryByteChannel();
            SevenZOutputFile szout = new SevenZOutputFile(sc);
            SevenZArchiveEntry sza = szout.createArchiveEntry(new File("this"), "that");
            szout.putArchiveEntry(sza);
            szout.write(s.getBytes());
            szout.closeArchiveEntry();
            szout.close();
            byte[] compressed = sc.array();
            if (undoTest == 0) {
                createDocument(s, compressed, uri, format);
            }
            if (undoTest == 1){
                createDocumentUndoVerion(s, compressed, uri, format);
            }
            return store.get(uri).getDocumentHashCode();
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected int compressBzip2(String s, CompressionFormat format, URI uri, int undoTest) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BZip2CompressorOutputStream bzout = new BZip2CompressorOutputStream(bos);
            bzout.write(s.getBytes());
            bzout.close();
            byte[] compressed = bos.toByteArray();
            if (undoTest == 0) {
                createDocument(s, compressed, uri, format);
            }
            if (undoTest == 1){
                createDocumentUndoVerion(s, compressed, uri, format);
            }
            return store.get(uri).getDocumentHashCode();
        }
        catch (IOException e) {
            return -1;
        }
    }


    ////////// Creation /////////////

    protected void createDocument(String s, byte[] compressedbytes, URI uri, CompressionFormat format) {
        DocumentImpl document = new DocumentImpl(uri, format, s, compressedbytes);  ///create document
        document.setLastUseTime(System.currentTimeMillis());  ///time stamp
        if (store.get(uri) != null) {
            this.totalBytes -= store.get(uri).getDocument().length;   ///Take away ejected doc's bytes from total
            this.deleteWords(uri);
            this.docHeap.delete(store.get(uri));
        }
        DocumentImpl putDoc = store.put(uri, document);
        this.addWords(document);
        this.docHeap.insert(document);
        this.totalBytes += document.getDocument().length;
        if (document.getDocument().length > this.maxDocBytes) {
            throw new IllegalArgumentException();
        }
        if ((store.getN() > this.maxDocCount) || (this.totalBytes > this.maxDocBytes)) {
            this.makeSpace();
        }
        if (putDoc == null) {
            Function putUndo = this.putUndoFunction();
            Function putRedo = this.putRedoFunction(format, s);
            Command putCommand = new Command(uri, putUndo, putRedo);
            this.commandStack.push(putCommand);
        }
        else {
            Function updatedPutUndo = this.updatedPutUndoFunction(putDoc.getCompressionFormat(), putDoc.toString(), putDoc.getLastUseTime());
            Function updatedPutRedo = this.updatedPutRedoFunction(format, s);
            Command updatedPutCommand = new Command(uri, updatedPutUndo, updatedPutRedo);
            this.commandStack.push(updatedPutCommand);
        }
    }

    private Function<URI, Boolean> updatedPutUndoFunction(CompressionFormat c, String string, Long putTime) {
        Function<URI, Boolean> updatedPutUndo = (uri1) -> {
            this.deleteWords(uri1);
            this.docHeap.delete(store.get(uri1));
            this.totalBytes -= store.get(uri1).getDocument().length;
            this.deleteDocumentUndoVersion(uri1);
            if (c.equals(this.defaultCompressionFormat)) {
                this.putDocumentUndoVersion(string, uri1);
                this.store.get(uri1).setLastUseTime(putTime);
                this.addWords(this.store.get(uri1));
                this.docHeap.insert(this.store.get(uri1));
                this.totalBytes += this.store.get(uri1).getDocument().length;
                return true;
            } else {
                this.putDocumentUndoVersion(string, uri1, c);
                this.addWords(this.store.get(uri1));
                this.store.get(uri1).setLastUseTime(putTime);
                this.docHeap.insert(this.store.get(uri1));
                this.totalBytes += this.store.get(uri1).getDocument().length;
                return true;
            }
        };
        return updatedPutUndo;
    }

    private Function<URI, Boolean> updatedPutRedoFunction(CompressionFormat format, String s) {
        Function<URI, Boolean> updatedPutRedo = (uri2) -> {
            if (format.equals(this.defaultCompressionFormat)) {
                this.putDocumentUndoVersion(s, uri2);
                this.addWords(this.store.get(uri2));
                this.docHeap.insert(store.get(uri2));
                this.totalBytes += store.get(uri2).getDocument().length;
                return true;
            }
            else {
                this.putDocumentUndoVersion(s, uri2, format);
                this.addWords(this.store.get(uri2));
                this.docHeap.insert(store.get(uri2));
                this.totalBytes += store.get(uri2).getDocument().length;
                return true;
            }
        };
        return updatedPutRedo;
    }

    private Function<URI, Boolean> putUndoFunction() {
        Function<URI, Boolean> putUndo = (uri1) -> {
            this.deleteWords(uri1);
            this.docHeap.delete(store.get(uri1));
            this.totalBytes -= store.get(uri1).getDocument().length;
            this.deleteDocumentUndoVersion(uri1);
            return true;
        };
        return putUndo;
    }

    private Function<URI, Boolean> putRedoFunction(CompressionFormat format, String s) {
        Function<URI, Boolean> putRedo = (uri2) -> {
            if (format.equals(this.defaultCompressionFormat)) {
                this.putDocumentUndoVersion(s, uri2);
                this.addWords(this.store.get(uri2));
                this.docHeap.insert(store.get(uri2));
                this.totalBytes += store.get(uri2).getDocument().length;
                return true;
            }
            else {
                this.putDocumentUndoVersion(s, uri2, format);
                this.addWords(this.store.get(uri2));
                this.docHeap.insert(store.get(uri2));
                this.totalBytes += store.get(uri2).getDocument().length;
                return true;
            }
        };
        return putRedo;
    }

    protected void createDocumentUndoVerion(String s, byte[] compressedbytes, URI uri, CompressionFormat format) {
        DocumentImpl document = new DocumentImpl(uri, format, s, compressedbytes);
        store.put(uri, document);
        if ((store.getN() > this.maxDocCount) || (this.totalBytes > this.maxDocBytes)) {
            this.makeSpace();
        }
        store.get(uri).setLastUseTime(System.currentTimeMillis());
    }



    ////////// Decompression /////////////

    protected String decompressJar(byte[] b) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.JAR, bis);
            JarArchiveEntry entry = (JarArchiveEntry)in.getNextEntry();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            IOUtils.copy(in, bos);
            bis.close();
            bos.close();
            in.close();
            String dcString = bos.toString();
            return dcString;
        }
        catch (IOException | ArchiveException e) {
            return null;
        }
    }

    protected String decompressZip(byte[] b) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, bis);
            ZipArchiveEntry entry = (ZipArchiveEntry)in.getNextEntry();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            IOUtils.copy(in, bos);
            bos.close();
            in.close();
            String dcString = bos.toString();
            return dcString;
        }
        catch (IOException | ArchiveException e) {
            return null;
        }
    }

    protected String decompressGzip(byte[] b) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            GzipCompressorInputStream gzIn = new GzipCompressorInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int n = 0;
            while (-1 != (n = gzIn.read())) {
                bos.write(n);
            }
            String dcString = bos.toString();
            return dcString;
        } catch (IOException e) {
            return null;
        }
    }

    protected String decompressSevenz(byte[] b) {
        try {
            SeekableInMemoryByteChannel sc = new SeekableInMemoryByteChannel(b);
            SevenZFile szf = new SevenZFile(sc);
            SevenZArchiveEntry entry = szf.getNextEntry();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] content = new byte[(int) entry.getSize()];
            szf.read(content, 0, content.length);
            bos.write(content);
            String dcString = bos.toString();
            return dcString;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String decompressBzip2(byte[] b) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            BZip2CompressorInputStream gzIn = new BZip2CompressorInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int n = 0;
            while (-1 != (n = gzIn.read())) {
                bos.write(n);
            }
            String dcString = bos.toString();
            return dcString;
        }
        catch (IOException e) {
            return null;
        }
    }

    protected void addWords(DocumentImpl doc) {
        String lowerCase = null;
        for (String current : doc.getWords()) {
            lowerCase = current.toLowerCase();
            wordTrie.put(lowerCase, doc);
        }
    }

    protected void deleteWords(URI uri) {
        String[] wordList = this.store.get(uri).getWords();
        for (String current : wordList) {
            this.wordTrie.delete(current, this.store.get(uri));
        }
    }

    protected void makeSpace() {
        if ((this.store.getN() > this.maxDocCount)) {
            while ((this.store.getN() > this.maxDocCount)) {
                DocumentImpl removedDoc = (DocumentImpl) this.docHeap.removeMin();
                this.deleteDocumentMaxVersion(removedDoc);
            }
        }
        if ((this.totalBytes > this.maxDocBytes)) {
            while ((this.totalBytes > this.maxDocBytes)) {
                DocumentImpl removedDoc = (DocumentImpl) this.docHeap.removeMin();
                this.deleteDocumentMaxVersion(removedDoc);
            }
        }
    }

    /**
     * deletes a document in a fashion which gets it out of all memory
     * @param doc the document being deleted
     */
    protected void deleteDocumentMaxVersion (DocumentImpl doc) {
        URI uri = doc.getKey();
        this.deleteWords(uri);
        this.totalBytes -= store.get(uri).getDocument().length;
        this.removeFromStack(uri);
        store.deleteObject(uri);
    }

    private void removeFromStack(URI uri) {
        int stackMax = commandStack.getMax();
        StackImpl temp = new StackImpl(stackMax);   ///Create temp stack to hold the taken out commands
        Command current = (Command) commandStack.peek();
        while (commandStack.size() != 0) {     ///remove all elements and store desired elements in temp stack
            if (current.getUri().equals(uri)) {
                commandStack.pop();
            }
            else {
                Command thisCommand = (Command) commandStack.pop();
                temp.push(thisCommand);
            }
            current = (Command) commandStack.peek();
        }
        while (temp.size() > 0) {
            Command thatCommand = (Command) temp.pop();
            commandStack.push(thatCommand);
        }
    }

    protected void printdocStore() {
        this.store.printTable();
    }

}
