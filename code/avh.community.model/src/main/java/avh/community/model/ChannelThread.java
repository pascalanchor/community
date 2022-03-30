package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the thread database table.
 * 
 */
@Entity
@Table(name="thread")
@NamedQuery(name="ChannelThread.findAll", query="SELECT c FROM ChannelThread c")
public class ChannelThread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private Timestamp cdate;

	private String keywords;

	private String subject;

	//bi-directional many-to-one association to Subscription
//	@ManyToOne(cascade = {CascadeType.ALL})
	@ManyToOne
	@JoinColumn(name="subscription_id")
	private Subscription subscription;

	public ChannelThread() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getCdate() {
		return this.cdate;
	}

	public void setCdate(Timestamp cdate) {
		this.cdate = cdate;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Subscription getSubscription() {
		return this.subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

}