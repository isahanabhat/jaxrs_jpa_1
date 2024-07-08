/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.jaxrs_jpa_1.resources;

import com.mycompany.jpa.User;
// import static java.util.Collections.singletonList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.json.JSONObject;


public class DerivedClass<T> extends JakartaEE8Resource<T> {
	
	private EntityManager em;
	private List<User> listUser;
	
	@Override
	protected String getSingle(String username, String password) throws NamingException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_jndi_mysql_pool");
		em = emf.createEntityManager();
		
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userID LIKE :user AND u.password LIKE :pass", User.class)
			.setParameter("user", username).setParameter("pass", password);
		listUser = query.getResultList();
		
		JSONObject j = new JSONObject();
		
		// if(listAges.get(0).getName() == null && listAges.get(0).getAge() == null){
		
		if(listUser.isEmpty()){
			j.put("status", "FAILURE");
		} else{
			j.put("status", "SUCCESS");
		}
		
		em.close();
		return j.toString();
	}

}
