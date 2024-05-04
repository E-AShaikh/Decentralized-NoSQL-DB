package com.db.model.index;

import com.db.BPlusTree.BTree;
import com.db.model.database.DocumentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtreeSupplier {
    private Map<DocumentDataType, BTree> bTreesMap;
    public BtreeSupplier(){
        bTreesMap=new HashMap<>();
        bTreesMap.put(DocumentDataType.LONG, new BTree<Long, List<String>>());
        bTreesMap.put(DocumentDataType.STRING, new BTree<String, List<String>>());
        bTreesMap.put(DocumentDataType.DOUBLE, new BTree<Double, List<String>>());
        bTreesMap.put(DocumentDataType.BOOLEAN, new BTree<Boolean, List<String>>());
    }
    public BTree get(DocumentDataType documentDataType){
        return bTreesMap.get(documentDataType);
    }
}
