package bzb.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {


//    @PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
//    public @ResponseBody
//    ResponseEntity claim(@PathVariable String taskId) {
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        String processInstanceId = task.getProcessInstanceId();
//        String user = (String) runtimeService.getVariable(processInstanceId, "username");
//        taskService.claim(taskId, user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
