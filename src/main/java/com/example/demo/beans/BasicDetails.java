package com.example.demo.beans;

import java.io.Serializable;

public class BasicDetails implements Serializable {

	private String nickName;
	private Double height;
	private Double weight;
	private Boolean isShy;
	private String fathterName;
	private String motherName;

	public BasicDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BasicDetails(String nickName, Double height, Double weight, Boolean isShy, String fathterName,
			String motherName) {
		super();
		this.nickName = nickName;
		this.height = height;
		this.weight = weight;
		this.isShy = isShy;
		this.fathterName = fathterName;
		this.motherName = motherName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Boolean getIsShy() {
		return isShy;
	}

	public void setIsShy(Boolean isShy) {
		this.isShy = isShy;
	}

	public String getFathterName() {
		return fathterName;
	}

	public void setFathterName(String fathterName) {
		this.fathterName = fathterName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fathterName == null) ? 0 : fathterName.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((isShy == null) ? 0 : isShy.hashCode());
		result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicDetails other = (BasicDetails) obj;
		if (fathterName == null) {
			if (other.fathterName != null)
				return false;
		} else if (!fathterName.equals(other.fathterName))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (isShy == null) {
			if (other.isShy != null)
				return false;
		} else if (!isShy.equals(other.isShy))
			return false;
		if (motherName == null) {
			if (other.motherName != null)
				return false;
		} else if (!motherName.equals(other.motherName))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicDetails [nickName=" + nickName + ", height=" + height + ", weight=" + weight + ", isShy=" + isShy
				+ ", fathterName=" + fathterName + ", motherName=" + motherName + "]";
	}

}
