
async function handleFileUpload(file) {
  const key = file.name.replaceAll(" ", "");
  let res = await fetch("https://blobstore.vercel.app/api/upload?key=" + key + "&type=" + file.type);
  if (!res.ok) {
    throw Error("line 6 failed to upload the file")
  }

  const payload = (await res.json());

  res = await fetch(payload.url, {
    method: "PUT",
    body: file,
  });

  if (!res.ok) {
    throw Error("failed to upload the file")
  }

  return payload.key
}

// biome-ignore lint/complexity/useArrowFunction: use arrow function as it uses native DOM
document.getElementById('productForm').addEventListener('submit', async function(event) {
  event.preventDefault();

  const productURL1 = document.getElementById("productURL1");
  const productURL2 = document.getElementById("productURL2");
  const productURL3 = document.getElementById("productURL3");

  const urls = []
  try {
    if (productURL1.files.length && productURL1.files[0]) {
      const url = await handleFileUpload(productURL1.files[0])
      urls.push(url)
    }
    if (productURL2.files.length && productURL2.files[0]) {
      const url = await handleFileUpload(productURL2.files[0])
      urls.push(url)
    }
    if (productURL3.files.length && productURL3.files[0]) {
      const url = await handleFileUpload(productURL3.files[0])
      urls.push(url)
    }
  } catch (error) {
    console.error(error)
    alert("Failed to upload the file")
    return
  }

  const formData = new FormData(event.target);
  const colorSelect = document.getElementById('colorSelect');
  const selectedColorOption = colorSelect.options[colorSelect.selectedIndex];
  const selectedColorHex = selectedColorOption.getAttribute('data-hex');

  const sizeSelect = document.getElementById('sizeSelect');
  const selectedSize = sizeSelect.options[sizeSelect.selectedIndex];

  const product = {
    product: {
      name: formData.get('name'),
      photo_urls: urls,
      description: formData.get('description'),
      price: formData.get('price'),
      categories: [
        {
          name: selectedSize.text,
          colors: [
            {
              name: selectedColorOption.text,
              qty: formData.get('quantity'),
              hex: selectedColorHex
            }
          ]
        }
      ],
      tags: [4],
      collections: [1]
    }
  };

  console.log(product)
  const productJson = JSON.stringify(product);

  fetch('http://localhost:8081/admin/products/create', {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: productJson
  })
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(data => {
      console.log('Success:', data);
    })
    .catch((error) => {
      console.error('Error:', error);
    });
});

