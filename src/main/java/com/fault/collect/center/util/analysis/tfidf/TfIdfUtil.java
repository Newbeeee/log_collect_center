package com.fault.collect.center.util.analysis.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdfUtil {

    private boolean weighted;
    private List<Document> documents;
    private Map<String, Double> corpus_dict;

    public TfIdfUtil() {
        weighted = false;
        documents = new ArrayList<>();
        corpus_dict = new HashMap<>();
    }

    public void clean() {
        documents.clear();
        corpus_dict.clear();
    }

    public void addDocument(String docName, List<String> words) {
        //创建一个字典
        Map<String, Double> docDict = new HashMap<>();
        for (String w : words) {
            if (!docDict.containsKey(w)) {
                docDict.put(w, 1.0);
            } else {
                docDict.put(w, docDict.get(w) + 1.0);
            }

            if (!corpus_dict.containsKey(w)) {
                corpus_dict.put(w, 1.0);
            } else {
                corpus_dict.put(w, corpus_dict.get(w) + 1.0);
            }
        }

        //规范化字典
        Double length = Double.valueOf(words.size());
        for (String k : docDict.keySet()) {
            docDict.put(k, docDict.get(k) / length);
        }

        //将规范后的字典加入集合
        documents.add(new Document(docName, docDict));
    }

    public List<Sim> similarities(List<String> words) {
        /**
         * 返回一个和words相似的对象为document的列表
         */

        //创建查询的字典
        Map<String, Double> queryDict = new HashMap<>();
        for (String w : words) {
            if (!queryDict.containsKey(w)) {
                queryDict.put(w, 1.0);
            } else {
                queryDict.put(w, queryDict.get(w) + 1.0);
            }
        }

        //规范化查询的字典
        Double length = Double.valueOf(words.size());
        for (String k : queryDict.keySet()) {
            queryDict.put(k, queryDict.get(k) / length);
        }

        //计算相似的列表
        List<Sim> sims = new ArrayList<>();
        for (Document doc : documents) {
            Double score = 0.0;
            Map<String, Double> docDict = doc.getDocDict();
            for (String k : queryDict.keySet()) {
                if (docDict.containsKey(k)) {
                    score += (queryDict.get(k) / corpus_dict.get(k)) +
                            (docDict.get(k) / corpus_dict.get(k));
                }
            }
            sims.add(new Sim(doc.getDocName(), score));
        }
        return sims;
    }
}
