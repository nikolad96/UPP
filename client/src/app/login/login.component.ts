import { Component, OnInit } from '@angular/core';
import {LoginInfo} from '../model/LoginInfo';
import {AuthService} from '../services/auth.service';
import {TokenStorageService} from '../services/tokenStorage/token-storage.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userF: string;
  passF: string;
  success: boolean;
  loginInfo: LoginInfo;
  roles: Array<string>;
  authority: any;

  constructor(private loginService: AuthService, private tokenStorage: TokenStorageService ) { }

  public submit() {
    this.loginInfo = new LoginInfo();
    this.loginInfo.username = this.userF;
    this.loginInfo.password = this.passF;

    this.loginService.login(this.loginInfo).subscribe(data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.roles = data.authorities;





        console.log('Uspesan Login');
      },
      error => {
        console.log(error);
        this.success = false;

      });
  }

  ngOnInit() {
    this.success = true;
  }


}
