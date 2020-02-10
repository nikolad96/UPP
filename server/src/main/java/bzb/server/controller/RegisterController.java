package bzb.server.controller;


import bzb.server.dto.FormFieldsDto;
import bzb.server.dto.FormSubmissionDto;
import bzb.server.dto.TaskDto;
import bzb.server.model.Role;
import bzb.server.model.User;
import bzb.server.service.impl.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/register")
public class RegisterController {

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

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("registracija");

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
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
        String processInstanceId = task.getProcessInstanceId();
        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }

    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);

//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);


        System.out.println("USAO U POST");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "podaci", dto);
        formService.submitTaskForm(taskId, map);
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

    @RequestMapping(method = RequestMethod.GET, path = "/getAdminTasks", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    ResponseEntity<List<FormFieldsDto>> getAdminTasks() {
        //provera da li korisnik sa id-jem pera postoji
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        System.out.println("Get Fields");


        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("admin").list();
        List<TaskDto> taskDtos = new ArrayList<>();

        for(Task t : tasks){
            System.out.println("Print task name:");
            System.out.println(t.getName());
            if(t.getName().equals("Odobrenje statusa recenzenta")){
                TaskDto taskDto = new TaskDto(t.getId(), t.getName(), t.getAssignee());
                taskDtos.add(taskDto);
            }
        }


        return new ResponseEntity(taskDtos,  HttpStatus.OK);
    }
    @PostMapping(path = "/tasks/claim/{taskId}/{username}", produces = "application/json")
    public @ResponseBody
    ResponseEntity claim(@PathVariable("taskId") String taskId,@PathVariable("username") String username) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        taskService.claim(taskId, username);
        System.out.println("TASK: "+task.getName()+"  CLAIMED");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(path = "/recenzent/{username}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TaskDto> complete(@PathVariable String username) {
        Task taskTemp = taskService.createTaskQuery().taskAssignee("nikola321").list().get(0);
        TaskDto dto = new TaskDto(taskTemp.getId(), taskTemp.getName(), taskTemp.getAssignee());

        User user = userService.findOneByUsername(username);
        user.setRecenzent(true);
        Role r = new Role();
        Integer i = 3;
        Long l = i.longValue();
        r.setId(l);
        r.setName("ROLE_RECENZENT");
        List<Role> roles = new ArrayList<>();
        roles = user.getRoles();
        roles.add(r);
        user.setRoles(roles);


        userService.deleteById(user.getId());
        userService.save(user);
        System.out.println("SAVED USER");
        taskService.complete(taskTemp.getId());
        System.out.println("COMPLETED TASK");
        return new ResponseEntity<TaskDto>(dto, HttpStatus.OK);
    }
}
