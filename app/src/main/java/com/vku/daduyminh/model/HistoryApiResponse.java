package com.vku.daduyminh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryApiResponse {

    @SerializedName("history")
    private List<Product> historyList;

    public List<Product> getHistoryList() {
        return historyList;
    }
}
