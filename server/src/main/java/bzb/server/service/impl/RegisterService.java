package bzb.server.service.impl;


import bzb.server.dto.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RegisterService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("USAO U SERVIS");
        String var = "Pera";
        var = var.toUpperCase();
        execution.setVariable("activated", "false");
        execution.setVariable("input", var);
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registracija");
        System.out.println(registration);
        User user = identityService.newUser("");
        for (FormSubmissionDto formField : registration) {
            if(formField.getFieldId().equals("username")) {
                user.setId(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("ime")) {
                user.setFirstName(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("prezime")) {
                user.setLastName(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("email")) {
                user.setEmail(formField.getFieldValue());
            }
        }

        identityService.saveUser(user);
        bzb.server.model.User user1 = new bzb.server.model.User();
        for (FormSubmissionDto formField : registration) {
            if(formField.getFieldId().equals("username")) {
                user1.setUsername(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("password")) {
                String salt = BCrypt.gensalt();
                String hashedPass = BCrypt.hashpw(formField.getFieldValue(), salt);
                user1.setPassword(hashedPass);
            }
            if(formField.getFieldId().equals("ime")) {
                user1.setIme(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("prezime")) {
                user1.setPrezime(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("email")) {
                user1.setEmail(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("drzava")) {
                user1.setDrzava(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("grad")) {
                user1.setGrad(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("titula")) {
                user1.setTitula(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("recenzent")) {
                user1.setRecenzent(formField.getFieldValue().equals("true"));
            }
            if(formField.getFieldId().equals("naucnaOblast")) {
                user1.setNaucnaOblast(formField.getFieldValue());
            }


            userService.update(user1);
        }
    }
}
