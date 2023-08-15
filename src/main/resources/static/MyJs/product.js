

async function getStar(id) {
	const response = await fetch('http://localhost:8080/product/star/' + id.toString());
	const numberValue = await response.json();
	return numberValue;
}


function getAll() {
	fetch('http://localhost:8080/product/search')
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			render(data.bookList)
			for (let i = 1; i <= data.pageCount; i++) {
				if (i == 1) {
					document.getElementById("pagination").innerHTML += `<li class="page-item"><a class = "page-link" href="javascript:void(0);" onclick="paging(${i})" >${i}</a></li>`
				} else {
					document.getElementById("pagination").innerHTML += `<li class="page-item"><a class = "page-link" href="javascript:void(0);" onclick="paging(${i})" >${i}</a></li>`
				}
			}
		})
}

function getByCategory(id) {
	fetch('http://localhost:8080/product/category/'+id)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			render(data.bookList)
			for (let i = 1; i <= data.pageCount; i++) {
				if (i == 1) {
					document.getElementById("pagination").innerHTML += `<li class="page-item"><a class = "page-link" href="javascript:void(0);" onclick="paging_category(${i},${id})" >${i}</a></li>`
				} else {
					document.getElementById("pagination").innerHTML += `<li class="page-item"><a class = "page-link" href="javascript:void(0);" onclick="paging_category(${i},${id})" >${i}</a></li>`
				}
			}
		})
}


function search(keyword) {
	fetch('http://localhost:8080/product/search?keyword=' + keyword)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			console.log(data.productList)
			render(data.productList)
			// let html = '';
			// for(let i = 1 ; i <= data.pageCount ; i++){
			//     html += `<a href="javascript:void(0);" onclick="paging4(${i},keyword)"  >${i}</a>`
			// }
		})
}



function paging_category(count,categoryId){
	fetch('http://localhost:8080/product/category/'+categoryId + '?page=' + count)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			render(data.bookList)
		})
}

function paging(count) {
	fetch('http://localhost:8080/product/search?page=' + count)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			render(data.bookList)
		})
}


function render(arr) {
	
	let productBlock = document.getElementById("product")
	let html = ''
	for (let product of arr) {
		getStar(product.bookID).then(result => { })
		html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="single-product-wrapper">
                                <div class="product-img">
                                    <img src="${product.image} " >
                                </div>
                                <div class="product-description d-flex align-items-center justify-content-between">
	                                <div class = "product-meta-data">
	                                	
	                                	<a href="detail/${product.bookID}"><h5>${product.title}</h5></a>
	                                	<h5>${product.author}</h5>	
	                                </div>
	                                
	                               <div class="ratings-cart text-right">
										
				
										
										<div class="cart">
											<a onclick="if((check(event))){ addToCart('${product.bookID}', '${product.title}', '${product.image}', 1);}" data-toggle="tooltip"
												data-placement="left" title="Add to Cart"><img
												src="../../img/core-img/cart.png" alt=""></a>
										</div>
								</div>
                                </div>
                               
                            </div>
                        </div>`
	}
	productBlock.innerHTML = html
}




function getProduct(id) {
	//console.log(document.getElementById('num_quantity').value)
	if (id !== undefined) {
		fetch('http://localhost:8080/product/detail/' + id)
			.then((response) => {
				return response.json()
			})
			.then((data) => {
				if (data !== undefined) {
					console.log("begin")
					console.log(data)
					getStar(id).then(result => {
						let z = ``
						for (let i = 0; i < result; i++) {
							z += `<i class="fa fa-star rating-color"></i>`
						}
						for (let i = result; i < 5; i++) {
							z += `<i class="fa fa-star nocheck"></i>`
						}
						let html = document.getElementById("product-detail")
						document.getElementById("description_area").innerHTML = `<h4 class ="text-center">${data.description}<h4>`;
						html.innerHTML = `<div class="col-12 col-lg-7">
	<div class="single_product_thumb">
		<div id="product_details_slider" class="carousel slide"
			data-ride="carousel">

			<div class="carousel-inner">
				<div class="carousel-item active">
					<a class="gallery_img" href="#"> <img
						class="d-block w-100" src="${data.image}"
						alt="">
					</a>
				</div>

			</div>
		</div>
		
	</div>
</div>

<div class="col-12 col-lg-5">
	<div class="single_product_desc">
		<div class="product-meta-data">
			<div class="line"></div>
			<p class="title">
			<h3 style="display: inline">Title: </h3> <h4 style="display: inline">${data.title}</h4><br>
			<h3 style="display: inline">Author: </h3> <h4 style="display: inline">${data.author}</h4><br>
			<h3 style="display: inline">Num Pages: </h3> <h4 style="display: inline">${data.pageNum}</h4><br>
			<h3 style="display: inline">Release Date: </h3> <h4 style="display: inline">${data.releaseDate}</h4><br>
			<h3 style="display: inline">Category: </h3> <h4 style="display: inline">${data.category.categoryName}</h4><br>
			</p>
		</div>


	</div>
	
	<div
	class="ratings-review mb-15 d-flex align-items-center justify-content-between">
	<div class="ratings">
		${z}
	</div>
	
	
</div>
	<div class="single_product_desc">
		<form class="cart clearfix" method="post">
			<div class="cart-btn d-flex mb-50">
				<p>Qty</p>
				<div class="quantity">
					<span class="qty-minus"
						onclick="var effect = document.getElementById('qty'); var qty = effect.value; if( !isNaN( qty ) &amp;&amp; qty &gt; 1 ) effect.value--;return false;"><i
						class="fa fa-caret-down" aria-hidden="true"></i></span> <input
						type="number" class="qty-text" id="qty" step="1" min="1" max="300"
						name="quantity" value="1"> <span class="qty-plus"
						onclick="var effect = document.getElementById('qty'); var qty = effect.value; if( !isNaN( qty )) effect.value++;return false;"><i
						class="fa fa-caret-up" aria-hidden="true"></i></span>
				</div>
			</div>
			
		</form>
		<button onclick ="if(check(event)){addToCart('${data.bookID}', '${data.title}' ,'${data.image}', document.getElementById('qty').value);}"  name="addtocart" value="5"
				class="btn amado-btn">Add to cart</button>
	</div>
</div>


`



					})


				}
			})

	} else {
		console.log("no product have id " + id)
	}

}



let productList = []

function all() {
	fetch('http://localhost:8080/product/getAll')
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			productList = data
		})
}



