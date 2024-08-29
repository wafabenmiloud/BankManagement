
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessagingService } from '../services/messaging.service';

@Component({
  selector: 'app-messaging',
  templateUrl: './messaging.component.html',
  styleUrls: ['./messaging.component.css']

})
export class MessagingComponent {
  messageForm: FormGroup;

  constructor(private fb: FormBuilder, private messagingService: MessagingService) {
    this.messageForm = this.fb.group({
      subject: ['', Validators.required],
      message: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.messageForm.valid) {
      this.messagingService.sendMessage(this.messageForm.value).subscribe(result => {
        console.log('Message sent:', result);
      });
    }
  }
}