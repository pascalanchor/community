package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the token database table.
 * 
 */
@Entity
@NamedQuery(name="Token.findAll", query="SELECT t FROM Token t")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="expiry_date")
	private Timestamp expiryDate;

	@Column(name="tdate")
	private Timestamp tokenDate;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="email")
	private User user;

	public Token() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Timestamp getTokenDate() {
		return this.tokenDate;
	}

	public void setTokenDate(Timestamp tokenDate) {
		this.tokenDate = tokenDate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}