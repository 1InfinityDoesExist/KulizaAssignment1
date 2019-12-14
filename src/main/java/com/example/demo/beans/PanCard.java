package com.example.demo.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;

@Entity(name = "PanCard")
@Table(name = "pan_card_detials", uniqueConstraints = { @UniqueConstraint(columnNames = "pan_number") })
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({ @TypeDef(name = "PanCardDetailsType", typeClass = PanCardDetailsType.class) })
public class PanCard extends BaseEntity implements Serializable {

	@Column(name = "pan_number", unique = true, nullable = false, updatable = false)
	@ApiModelProperty(notes = "PanCard Details")
	@NotBlank(message = "PanNumber Must Not Be Blank")
	@NotEmpty(message = "PanNumber Must Not Be Empty")
	private String PanNumber;

	@Column(name = "address", columnDefinition = "jsonb")
	@Type(type = "AddressType")
	@ApiModelProperty(notes = "Address Of the APanCard Holder")
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
	@ApiModelProperty(notes = "Life Time of the APanCard, Validity")
	private LocalDateTime validity;

	public PanCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PanCard(Long id, LocalDateTime creationDate, LocalDateTime modificationDate, Boolean isDeleted) {
		super(id, creationDate, modificationDate, isDeleted);
		// TODO Auto-generated constructor stub
	}

	public PanCard(
			@NotBlank(message = "PanNumber Must Not Be Blank") @NotEmpty(message = "PanNumber Must Not Be Empty") String panNumber,
			Address address, String fatherName, String motherName, Date dob, String sonOf, String daughterOf,
			String wifeOf, String husbandOf, LocalDateTime validity) {
		super();
		PanNumber = panNumber;
		this.address = address;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.dob = dob;
		this.sonOf = sonOf;
		this.daughterOf = daughterOf;
		this.wifeOf = wifeOf;
		this.husbandOf = husbandOf;
		this.validity = validity;
	}

	public String getPanNumber() {
		return PanNumber;
	}

	public void setPanNumber(String panNumber) {
		PanNumber = panNumber;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((PanNumber == null) ? 0 : PanNumber.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((daughterOf == null) ? 0 : daughterOf.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((husbandOf == null) ? 0 : husbandOf.hashCode());
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
		PanCard other = (PanCard) obj;
		if (PanNumber == null) {
			if (other.PanNumber != null)
				return false;
		} else if (!PanNumber.equals(other.PanNumber))
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
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (husbandOf == null) {
			if (other.husbandOf != null)
				return false;
		} else if (!husbandOf.equals(other.husbandOf))
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
		return "PanCard [PanNumber=" + PanNumber + ", address=" + address + ", fatherName=" + fatherName
				+ ", motherName=" + motherName + ", dob=" + dob + ", sonOf=" + sonOf + ", daughterOf=" + daughterOf
				+ ", wifeOf=" + wifeOf + ", husbandOf=" + husbandOf + ", validity=" + validity + "]";
	}

}
