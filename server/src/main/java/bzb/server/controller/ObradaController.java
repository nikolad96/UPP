package bzb.server.controller;


import bzb.server.dto.FormFieldsDto;
import bzb.server.dto.FormSubmissionDto;
import bzb.server.model.Casopis;
import bzb.server.model.Rad;
import bzb.server.model.User;
import bzb.server.repository.CasopisRepository;
import bzb.server.repository.RadRepository;
import bzb.server.service.impl.RadService;
import bzb.server.service.impl.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/obrada")
public class ObradaController {

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    RadService radService;

    @Autowired
    CasopisRepository casopisRepository;

    @Autowired
    RadRepository radRepository;

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @RequestMapping(method = RequestMethod.GET, path = "/get", produces = "application/json")
    public @ResponseBody
    FormFieldsDto get() {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        System.out.println("Get Fields");


        org.camunda.bpm.engine.identity.User user = identityService.newUser("");
        user.setPassword("123456");
        user.setId("nikola321");
        user.setFirstName("Nikola");
        user.setLastName("Djordjevic");
        identityService.saveUser(user);
//                org.camunda.bpm.engine.identity.User
              user = identityService.newUser("");
        user.setPassword("123456");
        user.setId("urednik321");
        user.setFirstName("Urednik");
        user.setLastName("Urednikovic");
        identityService.saveUser(user);
        Group  recenzenti = identityService.newGroup("");

        recenzenti.setId("recenzenti");
        recenzenti.setName("Recenzenti");
        identityService.saveGroup(recenzenti);

        user = identityService.newUser("");
        user.setPassword("123456");
        user.setId("recenzent1");
        user.setFirstName("recenzent1");
        user.setLastName("recenzent1");
        identityService.saveUser(user);
        identityService.createMembership("recenzent1","recenzenti");

        user = identityService.newUser("");
        user.setPassword("123456");
        user.setId("recenzent2");
        user.setFirstName("recenzent2");
        user.setLastName("recenzent2");
        identityService.saveUser(user);
        identityService.createMembership("recenzent2","recenzenti");


        identityService.setAuthenticatedUserId("nikola321");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("obrada");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        System.out.println(identityService.getCurrentAuthentication().getUserId());

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        List<Casopis> casopisList = casopisRepository.findAll();

        for(FormField field : properties) {
            if(field.getId().equals("casopis")){
                EnumFormType enumType = (EnumFormType) field.getType();
                for(Casopis c: casopisList){
                    if(userService.findOneByUsername(identityService.getCurrentAuthentication().getUserId()).getNaucnaOblast().equals(c.getOblast()))
                    enumType.getValues().put(c.getId().toString(), c.getNaziv());
                }
            }
        }

//        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("nikola321","123456"));
//
//        User user =  (User) authentication.getPrincipal();
//        System.out.println("USERNAME: "+ user.getUsername());

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/post/casopis/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postCasopis(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
//        System.out.println(dto);
//        System.out.println(map.get("casopis"));


        System.out.println("CASOPIS IDDD");
        System.out.println(map.get("casopis"));
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        Casopis c = casopisRepository.findOneById((Long.parseLong((String) map.get("casopis"))));
        System.out.println(c);
        runtimeService.setVariable(processInstanceId, "casopis", dto);
        runtimeService.setVariable(processInstanceId, "openAccess", c.getOpen());
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/post/podaci/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postPodaci(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "podaci", dto);
        Rad rad = new Rad();
        System.out.println((String) map.get("apstrakt"));
        System.out.println((String) map.get("oblast"));
        System.out.println((String) map.get("naslov"));
        rad.setApstrakt((String) map.get("apstrakt"));
        rad.setNaziv((String) map.get("naslov"));
//        rad.setOblast((String) map.get("oblast"));
        rad.setOblast("Geografija");
        radRepository.save(rad);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(rad.getId().toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/post/podaci/update/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity updatePodaci(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        identityService.setAuthenticatedUserId("nikola321");
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        List<FormSubmissionDto> dto1 = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "podaci");
        HashMap<String, Object> map1 = this.mapListToDto(dto1);



        System.out.println((String) map1.get("naslov"));

        Rad pom = radRepository.findOneByNaziv((String) map1.get("naslov"));

        Rad rad = new Rad();
        System.out.println(pom.getNaziv());
        System.out.println(pom.getPdf());
        rad.setPdf(pom.getPdf());
        rad.setId(pom.getId());
        System.out.println((String) map.get("apstrakt"));
        System.out.println((String) map.get("oblast"));
        System.out.println((String) map.get("naslov"));
        rad.setApstrakt((String) map.get("apstrakt"));
        rad.setNaziv((String) map.get("naslov"));
//        rad.setOblast((String) map.get("oblast"));
        rad.setOblast("Geografija");
//        radRepository.deleteById(pom.getId());
        radRepository.save(rad);
        formService.submitTaskForm(taskId, map);
        runtimeService.setVariable(processInstanceId, "podaci", dto);
        return new ResponseEntity<>(rad.getId().toString(), HttpStatus.OK);
    }


    @PostMapping(path = "/post/file/{radId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postPodaci(@RequestParam("file") MultipartFile file , @PathVariable String radId) {
       Rad rad = radRepository.findOneById(Long.parseLong(radId));
        rad = radService.savePdf(file,rad);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get/{username}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getByUserName(@PathVariable String username) {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        System.out.println("Get Fields");
        Task task = taskService.createTaskQuery().taskAssignee("nikola321").list().get(0);


        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }
        for(FormField field : properties) {
            if(field.getId().equals("oblast")){
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().put("Geografija","Geografija");
                enumType.getValues().put("Biologija","Biologija");
            }
        }
        String processInstanceId = task.getProcessInstanceId();
        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/get/urednik/{username}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getForUrednik(@PathVariable String username) {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        System.out.println("Get Fields");
        Task task = taskService.createTaskQuery().taskAssignee("urednik321").list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        List<FormSubmissionDto> dto = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "podaci");

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
            if(fp.getId().equals("naslov")){
//                System.out.println("vrednost:");
//                System.out.println(fp.getValue());
            }
        }

        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }

    @PostMapping(path = "/post/provera1/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity provera1(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String proveren = "";
        for(FormSubmissionDto fs : dto){
            System.out.println("FEILD NAME:  ");
            System.out.println(fs.getFieldId());
            if(fs.getFieldId().equals("prihvatljiv")){
                System.out.println("USAO U IF");
                if(fs.getFieldValue().equals("true")){
                    runtimeService.setVariable(processInstanceId, "prihvatljiv", "true");
                    System.out.println("PRIHVATLJIV JEEEEEEEEEE");
                    proveren = "true";
                }
                else{
                    runtimeService.setVariable(processInstanceId, "prihvatljiv", "false");
                    System.out.println("NIJEEEEEEEEE PRIHVATLJIV ");
                    proveren = "false";
                }
                break;
            }
        }

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(proveren, HttpStatus.OK);
    }

    @PostMapping(path = "/post/formatiranje/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity formatiranje(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String formatirano = "";
        for(FormSubmissionDto fs : dto){
            System.out.println("FEILD NAME:  ");
            System.out.println(fs.getFieldId());
            if(fs.getFieldId().equals("formatiranje")){
                System.out.println("USAO U IF");
                if(fs.getFieldValue().equals("true")){
                    runtimeService.setVariable(processInstanceId, "formatiran", "true");
                    System.out.println("formatiran JEEEEEEEEEE");
                    formatirano = "true";
                }
                else{
                    runtimeService.setVariable(processInstanceId, "formatiran", "false");
                    System.out.println("NIJEEEEEEEEE formatiran ");
                    formatirano = "false";
                }
                break;
            }
        }

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(formatirano, HttpStatus.OK);
    }
    @PostMapping(path = "/post/broj/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity brojRecenzenata(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        for(FormSubmissionDto fs : dto){
            System.out.println("FEILD NAME:  ");
            System.out.println(fs.getFieldId());
            if(fs.getFieldId().toString().equals("brojRecenzenata")){
                System.out.println("USAO U IF");
                runtimeService.setVariable(processInstanceId, "brojRecenzenata", fs.getFieldValue());
                System.out.println(fs.getFieldValue());
                break;
            }
        }
        System.out.println(map);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/post/recenzenti/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity recenzenti(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        System.out.println("USAO U POST");
        System.out.println(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        for(FormSubmissionDto fs : dto){
            System.out.println("FEILD NAME:  ");
            System.out.println(fs.getFieldId());
            if(fs.getFieldId().toString().equals("brojRecenzenata")){
                System.out.println("USAO U IF");
                runtimeService.setVariable(processInstanceId, "recenzentiL", fs.getFieldValue());
                System.out.println(fs.getFieldValue());
                break;
            }
        }
        System.out.println(map);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/download/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> showFile(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        List<FormSubmissionDto> dto1 = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "podaci");
        HashMap<String, Object> map1 = this.mapListToDto(dto1);
        Rad rad = radRepository.findOneByNaziv((String) map1.get("naslov"));


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + rad.getNaziv() + "\"")
                .body(new ByteArrayResource(rad.getPdf()));
    }



}
