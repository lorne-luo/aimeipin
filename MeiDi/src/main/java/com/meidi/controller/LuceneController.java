//package com.meidi.controller;
//
//import com.meidi.util.MdCommon;
//import com.meidi.util.MdConstants;
//import com.meidi.util.MdModel;
//import com.meidi.util.lucene.CommodityIndexSearcher;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.wltea.analyzer.IKSegmentation;
//import org.wltea.analyzer.Lexeme;
//import org.wltea.analyzer.lucene.IKSimilarity;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by luanpeng on 16/3/24.
// */
//@Controller
//@RequestMapping("/lucene")
//public class LuceneController implements MdConstants{
//
//    @Resource
//    private CommodityRepository commodityRepository;
//
//    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
//    public ModelAndView searchCommodity(HttpServletRequest request, @PathVariable String keyword) {
//        int currentPage = 1;//当前页
//        int pageSize = 2;//每页数量
//        int totalPage = 0;//总页数
//        List<MdCommodity> searchResult = new ArrayList<>();
//
//
//        TopDocs topDocs = null;
//        IndexSearcher indexSearcher = CommodityIndexSearcher.getIndexSearcher(COMMODITY_INDEX_PATH);
//
//        indexSearcher.setSimilarity(new IKSimilarity());
//        BooleanQuery booleanQuery = new BooleanQuery();
//
//        try {
//            //关键词分词搜索
//            if (!MdCommon.isEmpty(keyword)) {
//                Lexeme lexeme = null;
//                IKSegmentation ikSegmentation = new IKSegmentation(new StringReader(keyword));//分词
//                PhraseQuery phraseQuery = new PhraseQuery();
//
//                while ((lexeme = ikSegmentation.next()) != null) {
//                    String keywordStr = lexeme.getLexemeText();
//                    phraseQuery.add(new Term("description", keywordStr));
//                }
//                phraseQuery.setSlop(2);//设置每两个词不超过2个单词
//                booleanQuery.add(phraseQuery, BooleanClause.Occur.MUST);
//
//            }
//
//            SortField[] sortFields = null;
//            sortFields = new SortField[]{new SortField("createTime", SortField.STRING, true)};//按创建时间排序
//
//            Sort sort = new Sort(sortFields);
//            topDocs = indexSearcher.search(booleanQuery, null, 10000, sort);
//            totalPage = topDocs.scoreDocs.length / pageSize;
//            if (topDocs.scoreDocs.length % pageSize > 0) {
//                totalPage += 1;
//            }
//            if (totalPage > 0) {
//                int startIndex = pageSize * (currentPage - 1);//查询起始地址
//                int endIndex = startIndex + pageSize;//查询结束地址
//                if (endIndex > topDocs.scoreDocs.length) {
//                    endIndex = topDocs.scoreDocs.length;
//                }
//                for (int i = startIndex; i < endIndex; i++) {
//                    Document document = indexSearcher.doc(topDocs.scoreDocs[i].doc);
//                    String id = document.get("id");
//                    MdCommodity commodity = commodityRepository.findOne(Integer.parseInt(id));
//                    searchResult.add(commodity);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        MdModel model = new MdModel(request);
//        model.put("searchResult", searchResult);
//        model.put("currentPage", currentPage + 1);
//        model.put("totalPage", totalPage);
//
//        return new ModelAndView("test", model);
//    }
//}
