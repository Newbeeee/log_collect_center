package com.fault.collect.center.util.analysis.tfidf;

public class Sim {
    String docName;
    Double score;

    public Sim(String docName, Double score) {
        this.docName = docName;
        this.score = score;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "name = " + getDocName() + ";" + "score = " + getScore();
    }
}
