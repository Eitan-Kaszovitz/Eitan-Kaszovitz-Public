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
import java.nio.charset.StandardCharsets;
import java.util.function.Function;


public class DocumentStoreImpl implements DocumentStore {

    private HashTableImpl<URI, Document> store;
    private CompressionFormat defaultCompressionFormat = CompressionFormat.ZIP;
    private StackImpl commandStack;

    public DocumentStoreImpl() {
        this.store = new HashTableImpl<>(4);
        this.commandStack = new StackImpl(20);
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
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
            if (store.get(uri) != null) {
                if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {
                    return docString.hashCode();
                }
            }
            else {
                switch (this.defaultCompressionFormat) {
                    case JAR:
                        return this.compressJar(docString, this.defaultCompressionFormat, uri, 0);
                    case ZIP:
                        return this.compressZip(docString, this.defaultCompressionFormat, uri, 0);
                    case GZIP:
                        return this.compressGzip(docString, this.defaultCompressionFormat, uri, 0);
                    case SEVENZ:
                        return this.compressSevenz(docString, this.defaultCompressionFormat, uri, 0);
                    case BZIP2:
                        return this.compressBzip2(docString, this.defaultCompressionFormat, uri, 0);
                }
            }
        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
        return -1;
    }

    public int putDocument(InputStream input, URI uri, CompressionFormat format) {
        try {
            if (input == null) {
                deleteDocument(uri);
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
            if (store.get(uri) != null) {
                if (store.get(uri).getDocumentHashCode() == docString.hashCode()) {
                    return docString.hashCode();
                }
            }
            else {
                switch (format) {
                    case JAR:
                        return this.compressJar(docString, format, uri, 0);
                    case ZIP:
                        return this.compressZip(docString, format, uri, 0);
                    case GZIP:
                        return this.compressGzip(docString, format, uri, 0);
                    case SEVENZ:
                    return this.compressSevenz(docString, format, uri, 0);
                    case BZIP2:
                        return this.compressBzip2(docString, format, uri, 0);
                }
            }

        }
        catch (IOException | ArchiveException e) {
            return -1;
        }
        return -1;
    }

    public int putDocumentUndoVersion(InputStream input, URI uri) {
        try {
            if (input == null) {
                deleteDocument(uri);
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
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

    public int putDocumentUndoVersion(InputStream input, URI uri, CompressionFormat format) {
        try {
            if (input == null) {
                deleteDocument(uri);
            }
            byte[] bytes = IOUtils.toByteArray(input);
            String docString = new String(bytes);
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
        return store.get(uri).getDocument();
    }

    public boolean deleteDocument(URI uri) {
        Boolean deleteStatus = store.deleteObject(uri);
        if (deleteStatus.equals(true)) {
            String s = getDocument(uri);
            InputStream input = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            CompressionFormat format = store.get(uri).getCompressionFormat();
            Function<URI, Boolean> deleteUndo = (uri1) -> {
                if (format.equals(this.defaultCompressionFormat)) {
                    this.putDocumentUndoVersion(input, uri1);
                    return true;
                } else {
                    this.putDocumentUndoVersion(input, uri1, format);
                    return true;
                }
            };
            Function<URI, Boolean> deleteRedo = (uri2) -> {
                this.deleteDocument(uri2);
                return true;
            };
            Command putCommand = new Command(uri, deleteUndo, deleteRedo);
            this.commandStack.push(putCommand);
        }
        return deleteStatus;
    }

    public boolean deleteDocumentUndoVersion(URI uri) {
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
            throw new IllegalStateException("stack empty: no action done previously");
        }
        int stackMax = commandStack.getMax();
        StackImpl temp = new StackImpl(stackMax);
        Command current = (Command) commandStack.peek();
        while (!current.getUri().equals(uri) && (commandStack.size() != 0)) {
            Command thisCommand = (Command) commandStack.pop();
            thisCommand.undo();
            temp.push(thisCommand);
            current = (Command) commandStack.peek();
        }
        Boolean undoCommand = current.undo();
        while (temp.size() > 0) {
            Command thatCommand = (Command) temp.pop();
            thatCommand.redo();
            commandStack.push(thatCommand);
        }
        return undoCommand;
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
        DocumentImpl document = new DocumentImpl(uri, format, s, compressedbytes);
        store.put(uri, document);
        InputStream input = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        Function<URI, Boolean> putUndo = (uri1) -> {
            this.deleteDocumentUndoVersion(uri1);
            return true;
        };
        Function<URI, Boolean> putRedo = (uri2) -> {
            if (format.equals(this.defaultCompressionFormat)) {
                this.putDocument(input, uri2);
                return true;
            }
            else {
                this.putDocument(input, uri2, format);
                return true;
            }
        };
        Command putCommand = new Command(uri, putUndo, putRedo);
        this.commandStack.push(putCommand);
    }

    protected void createDocumentUndoVerion(String s, byte[] compressedbytes, URI uri, CompressionFormat format) {
        DocumentImpl document = new DocumentImpl(uri, format, s, compressedbytes);
        store.put(uri, document);
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

    protected void printdocStore() {
        this.store.printTable();
    }

}
