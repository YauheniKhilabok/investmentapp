package com.epam.invpol.config;

import com.epam.invpol.dto.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionServiceConfig {

    @Bean(name = "conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new DepartmentConverter());
        converters.add(new DepartmentDtoConverter());
        converters.add(new EmployeeConverter());
        converters.add(new EmployeeDtoConverter());
        converters.add(new ProgramConverter());
        converters.add(new ProgramDtoConverter());
        converters.add(new InvestmentConverter());
        converters.add(new InvestmentDtoConverter());
        return converters;
    }
}
