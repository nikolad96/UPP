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
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http : Http) {
    

  }

  download(taskId){
    const httpOptions = {
      'responseType'  : 'arraybuffer' as 'json'
    };
    return this.httpClient.get("http://localhost:8080/obrada/download/".concat(taskId), httpOptions);
  }
  postFile(radId, data) {
    return this.http.post("http://localhost:8080/obrada/post/file/".concat(radId), data) as Observable<any>;
  }
  startProcess() {
    return this.httpClient.get('http://localhost:8080/welcome/get') as Observable<any>;
  }
  startRegistrationProcess() {
    return this.httpClient.get('http://localhost:8080/register/get') as Observable<any>;
  }

  startObradaProcess() {
    return this.httpClient.get('http://localhost:8080/obrada/get') as Observable<any>;
  }
  postCasopisTask(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/casopis/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  postPodaciTask(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/podaci/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  posUpdate(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/podaci/update/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  postProvera1Task(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/provera1/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  postBrojRecenzenata(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/broj/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  postRecenzenti(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/recenzenti/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  postFormatiranTask(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/post/formatiranje/".concat(taskId), casopis, httpOptions) as Observable<any>;
  }
  getTask(username) {
    return this.httpClient.get('http://localhost:8080/obrada/get/'.concat(username)) as Observable<any>;
  }
  getUrednikTask(username) {
    return this.httpClient.get('http://localhost:8080/obrada/get/urednik/'.concat(username)) as Observable<any>;
  }
  getTasks(processInstance : string){

    return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }
  getAdminTasks(){

    return this.httpClient.get('http://localhost:8080/register/getAdminTasks') as Observable<any>
  }

  claimTask(taskId, username){
    return this.httpClient.post('http://localhost:8080/register/tasks/claim/' + taskId + '/' + username, null) as Observable<any>
  }
  getRecenzentTask(username) {
    return this.httpClient.get('http://localhost:8080/register/get/'.concat(username)) as Observable<any>
  }

  complete(username){
    return this.httpClient.post('http://localhost:8080/register/recenzent/'.concat(username), null) as Observable<any>
  }

  completeTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

}
