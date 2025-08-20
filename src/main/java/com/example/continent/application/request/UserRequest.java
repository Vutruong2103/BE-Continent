package com.example.continent.application.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
// This class is currently empty, but can be extended in the future to include user request details
public class UserRequest {

    @NotNull
    String userName;

    @NotNull
    String password;

    @NotNull
    @Email
    String email;

    @NotNull
    String phoneNumber;

    @NotNull
    String fullName;

    @NotNull
    Long roleId;
}
