

function loadPurchased(userID) {
	fetch('http://localhost:8080/purchared/' + userID, {
		method: "post",
	}).then((response) => {
		return response.json();
	})
		.then((data) => {
			let html = '';

			for (let cart of data) {
				html += `<tr id = "cart${cart.book.bookID}">
	<td class="cart_product_img"><a href="/view/detail/${cart.book.bookID}"><img
			src = "${cart.book.image} " alt=""></a></td>
	<td class="cart_product_desc">
		<h5>${cart.book.title}</h5>
	</td>
	<td class="qty">
		<div class="qty-btn d-flex">
			
			<div class="quantity">
				<input type="text" readonly
					class="qty-text" 
					name="quantity" value="${cart.quantity}"> 
			</div>
		</div>
	</td>
	<td>
	   <a type="button"  onclick ="if(confirmOrder())(deleteOrder(${cart.orederDetailID}))" data-toggle="modal" class="btn btn-success btn-sm btn-icon-text mr-3">
                              				Delete
       <i class="typcn typcn-edit btn-icon-append"></i>
	</td>

</tr>
`

			}
			document.getElementById("purcharsedList").innerHTML = html;
		})
}

function confirmOrder(){
	var c = confirm("Are you sure you want to delete this purchase ?");
	if(!c){
		return false;
	}
	return true;
}

function deleteOrder(orderID) {
	fetch('http://localhost:8080/order?orderId='+orderID, {
		method: "delete"
	})
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			alert(data.message);
			location.reload();
		})
}


function addToCart(productId, productName, image, quantity) {
	console.log('test cart', quantity)
	fetch('http://localhost:8080/cart', {
		method: "post",
		headers: {
			"Content-Type": "application/json",
			// 'Content-Type': 'application/x-www-form-urlencoded',
		},
		body: JSON.stringify({
			"bookID": productId,
			"bookName": productName,
			"image": image,
			"quantity": quantity
		})
	})
		.then((response) => {
			return response.json()
		})
		.then((data) => {

			if (data.count == undefined) {
				document.getElementById("cartCount").innerText = 0;
			} else {
				document.getElementById("cartCount").innerText = '(' + data.count + ')';
			}
		})
}

/*
<div class="cart-fav-search mb-100">
				<a href="cart.html" class="cart-nav"><img
					src="../../img/core-img/cart.png" alt=""> Cart <span id = "cartCount">(0)</span></a>
				<a href="#" class="fav-nav"><img
					src="../../img/core-img/favorites.png" alt=""> Favourite</a> <a
					href="#" class="search-nav"><img
					src="../../img/core-img/search.png" alt=""> Search</a>
			</div>


*/


function displayCart() {
	fetch('http://localhost:8080/cart/display')
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			document.getElementById("cartCount").innerText = '(' + data.count + ')';

		})
}

displayCart();


function loadCart() {
	fetch('http://localhost:8080/cart')
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			if (data.message !== undefined) {
				document.getElementById("checkCart").innerText = "Nothing In Shoping Cart"
			}
			else {
				let html = '';

				for (let cart in data) {
					console.log('test')
					console.log(cart)
					console.log(data[cart])
					html += `<tr id = "cart${data[cart].bookID}">
	<td class="cart_product_img"><a href="/view/detail/${data[cart].bookID}"><img
			src = "${data[cart].image}" alt=""></a></td>
	<td class="cart_product_desc">
		<h5>${data[cart].bookName}</h5>
	</td>
	<td class="qty">
		<div class="qty-btn d-flex">
			
			<div class="quantity">
				<input type="number" onchange="updateCart('${data[cart].bookID}', '${data[cart].bookName}' , '${data[cart].image}');"
					class="qty-text" id="quantity${data[cart].bookID}" step="1" min="1" max="300"
					name="quantity" value="${data[cart].quantity}"> 
			</div>
		</div>
	</td>
	<form>
		<td >
			<div class="col-sm-6 col-md-4 col-lg-3">
				<button onclick="if(confirmmmm())(deleteCart(${data[cart].bookID}));"  class="mdi mdi-delete" style="color: red;"></button>
			</div>	
		</td>
	</form>
	
</tr>
`
					console.log(cart)
				}
				document.getElementById("cartList").innerHTML = html;
				fetch('http://localhost:8080/cart/display')
					.then((response) => {
						return response.json()
					})
					.then((data) => {
						document.getElementById("cartPrice").innerText = data.count;

					})
			}
		})
}
function confirmmmm() {
	var c = confirm("Are you sure you want to delete this order ?");
	if(!c){
		return false;
	}
	location.reload();
	return true;
}

function updateCart(bookID, Title, image) {
	let count = document.getElementById("quantity" + bookID).value;
	fetch('http://localhost:8080/cart', {
		method: "put",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"bookID": bookID,
			"bookName": Title,
			"image": image,
			"quantity": count
		})
	})
		.then((response) => {
			return response.json()
		})
		.then((data) => {

			fetch('http://localhost:8080/cart/display')
				.then((response) => {
					return response.json()
				})
				.then((data) => {
					console.log(data)
					displayCart()
					document.getElementById("cartPrice").innerText = data.count;

				})
			console.log(data)
		})

}


function deleteCart(id) {
	fetch('http://localhost:8080/cart/' + id, {
		method: "delete"
	})
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			fetch('http://localhost:8080/cart/display')
				.then((response) => {
					return response.json()
				})
				.then((data) => {
					document.getElementById("cartCount").innerText = "(" + data.count + ")";
					document.getElementById("cartPrice").innerText = data.count;
				})
		})

}


function payment(id) {

	if (id === 'null') {
		window.location.href = "/login"
	}
	fetch('http://localhost:8080/cart/pay?id=' + id)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			if (data.message === 'Pay Successful!') {
				alert(data.message)
				window.location.href = "/view/shop"
			}
			else {
				alert(data.message)
			}
		})
}
