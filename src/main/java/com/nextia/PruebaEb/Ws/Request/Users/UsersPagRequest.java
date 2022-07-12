package com.nextia.PruebaEb.Ws.Request.Users;

public class UsersPagRequest {
    public Integer page;
    public Integer maxResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}
