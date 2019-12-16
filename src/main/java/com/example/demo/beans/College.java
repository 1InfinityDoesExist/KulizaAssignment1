package com.example.demo.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "College")
@Table(name = "college")
public class College extends BaseEntity implements Serializable {

	private String collgeName;

	public College() {
		super();
		// TODO Auto-generated constructor stub
	}

	public College(Long id, LocalDateTime creationDate, LocalDateTime modificationDate, boolean isDeleted) {
		super(id, creationDate, modificationDate, isDeleted);
		// TODO Auto-generated constructor stub
	}

	public College(String collgeName) {
		super();
		this.collgeName = collgeName;
	}

	public String getCollgeName() {
		return collgeName;
	}

	public void setCollgeName(String collgeName) {
		this.collgeName = collgeName;
	}

	@Override
	public String toString() {
		return "College [collgeName=" + collgeName + "]";
	}

}
