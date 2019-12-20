package com.example.demo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Person")
@Table(name = "person", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "pan_number", "aadhar_card_number", "phone_number", "email" }) })
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({ @TypeDef(name = "AddressType", typeClass = AddressType.class),
		@TypeDef(name = "BasicDetails", typeClass = BasicDetails.class) })
@ApiModel(value = "Person", description = "Person Entity")
public class Person extends BaseEntity implements Serializable {

	@Column(name = "full_name")
	@Size(min = 4, max = 12, message = "Full Name must be between 4 - 12 of Lenght")
	@ApiModelProperty(notes = "Full Name of The Person")
	private String fullName;
	@Column(name = "age")
	@Min(value = 24)
	@Max(value = 99)
	@ApiModelProperty(notes = "Age of The Person")
	private Long age;
	@Column(name = "is_married")
	@ApiModelProperty(notes = "Marital Status of The Person")
	private Boolean isMarried;
	@Column(name = "dob")
	@ApiModelProperty(notes = "Birth Date of The Person")
	private Date dob;
	@Email
	@Column(name = "email")
	@NotBlank(message = "Email Can't Be Blank")
	@NotEmpty(message = "Email Can't Be Empty")
	@ApiModelProperty(notes = "Email of The Person")
	private String email;
	@Column(name = "phone_number")
	@NotBlank(message = "PhoneNumber Can't Be Blank")
	@NotEmpty(message = "PhoneNumber Can't Be Empty")
	@ApiModelProperty(notes = "Contat Number of The Person")
	private String phoneNumber;
	@Column(name = "aadhar_card_number")
	@NotBlank(message = "AadharCardNumber Can't Be Blank")
	@NotEmpty(message = "AadharCardNumber Can't Be Empty")
	@ApiModelProperty(notes = "Aadhar Card Number of The Person")
	private String aadharCardNumber;

	@Column(name = "pan_number")
	@NotBlank(message = "PanCardNumber Can't Be Blank")
	@NotEmpty(message = "PanCardNumber Can't Be Empty")
	@ApiModelProperty(notes = "PanCardNumber of The Person")
	private String panNumber;

	@Column(name = "address", columnDefinition = "jsonb")
	@ApiModelProperty(notes = "address of the Person")
	@Type(type = "AddressType")
	private Address address;

	@Column(name = "basicDetails", columnDefinition = "jsonb")
	@ApiModelProperty(notes = "basicDetails")
	@Type(type = "BasicDetails")
	private BasicDetails basicDetials;

	// Person - AadharCard OneToOne Mapping
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("person")
	@Column(name = "aadhar_card_no")
	private AadharCard aadharCard;

	// Person - Son One To Many
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "sonId")
	@JsonIgnoreProperties("son")
	private List<Son> son = new ArrayList<Son>();

	// Person - Daughter One To Many
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "daughterId")
	@JsonIgnoreProperties("daughter")
	private List<Daughter> daughter = new ArrayList<Daughter>();

	public AadharCard getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(AadharCard aadharCard) {
		this.aadharCard = aadharCard;
	}

	public List<Son> getSon() {
		return son;
	}

	public void setSon(List<Son> son) {
		this.son = son;
	}

	public List<Daughter> getDaughter() {
		return daughter;
	}

	public void setDaughter(List<Daughter> daughter) {
		this.daughter = daughter;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(@Size(min = 4, max = 12, message = "Full Name must be between 4 - 12 of Lenght") String fullName,
			@Min(24) @Max(99) Long age, Boolean isMarried, Date dob, @Email @NotBlank @NotEmpty String email,
			@NotBlank @NotEmpty String phoneNumber, @NotBlank @NotEmpty String aadharCardNumber,
			@NotBlank @NotEmpty String panNumber, Address address, BasicDetails basicDetials) {
		super();
		this.fullName = fullName;
		this.age = age;
		this.isMarried = isMarried;
		this.dob = dob;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.aadharCardNumber = aadharCardNumber;
		this.panNumber = panNumber;
		this.address = address;
		this.basicDetials = basicDetials;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Boolean getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(Boolean isMarried) {
		this.isMarried = isMarried;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getAadharCardNumber() {
		return aadharCardNumber;
	}

	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BasicDetails getBasicDetials() {
		return basicDetials;
	}

	public void setBasicDetials(BasicDetails basicDetials) {
		this.basicDetials = basicDetials;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aadharCardNumber == null) ? 0 : aadharCardNumber.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((basicDetials == null) ? 0 : basicDetials.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((isMarried == null) ? 0 : isMarried.hashCode());
		result = prime * result + ((panNumber == null) ? 0 : panNumber.hashCode());
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
		Person other = (Person) obj;
		if (aadharCardNumber == null) {
			if (other.aadharCardNumber != null)
				return false;
		} else if (!aadharCardNumber.equals(other.aadharCardNumber))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (basicDetials == null) {
			if (other.basicDetials != null)
				return false;
		} else if (!basicDetials.equals(other.basicDetials))
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
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (isMarried == null) {
			if (other.isMarried != null)
				return false;
		} else if (!isMarried.equals(other.isMarried))
			return false;
		if (panNumber == null) {
			if (other.panNumber != null)
				return false;
		} else if (!panNumber.equals(other.panNumber))
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
		return "Person [fullName=" + fullName + ", age=" + age + ", isMarried=" + isMarried + ", dob=" + dob
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", aadharCardNumber=" + aadharCardNumber
				+ ", panNumber=" + panNumber + ", address=" + address + ", basicDetials=" + basicDetials + "]";
	}

}
