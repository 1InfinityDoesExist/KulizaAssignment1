package com.example.demo.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

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

@Entity(name = "Son")
@Table(name = "son_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "phone_number" }) })
@ApiModel(value = "Son", description = "Son Properties")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({ @TypeDef(name = "AddressType", typeClass = AddressType.class),
		@TypeDef(name = "BasicDetailsType", typeClass = BasicDetailsType.class) })
public class Son extends BaseEntity implements Serializable {
	@Column(name = "is_single")
	@ApiModelProperty(notes = "He is Single or not")
	private boolean isSingle;
	@Column(name = "pet_name")
	@ApiModelProperty(notes = "pet_name")
	private String petName;
	@Column(name = "address", columnDefinition = "jsonb")
	@ApiModelProperty(notes = "Address of the Son")
	@Type(type = "AddressType")
	private Address address;
	@Column(name = "basic_details", columnDefinition = "jsonb")
	@ApiModelProperty(notes = "basic_details")
	@Type(type = "BasicDetailsType")
	private BasicDetails basicDetails;
	@Column(name = "email", unique = true, nullable = false, updatable = true)
	@NotBlank(message = "Email Id Must Not Be Blank")
	@NotEmpty(message = "Email Id Must Not Be Empty")
	@ApiModelProperty(notes = "Email Of The Person")
	private String email;
	@Column(name = "phone_number", unique = true, nullable = false, updatable = true)
	@NotBlank(message = "PhoneNumber Must Not Be Blank")
	@NotEmpty(message = "PhoneNumber Must Not Be Empty")
	@ApiModelProperty(notes = "Phone Number Of The Person")
	private String phoneNumber;

	// Son - Person One To Many Operation
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "son_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("son")
	private Person person;

	// son - aaharCard one to one mapping
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "son")
	@JsonIgnoreProperties("son")
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

	public Son() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Son(Long id, LocalDateTime creationDate, LocalDateTime modificationDate, Boolean isDeleted) {
		super(id, creationDate, modificationDate, isDeleted);
		// TODO Auto-generated constructor stub
	}

	public Son(boolean isSingle, String petName, Address address, BasicDetails basicDetails,
			@NotBlank(message = "Email Id Must Not Be Blank") @NotEmpty(message = "Email Id Must Not Be Empty") String email,
			@NotBlank(message = "PhoneNumber Must Not Be Blank") @NotEmpty(message = "PhoneNumber Must Not Be Empty") String phoneNumber) {
		super();
		this.isSingle = isSingle;
		this.petName = petName;
		this.address = address;
		this.basicDetails = basicDetails;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((basicDetails == null) ? 0 : basicDetails.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isSingle ? 1231 : 1237);
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		Son other = (Son) obj;
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isSingle != other.isSingle)
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Son [isSingle=" + isSingle + ", petName=" + petName + ", address=" + address + ", basicDetails="
				+ basicDetails + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}

}
