package com.cg.mypaymentapp.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.cg.mypaymentapp.Util.PAUtil;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo
{
	EntityManager entityManager;
	

	private Map<String, Customer> data ; 
	
	public WalletRepoImpl(Map<String, Customer> data) 
	{
		super();
		this.data = data;
	}
	
	public WalletRepoImpl() 
	{
		entityManager = PAUtil.getEntityManager();
		

	}
	
	public boolean save(Customer customer)
	{
								
		try {
			entityManager.persist(customer);
		}
		catch(Exception e) {
			throw new InvalidInputException();
		}
			return true;
		
	}

	
	public Customer findOne(String mobileNo)
	{
		//finding phone number
		Customer customer = entityManager.find(Customer.class, mobileNo);
		
		return customer;
		
	}
	@Override
	public void startTransaction() {
		// TODO Auto-generated method stub
		entityManager.getTransaction().begin();
	}
	@Override
	public void commitTransaction() {
		// TODO Auto-generated method stub
		entityManager.getTransaction().commit();
		
	}
	
	@Override
	public Customer updateBalance(Customer customer, Transactions transactionType) {
		
		entityManager.merge(customer);
		entityManager.persist(transactionType);
		return customer;
	}
	@Override
	public List<Transactions> recentTransactions(String mobileNumber) {
	
		TypedQuery<Transactions> query = entityManager.createQuery("select trans from Transactions trans where trans.mobileNumber=:pMob",Transactions.class);
		query.setParameter("pMob", mobileNumber);
		List<Transactions> transList = query.getResultList();
		return transList;
	}
}
