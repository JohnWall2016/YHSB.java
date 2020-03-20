package cn.yhsb.cjb.service;

import java.util.HashMap;
import java.util.List;

public class PageRequest extends CustomRequest {
    int page, pagesize;
    List<Object> filtering = List.of();
    List<Object> sorting = List.of();
    List<Object> totals = List.of();

    public void setSorting(HashMap<String, String> sorting) {
        this.sorting = List.of(sorting);
    }

    public void setTotals(HashMap<String, String> totals) {
        this.totals = List.of(totals);
    }

    public PageRequest(String id, int page, int size) {
        super(id);
        this.page = page;
        this.pagesize = size;
    }

    public PageRequest(String id) {
        this(id, 1, 15);
    }
}