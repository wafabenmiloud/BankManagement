import { Component } from '@angular/core';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent {
  transactions: any[] = [];

  constructor(private accountService: AccountService) {
    this.accountService.getTransactionHistory('12345').subscribe(data => {
      this.transactions = data;
    });
  }
}
