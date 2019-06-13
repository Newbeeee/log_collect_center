package com.fault.collect.center.util.analysis.tfidf;

import java.util.Map;

public class Document {
    String docName;
    Map<String, Double> docDict;

    public Document(String docName, Map<String, Double> docDict) {
        this.docName = docName;
        this.docDict = docDict;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Map<String, Double> getDocDict() {
        return docDict;
    }

    public void setDocDict(Map<String, Double> docDict) {
        this.docDict = docDict;
    }
}
