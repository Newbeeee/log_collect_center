package com.fault.collect.center.service.response;





import com.fault.collect.center.entity.FaultLog;

import java.util.List;

public class FaultLogsResponse {
    List<FaultLog> results;

    public List<FaultLog> getResults() {
        return results;
    }

    public void setResults(List<FaultLog> results) {
        this.results = results;
    }
}
