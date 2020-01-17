import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../services/users/user.service';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  processId: string;
  username: string;
  constructor(private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit() {
    this.processId = this.route.snapshot.paramMap.get('processId');
    this.username = this.route.snapshot.paramMap.get('username');
    console.log(this.processId);
    const x = this.userService.verifyEmail(this.processId, this.username);
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
