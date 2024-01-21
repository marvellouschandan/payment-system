# payment-system

## Creating the UI
Add script in the index.html file:
```html
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
```

In react, just create a Form component as below:
```javascript
import axios from "axios";
import React, { useEffect, useState } from "react";

const BASE_URL = 'http://localhost:8080/payment-system';
const RZP_ID_KEY = '';

const Form = () => {
    const [enteredName, setEnteredName] = useState('');
    const [enteredEmail, setEnteredEmail] = useState('');
    const [enteredContact, setEnteredContact] = useState('');
    const [enteredAmount, setEnteredAmount] = useState('');

    const nameChangeHandler = (event) => {
        setEnteredName(event.target.value);
    };

    const emailChangeHandler = (event) => {
        setEnteredEmail(event.target.value);
    };

    const contactChangeHandler = (event) => {
        setEnteredContact(event.target.value);
    };

    const amountChangeHandler = (event) => {
        setEnteredAmount(event.target.value);
    }



    const submitHandler = (event) => {
        // So, that page doesn't refresh
        event.preventDefault();

        const data = {
            amount: enteredAmount,
            currency: 'INR'
        };

        console.log(data);

        checkout(data).then(response => {
            console.log(response.data);

            var options = {
                "key": RZP_ID_KEY, // Enter the Key ID generated from the Dashboard
                "amount": response.data.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
                "currency": response.currency,
                "name": "Support Chandan", //your business name
                "description": "Offer Chandan a coffee",
                "image": "",
                "order_id": response.data.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
                "handler": function (responseData){
                    confirmPayment(responseData).then(response => {
                        console.log(response.data);
                    });
                },
                "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information, especially their phone number
                    "name": enteredName, //your customer's name
                    "email": enteredEmail,
                    "contact": enteredContact  //Provide the customer's phone number for better conversion rates 
                },
                "notes": response.data.notes,
                "theme": {
                    "color": "#3399cc"
                }
            };
            var rzp1 = new Razorpay(options);
            rzp1.on('payment.failed', function (response) {
                alert(response.error.code);
                alert(response.error.description);
                alert(response.error.source);
                alert(response.error.step);
                alert(response.error.reason);
                alert(response.error.metadata.order_id);
                alert(response.error.metadata.payment_id);
            });
            rzp1.open();
        })
    };

    const checkout = (data) => {
        return axios({
            'method': 'POST',
            'url': `${BASE_URL}/checkout`,
            'data': data
        })
    }

    const confirmPayment = (data) => {
        return axios({
            'method': 'POST',
            'url': `${BASE_URL}/confirm`,
            'data': data
        })
    }

    return (
        <form onSubmit={submitHandler}>
            <div>
                <div>
                    <label>Name</label>
                    {/* Gets triggered on every key stroke */}
                    <input type="text" value={enteredName} onChange={nameChangeHandler} />
                </div>
                <div>
                    <label>Email</label>
                    <input type="email" value={enteredEmail} onChange={emailChangeHandler} />
                </div>
                <div>
                    <label>Contact</label>
                    <input type="number" value={enteredContact} onChange={contactChangeHandler} />
                </div>
                <div>
                    <label>Amount</label>
                    <input type="text" value={enteredAmount} onChange={amountChangeHandler} />
                </div>
            </div>
            <div className="new-expense__actions">
                <button type="submit">Pay Now</button>
            </div>
        </form>
    );
}

export default Form;
```

Now add you razorpay key in above component.

And in the main component, just use the Form component.
```javascript
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import Form from './Form.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Form/>
  </React.StrictMode>,
)
```

Done




