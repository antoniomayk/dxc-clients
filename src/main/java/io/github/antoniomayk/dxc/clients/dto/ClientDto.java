package io.github.antoniomayk.dxc.clients.dto;

import io.github.antoniomayk.dxc.clients.constraint.PhoneNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO for a {@link io.github.antoniomayk.dxc.clients.entity.Client}.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@ApiModel(description = "DTO for a Client")
public class ClientDto {
  @ApiModelProperty(example = "John Doe", required = true, notes = "Full name of the client")
  @NotBlank
  @Size(min = 2, max = 100)
  private String fullName;

  @ApiModelProperty(
      example = "john.doe@example.com",
      required = true,
      notes = "Email address of the client")
  @Email
  private String email;

  @ApiModelProperty(
      example = "+1-555-123-4567",
      required = true,
      notes = "Phone number of the client")
  @PhoneNumber
  private String phoneNumber;

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

  @Override
  public String toString() {
    return "ClientDto [fullName="
        + fullName
        + ", email="
        + email
        + ", phoneNumber="
        + phoneNumber
        + "]";
  }
}
