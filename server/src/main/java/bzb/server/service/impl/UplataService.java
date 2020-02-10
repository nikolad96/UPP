package bzb.server.service.impl;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UplataService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("******************************");
        System.out.println("UPLATA JE PROSLAAAAAAAA");
        System.out.println("******************************");
    }
}
