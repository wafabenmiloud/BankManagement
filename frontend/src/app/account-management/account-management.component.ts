import { Component } from '@angular/core';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-account-management',
  templateUrl: './account-management.component.html',
  styleUrls: ['./account-management.component.css']
})
export class AccountManagementComponent {
  accountDetails: any;

  constructor(private accountService: AccountService) {
    this.accountService.getAccountDetails('12345').subscribe(data => {
      this.accountDetails = data;
    });
  }
}
