const plus = document.getElementById("plus");
const minus = document.getElementById("minus");
const qty = document.getElementById("qty");

const product = JSON.parse(document.getElementById("product")?.innerText ?? "{}");

const categorySelector = document.querySelectorAll("input[name=sizes]");
const colorDisplay = document.getElementById("color-display");

function getColorsFromCategory(slug) {
  if (!slug) {
    return undefined;
  }

  const category = product.categories.find(c => c.slug === slug);
  if (!category) {
    return undefined;
  }

  return category.colors;
}

function getColorFromSlug(colors, slug) {
  if (!colors) {
    return undefined;
  }

  const color = colors.find(c => c.slug === slug);
  if (!color) {
    return undefined;
  }

  return color;
}

// biome-ignore lint/complexity/noForEach: <explanation>
categorySelector.forEach((selector) => {
  selector.addEventListener("click", (event) => {
    const value = event.currentTarget.value;
    const colors = getColorsFromCategory(value);

    qty.innerHTML = "01";

    // biome-ignore lint/complexity/noForEach: <explanation>
    document.querySelectorAll("#color-display div").forEach(element => element.remove());
    if (!colors) {
      return;
    }

    // biome-ignore lint/complexity/noForEach: <explanation>
    colors.forEach(color => {
      const colorDiv = document.createElement("div");
      const radioInput = document.createElement("input");
      radioInput.type = "radio";
      radioInput.id = color.slug;
      radioInput.name = "colors";
      radioInput.value = color.slug;
      radioInput.checked = colors[0].slug === color.slug;

      const colorLabel = document.createElement("label");
      colorLabel.htmlFor = color.slug;

      const colorSpan = document.createElement("span");
      colorSpan.style.backgroundColor = color.hex;

      const checkImg = document.createElement('img');
      checkImg.src = 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg';
      checkImg.alt = 'Checked Icon';

      colorSpan.appendChild(checkImg);
      colorLabel.appendChild(colorSpan);
      colorDiv.appendChild(radioInput);
      colorDiv.appendChild(colorLabel);

      colorDisplay.appendChild(colorDiv);
    });
  });
})

plus.addEventListener("click", () => {
  const categorySlug = document.querySelector(`input[name="sizes"]:checked`).value;
  const colorSlug = document.querySelector(`input[name="colors"]:checked`).value;
  const color = getColorFromSlug(getColorsFromCategory(categorySlug), colorSlug);
  const quantity = parseInt(qty.innerText);
  if (quantity >= color.quantity) {
    plus.disabled = true;
    plus.ariaDisabled = true;
    qty.innerText = String(color.quantity).padStart(2, "0");
    return;
  }

  plus.disabled = false;
  plus.ariaDisabled = false;
  qty.innerText = String(quantity + 1).padStart(2, "0");
});

minus.addEventListener("click", () => {
  const quantity = parseInt(qty.innerText);
  if (quantity <= 1) {
    minus.disabled = true;
    minus.ariaDisabled = true;
    qty.innerText = "01";
    return;
  }

  minus.disabled = false;
  minus.ariaDisabled = false;
  qty.innerText = String(quantity - 1).padStart(2, "0");
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
