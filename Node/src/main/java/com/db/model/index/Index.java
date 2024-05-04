package com.db.model.index;

import com.db.BPlusTree.BTree;
import com.db.model.database.DocumentDataType;
import org.json.simple.JSONObject;

public class Index {
    protected BTree index;
    protected JSONObject indexPropertyObject;
    public Index(DocumentDataType documentDataType){
        BtreeSupplier btreeSupplier=new BtreeSupplier();
        index=btreeSupplier.get(documentDataType);
    }
    public BTree getIndex() {
        return index;
    }

    public void setIndex(BTree index) {
        this.index = index;
    }

    public JSONObject getIndexPropertyObject() {
        return indexPropertyObject;
    }

    public void setIndexPropertyObject(JSONObject indexObject) {
        this.indexPropertyObject = indexObject;
    }
}
