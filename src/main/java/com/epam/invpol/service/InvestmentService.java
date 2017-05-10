package com.epam.invpol.service;

import com.epam.invpol.domain.Investment;
import org.springframework.data.domain.Page;

public interface InvestmentService {

    Investment save(Investment investment);

    Investment update(Investment investment);

    void remove(Investment investment);

    Investment getById(long id);

    Page<Investment> getInvestments(int pageNumber, int limit);
}
