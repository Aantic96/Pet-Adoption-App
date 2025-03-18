package com.example.pet_adoption.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class StringToDateConverter {
    
    public Date convertStringToDate(String dateString) {
        if (dateString != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return new Date(sdf.parse(dateString).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
