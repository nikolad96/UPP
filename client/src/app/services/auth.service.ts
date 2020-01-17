import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginInfo} from '../model/LoginInfo';
import {JwtResponse} from '../model/JwtResponse';

const httpOptions = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/auth/login';


  constructor(private http: HttpClient) { }

  login(loginInfo: LoginInfo): Observable<any> {
    return this.http.post<JwtResponse>(this.loginUrl, loginInfo, httpOptions);
  }


}
