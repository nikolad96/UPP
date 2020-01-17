import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  fetchUsers() {
    return this.httpClient.get("http://localhost:8080/user/fetch", httpOptions) as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/register/post/".concat(taskId), user, httpOptions) as Observable<any>;
  }
  verifyEmail(processId, username) {
    return this.httpClient.get("http://localhost:8080/mail/verify/"+processId+"/"+username, httpOptions) as Observable<any>;
  }
}
