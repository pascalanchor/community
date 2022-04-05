package avh.community.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the channel database table.
 * 
 */
@Entity
@NamedQuery(name="Channel.findAll", query="SELECT c FROM Channel c")
public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="creation_date")
	private Timestamp creationDate;

	private String description;

	private String name;

	private String scope;

	public Channel() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        return (anObject instanceof Channel) && (((Channel) anObject).getEid().equals(this.getEid()));
    }
	
	public boolean isEmpty() {
		return this.getEid().isEmpty();
	}

}