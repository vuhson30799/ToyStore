function priceRange() {
    var price1 = document.getElementsByName("price1")[0].value.trim();
    var price2 = document.getElementsByName("price2")[0].value.trim();
    document.getElementsByName("price1")[0].value = price1;
    document.getElementsByName("price2")[0].value = price2;
    var pattern = /^\d*$/;
    if (!pattern.test(price1) || !pattern.test(price2) || (price1 == "" && price2 == "") || (parseInt(price1) > parseInt(price2))) {
        return false;
    }
    return true;
}

function validateSearch() {
    var word = document.getElementsByName("word")[0].value.trim();
    document.getElementsByName("word")[0].value = word;
    if (word == "") {
        return false;
    }
    return true;
}

function cartValidate() {
    var qty = document.getElementsByName("qty")[0].value.trim();
    var qtyInStock = document.getElementsByName("qtyInStock")[0].value;
    document.getElementsByName("qty")[0].value = qty;
    var pattern = /^\d*$/;
    if (qty == "" || !pattern.test(qty) || parseInt(qtyInStock) == 0 || parseInt(qty) == 0 || parseInt(qty) > parseInt(qtyInStock)) {
        alert("Please check again quantity text box!");
        return false;
    }
    return true;
}

function validateCheckout() {
    var pattern = /^\d*$/;
    var cQty = document.getElementsByName("cQty");
    var i;
    for (i = 0; i < cQty.length; i++) {
         var qty = document.getElementsByName("cQty")[i].value.trim();
         document.getElementsByName("cQty")[i].value = qty;
         var qtyInStock = document.getElementsByName("ccQtyInStock")[i].value;
         if (qty == "" || !pattern.test(qty) || parseInt(qty) == 0 || parseInt(qty) > parseInt(qtyInStock)) {
             alert("Please check again quantity text box!");
             return false;
         }
    }
    return true;
}

function checkboxValidate() {
    var checkbox = document.getElementsByName("removes");
    for (var i = 0; i < checkbox.length; i++) {
        if (checkbox[i].checked == true) {
            return true;
        }
    }
    alert("You have not selected any checkboxes!");
    return false;
}