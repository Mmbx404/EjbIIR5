package metier.beans;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SmartPhone
 *
 */
@Entity

public class SmartPhone implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String ref;
	private String name;
	private String marque;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	@OneToMany(targetEntity = Position.class, mappedBy = "smartPhone", fetch = FetchType.EAGER)
	private List<Position> positions;
	private static final long serialVersionUID = -558553967080513790L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Position> getPosition() {
		return positions;
	}
	public void setPosition(List<Position> positions) {
		this.positions = positions;
	}
	public SmartPhone(Long id, String ref, String name, String marque, User user, List<Position> positions) {
		super();
		this.id = id;
		this.ref = ref;
		this.name = name;
		this.marque = marque;
		this.user = user;
		this.positions = positions;
	}
	public SmartPhone() {
		super();
		// TODO Auto-generated constructor stub
	}
}
