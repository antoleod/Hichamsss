package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="representations")
public class Representation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private Show show;

	/**
	 * Date de création de la représentation
	 */
	private LocalDateTime when;
	
	/**
	 * Lieu de prestation de la représentation
	 */
	@ManyToOne
	@JoinColumn(name="location_id", nullable=true)
	private Location location;
	
	@ManyToMany
	@JoinTable(
		  name = "reservation", 
		  joinColumns = @JoinColumn(name = "representation_id"), 
		  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Representation() { }
	
	//…
	
	public List<User> getUsers() {
		return users;
	}

	public Representation addUser(User user) {
		if(!this.users.contains(user)) {
			this.users.add(user);
			user.addRepresentation(this);
		}
		
		return this;
	}
	
	public Representation removeUser(User user) {
		if(this.users.contains(user)) {
			this.users.remove(user);
			user.getRepresentations().remove(this);
		}
		
		return this;
	}

	@Override
	public String toString() {
		return "Representation [id=" + id + ", show=" + show + ", when=" + when 
				+ ", location=" + location + "]";
	}
	
}

