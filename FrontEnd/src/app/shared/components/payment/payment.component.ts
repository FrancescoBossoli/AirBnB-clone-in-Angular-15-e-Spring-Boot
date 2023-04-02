import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICreateOrderRequest, IPayPalConfig, NgxPayPalModule } from 'ngx-paypal';
import { environment } from 'src/environments/environment.development';

@Component({
   selector: 'app-payment',
   templateUrl: './payment.component.html',
   styleUrls: ['./payment.component.scss'],
   changeDetection: ChangeDetectionStrategy.OnPush,
   standalone: true,
   imports: [CommonModule, NgxPayPalModule]
})
export class PaymentComponent implements OnInit {

   payPalConfig?: IPayPalConfig;
   @Input()price:number = 0;
   @Output() paymentAccepted = new EventEmitter();

   ngOnInit(): void {
      this.initConfig();
   }

   private initConfig(): void {
      this.payPalConfig = {
         currency: 'EUR', clientId: environment.paypalId, createOrderOnClient: (data) => <ICreateOrderRequest> {
            intent: 'CAPTURE', purchase_units: [{
                  amount: {
                     currency_code: 'EUR',
                     value: this.price.toString(),
                     breakdown: {
                        item_total: {
                           currency_code: 'EUR',
                           value: this.price.toString()
                        }
                     }
                  },
                  items: [
                     {
                        name: 'Enterprise Subscription',
                        quantity: '1',
                        category: 'DIGITAL_GOODS',
                        unit_amount: {
                           currency_code: 'EUR',
                           value: this.price.toString(),
                        },
                     }
                  ]
            }]
         },
         advanced: { commit: 'true' },
         style: {
            label: 'paypal',
            layout: 'vertical'
         },
         onApprove: (data, actions) => {
            console.log('onApprove - transaction was approved, but not authorized', data, actions);
            actions.order.get().then((details: any) => {
               console.log('onApprove - you can get full order details inside onApprove: ', details);
            });
         },
         onClientAuthorization: (data) => {
            console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
            this.paymentAccepted.emit();
         },
         onCancel: (data, actions) => {
            console.log('OnCancel', data, actions);
         },
         onError: err => {
            console.log('OnError', err);
         },
         onClick: (data, actions) => {
            console.log('onClick', data, actions);
         },
      };
   }
}
