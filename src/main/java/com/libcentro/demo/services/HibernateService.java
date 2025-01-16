package com.libcentro.demo.services;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void closeHibernateSession() {
        if (entityManagerFactory != null) {
            System.out.println("Closing Hibernate Session");
            entityManagerFactory.close();
        }
    }

}