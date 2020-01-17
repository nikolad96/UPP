package bzb.server.handlers;

import bzb.server.dto.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class SendMailHandler implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Usao u slanje mejla");

        try{
            System.out.println("Usao u Try");
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("nikola.djordjevic04@gmail.com");
            final User user = identityService.newUser("");
            msg.setSubject("Verfikacija UPP TEST");
            String username = "";
            List<FormSubmissionDto> registrations = (List<FormSubmissionDto>)execution.getVariable("registracija");
            for(FormSubmissionDto fs: registrations){
                if(fs.getFieldId().equals("username")){
                    username = fs.getFieldValue();
                }
            }



            String processInstanceId = execution.getProcessInstanceId();
            String salt = BCrypt.gensalt();
            String hash = BCrypt.hashpw(username, salt);
            runtimeService.setVariable(processInstanceId,"hash", hash);
            msg.setText("Verifikujte registraciju klikom na sledeci link: \n http://localhost:4200/verify/"+processInstanceId+"/"+username);
            javaMailSender.send(msg);
            System.out.println("POSLATO");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
