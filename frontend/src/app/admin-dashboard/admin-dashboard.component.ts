
import { Component } from '@angular/core';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']

})
export class AdminDashboardComponent {
  accounts: any[] = [];

  constructor(private accountService: AccountService) {
    this.accountService.getAllAccounts().subscribe(data => {
      this.accounts = data;
    });
  }

  deactivateAccount(accountId: string) {
    this.accountService.deactivateAccount(accountId).subscribe(result => {
      console.log('Account deactivated:', result);
    });
  }
}
