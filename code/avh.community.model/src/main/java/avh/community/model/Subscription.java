package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@NamedQuery(name="Subscription.findAll", query="SELECT s FROM Subscription s")
public class Subscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String status;

	//uni-directional many-to-one association to Channel
	@ManyToOne
	@JoinColumn(name="channel_id")
	@Autowired
	private Channel channel;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	@Autowired 
	private User user;

	//bi-directional many-to-one association to ChannelThread
	@OneToMany(mappedBy="subscription")
	private List<ChannelThread> threads;

	public Subscription() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel ch) {
		this.channel=ch;
		
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user=user;
	}

	public List<ChannelThread> getThreads() {
		return this.threads;
	}

	public void setThreads(List<ChannelThread> threads) {
		this.threads = threads;
	}

	public ChannelThread addThread(ChannelThread thread) {
		getThreads().add(thread);
		thread.setSubscription(this);

		return thread;
	}

	public ChannelThread removeThread(ChannelThread thread) {
		getThreads().remove(thread);
		thread.setSubscription(null);

		return thread;
	}

}