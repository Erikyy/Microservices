package ee.erik.shared.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private UUID id;
    private String name;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Double balance;
}
