package com.db.index;

import com.db.index.BPlusTree.BTree;
import com.db.model.database.DocumentDataTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtreeSupplier {
    private Map<DocumentDataTypes, BTree> bTreesMap;
    public BtreeSupplier(){
        bTreesMap=new HashMap<>();
        bTreesMap.put(DocumentDataTypes.LONG,new BTree<Long, List<String>>());
        bTreesMap.put(DocumentDataTypes.STRING,new BTree<String, List<String>>());
        bTreesMap.put(DocumentDataTypes.DOUBLE,new BTree<Double, List<String>>());
        bTreesMap.put(DocumentDataTypes.BOOLEAN,new BTree<Boolean, List<String>>());
    }
    public BTree get(DocumentDataTypes documentDataType){
        return bTreesMap.get(documentDataType);
    }
}
