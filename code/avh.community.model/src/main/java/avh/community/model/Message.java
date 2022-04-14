package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String body;

	private Timestamp cdate;

	private Integer stars;

	//uni-directional many-to-one association to Message
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="reply_to")
	private Message message;

	//uni-directional many-to-one association to Subscription
	@ManyToOne
	@JoinColumn(name="subscription_id")
	private Subscription subscription;

	//uni-directional many-to-one association to ChannelThread
	@ManyToOne
	@JoinColumn(name="thread_id")
	private ChannelThread thread;

	public Message() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Timestamp getCdate() {
		return this.cdate;
	}

	public void setCdate(Timestamp cdate) {
		this.cdate = cdate;
	}

	public Integer getStars() {
		return this.stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Subscription getSubscription() {
		return this.subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public ChannelThread getThread() {
		return this.thread;
	}

	public void setThread(ChannelThread thread) {
		this.thread = thread;
	}

}