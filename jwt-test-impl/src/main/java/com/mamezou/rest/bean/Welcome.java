package com.mamezou.rest.bean;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class Welcome {
    private String iss;
    private String upn;
    private Set<String> groups = new HashSet<>();
    private Map<String, String> claims = new HashMap<>();

    public Welcome() {}
    
    public Welcome(String iss, String upn, Set<String> groups, Map<String, String> claims) {
        this.iss = iss;
        this.upn = upn;
        this.groups = groups;
        this.claims = claims;
    }

    public String getIss() {
        return iss;
    }

    public String getUpn() {
        return upn;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Map<String, String> getClaims() {
        return claims;
    }
}
