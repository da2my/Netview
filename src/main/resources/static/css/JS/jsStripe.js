var PUBLISHABLE_KEY ="pk_test_51M2ITwE31NdlKO7Zo1THweZUyJQfJ0JiJQ1i2MNylDDtZKnKuNSUrl7Op6uKp3iNY4IRJ6cQ9UjOeKLrBzRhTAP200gLRv69nA";

var DOMAIN = location.href.replace(/[^/]*$/, "");

var stripe = Stripe(PUBLISHABLE_KEY);

var handleResult = function (result) {
  if (result.error) {
    console.log(result.error.message);
  }
};

$(document).ready(function () {
  var urlParams = new URLSearchParams(window.location.search);
  if (urlParams.has("success")) {
    var success = urlParams.get("success") == "true";
    if (success) {
      $("#payment").hide();
      $("#status").text(
        "Thank you for purchasing Biller! Your order will arrive within the hour."
      );
    } else {
      $("#status").text(
        "There was an error processing your payment. Please try again."
      );
    }
  }

  $("#order-btn1").click(function (event) {
    var checkoutMode = $(event.target).data("checkoutMode");
    var priceId = $(event.target).data("priceId");
    var items = [{ price: priceId, quantity: 1 }];
    stripe
      .redirectToCheckout({
        mode: checkoutMode,
        lineItems: items,
        successUrl: DOMAIN + "pagoexito/true",
        cancelUrl: DOMAIN + "main.html?success=false",
      })
      .then(handleResult);
  }
  );
  $("#order-btn2").click(function (event) {
    var checkoutMode = $(event.target).data("checkoutMode");
    var priceId = $(event.target).data("priceId");
    var items = [{ price: priceId, quantity: 1 }];
    stripe
      .redirectToCheckout({
        mode: checkoutMode,
        lineItems: items,
        successUrl: DOMAIN + "pagoexito/true",
        cancelUrl: DOMAIN + "main.html?success=false",
      })
      .then(handleResult);
  }
  );
});




/*
const stripe = require('stripe')('sk_test_51M2ITwE31NdlKO7ZdAWpRH9jTYmhXfraWVmXwZvnvz5FoOVjyWMyHsBASHItPZyhSES6lbKCRrLGI4W3MUKpTqiI00Cn23DYcl');

const deleted = await stripe.subscriptions.del(
  'sub_1M9uCkE31NdlKO7ZnzOkHSkS'
);
*/