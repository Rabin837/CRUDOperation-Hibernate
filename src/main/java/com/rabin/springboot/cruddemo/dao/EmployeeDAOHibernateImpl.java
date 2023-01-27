package com.rabin.springboot.cruddemo.dao;

import com.rabin.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO{
   //define field for entityManager
   private EntityManager entityManager;

   //set up constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager theEntityManager){
        entityManager=theEntityManager;
    }

    @Override
    public List<Employee> findAll() {
        //get the current hibernate session
        Session currentSession=entityManager.unwrap(Session.class);
        //create a query
        Query<Employee> theQuery= currentSession.createQuery("from Employee",Employee.class);
        //execute query and get result list
        List<Employee> employees =theQuery.getResultList();

        //return the results
        return employees;
    }

    @Override
    public Employee findById(int theId) {

        //get the current hibernate session
        Session currentSession=entityManager.unwrap(Session.class);

        //get the employee
        Employee theEmployee= currentSession.get(Employee.class, theId);

        //return the employee
        return  theEmployee;
    }

    @Override
    public void save(Employee theEmployee) {

        //get the current hibernate session
        Session currentSession=entityManager.unwrap(Session.class);

        //save employee
        currentSession.saveOrUpdate(theEmployee);

    }

    //Deleting Single employees by Id
    @Override
    public void deleteById(int theId) {
       //get the current hibernate session
        Session currentSession= entityManager.unwrap(Session.class);

        //delete object by primary key
        Query theQuery= currentSession.createQuery("Delete from Employee where id=:employeeId" );
        theQuery.setParameter("employeeId",theId);
        theQuery.executeUpdate();
    }
}
