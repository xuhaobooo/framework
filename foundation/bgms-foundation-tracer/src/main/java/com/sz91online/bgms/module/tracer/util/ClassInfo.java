package com.sz91online.bgms.module.tracer.util;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private String module;
    private String type;
    private List<String> excludeHistoryFields;

    public ClassInfo(String module, String type) {
        this.module = module;
        this.type = type;
        excludeHistoryFields = new ArrayList<>();
        excludeHistoryFields.add("id");
        excludeHistoryFields.add("lastupdatedtime");
        excludeHistoryFields.add("createdtime");
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addExcludeHistoryField(String field) {
        excludeHistoryFields.add(field);
    }

    public List<String> getExcludeHistoryFields() {
        return excludeHistoryFields;
    }
}
