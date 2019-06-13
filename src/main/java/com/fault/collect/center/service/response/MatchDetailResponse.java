package com.fault.collect.center.service.response;

import com.fault.collect.center.entity.MatchDetail;

import java.util.List;

public class MatchDetailResponse {
    List<MatchDetail> matchDetails;

    public List<MatchDetail> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchDetail> matchDetails) {
        this.matchDetails = matchDetails;
    }
}
