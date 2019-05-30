package edu.yu.cs.com1320.project.Impl;

import java.net.URI;

public class URIComp implements Comparable<URIComp> {

    private URI uri;
    private BTreeImpl btree;

    public URIComp (URI uri, BTreeImpl btree) {
        this.uri = uri;
        this.btree = btree;
    }

    protected DocumentImpl getDoc(){
        return (DocumentImpl) btree.get(uri);
    }

    @Override
    public int compareTo(URIComp o) {
        if (((DocumentImpl)this.btree.get(this.uri)).getLastUseTime() > ((DocumentImpl)o.btree.get(o.uri)).getLastUseTime()) {
            return 1;
        }
        else if (((DocumentImpl)this.btree.get(this.uri)).getLastUseTime() < ((DocumentImpl)o.btree.get(o.uri)).getLastUseTime()) {
            return -1;
        }
        else {
            return 0;
        }
    }

}
