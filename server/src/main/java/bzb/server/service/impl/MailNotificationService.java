package bzb.server.service.impl;

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
public class MailNotificationService implements JavaDelegate {

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
            msg.setSubject("Notifikacija");
            String naslov = "";
            List<FormSubmissionDto> registrations = (List<FormSubmissionDto>)execution.getVariable("podaci");
            for(FormSubmissionDto fs: registrations){
                if(fs.getFieldId().equals("naslov")){
                    naslov = fs.getFieldValue();
                }
            }

            msg.setText("Obavestavamo vas da je dodat novi rad: " + naslov + " U casopisu na kojem ste glavni urednik");
            javaMailSender.send(msg);
            System.out.println("POSLATO");
            execution.setVariable("glavniUrednik","urednik321");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
