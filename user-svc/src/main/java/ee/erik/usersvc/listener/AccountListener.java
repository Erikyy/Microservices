package ee.erik.usersvc.listener;

import ee.erik.shared.user.dto.CreateAccountDto;
import ee.erik.shared.user.dto.DeleteAccountDto;
import ee.erik.shared.user.dto.UpdateAccountDto;
import ee.erik.usersvc.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@KafkaListener(groupId = "account-group", topics = "account")
@Component
@AllArgsConstructor
public class AccountListener {
    private final AccountService accountService;

    @KafkaHandler
    public void accountCreateHandler(CreateAccountDto createAccountDto) {
        this.accountService.create(createAccountDto);
    }

    @KafkaHandler
    public void accountUpdateHandler(UpdateAccountDto updateAccountDto) {
        this.accountService.update(updateAccountDto);
    }

    @KafkaHandler
    public void accountDeleteHandler(DeleteAccountDto deleteAccountDto) {
        this.accountService.delete(deleteAccountDto);
    }
}
