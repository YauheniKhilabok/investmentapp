package com.epam.invpol.controller;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.dto.InvestmentDto;
import com.epam.invpol.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private Converter<Investment, InvestmentDto> investmentConverter;

    @PostMapping
    public ResponseEntity<InvestmentDto> saveInvestment(@Valid @RequestBody InvestmentDto investmentDto) {
        Investment investment = conversionService.convert(investmentDto, Investment.class);
        Investment savedInvestment = investmentService.save(investment);
        InvestmentDto createdInvestmentDto = conversionService.convert(savedInvestment, InvestmentDto.class);
        return new ResponseEntity<>(createdInvestmentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDto> updateInvestment(@PathVariable("id") long id, @Valid @RequestBody InvestmentDto investmentDto) {
        investmentDto.setId(id);
        Investment investment = conversionService.convert(investmentDto, Investment.class);
        Investment updatedInvestment = investmentService.update(investment);
        InvestmentDto updatedInvestmentDto = conversionService.convert(updatedInvestment, InvestmentDto.class);
        return new ResponseEntity<>(updatedInvestmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeInvestment(@PathVariable("id") long id) {
        Investment investment = new Investment();
        investment.setId(id);
        investmentService.remove(investment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDto> getConcreteInvestment(@PathVariable("id") long id) {
        Investment investment = investmentService.getById(id);
        InvestmentDto investmentDto = conversionService.convert(investment, InvestmentDto.class);
        return new ResponseEntity<>(investmentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<InvestmentDto>> getInvestments(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                                              @RequestParam(name = "limit", required = false, defaultValue = "50") int limit) {
        Page<Investment> investments = investmentService.getInvestments(pageNumber, limit);
        return new ResponseEntity<>(investments.map(investmentConverter), HttpStatus.OK);
    }
}
