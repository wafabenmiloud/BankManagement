import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(accountData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, accountData);
  }

  getAccountDetails(accountId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${accountId}`);
  }

  getTransactionHistory(accountId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${accountId}/transactions`);
  }

  transferFunds(transferData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/transfer`, transferData);
  }

  getAllAccounts(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  deactivateAccount(accountId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${accountId}`);
  }
}
