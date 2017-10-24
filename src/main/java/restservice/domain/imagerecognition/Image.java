package restservice.domain.imagerecognition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import restservice.domain.User;

@Entity
@Data
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Lob
	@Column(length = 1000000)
	private String data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private User user;
	
	@Lob
	@Column(length = 10000)
	private String prediction;

	public Image() {
		super();
	}
	
	public Image(String data) {
		super();
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

}
