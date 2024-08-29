import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AccountCreationComponent } from './account-creation/account-creation.component';
import { AccountManagementComponent } from './account-management/account-management.component';
import { TransactionHistoryComponent } from './transaction-history/transaction-history.component';
import { TransferFundsComponent } from './transfer-funds/transfer-funds.component';
import { MessagingComponent } from './messaging/messaging.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'account-creation', component: AccountCreationComponent },
  { path: 'account-management', component: AccountManagementComponent },
  { path: 'transaction-history', component: TransactionHistoryComponent },
  { path: 'transfer-funds', component: TransferFundsComponent },
  { path: 'messaging', component: MessagingComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
