package com.example.demo.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name = "AadharCard")
@Table(name = "aadhar_card_details", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "mobile_number", "email" }) })
@ApiModel(value = "AadharCard", description = "AadharCard Detials")
public class AadharCard extends BaseEntity implements Serializable {

	@Column(name = "aadhar_number", updatable = false)
	@Size(min = 16, max = 16, message = "Aadhar Card Number must be of 16char Long")
	@ApiModelProperty(notes = "AadharCard Details")
	private String aadharNumber;

	@Column(name = "address", columnDefinition = "jsonb")
	@Type(type = "AddressType")
	@ApiModelProperty(notes = "Address Of the AaadharCard Holder")
	private Address address;

	@Column(name = "father_name")
	@ApiModelProperty(notes = "Father Name")
	private String fatherName;

	@Column(name = "mother_name")
	@ApiModelProperty(notes = "Name of the Mother of the Card Holder")
	private String motherName;

	@Column(name = "dob")
	@ApiModelProperty(notes = "Date Of Birth Of the Person")
	private Date dob;

	@Column(name = "son_of_")
	@ApiModelProperty(notes = "Son_of")
	private String sonOf;

	@Column(name = "daughter_of_")
	@ApiModelProperty(notes = "daughter of")
	private String daughterOf;

	@Column(name = "wife_of_")
	@ApiModelProperty(notes = "Wife Of")
	private String wifeOf;

	@Column(name = "husband_of_")
	@ApiModelProperty(notes = "Husband of")
	private String husbandOf;

	@Column(name = "validity")
	@ApiModelProperty(notes = "Life Time of the AaadharCard, Validity")
	private LocalDateTime validity;

	@Column(name = "first_name")
	@ApiModelProperty(notes = "First Name of the Person")
	private String firstName;

	@Column(name = "last_name")
	@ApiModelProperty(notes = "Last Name of the Person")
	private String lastName;

	@Column(name = "mobile_number")
	@ApiModelProperty(notes = "Mobile Number of the Person")
	@NotBlank(message = "Mobile Number must not be blank")
	@NotEmpty(message = "Mobile Number must not be Empty")
	private String mobileNumber;

	@Column(name = "email")
	@ApiModelProperty(notes = "Email of the Person")
	@NotBlank(message = "Email Field Must not be Blank")
	@NotEmpty(message = "Email Field Must not be Empty")
	private String email;

	@Column(name = "gender")
	@ApiModelProperty(notes = "Geneder of the Person")
	private String gender;

	// AadharCard -Person One to One Mapping
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "person_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("aadharCard")
	private Person person;

	// aadharCard Son
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "son_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("aadharCard")
	private Son son;

	// aadharCard Daughter
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "daughter_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("aadharCard")
	private Daughter daughter;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public AadharCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AadharCard(Long id, LocalDateTime creationDate, LocalDateTime modificationDate, Boolean isDeleted) {
		super(id, creationDate, modificationDate, isDeleted);
		// TODO Auto-generated constructor stub
	}

	public AadharCard(String aadharNumber, Address address, String fatherName, String motherName, Date dob,
			String sonOf, String daughterOf, String wifeOf, String husbandOf, LocalDateTime validity, String firstName,
			String lastName,
			@NotBlank(message = "Mobile Number must not be blank") @NotEmpty(message = "Mobile Number must not be Empty") String mobileNumber,
			@NotBlank(message = "Email Field Must not be Blank") @NotEmpty(message = "Email Field Must not be Empty") String email,
			String gender) {
		super();
		this.aadharNumber = aadharNumber;
		this.address = address;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.dob = dob;
		this.sonOf = sonOf;
		this.daughterOf = daughterOf;
		this.wifeOf = wifeOf;
		this.husbandOf = husbandOf;
		this.validity = validity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.gender = gender;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSonOf() {
		return sonOf;
	}

	public void setSonOf(String sonOf) {
		this.sonOf = sonOf;
	}

	public String getDaughterOf() {
		return daughterOf;
	}

	public void setDaughterOf(String daughterOf) {
		this.daughterOf = daughterOf;
	}

	public String getWifeOf() {
		return wifeOf;
	}

	public void setWifeOf(String wifeOf) {
		this.wifeOf = wifeOf;
	}

	public String getHusbandOf() {
		return husbandOf;
	}

	public void setHusbandOf(String husbandOf) {
		this.husbandOf = husbandOf;
	}

	public LocalDateTime getValidity() {
		return validity;
	}

	public void setValidity(LocalDateTime validity) {
		this.validity = validity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aadharNumber == null) ? 0 : aadharNumber.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((daughterOf == null) ? 0 : daughterOf.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((husbandOf == null) ? 0 : husbandOf.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
		result = prime * result + ((sonOf == null) ? 0 : sonOf.hashCode());
		result = prime * result + ((validity == null) ? 0 : validity.hashCode());
		result = prime * result + ((wifeOf == null) ? 0 : wifeOf.hashCode());
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
		AadharCard other = (AadharCard) obj;
		if (aadharNumber == null) {
			if (other.aadharNumber != null)
				return false;
		} else if (!aadharNumber.equals(other.aadharNumber))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (daughterOf == null) {
			if (other.daughterOf != null)
				return false;
		} else if (!daughterOf.equals(other.daughterOf))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (husbandOf == null) {
			if (other.husbandOf != null)
				return false;
		} else if (!husbandOf.equals(other.husbandOf))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (motherName == null) {
			if (other.motherName != null)
				return false;
		} else if (!motherName.equals(other.motherName))
			return false;
		if (sonOf == null) {
			if (other.sonOf != null)
				return false;
		} else if (!sonOf.equals(other.sonOf))
			return false;
		if (validity == null) {
			if (other.validity != null)
				return false;
		} else if (!validity.equals(other.validity))
			return false;
		if (wifeOf == null) {
			if (other.wifeOf != null)
				return false;
		} else if (!wifeOf.equals(other.wifeOf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AadharCard [aadharNumber=" + aadharNumber + ", address=" + address + ", fatherName=" + fatherName
				+ ", motherName=" + motherName + ", dob=" + dob + ", sonOf=" + sonOf + ", daughterOf=" + daughterOf
				+ ", wifeOf=" + wifeOf + ", husbandOf=" + husbandOf + ", validity=" + validity + ", firstName="
				+ firstName + ", lastName=" + lastName + ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", gender=" + gender + "]";
	}

}
