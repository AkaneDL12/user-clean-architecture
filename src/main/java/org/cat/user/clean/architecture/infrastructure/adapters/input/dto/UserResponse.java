package org.cat.user.clean.architecture.infrastructure.adapters.input.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String firstname;
    private String lastname;
}
