package bzb.server.service.impl;

import bzb.server.model.User;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckAktivityService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserService userService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        identityService.setAuthenticatedUserId("nikola321");
        String username = identityService.getCurrentAuthentication().getUserId();
        User user = userService.findOneByUsername(username);
        System.out.println(user.getUsername());
        delegateExecution.setVariable("aktivan",false);
    }
}
