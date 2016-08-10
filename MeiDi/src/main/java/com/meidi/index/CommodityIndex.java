//package com.meidi.index;
//
//import java.io.File;
//import java.util.Date;
//
//import com.meidi.domain.MdCommodity;
//import com.meidi.util.MdCommon;
//import com.meidi.util.MdConstants;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.DateTools;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.NumericField;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//
///**
// * 商品索引
// */
//public class CommodityIndex implements MdConstants{
//
//    /**
//     * @param commodity
//     * @param flag      1 新增索引 2更新索引 3删除索引
//     */
//    public static void execute(MdCommodity commodity, String flag) {
//        IndexWriter indexWriter = null;
//        try {
//            File indexFile = new File(COMMODITY_INDEX_PATH);
//            if (!indexFile.exists()) {
//                indexFile.mkdirs();
//            }
//
//            //准备建立索引
//            Directory directory = FSDirectory.open(indexFile);
//            Analyzer analyzer = new IKAnalyzer();
//            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);
//            IndexWriter.unlock(directory);
//            indexWriter = new IndexWriter(directory, iwc);
//
//            if ("update".equals(flag)) {//更新索引
//                indexWriter.deleteDocuments(new Term("id", commodity.getId().toString()));
//            } else if ("delete".equals(flag)) {//删除索引
//                indexWriter.deleteDocuments(new Term("id", commodity.getId().toString()));
//                return;
//            } else if (MdCommon.isEmpty(flag)) {
//                throw new Exception("flag is null!");
//            } else if (!"add".equals(flag)) {
//                throw new Exception("flag is error!");
//            }
//
//            String id = commodity.getId().toString();
//            String description = commodity.getDescription();
//            Date createTime = commodity.getCreateTime();
//
//            Document doc = new Document();
//            doc.add(new Field("id", id, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
//            doc.add(new NumericField("idnum").setIntValue(Integer.parseInt(id)));
//            doc.add(new Field("description", description, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
//            doc.add(new Field("createTime", DateTools.dateToString(createTime, DateTools.Resolution.MILLISECOND), Field.Store.YES, Field.Index.NOT_ANALYZED));
//            indexWriter.addDocument(doc);
//
//            indexWriter.commit();
//            indexWriter.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (indexWriter != null) {
//                    indexWriter.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//}
