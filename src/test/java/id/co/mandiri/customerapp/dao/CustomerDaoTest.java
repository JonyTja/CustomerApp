package id.co.mandiri.customerapp.dao;

import id.co.mandiri.customerapp.domain.Customer;
import id.co.mandiri.customerapp.domain.CustomerException;
import id.co.mandiri.customerapp.util.DbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {
    private static CustomerDao dao;

    @BeforeEach
    void setUp() {
        Connection connection= DbUtil.getConnection();
        dao=new CustomerDao(connection);
    }

    @Test
    void displayAllCustomers() {
    }

    @Test
    void addCustomer() {
        Customer customer=new Customer("John","Die", LocalDate.of(1999,9,9));

        try{
            dao.addCustomer(customer);
            Customer result=dao.findCustomer(3);
            Assertions.assertEquals("John", result.getFirstName());
        }catch (CustomerException e){
            e.printStackTrace();
        }
    }

    @Test
    void addCustomerWithId() {
        Customer customer=new Customer(1,"John","Die", LocalDate.of(1999,9,9));

        try{
            dao.addCustomerWithId(customer);
            Customer result=dao.findCustomer(1);
            Assertions.assertEquals("John", result.getFirstName());
        }catch (CustomerException e){
            e.printStackTrace();
        }
    }

    @Test
    void editCustomer() {
        try{
            Customer customer=dao.findCustomer(3);
            customer.setFirstName("Tom");
            customer.setLastName("Hanks");
            customer.setDateOfBirth(LocalDate.now());
            dao.editCustomer(customer);
            Customer result=dao.findCustomer(3);
            Assertions.assertEquals("Tom", result.getFirstName());
            Assertions.assertEquals("Hanks", result.getLastName());
            Assertions.assertEquals(LocalDate.now(), result.getDateOfBirth());
        }catch (CustomerException e){
            e.printStackTrace();
        }
    }

    @Test
    void findCustomer() {
        Exception e=Assertions.assertThrows(
                CustomerException.class,
                ()->dao.findCustomer(100)
        );
        Assertions.assertEquals("Customer Tidak Ditemukan",e.getMessage());
    }

    @Test
    void deleteCustomer() {
        try{
            dao.deleteCustomer(2);
            Exception e=Assertions.assertThrows(
                    CustomerException.class,
                    ()->dao.findCustomer(2));
            Assertions.assertEquals("Customer Tidak Ditemukan",e.getMessage());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }
}