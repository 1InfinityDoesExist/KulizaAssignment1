package com.example.demo.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Daughter")
@Table(name = "daughter_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "mobile_number" }) })
@ApiModel(value = "Daughter", description = "Details of Daughter Class")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({ @TypeDef(name = "AddressType", typeClass = AddressType.class),
		@TypeDef(name = "BasicDetailsType", typeClass = BasicDetailsType.class),

		@TypeDef(name = "PanCardDetailsType", typeClass = PanCardDetailsType.class) })
public class Daughter extends BaseEntity implements Serializable {

	@Column(name = "mobile_number", unique = true, nullable = false, updatable = true)
	@ApiModelProperty(notes = "Mobile Number Of Daughter")
	@NotBlank(message = "Mobile Number Must Not Be Blank, Mandatory Field")
	@NotEmpty(message = "Mobile Number Must Not Be Empty, Mandatory Field")
	private String mobileNumber;

	@Column(name = "is_single")
	@ApiModelProperty(notes = "Nulla Hey Ke Nahi")
	private Boolean isSingle;

	@Column(name = "panCard", columnDefinition = "jsonb")
	@ApiModelProperty(notes = "PanCard Details of the Daughter")
	@Type(type = "PanCardDetailsType")
	private PanCard panCard;

	@Column(name = "pet_name")
	@ApiModelProperty(notes = "Name of the Pet That She Had..!!!")
	private String petName;

	@Column(name = "has_pet")
	@ApiModelProperty(notes = "Does She had any pet")
	private String hasPet;

	@Column(name = "address", columnDefinition = "jsonb")
	@Type(type = "AddressType")
	@ApiModelProperty(notes = "Address Of The Daughter")
	private Address address;

	@Column(name = "basic_details", columnDefinition = "jsonb")
	@Type(type = "BasicDetailsType")
	@ApiModelProperty(notes = "Basic Detials of the Daughter")
	private BasicDetails basicDetails;

	// person - daughter
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "person_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("daughter")
	private Person person;

	// daughter - aadharCard
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "daughter")
	@JsonIgnoreProperties("daughter")
	private AadharCard aadharCard;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public AadharCard getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(AadharCard aadharCard) {
		this.aadharCard = aadharCard;
	}

	public Daughter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Daughter(
			@NotBlank(message = "Mobile Number Must Not Be Blank, Mandatory Field") @NotEmpty(message = "Mobile Number Must Not Be Empty, Mandatory Field") String mobileNumber,
			Boolean isSingle, PanCard panCard, String petName, String hasPet, Address address,
			BasicDetails basicDetails) {
		super();
		this.mobileNumber = mobileNumber;
		this.isSingle = isSingle;
		this.panCard = panCard;
		this.petName = petName;
		this.hasPet = hasPet;
		this.address = address;
		this.basicDetails = basicDetails;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(Boolean isSingle) {
		this.isSingle = isSingle;
	}

	public PanCard getPanCard() {
		return panCard;
	}

	public void setPanCard(PanCard panCard) {
		this.panCard = panCard;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getHasPet() {
		return hasPet;
	}

	public void setHasPet(String hasPet) {
		this.hasPet = hasPet;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BasicDetails getBasicDetails() {
		return basicDetails;
	}

	public void setBasicDetails(BasicDetails basicDetails) {
		this.basicDetails = basicDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((basicDetails == null) ? 0 : basicDetails.hashCode());
		result = prime * result + ((hasPet == null) ? 0 : hasPet.hashCode());
		result = prime * result + ((isSingle == null) ? 0 : isSingle.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((panCard == null) ? 0 : panCard.hashCode());
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Daughter other = (Daughter) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (basicDetails == null) {
			if (other.basicDetails != null)
				return false;
		} else if (!basicDetails.equals(other.basicDetails))
			return false;
		if (hasPet == null) {
			if (other.hasPet != null)
				return false;
		} else if (!hasPet.equals(other.hasPet))
			return false;
		if (isSingle == null) {
			if (other.isSingle != null)
				return false;
		} else if (!isSingle.equals(other.isSingle))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (panCard == null) {
			if (other.panCard != null)
				return false;
		} else if (!panCard.equals(other.panCard))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Daughter [mobileNumber=" + mobileNumber + ", isSingle=" + isSingle + ", panCard=" + panCard
				+ ", petName=" + petName + ", hasPet=" + hasPet + ", address=" + address + ", basicDetails="
				+ basicDetails + "]";
	}

}
