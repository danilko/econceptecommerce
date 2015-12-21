/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p/>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p/>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


var NAMESPACE_CONTROLLER = {

	showView : function(viewName) {
		viewNames = [ "main", "catalog", "shoppingCart", "checkoutAddress",
				"productDetail", "checkoutConfirmation" ];

		$.each(viewNames, function(index, currentViewName) {
			if (currentViewName != viewName) {
				$("#div_" + currentViewName).hide();
			}
		});

		$("#div_" + viewName).show();
	},
	showCatalog : function() {
		var products = NAMESPACE_CONTROLLER.getAllProducts();

		$("#div_catalog").empty();
		var $catalogDiv = $("<div></div>");

		$.each(products, function(i, item) {
			var $rowDiv = $("<div></div>");
			$rowDiv.attr("class", "row");
			$rowDiv.attr("id", "div_" + item.productSerialNumber);

			// var $div = $("<div></div>");
			// $div.attr("class", "col-md-4");
			// $div.attr("name", "div_" +
			// item.productSerialNumber);
			// $div.html(item.productSerialNumber);
			// $rowDiv.append($div);

			$div = $("<div></div>");
			$div.attr("class", "col-md-4");
			$div.append($("<img>").attr("src", item.productImageList[0]).attr(
					"height", "auto").attr("width", "300"));

			$rowDiv.append($div);

			$div = $("<div></div>");
			$div.attr("class", "col-md-6");
			$div.append($("<h2></h2>").html(item.productName));
			$div.append($("<hr/>"));
			$div.append($("<h3></h3>").html(item.productSerialNumber));
			$div.append($("<br/>"));
			$div.append($("<p></p>").html(
					item.productDescription.substring(0, 300) + "..."));

			// $div = $("<div></div>");
			// $div.attr("class", "col-md-2");
			// $div.attr("name", "div_" +
			// item.productDescription);
			// $div.html(item.productDescription);
			// $rowDiv.append($div);

			$div.append($("<p></p>").append(
					$("<span></span>").attr("class",
							"glyphicon glyphicon glyphicon-tag")).append(
					" " + item.productPrice + " USD per Unit"));
			$div.append($("<br/>"));

			$div.append($("<a></a>").attr("class", "btn").attr("hfre",
					"#!/catalog/" + item.productSerialNumber).attr(
					"onClick",
					"NAMESPACE_CONTROLLER.addShoppingCartItem(\""
							+ item.productSerialNumber + "\")").html(
					$("<span></span>").attr("class",
							"glyphicon glyphicon-shopping-cart")).append(
					" Add to Cart"));
			// $btnDiv.html($("span").attr("class",
			// "glyphicon glyphicon-shopping-cart"));

			$div.append("<br/>");

			$div.append($("<a></a>").attr("class", "btn").attr("href",
					"#!/catalog/product/" + item.productSerialNumber).attr(
					"onClick",
					"NAMESPACE_CONTROLLER.showProductDetail(\""
							+ item.productSerialNumber + "\")").html(
					$("<span></span>").attr("class",
							"glyphicon glyphicon-th-large")).append(
					" Show Product Detail"));

			// $btnDiv.append($("span").attr("class",
			// "glyphicon glyphicon-list"));

			$rowDiv.append($div);

			$catalogDiv.append($rowDiv);
			$catalogDiv.append("<br/>");
		});

		$("#div_catalog").append($catalogDiv);

		NAMESPACE_CONTROLLER.showView("catalog");
	},
	showProductDetail : function(productSerialNumber) {
		product = NAMESPACE_CONTROLLER
				.getProductByProductSerialNumber(productSerialNumber);
		$("#img_productImage").attr("src", product.productImageList[0]);
		$("#h2_productName").html(product.productName);
		$("#h2_productSerialNumber").html(product.productSerialNumber);
		$("#span_productPrice").html(product.productPrice);
		$("#a_productAddToCart").attr(
				"onClick",
				"NAMESPACE_CONTROLLER.addShoppingCartItem(\""
						+ product.productSerialNumber + "\")");

		$("#p_productDescription").html(product.productDescription);

		$("#tbody_productSpecifications").empty();
		productSpecificationList = product.productSpecificationList;
		$
				.each(
						productSpecificationList,
						function(index, productSpecification) {
							$("#tbody_productSpecifications")
									.append(
											$("<tr></tr>")
													.append(
															$("<td></td>")
																	.html(
																			productSpecification.productSpecificationName))
													.append(
															$("<td></td>")
																	.html(
																			productSpecification.productSpecificationValue)));
						});
		NAMESPACE_CONTROLLER.showView("productDetail");
	},
	getAllProducts : function() {
		var catalog = sessionStorage.getItem("catalog");

		if (catalog == null) {
			$.ajax({
				url : "rest/product/_all",
				context : document.body
			}).done(function(data) {
				sessionStorage.setItem("catalog", JSON.stringify(data));
				catalog = data;
			});
		} else {
			catalog = JSON.parse(catalog);
		}
		
		return catalog;
	},
	getProductByProductSerialNumber : function(productSerialNumber) {
		var products = NAMESPACE_CONTROLLER.getAllProducts();
		var product = null;
		$.each(products, function(i, item) {
			if (item.productSerialNumber == productSerialNumber) {
				product = item;
			}
		});
		return product;
	},
	getShoppingCart : function() {
		var shoppingCart = sessionStorage.getItem("cart");

		if (shoppingCart == null) {
			shoppingCart = {
				"productShoppingCartList" : [],
				"productShipmentReferenceId" : "",
				"productShipmentRatingReferenceId" : ""
			};
			sessionStorage.setItem("cart", JSON.stringify(shoppingCart));

		} else {
			shoppingCart = JSON.parse(shoppingCart);
		}

		return shoppingCart;
	},
	generateShoppingCartContent(id, checkout)
	{
		$("#div_" + id).empty();
		
		var shoppingCart = NAMESPACE_CONTROLLER.getShoppingCart();
		var cart = shoppingCart.productShoppingCartList;

		var $catalogDiv = $("<div></div>");

		$catalogDiv
				.append($("<div></div>").append($("<h1>Shopping Cart</h1>")));
		var total = 0.0;
		var quantity = 0;
		$
				.each(
						cart,
						function(i, item) {
							var product = NAMESPACE_CONTROLLER
									.getProductByProductSerialNumber(item.productSerialNumber);

							var $rowDiv = $("<div></div>");
							$rowDiv.attr("class", "row");
							$rowDiv.attr("id", "div_shopping_cart_item_"
									+ item.productSerialNumber);

							$div = $("<div></div>");
							$div.attr("class", "col-md-3");
							$div.html($("<a></a>").attr("href", "#!/catalog/product/" + product.productSerialNumber).attr("onClick", "NAMESPACE_CONTROLLER.showProductDetail(\"" + product.productSerialNumber +"\")").html(product.productName));
							$rowDiv.append($div);


							$div = $("<div></div>");
							$div.attr("class", "col-md-3");
							
							if (checkout == true)
								{
							$div.html(item.productQuantity + " Units");
								}
							else
								{
							$div.html($("<input />").attr("id", "input_shoppingCart_quantity_" + item.productSerialNumber).attr("type", "number").attr("min", "1").attr("value", item.productQuantity));

							$div.append(" Units");
								}
							
							$div.append($btnDiv);
							
							$rowDiv.append($div);
							
							quantity = quantity
									+ parseFloat(item.productQuantity);

							$div = $("<div></div>");
							$div.attr("class", "col-md-3");
							$div
									.html((parseFloat(item.productQuantity) * parseFloat(product.productPrice))
											+ " USD");
							$rowDiv.append($div);
							total = total
									+ (parseFloat(item.productQuantity) * parseFloat(product.productPrice));

							if (checkout == false)
								{
								$div = $("<div></div>");
								$div.attr("class", "col-md-4");
								$btnDiv = $("<button/>")
								.attr("class", "btn btn-default")
								.attr("type", "button")
								.attr("onClick","NAMESPACE_CONTROLLER.updateShoppingCartItemQuantity(\""
								+ item.productSerialNumber + "\")")
								.append($("<span/>").attr("class", "glyphicon glyphicon-cloud-upload"))
								.append(" Update Quantity");
								$div.append($btnDiv);
								
								
								$div.append(" ");
							$btnDiv = $("<button/>")
							.attr("class", "btn btn-default")
							.attr("type", "button")
							.attr("onClick",
									"NAMESPACE_CONTROLLER.removeShoppingCartItem(\""
											+ item.productSerialNumber + "\")")
							.append($("<span/>").attr("class", "glyphicon glyphicon-remove"))
							.append(" Remove Item");
							
							$div.append($btnDiv);
							
							$rowDiv.append($div);
							
								}
							
							$catalogDiv.append($rowDiv);
							$catalogDiv.append("<br/>");
						});

		$catalogDiv.append($("<div></div>").attr("class", "row").html(
				$("<div></div>").attr("class", "col-md-2").html($("<strong></strong>").html(
						"Sub Total: " + total + " USD"))));

		$catalogDiv.append("<br/>");
		
		var shipmentRating = NAMESPACE_CONTROLLER
				.getShipmentRatingByShipmentRatingReferenceId(shoppingCart.productShipmentRatingReferenceId)

		if (shipmentRating != null) {
			$catalogDiv.append($("<div></div>").attr("class", "row").html(
					$("<div></div>").attr("class", "col-md-4").html(
							"Shipping Method: "
									+ shipmentRating.shipmentProvider + " "
									+ shipmentRating.shipmentProviderServiceLevelName)));

			$catalogDiv.append($("<div></div>").attr("class", "row").html(
					$("<div></div>").attr("class", "col-md-4").html(
							"Shipping Cost: " + shipmentRating.shipmentAmount
									+ " " + shipmentRating.shipmentAmountCurrency
									+ " Per Unit")));

			var address = NAMESPACE_CONTROLLER.getShipmentAddress();

			$catalogDiv.append($("<div></div>").attr("class", "row").html(
					$("<div></div>").attr("class", "col-md-4")
					.append(
							$("<p></p>").html(
									"Email Address: " + address.addressEmail))
					.append($("<strong><strong>").html("Shipping Address: ")).append(
							$("<p></p>").html(address.addressCompany)).append(
							$("<p></p>").html(address.addressName)).append(
							$("<p></p>").html(
									address.addressStreet1 + " "
											+ address.addressStreet2)).append(
							$("<p></p>").html(
									address.addressCity + ", "
											+ address.addressState)).append(
							$("<p></p>").html(address.addressZip)).append(
							$("<p></p>").html(address.addressCountry))));
		}

		var $rowDiv = $("<div></div>");
		$rowDiv.attr("class", "row");

		var $colDiv = $("<div></div>");
		$colDiv.attr("class", "col-md-2");


		if(checkout == false)
	    {
			
			var $btnDiv = $("<a></a>");
			$btnDiv.attr("class", "btn btn-default");
			$btnDiv.attr("href", "#!/catalog");
			$btnDiv.attr("onClick", "NAMESPACE_CONTROLLER.showCatalog()");
			$btnDiv.append($("<span/>").attr("class", "glyphicon glyphicon-shopping-cart"));
			$btnDiv.append(" Continue Shopping");

			$colDiv.append($btnDiv);
			$rowDiv.append($colDiv);

			
		$colDiv = $("<div></div>");
		$colDiv.attr("class", "col-md-2");
		$btnDiv = $("<button/>");
		$btnDiv.attr("class", "btn btn-default");
		$btnDiv.attr("type", "button");
		$btnDiv.attr("onClick", "NAMESPACE_CONTROLLER.clearShoppingCart()");
		$btnDiv.append($("<span/>").attr("class", "glyphicon glyphicon-trash"));
		$btnDiv.append(" Clear Shopping Cart");

		$colDiv.append($btnDiv);
		$rowDiv.append($colDiv);

		if (quantity > 0) {

			$rowDiv
					.append($("<div></div>")
							.attr("class", "col-md-2")
							.append(
									$("<button/>")
											.attr("class", "btn btn-default")
											.attr("type", "button")
											.attr("onClick",
													"NAMESPACE_CONTROLLER.showCheckoutShippingAddress()")
											.html(
													"Next: Enter Shipping Address")));
		}

			}
		else
		{
			$colDiv.append($("<a></a>")
					.attr("id", "paypalCheckout")
						.html($("<img>")
						.attr("src","https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-medium.png")
						.attr("alt","Check out with PayPal")));
			
			$rowDiv.append($colDiv);
		}
		
		$catalogDiv.append($rowDiv);
		$("#div_" + id).append($catalogDiv);
	},
	setShoppingCartQuantity : function()
	{
		
		var cart = NAMESPACE_CONTROLLER.getShoppingCart().productShoppingCartList;

		var quantity = 0;
		$
				.each(
						cart,
						function(i, item) {
							quantity = quantity
									+ parseFloat(item.productQuantity);

						});
		
		$("#span_shoppingCartQuantity").html(quantity);
	},
	showShoppingCart : function() {
		
		NAMESPACE_CONTROLLER.generateShoppingCartContent("shoppingCart", false);
		NAMESPACE_CONTROLLER.showView("shoppingCart");
	},
	showCheckoutShippingAddress : function() {
		
		address = NAMESPACE_CONTROLLER.getShipmentAddress();
		if (address != null) {
			$("#div_checkout_addressName").val(address.addressName);
			$("#div_checkout_addressCompany").val(address.addressCompany);
			$("#div_checkout_addressStreet1").val(address.addressStreet1);
			$("#div_checkout_addressStreet2").val(address.addressStreet2);

			$("#div_checkout_addressCity").val(address.addressStreetNo);
			$("#div_checkout_addressZip").val(address.addressZip);
			$("#div_checkout_addressCountry").val(address.addressCountry);
			$("#div_checkout_addressEmail").val(address.addressEmail);
			$("#div_checkout_addressState").val(address.addressState);
		}

		NAMESPACE_CONTROLLER.showShipmentRatingList(NAMESPACE_CONTROLLER
				.getShipmentRatingList());

		NAMESPACE_CONTROLLER.showView("checkoutAddress");
	},
	addShoppingCartItem : function(itemSerialNumber) {
		var shoppingCart = NAMESPACE_CONTROLLER.getShoppingCart();
		var product = NAMESPACE_CONTROLLER
				.getProductByProductSerialNumber(itemSerialNumber);
		var cart = shoppingCart.productShoppingCartList;

		var found = false;
		if (cart.length > 0) {
			$.each(cart, function(i, item) {
				if (item.productSerialNumber == itemSerialNumber) {
					cart[i].productQuantity = cart[i].productQuantity + 1;
					found = true;
				}
			});
		}

		if (found == false) {
			var newItem = {
				"productQuantity" : 1,
				"productSerialNumber" : itemSerialNumber
			};
			cart.push(newItem);
		}
		shoppingCart.productShoppingCartList = cart;

		sessionStorage.setItem("cart", JSON.stringify(shoppingCart));
		NAMESPACE_CONTROLLER.setShoppingCartQuantity();
	},
	removeShoppingCartItem : function(itemSerialNumber) {

		var shoppingCart = NAMESPACE_CONTROLLER.getShoppingCart();
		var product = NAMESPACE_CONTROLLER
				.getProductByProductSerialNumber(itemSerialNumber);
		var cart = shoppingCart.productShoppingCartList;

		var index = -1;
		if (cart.length > 0) {
			$.each(cart, function(i, item) {
				if (item.productSerialNumber == itemSerialNumber) {
					index = i;
				}
			});
		}

		if (index > -1) {
			cart.splice(index, 1);
		}
		
		shoppingCart.productShoppingCartList = cart;

		sessionStorage.setItem("cart", JSON.stringify(shoppingCart));
		NAMESPACE_CONTROLLER.setShoppingCartQuantity();
		NAMESPACE_CONTROLLER.showShoppingCart();
	},
	updateShoppingCartItemQuantity : function(itemSerialNumber) { 
		var shoppingCart = NAMESPACE_CONTROLLER.getShoppingCart();
		var product = NAMESPACE_CONTROLLER
				.getProductByProductSerialNumber(itemSerialNumber);
		var cart = shoppingCart.productShoppingCartList;

		if (cart.length > 0) {
			$.each(cart, function(i, item) {
				if (item.productSerialNumber == itemSerialNumber) {
					quantity = parseFloat($("#input_shoppingCart_quantity_" + itemSerialNumber).val());
				
					if(quantity != null && !isNaN(quantity) && isFinite(quantity) && quantity > 0)
						{
					cart[i].productQuantity = quantity;
						}
					else
					{
						$("#input_shoppingCart_quantity_" + itemSerialNumber).val(cart[i].productQuantity);
					}
				}
			});
		}

		shoppingCart.productShoppingCartList = cart;

		sessionStorage.setItem("cart", JSON.stringify(shoppingCart));
		NAMESPACE_CONTROLLER.setShoppingCartQuantity();
		
		NAMESPACE_CONTROLLER.showShoppingCart();
	},
	addShipment : function(shipmentId, shipmentMethodId) {
		var shoppingCart = NAMESPACE_CONTROLLER.getShoppingCart();
		shoppingCart.productShipmentReferenceId = shipmentId;
		shoppingCart.productShipmentRatingReferenceId = shipmentMethodId;
		sessionStorage.setItem("cart", JSON.stringify(shoppingCart));

		NAMESPACE_CONTROLLER.showCheckoutShippingAddress();
	},
	clearShoppingCart : function() {
		sessionStorage.removeItem("cart");
		sessionStorage.removeItem("shipmentRating");
		NAMESPACE_CONTROLLER.setShoppingCartQuantity();
		
		NAMESPACE_CONTROLLER.showShoppingCart();
	},
	validateAddress : function() {

		var addresses = [ {
			"addressName" : "Unique Audio",
			"addressCompany" : "Unique Audio",
			"addressStreet1" : "180 Metro Dr.",
			"addressStreet2" : "",
			"addressStreetNo" : "",
			"addressCity" : "San Jose",
			"addressZip" : "95112",
			"addressCountry" : "US",
			"addressEmail" : "",
			"addressState" : "CA"
		}, {
			"addressName" : $("#div_checkout_addressName").val(),
			"addressCompany" : $("#div_checkout_addressCompany").val(),
			"addressStreet1" : $("#div_checkout_addressStreet1").val(),
			"addressStreet2" : $("#div_checkout_addressStreet2").val(),
			"addressStreetNo" : "",
			"addressCity" : $("#div_checkout_addressCity").val(),
			"addressZip" : $("#div_checkout_addressZip").val(),
			"addressCountry" : $("#div_checkout_addressCountry").val(),
			"addressEmail" : $("#div_checkout_addressEmail").val(),
			"addressState" : $("#div_checkout_addressState").val()
		} ];

		$("#div_ShippingRating").empty();

		$("#div_ShippingRating")
				.html(
						$("<div></div>")
								.attr("class", "bg-success")
								.html(
										"<p>Computing Shipping Costs, please wait for a monment...</p>"));

		$.ajax({
			url : "rest/payment/productShipment",
			contentType : "application/json",
			method : "POST",
			data : JSON.stringify(addresses)
		}).done(

				function(shippingObject) {
					var shippingId = shippingObject.shippingId;
					
					NAMESPACE_CONTROLLER.addShipment(shippingId, "");
					
					$.ajax(
							{
								url : "rest/payment/productShipment/rating/"
										+ shippingObject.shippingId,
								contentType : "application/json",
							}).done(
							function(ratingList) {

								NAMESPACE_CONTROLLER
										.setShipmentAddress(addresses[1]);
								NAMESPACE_CONTROLLER
										.setShipmentRatingList(ratingList);
								NAMESPACE_CONTROLLER
										.showShipmentRatingList(ratingList);
							});
				}).fail(function(jqXHR, textStatus)
						 {
					$("#div_ShippingRating")
					.html(
							$("<div></div>")
									.attr("class", "bg-failure")
									.html(
											"<p>Computing Shipping Cost invalid, please check input field and try again</p>"));

						 });

	},
	checkoutConfirmation : function() {


		var recaptcha = grecaptcha.getResponse();
		
		if(recaptcha == null || recaptcha =="")
		{
		 	$("#div_checkoutConfirmationOrderStatus")
			.html(
					$("<div></div>")
							.attr("class", "bg-failure")
							.html(
									"<p>reCaptcha is not valid, please try again when convenient</p>"));

		}
		else
		{
					
             $("#div_checkoutConfirmationOrderStatus").html("Generating Order, please wait for a moment...");


	         $.ajax({ url : "rest/payment/checkout?recaptcha=" + recaptcha, contentType :
		 "application/json", method : "POST", data :
		 JSON.stringify(NAMESPACE_CONTROLLER.getShoppingCart())
		 }).done(function(payment) { 
				NAMESPACE_CONTROLLER.generateShoppingCartContent("checkoutConfirmation", true);

				 $("#paypalCheckout").attr("href",
			 payment.paypalHref);
				
				 NAMESPACE_CONTROLLER.showView("checkoutConfirmation");
		 
		 }).fail(function(jqXHR, textStatus)
				 {
			 	$("#div_checkoutConfirmationOrderStatus")
				.html(
						$("<div></div>")
								.attr("class", "bg-fail")
								.html(
										"<p>Computing order fail, please check input field and try again</p>"));

				 });
		 
		
		}
	},
	setShipmentRatingList : function(shipmentRatingList) {
		sessionStorage.setItem("shipmentRating", JSON
				.stringify(shipmentRatingList));
	},
	showShipmentRatingList : function(shipmentRatingList) {
		$("#div_ShippingRating").empty();

		var productShipmentReferenceId = NAMESPACE_CONTROLLER.getShoppingCart().productShipmentReferenceId;
	
		var productShipmentRatingReferenceId = NAMESPACE_CONTROLLER
				.getShoppingCart().productShipmentRatingReferenceId;

		if (productShipmentReferenceId != null) {
			var $catalogDiv = $("<div></div>");
			$
					.each(
							shipmentRatingList,
							function(i, rating) {
								var $rowDiv = $("<div></div>");

								$rowDiv.attr("class", "row");

								$div = $("<div></div>");
								$div.attr("class", "col-md-2");

								var $image = $("<img>");
								$image.attr("src",
										rating.shipmentProviderImage75);
								$div.append($("<p></p>").html(
										rating.shipmentProvider));
								$div.append($("<p></p>").append($image));

								$rowDiv.append($div);

								$div = $("<div></div>");
								$div.attr("class", "col-md-2");
								$div
										.html(rating.shipmentProviderServiceLevelName);
								$rowDiv.append($div);

								$div = $("<div></div>");
								$div.attr("class", "col-md-3");
								$div.html(rating.shipmentProviderDurationTerms);
								$rowDiv.append($div);

								$div = $("<div></div>");
								$div.attr("class", "col-md-2");
								$div.html(rating.shipmentAmount + " "
										+ rating.shipmentAmountCurrency
										+ " per unit");
								$rowDiv.append($div);

								$div = $("<div></div>");
								$div.attr("class", "col-md-2");

								var $btn = $("<button></button>")
								if (productShipmentRatingReferenceId != null
										&& productShipmentRatingReferenceId == rating.shipmentReferenceId) {

									$btn.html("Selected Method").attr("type",
											"button").attr("class",
											"btn btn-primary").attr("disable",
											"true");
								} else {
									$btn
											.html("Select Shipping Method")
											.attr("type", "button")
											.attr("class", "btn btn-default")
											.attr(
													"onClick",
													"NAMESPACE_CONTROLLER.addShipment(\""
															+ productShipmentReferenceId
															+ "\",\""
															+ rating.shipmentReferenceId
															+ "\")");
								}
								$div.html($btn);
								$rowDiv.append($div);

								$catalogDiv.append($rowDiv);
								$catalogDiv.append("<br/>");
							});

			$("#div_ShippingRating").append($catalogDiv);

			if (productShipmentRatingReferenceId != null
					&& productShipmentRatingReferenceId != "") {
				grecaptcha.reset();
				$("#div_checkoutConfirmationOrderStatus").html();
				$("#div_checkoutConfirmationDialog").show();
			} else {
				$("#div_checkoutConfirmationDialog").hide();
			}
		}
	},
	getShipmentRatingList : function() {
		var shipmentRatingList = sessionStorage.getItem("shipmentRating");

		if (shipmentRatingList == null) {

			shipmentRatingList = [];
		} else {
			shipmentRatingList = JSON.parse(shipmentRatingList);
		}

		return shipmentRatingList;
	},
	getShipmentRatingByShipmentRatingReferenceId : function(
			shipmentRatingReferenceId) {
		var getShipmentRatingList = NAMESPACE_CONTROLLER
				.getShipmentRatingList();

		var targetShipmentRating = null;

		$
				.each(
						getShipmentRatingList,
						function(index, shipmentRating) {
							if (shipmentRating.shipmentReferenceId == shipmentRatingReferenceId) {
								targetShipmentRating = shipmentRating;
							}
						});

		return targetShipmentRating;
	},
	setShipmentAddress : function(address) {
		sessionStorage.setItem("shippingAddress", JSON.stringify(address));
	},
	getShipmentAddress : function() {
		address = sessionStorage.getItem("shippingAddress");
		if (address != null) {
			address = JSON.parse(address);
		}

		return address;
	},
	uriRouter : function(targetRoutingURI)
	{
		router = document.createElement("a");
		router.href=targetRoutingURI
		uriPaths = router.hash.replace("#!", "").split("?")[0].split('/')
		
		mainLayer = uriPaths[1];
		secondLayer = uriPaths[2];
		secondLayerId = uriPaths[3];
		
		if(typeof mainLayer == 'undefined' || mainLayer.localeCompare("main") == 0)
		{
			NAMESPACE_CONTROLLER.showView("main");
		}
		else if(mainLayer.localeCompare("catalog") == 0)
		{
			if(typeof secondLayer != 'undefined' && secondLayer.localeCompare("product") == 0 && typeof secondLayerId != 'undefinied')	
			{
				NAMESPACE_CONTROLLER.showProductDetail(secondLayerId);
			}
			else
			{
				NAMESPACE_CONTROLLER.showCatalog();
			}
			
		}
		else if(mainLayer.localeCompare("shoppingCart") == 0)
		{
				NAMESPACE_CONTROLLER.showShoppingCart();
		}
		else
			{
		NAMESPACE_CONTROLLER.showView("main");
			}
	}
}

window.onpopstate = function(event)
{
	NAMESPACE_CONTROLLER.uriRouter(document.location);
	return false;
};

NAMESPACE_CONTROLLER.setShoppingCartQuantity();
NAMESPACE_CONTROLLER.getAllProducts();
NAMESPACE_CONTROLLER.uriRouter(document.location);

