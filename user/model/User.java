package com.cpe.springboot.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.hibernate.annotations.Table;

@Entity //This tells Hibernate to make a table out of this class
//@Table (appliesTo = "user_majeur")
//Fait référence à la table nommée user_majeur dans la base de données
@Table (name="user_majeur")

public class User {
	
	//attribut de la classe user qui correspondent aux colonnes de la database
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	private Boolean admin;
	@Column(unique=true)
	private String login;
	private String password;
	private int queue;//numero liste d'attente
	
	/*--------------------------------------------------------------------------------------------------*/
	// Constructeurs (Constructeur vide nécessaire vide pour remplir la database)
	/*--------------------------------------------------------------------------------------------------*/
	
	public User() {
		this.login = "";
		this.password="";	
		this.admin = false;
		this.queue = -1;
	}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.admin = false;
		this.queue = -1;
	}
	
	/*--------------------------------------------------------------------------------------------------*/
	//Getter et Setter
	/*--------------------------------------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return le login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * 
	 * @param login est donnée à l'attribut login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * 
	 * @return le name
	 */
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * la valeur admin est donné 0 non admin 1 admin
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	/**
	 * 
	 * @return le password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password est donnée à l'attribut password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public int getQueue() {
		return queue;
	}
	public void setQueue(int numero) {
		this.queue = numero;
	}

}
