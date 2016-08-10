//package com.meidi.util.lucene;
//
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.SearcherFactory;
//import org.apache.lucene.search.SearcherManager;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Created by luanpeng on 16/3/20.
// */
//public class CommodityIndexSearcher {
//
//    /**
//     *
//     * @param indexPath 索引文件位置
//     * @return
//     */
//    public static IndexSearcher getIndexSearcher(String indexPath) {
//
//        try {
//            Directory directory = FSDirectory.open(new File(indexPath));
//            SearcherManager searcherManager = new SearcherManager(directory, new SearcherFactory());
//            return searcherManager.acquire();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
