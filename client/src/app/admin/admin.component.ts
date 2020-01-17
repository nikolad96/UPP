import { Component, OnInit } from '@angular/core';
import {RepositoryService} from '../services/repository/repository.service';
import {TokenStorageService} from '../services/tokenStorage/token-storage.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  username: string;

  constructor(private repositoryService: RepositoryService, private tokenStorage: TokenStorageService) {
    this.username = this.tokenStorage.getUsername();
  }

  ngOnInit() {
  }

  getTasks() {
    let x = this.repositoryService.getAdminTasks();

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  claim(taskId){
    let x = this.repositoryService.claimTask(taskId, this.username);

    x.subscribe(
      res => {
        console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
