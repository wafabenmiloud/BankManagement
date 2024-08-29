import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms'; // Import this module

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AccountCreationComponent } from './account-creation/account-creation.component';
import { AccountManagementComponent } from './account-management/account-management.component';
import { TransactionHistoryComponent } from './transaction-history/transaction-history.component';
import { TransferFundsComponent } from './transfer-funds/transfer-funds.component';
import { MessagingComponent } from './messaging/messaging.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AccountCreationComponent,
    AccountManagementComponent,
    TransactionHistoryComponent,
    TransferFundsComponent,
    MessagingComponent,
    AdminDashboardComponent,
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule  // Add this line

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
