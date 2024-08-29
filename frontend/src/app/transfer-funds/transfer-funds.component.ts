
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-transfer-funds',
  templateUrl: './transfer-funds.component.html',
  styleUrls: ['./transfer-funds.component.css']

})
export class TransferFundsComponent {
  transferForm: FormGroup;

  constructor(private fb: FormBuilder, private accountService: AccountService) {
    this.transferForm = this.fb.group({
      fromAccount: ['', Validators.required],
      toAccount: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
    });
  }

  onSubmit() {
    if (this.transferForm.valid) {
      this.accountService.transferFunds(this.transferForm.value).subscribe(result => {
        console.log('Transfer successful:', result);
      });
    }
  }
}
