package com.grupo7.oo2spring.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class EmailToken {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int idToken;

	    private String token;

	    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	    @JoinColumn(nullable = false, name = "usuario_id")
	    @OnDelete(action = OnDeleteAction.CASCADE)
	    private Usuario usuario;

	    private LocalDateTime expiryDate;

		public EmailToken(String token, Usuario usuario, LocalDateTime expiryDate) {
			super();
			this.token = token;
			this.usuario = usuario;
			this.expiryDate = expiryDate;
		}

		public int getIdToken() {
			return idToken;
		}

		public void setIdToken(int idToken) {
			this.idToken = idToken;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public LocalDateTime getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(LocalDateTime expiryDate) {
			this.expiryDate = expiryDate;
		}
	    
	    

}
