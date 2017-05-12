package com.epam.invpol.service.impl;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.repository.InvestmentRepository;
import com.epam.invpol.service.InvestmentService;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.InvalidLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    private final ServiceHelper serviceHelper;

    @Autowired
    public InvestmentServiceImpl(InvestmentRepository investmentRepository, ServiceHelper serviceHelper) {
        this.investmentRepository = investmentRepository;
        this.serviceHelper = serviceHelper;
    }

    @Override
    @Transactional
    public Investment save(Investment investment) {
        if (investment.getId() != null) {
            if (isIdExist(investment.getId())) {
                throw new EntityAlreadyExistException("Investment with such id already exists.");
            }
        }
        Investment savedInvestment = investmentRepository.save(investment);
        return investmentRepository.findOne(savedInvestment.getId());
    }

    private boolean isIdExist(long id) {
        Investment investment = investmentRepository.findOne(id);
        return investment != null;
    }

    @Override
    @Transactional
    public Investment update(Investment investment) {
        getById(investment.getId());
        Investment updatedInvestment = investmentRepository.save(investment);
        return getById(updatedInvestment.getId());
    }

    @Override
    @Transactional
    public void remove(Investment investment) {
        getById(investment.getId());
        investmentRepository.delete(investment);
    }

    @Override
    public Investment getById(long id) {
        Investment investment = investmentRepository.findOne(id);
        if (investment == null) {
            throw new EntityNotFoundException("Requested investment is not found.");
        }
        return investment;
    }

    @Override
    public Page<Investment> getInvestments(int pageNumber, int limit) {
        if (serviceHelper.checkLimitAllowableSize(limit)) {
            throw new InvalidLimitException("Limit value is not allowed.");
        }
        int offset = pageNumber - 1;
        PageRequest pageRequest = new PageRequest(offset, limit);
        return investmentRepository.findAll(pageRequest);
    }
}
