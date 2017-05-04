package com.epam.invpol.service.impl;

import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {

    public boolean checkLimitAllowableSize(int limit) {
        int maxAllowableLimitSize = 100;
        return limit > maxAllowableLimitSize;
    }
}
