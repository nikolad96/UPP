import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-odabir-casopis',
  templateUrl: './odabir-casopis.component.html',
  styleUrls: ['./odabir-casopis.component.css']
})
export class OdabirCasopisComponent implements OnInit {


  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private userService : UserService, private repositoryService : RepositoryService,private router: Router) {
    


    const x = repositoryService.startObradaProcess();

    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
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
    let x = this.repositoryService.postCasopisTask(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        
        // alert("You choose succesfully");
        this.router.navigate(["/unos-info"]);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
  }

}
