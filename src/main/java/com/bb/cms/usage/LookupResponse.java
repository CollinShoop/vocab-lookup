package com.bb.cms.usage;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LookupResponse {
    private List<String> simpleUsage;
    private String pronunciation;

    public void addSimpleUsage(String s) {
        if (simpleUsage == null) {
            simpleUsage = new ArrayList<>();
        }
        simpleUsage.add(s);
    }
}
