import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unos-info',
  templateUrl: './unos-info.component.html',
  styleUrls: ['./unos-info.component.css']
})
export class UnosInfoComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  fileUrl: string;
  fileToUpload: File;
  radId: any;
  fileName: any;
  username : string;

  constructor(private userService : UserService, private repositoryService : RepositoryService,private router: Router) {
    
    this.username = "nikola321";

    const x = repositoryService.getTask(this.username);

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

   handleFileInput(file:FileList){
    this.fileToUpload =file.item(0);
    var reader = new FileReader();
    reader.onload=(event:any)=>{
      this.fileUrl =event.target.result;
    }
    reader.readAsDataURL(this.fileToUpload);
    console.log("URL "+this.fileUrl);
    console.log("file "+this.fileToUpload);
  }
   onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.repositoryService.postPodaciTask(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.radId = res;
          const formData = new FormData();  
          formData.append("file", this.fileToUpload);
          this.repositoryService.postFile(this.radId, formData).subscribe(
            data => {
              this.router.navigate(["/login"]);
            }, error => {
              alert("error uploading file");
            }
          );
        },
      err => {
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
  }
}
