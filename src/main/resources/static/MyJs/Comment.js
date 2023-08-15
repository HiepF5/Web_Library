let star;

function setStar(s){
	star = s
}

function getCommentByProductId(id) {
	fetch('http://localhost:8080/comment?productId=' + id)
		.then((response) => {
			return response.json()
		})
		.then((data) => {
			console.log(data)
			let html = ''
			if (data.length != 0) {

				for (let cm of data) {
					tmp = ``
					for(let i =0 ; i<cm.star;i++){
						tmp +=`<i class="fa fa-star rating-color"></i>`
					}
					for(let i = cm.star ; i<5;i++){
						tmp += `<i class="fa fa-star nocheck"></i>`
					}
					html += `<div class="wrap-pic-s size-109 bor0 of-hidden m-r-18 m-t-6">
												<img src="https://img.myloview.com/posters/man-avatar-graphic-sign-anonymous-male-profile-sign-in-the-circle-isolated-on-white-background-vector-illustration-700-250607736.jpg" alt="AVATAR">
											</div> <div class="size-207">
	<div class="flex-w flex-sb-m p-b-17">
		<span class="mtext-107 cl2 p-r-20"><b> ${cm.user.username}</b></span> <span
			class="fs-18 cl11" id = "bookStar" th:inline="text">${tmp} 
		</span>
	</div>

	<p class="stext-102 cl6">${cm.content}</p><br><br>
</div>`
				}
			}

			document.getElementById("comment").innerHTML = html;

		})
}

function addComment(productId, userId) {
	if (userId === 'null') {
		window.location.href = '/login'
	}
	let content = document.getElementById("review").value
	if (content !== '') {
		fetch('http://localhost:8080/comment', {
			method: "post",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify({
				"content": content,
				"productId": productId,
				"userId": userId,
				"star" : star
			})
		})
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				document.getElementById("comment").innerHTML += `<div class="size-207">
	<div class="flex-w flex-sb-m p-b-17">
		<span class="mtext-107 cl2 p-r-20"><h4><b> ${data.user.username}</b></h4></span> <span
			class="fs-18 cl11"> <i class="zmdi zmdi-star"></i> <i
			class="zmdi zmdi-star"></i> <i class="zmdi zmdi-star"></i> <i
			class="zmdi zmdi-star"></i> <i class="zmdi zmdi-star-half"></i>
		</span>
	</div>

	<p class="stext-102 cl6">${data.content}</p>
</div>`
			})
	}
}


