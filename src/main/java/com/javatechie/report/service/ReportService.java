package com.javatechie.report.service;

import com.javatechie.report.entity.Employee;
import com.javatechie.report.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository repository;


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "/Users/priyabrata/Documents/reports/";
        List<Employee> employees = repository.findAll();
        //load file and compile it
       

        
        InputStream employeeReportStream
        = getClass().getResourceAsStream("/employees.jrxml");
        JasperReport jasperReport
        = JasperCompileManager.compileReport(employeeReportStream);
        
        
        
        
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "employees.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "employees.pdf");
        }

        return "report generated in path : " + path;
    }
}
