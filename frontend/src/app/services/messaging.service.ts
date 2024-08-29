import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {
  private apiUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) {}

  sendMessage(messageData: any): Observable<any> {
    return this.http.post(this.apiUrl, messageData);
  }
}
