import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {RepositoryService} from '../services/repository/repository.service';
import {TokenStorageService} from '../services/tokenStorage/token-storage.service';

@Component({
  selector: 'app-recenzent',
  templateUrl: './recenzent.component.html',
  styleUrls: ['./recenzent.component.css']
})
export class RecenzentComponent implements OnInit {


  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  username: string;
  constructor(private userService : UserService, private repositoryService : RepositoryService, private tokenStorage: TokenStorageService) {
    this.username = this.tokenStorage.getUsername();
    const x = repositoryService.getRecenzentTask(this.username);

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          if (field.type.name=="string") {
            let s = field.value.value;
            console.log(field.value.value);
          }
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

  ngOnInit() {
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.repositoryService.complete(this.username);

    x.subscribe(
      res => {
        console.log(res);

        alert("Recenzent prihvacen!")
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
