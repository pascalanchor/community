package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the credentials database table.
 * 
 */
@Entity
@NamedQuery(name="Credentials.findAll", query="SELECT c FROM Credentials c")
public class Credentials implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String pwd;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="email")
	private User user;

	public Credentials() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}