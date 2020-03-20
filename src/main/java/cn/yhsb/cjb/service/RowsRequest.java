package cn.yhsb.cjb.service;

import java.util.ArrayList;

public class RowsRequest<T> extends CustomRequest {
    ArrayList<T> rows = new ArrayList<>();

    public void addRow(T row) {
        rows.add(row);
    }

    public RowsRequest(String id) {
        super(id);
    }
}