import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-provera1',
  templateUrl: './provera1.component.html',
  styleUrls: ['./provera1.component.css']
})
export class Provera1Component implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  username : string;

  constructor(private userService : UserService, private repositoryService : RepositoryService,private router: Router) {
    
    this.username = "urednik321";

    const x = repositoryService.getUrednikTask(this.username);

    x.subscribe(
      res => {
       
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
         console.log(field.value);
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );
   }
   onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.repositoryService.postProvera1Task(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log("RES");
        console.log(res);
        this.router.navigate(["/formatiran"]);
        // alert("Uspesno uneseni podaci")
      },
      err => {
        console.log("Error occured");
      }
    );
  }
  ngOnInit() {
  }
}
