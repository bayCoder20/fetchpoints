package org.example.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_log")
public class TransactionLogModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="event")
	private String event;
	
	@Column(name="payerName")
	private String payerName;

	@Column(name="points")
	private Integer points;
	
	@Column(name="pointsId")
	private Integer pointsId;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name="createdDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date createdDate;
}
