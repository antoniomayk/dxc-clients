package io.github.antoniomayk.dxc.clients.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Client entity.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "clients")
@ApiModel(description = "Client entity")
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", unique = true, columnDefinition = "bigint auto_increment")
  @ApiModelProperty(example = "1", notes = "Unique identifier of the client")
  private Long id;

  @NotBlank
  @Size(min = 2, max = 100)
  @Column(name = "full_name", nullable = false, columnDefinition = "varchar(255)")
  @ApiModelProperty(example = "John Doe", required = true, notes = "Full name of the client")
  private String fullName;

  @Email
  @NotBlank
  @Column(name = "email", nullable = false, columnDefinition = "varchar(255)")
  @ApiModelProperty(
      example = "john.doe@example.com",
      required = true,
      notes = "Email address of the client")
  private String email;

  @NotBlank
  @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(20)")
  @ApiModelProperty(
      example = "+1-555-123-4567",
      required = true,
      notes = "Phone number of the client")
  private String phoneNumber;

  @Column(name = "deleted_at", nullable = true, columnDefinition = "timestamp default null")
  @ApiModelProperty(
      example = "2023-06-15T10:30:00",
      notes = "Timestamp when the client was deleted, if applicable")
  private LocalDateTime deletedAt;

  @CreatedBy
  @Column(
      name = "created_by",
      nullable = false,
      columnDefinition = "varchar(255) default 'INTERNAL'")
  @ApiModelProperty(example = "admin", notes = "User who created the client record")
  private String createdBy;

  @CreatedDate
  @Column(
      name = "created_at",
      nullable = false,
      columnDefinition = "timestamp default CURRENT_TIMESTAMP")
  @ApiModelProperty(
      example = "2023-06-15T09:00:00",
      notes = "Timestamp when the client record was created")
  private LocalDateTime createdAt;

  @LastModifiedBy
  @Column(
      name = "modified_by",
      nullable = false,
      columnDefinition = "varchar(255) default 'INTERNAL'")
  @ApiModelProperty(example = "admin", notes = "User who last modified the client record")
  private String modifiedBy;

  @LastModifiedDate
  @Column(
      name = "modified_at",
      nullable = false,
      columnDefinition = "timestamp default CURRENT_TIMESTAMP")
  @ApiModelProperty(
      example = "2023-06-15T09:30:00",
      notes = "Timestamp when the client record was last modified")
  private LocalDateTime modifiedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
}
