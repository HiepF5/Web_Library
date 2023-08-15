let currentProductId;
let currentPage;
let currentCategoryId;
let currentPageCategory;
let currentUserId;
let currentUserPage;
function setCurrentUserId(id) {
	currentUserId = id;
}
function setCurrentCategoryId(id) {
	currentCategoryId = id;
	console.log(currentCategoryId)
}
function setCurrentProductId(id) {
	currentProductId = id;
	console.log(currentProductId)
}

function product() {
	document.getElementById("product").style.display = "block";
	document.getElementById("category").style.display = "none";
	document.getElementById("user").style.display = "none";
}
function category() {
	document.getElementById("category").style.display = "block";
	document.getElementById("product").style.display = "none";
	document.getElementById("user").style.display = "none";
}

function user() {
	document.getElementById("user").style.display = "block";
	document.getElementById("category").style.display = "none";
	document.getElementById("product").style.display = "none";
}


function deleteProduct() {
	fetch('http://localhost:8080/product/' + currentProductId, {
		method: "delete"
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			document.getElementById("deleteEmployeeModal").style.display = "none";
			document.querySelector(".modal-backdrop").style.display = "none";
			alert(data.message);
			loadProduct(currentPage)
		})
}


function loadProduct(page) {
	currentPage = page;
	fetch('http://localhost:8080/product/search?page=' + page)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			let html = '';
			for (let product of data.bookList) {
				html += `<tr>
                                
                                <th>${product.bookID}</th>
                                <th>${product.title}</th>
                                <th>${product.author}</th>
                                <th>${product.releaseDate}</th>
                                <th>${product.pageNum}</th>
                               	<th>${product.category.categoryName}</th>
                                <th id="result-${product.bookID}"></th>
                                <th>
                                 
                                    <a onclick="setCurrentProductId(${product.bookID}); getProductById(${product.bookID});" href="/book/addBook/${product.bookID}" type="button" class="btn btn-success btn-sm btn-icon-text mr-3">
                              				View
                              		<i class="typcn typcn-edit btn-icon-append"></i>                          
                            		</a>
                                    <a onclick="setCurrentProductId(${product.bookID});" href="#deleteEmployeeModal"" type="button"  data-toggle="modal" class="btn btn-success btn-sm btn-icon-text mr-3">
                              				Delete
                              		<i class="typcn typcn-edit btn-icon-append"></i>                          
                            		</a>
                                   
                                </th>
                            </tr>`
			}
			document.getElementById("listProduct").innerHTML = html;
			let html2 = '';
			for (let i = 1; i <= data.pageCount; i++) {
				html2 += `<li class="page-item "><a href="javascript:void(0);" onclick="loadProduct(${i})" class="page-link">${i}</a></li>`
			}
			document.getElementById("paging1").innerHTML = html2;
			for (let product of data.bookList) {
				getSold(product.bookID)
					.then(result => {
						const elementID = `result-${product.bookID}`;
						document.getElementById(elementID).innerHTML = result;
					})
					.catch(error => {
						console.error(error);
					});
			}
		})
}
/*
function getSold(id) {
	fetch('http://localhost:8080/product/getSold/'+id.toString())
		.then((response)=> {return response.json()})
		.then(data => {
			
		})
	
}
*/
async function getSold(id) {
	const response = await fetch('http://localhost:8080/product/getSold/' + id.toString());
	const numberValue = await response.json();
	return numberValue;
}


function loadCategory(page) {
	currentPageCategory = page;
	fetch('http://localhost:8080/category?page=' + page)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			let html = '';
			for (let category of data.categories) {
				html += `<tr>
                               
                                <th>${category.categoryId}</th>
                                <th>${category.categoryName}</th>
                                <th>
          
                                    <a href="#deleteCategory"  onclick="setCurrentCategoryId(${category.categoryId})" class="btn btn-success btn-sm btn-icon-text mr-3" data-toggle="modal">
                                    	Delete
                                        <i class="typcn typcn-edit btn-icon-append"></i>  
                                    </a>
                                </th>
                            </tr>`
			}
			document.getElementById("listCategory").innerHTML = html;
			let html2 = '';
			for (let i = 1; i <= data.pageCount; i++) {
				html2 += `<li class="page-item "><a href="javascript:void(0);" onclick="loadCategory(${i})" class="page-link">${i}</a></li>`
			}
			document.getElementById("paging2").innerHTML = html2;
		})
}


function getAllCategory() {
	fetch('http://localhost:8080/category/getAll')
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			let html = '';
			for (let category of data) {
				html += ` <option value="${category.categoryName}">${category.categoryName}</option>`
			}
			document.getElementById("categorySelection").innerHTML = html;
			
		})
}

function getCategoryById(id) {
	console.log('ok')
	fetch('http://localhost:8080/category/' + id)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			document.getElementById('editCategoryName').value = data.categoryName

			document.getElementById('editCateBtn').disabled = true;
		})
}

let currentProduct;

function getProductById(id) {
	fetch('http://localhost:8080/product/' + id)
		.then((response) => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then((data) => {
			currentProduct = data
			document.getElementById('bookTitle').value = data.title
			document.getElementById('pagenum').value = data.pageNum
			document.getElementById('bookAuthor').value = data.author
			document.getElementById('publicationDate').value = data.releaseDate
			document.getElementById('Description').value = data.description
			let option = document.getElementById('categorySelection');
			const category = data.category.categoryName;
			console.log(category)
			for (let i = 0; i < option.options.length; i++) {
				if (option.options[i].value === category) {
					option.selectedIndex = i;
					break;
				}
			}
			const img = document.getElementById('img');
			img.setAttribute('src', `${data.image}`);
			console.log('Kien')
			//document.getElementById('editProduct').disabled = true;

		})
}

function enable() {
	console.log('ok')
	let myButton = document.getElementById("editProduct");
	myButton.disabled = false;
}
function enable2() {
	console.log('ok')
	let myButton = document.getElementById("editCateBtn");
	myButton.disabled = false;
}




//loadUser(1)

function getImg() {
	const file = document.getElementById("uploadImg").files[0];
	if (file) {
		const urlFile = URL.createObjectURL(file);
		console.log('image',urlFile)
		const img = document.getElementById('img');
		img.setAttribute('src', urlFile);
	}
}
function getImg2() {
	enable()
	const file = document.getElementById("editUploadImg").files[0];
	const urlFile = URL.createObjectURL(file);
	const img = document.getElementById('editImg');
	img.setAttribute('src', urlFile);
}
/*
document.getElementById('editCateBtn').addEventListener('click', (e) => {
	e.preventDefault();
	const categoryName = document.getElementById('editCategoryName').value
	fetch('http://localhost:8080/category/' + currentCategoryId, {
		method: "put",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			'categoryName': categoryName,

		})
	}).then((response) => {
		return response.json()
	}).then((data) => {
		if (data.message === undefined) {
			document.getElementById("editCategory").style.display = "none";
			document.querySelector(".modal-backdrop").style.display = "none";
			loadCategory(currentPage)
		}
		else {
			document.getElementById('editCategoryError').innerText = data.message;
		}
	})
})
*/






