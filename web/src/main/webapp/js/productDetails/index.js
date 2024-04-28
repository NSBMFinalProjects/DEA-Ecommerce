const plus = document.getElementById("plus");
const minus = document.getElementById("minus");
const qty = document.getElementById("qty");

const product = JSON.parse(document.getElementById("product")?.innerText ?? "{}");

plus.addEventListener("click", () => {
  qty.innerText = String(parseInt(qty.innerText) + 1).padStart(2, "0");
});

minus.addEventListener("click", () => {
  let quantity = parseInt(qty.innerText);
  if (quantity <= 1) {
    qty.disabled = true;
    qty.ariaDisabled = true;
    qty.innerText = "01";
    return;
  }

  quantity--;
  qty.disabled = false;
  qty.ariaDisabled = false;
  qty.innerText = String(quantity).padStart(2, "0");
});

const formEl = document.querySelector(".addToCartForm");
// biome-ignore lint/complexity/useArrowFunction: <explanation>
formEl.addEventListener("submit", function(event) {
  event.preventDefault();

  const urlParams = new URLSearchParams(window.location.search);
  const productId = urlParams.get('id');

  const productName = document.querySelector('input[name="dress-name"]').value;
  const productPrice = document.querySelector('input[name="dress-price"]').value;
  const quantity = document.querySelector('.num').innerText;

  const sizeRadios = document.querySelectorAll('input[name="options-base"]');
  let selectedSize = '';
  sizeRadios.forEach(radio => {
    if (radio.checked) {
      switch (radio.id) {
        case 'option5':
          selectedSize = 'small';
          break;
        case 'option6':
          selectedSize = 'medium';
          break;
        case 'option7':
          selectedSize = 'large';
          break;
        default:
          selectedSize = 'Unknown';
      }
    }
  });

  // Create the product object with the selected size
  const product = {
    id: productId, // Assign the product ID
    name: productName,
    price: productPrice,
    quantity: quantity,
    size: selectedSize // Include the selected size
  };

  // Retrieve the existing products from local storage
  let products = localStorage.getItem('products');
  // If there are no products in local storage, initialize an empty array
  if (!products) {
    products = [];
  } else {
    // If there are products, parse the JSON string back into an array
    products = JSON.parse(products);
  }

  // Append the new product to the array
  products.push(product);

  // Stringify the updated array and store it back in local storage
  localStorage.setItem('products', JSON.stringify(products));

  alert('Product added to cart!');
});
