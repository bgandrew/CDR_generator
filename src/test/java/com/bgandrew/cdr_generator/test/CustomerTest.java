/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgandrew.cdr_generator.test;

import com.bgandrew.cdr_generator.model.CITY;
import com.bgandrew.cdr_generator.model.Customer;
import com.bgandrew.cdr_generator.model.LocationSet;
import com.bgandrew.cdr_generator.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerTest {
    
    public CustomerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testImportExport() {
        System.out.println("begin testing of import/export");
        CITY city = Utils.randomCity();
        LocationSet set1 = new LocationSet(city,
                        Utils.randomLocationInCity(city),
                        Utils.randomLocationInCity(city),
                        Utils.randomLocationInCity(city));
        LocationSet set2 = new LocationSet(city,
                        Utils.randomLocationInCity(city),
                        Utils.randomLocationInCity(city),
                        Utils.randomLocationInCity(city));
        
        Customer customer1 = Customer.generateCustomer(LocalDateTime.now(), set1);
        Customer customer2 = Customer.generateCustomer(LocalDateTime.now(), set2);
        
        customer1.call(customer2, Customer.CallType.call, 100, 0);
        
        List<Customer> customers = Arrays.asList(customer1, customer2);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listOfCustomers = new TypeToken<List<Customer>>(){}.getType();
        
        String gsonString = gson.toJson(customers, listOfCustomers);
        
        customers = gson.fromJson(gsonString, listOfCustomers);
        
        Assert.assertEquals(1, customers.get(0).getNumberOfCalls());
        Assert.assertEquals(1, customers.get(1).getNumberOfCalls());
        Assert.assertEquals(customers.get(0).getLocationSet(), set1);
        Assert.assertEquals(customers.get(1).getLocationSet(), set2);
        
        System.out.println("finished testing of import/export");
        
    }
}
