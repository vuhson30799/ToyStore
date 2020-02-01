$(document).ready(function () {
    getHistoryOrders(historyPage);
    getDelivering(deliveringPage);
    getInventory(inventoryPage);
});

var rowsQty = 10;
var deliveringPage = 0;
var inventoryPage = 0;
var historyPage = 0;

function getInventory(page) {

    inventoryPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/inventory/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";

            strHtml += '<div class="btn-inventory">';
            strHtml += '<button class="input-btn" onclick="displayCreate()">Create new product</button>';
            strHtml += '</div>';

            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Manufacture</th>';
            strHtml += '<th>Category</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Unit Price</th>';
            strHtml += '<th>Old Price</th>';
            strHtml += '<th class="align-center">Update</th>';
            strHtml += '<th class="align-center">Delete</th>';
            strHtml += '</tr>';

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {

                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].id + '">' + data[i].name + '</a></td>';
                    strHtml += '<td><a class="a-text" href="/products-brand/' + data[i].brandId + '">' + data[i].brandName + '</a></td>';
                    strHtml += '<td><a class="a-text" href="/products-category/' + data[i].categoryId + '">' + data[i].categoryName + '</a></td>';
                    strHtml += '<td>' + data[i].quantityInStock + '</td>';
                    strHtml += '<td>' + data[i].price + '</td>';
                    strHtml += '<td>' + data[i].oldPrice + '</td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="displayUpdate(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/edit.png" /></a></td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="deleteToy(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/remove.png" /></a></td>';
                    strHtml += '</tr>';

                }
            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getInventory(' + (page - 1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getInventory(' + i + ')">' + (i + 1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getInventory(' + i + ')">' + (i + 1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getInventory(' + (page + 1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#inventory').html(strHtml);

        }
    });

}

function getHistoryOrders(page) {

    historyPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/history/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";
            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Total Price</th>';
            strHtml += '<th>Order Date</th>';
            strHtml += '<th>Delivered Date</th>';
            strHtml += '<th>Customer Name</th>';
            strHtml += '<th>Status</th>';
            strHtml += '</tr>';

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {
                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].toyId + '">' + data[i].toyName + '</a></td>';
                    strHtml += '<td>' + data[i].quantity + '</td>';
                    strHtml += '<td>' + data[i].totalPrice + '</td>';
                    strHtml += '<td>' + data[i].orderDate + '</td>';
                    strHtml += '<td>' + data[i].deliveredDate + '</td>';
                    strHtml += '<td><a class="a-text" onclick="customerDetail(this)" id="' + data[i].customerId + '">' + data[i].customerName + '</a></td>';
                    strHtml += '<td>' + data[i].status + '</td>';
                    strHtml += '</tr>';
                }

            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + (page - 1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getHistoryOrders(' + i + ')">' + (i + 1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + i + ')">' + (i + 1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + (page + 1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#history').html(strHtml);

        }
    });


}

function getDelivering(page) {

    deliveringPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/delivering/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";
            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Total Price</th>';
            strHtml += '<th>Order Date</th>';
            strHtml += '<th>Customer Name</th>';
            strHtml += '<th class="align-center">Remove</th>';
            strHtml += '</tr>';

            var customerInfo = "";
            var removeHtml = "";

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {

                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].toyId + '">' + data[i].toyName + '</a></td>';
                    strHtml += '<td>' + data[i].quantity + '</td>';
                    strHtml += '<td>' + data[i].totalPrice + '</td>';
                    strHtml += '<td>' + data[i].orderDate + '</td>';
                    strHtml += '<td><a class="a-text" onclick="customerDetail(this)" id="' + data[i].customerId + '">' + data[i].customerName + '</a></td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="removeOrder(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/remove.png" /></a></td>';
                    strHtml += '</tr>';

                    customerInfo += '<div id="customer' + data[i].customerId + '" class="modal">';
                    customerInfo += '<div class="modal-content">';
                    customerInfo += '<span class="modal-title">Customer Information</span>';
                    customerInfo += '<span class="close" onclick="closeCustomer(this)" id="' + data[i].customerId + '">&times;</span>';
                    customerInfo += '<table align="center">';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Name</b></td>';
                    customerInfo += '<td>' + data[i].customerName + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Phone</b></td>';
                    customerInfo += '<td>' + data[i].customerPhone + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Email</b></td>';
                    customerInfo += '<td>' + data[i].customerEmail + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Address</b></td>';
                    customerInfo += '<td>' + data[i].customerAddress + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '</table>';
                    customerInfo += '</div>';
                    customerInfo += '</div>';

                    removeHtml += '<div id="order' + data[i].id + '" class="modal">';
                    removeHtml += '<div class="modal-content">';
                    removeHtml += '<span class="modal-title">Remove Order</span>';
                    removeHtml += '<span class="close" onclick="closeRemove(this)" id="' + data[i].id + '">&times;</span><br>';
                    removeHtml += '<div class="choose">Choose your order status?</div>';
                    removeHtml += '<div>';
                    removeHtml += '<button class="btn btn--pill btn--green" onclick="cancelOrder(this)" id="' + data[i].id + '">Cancel Order</button>';
                    removeHtml += '&nbsp;&nbsp;&nbsp;&nbsp;';
                    removeHtml += '<button class="btn btn--pill btn--green" onclick="deliveredOrder(this)" id="' + data[i].id + '">Delivered Order</button>';
                    removeHtml += '</div>';
                    removeHtml += '</div>';
                    removeHtml += '</div>';
                }


            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getDelivering(' + (page - 1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getDelivering(' + i + ')">' + (i + 1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getDelivering(' + i + ')">' + (i + 1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getDelivering(' + (page + 1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#customerModal').html(customerInfo);
            $('#removeModal').html(removeHtml);
            $('#delivering').html(strHtml);

        }
    });

}

function customerDetail(obj) {

    var id = obj.getAttribute("id");
    var cusId = 'customer' + id;

    if (id != null) {
        document.getElementById(cusId).style.display = 'block';
    }

}

function closeCustomer(obj) {

    var id = obj.getAttribute("id");
    var cusId = 'customer' + id;

    if (id != null) {
        document.getElementById(cusId).style.display = 'none';
    }

}

function removeOrder(obj) {

    var id = obj.getAttribute("id");
    var orderId = 'order' + id;

    if (id != null) {
        document.getElementById(orderId).style.display = 'block';
    }

}

function closeRemove(obj) {

    var id = obj.getAttribute("id");
    var orderId = 'order' + id;

    if (id != null) {
        document.getElementById(orderId).style.display = 'none';
    }

}

function cancelOrder(obj) {

    var id = obj.getAttribute("id");

    var statusObj = new Object();
    statusObj.orderId = id;
    statusObj.status = "CANCELLED";

    $.ajax({
        type: "post",
        url: "http://localhost:8080/remove/",
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(statusObj),
        success: function (status) {
            getDelivering(deliveringPage);
            getHistoryOrders(historyPage);
        },
        error: function (error) {
            alert("error " + error);
        }
    });

}

function deliveredOrder(obj) {

    var id = obj.getAttribute("id");

    var statusObj = new Object();
    statusObj.orderId = id;
    statusObj.status = "DELIVERED";

    $.ajax({
        type: "post",
        url: "http://localhost:8080/remove/",
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(statusObj),
        success: function (status) {
            getDelivering(deliveringPage);
            getHistoryOrders(historyPage);
        },
        error: function (error) {
            alert("error " + error);
        }
    });

}

function deleteToy(obj) {

    var id = obj.getAttribute("id");

    if (confirm("Are you sure to delete this toy?")) {

        $.ajax({
            type: "delete",
            url: "http://localhost:8080/delete/toy/" + id,
            success: function (status) {
                getInventory(inventoryPage);
            },
            error: function (error) {
                alert("error " + error);
            }
        });
    }

}

function displayUpdate(obj) {
    var id = obj.getAttribute("id");

    $.ajax({
        type: "get",
        url: "http://localhost:8080/toy/" + id,
        success: function (data) {
            var strHtml = "";
            strHtml +=
                '<div class="modal" id="updateModalChild" style="display: block">\n' +
                '    <div class="modal-content">\n' +
                '        <span class="modal-title">Toy Information</span>\n' +
                '        <span class="close" onclick="closeUpdate(this)">&times;</span>\n' +
                '        <form id="updateFormAJAX" method="post" action="/update-toy">\n' +
                '            <input type="hidden" id="id" name="id" value="' + data.id + '">\n' +
                '            <input type="hidden" id="brandId" name="brandId" value="' + data.brandId + '">\n' +
                '            <input type="hidden" id="categoryId" name="categoryId" value="' + data.categoryId + '">\n' +
                '            <input type="hidden" id="image" name="image" value="' + data.image + '">\n' +
                '            <input type="hidden" id="manufacturingDate" name="manufacturingDate" value="' + data.manufacturingDate + '">\n' +
                '            <input type="hidden" id="description" name="description" value="' + data.description + '">\n' +
                '            <input type="hidden" id="information" name="information" value="' + data.information + '">\n' +
                '            <table align="center">\n' +
                '                <tr>\n' +
                '                    <td><b>Name</b></td>\n' +
                '                    <td><input type="text" id="name" name="name" value="' + data.name + '"> </td>\n' +
                '                </tr>\n' +
                '                <tr>\n' +
                '                    <td><b>Manufacture</b></td>\n' +
                '                    <td><input type="text" id="brandName" name="brandName" value="' + data.brandName + '"> </td>\n' +
                '                </tr>\n' +
                '                <tr>\n' +
                '                    <td><b>Category</b></td>\n' +
                '                    <td><input type="text" id="categoryName" name="categoryName" value="' + data.categoryName + '"> </td>\n' +
                '                </tr>\n' +
                '                <tr>\n' +
                '                    <td><b>Quantity</b></td>\n' +
                '                    <td><input type="text" id="quantityInStock" name="quantityInStock" value="' + data.quantityInStock + '"> </td>\n' +
                '                </tr>\n' +
                '                <tr>\n' +
                '                    <td><b>Unit price</b></td>\n' +
                '                    <td><input type="text" id="price" name="price" value="' + data.price + '"> </td>\n' +
                '                </tr>\n' +
                '                <tr>\n' +
                '                    <td><b>Old Price</b></td>\n' +
                '                    <td><input type="text" id="oldPrice" name="oldPrice" value="' + data.oldPrice + '"> </td>\n' +
                '                </tr>\n' +
                '            </table>\n' +
                '            <button type="submit" class="btn btn--pill btn--green" onclick="updateToy(this)">Update</button>\n' +
                '        </form>\n' +
                '    </div>\n' +
                '</div>';

            $('#updateModal').html(strHtml);
        }
    })
}

function closeUpdate() {
    document.getElementById("updateModalChild").style.display = 'none';

}

function updateToy() {
    $('#updateFormAJAX').submit(function (event) {
        event.preventDefault();
    });
    var toyJSon = new Object();
    toyJSon.id = $('#id').val();
    toyJSon.brandId = $('#brandId').val();
    toyJSon.categoryId = $('#categoryId').val();
    toyJSon.name = $('#name').val();
    toyJSon.brandName = $('#brandName').val();
    toyJSon.categoryName = $('#categoryName').val();
    toyJSon.quantityInStock = $('#quantityInStock').val();
    toyJSon.price = $('#price').val();
    toyJSon.oldPrice = $('#oldPrice').val();
    toyJSon.image = $('#image').val();
    toyJSon.description = $('#description').val();
    toyJSon.information = $('#information').val();
    toyJSon.manufacturingDate = $('#manufacturingDate').val();
    $.ajax({
        type: 'post',
        url: 'http://localhost:8080/update-toy',
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(toyJSon),
        success: function () {
            alert('success');
            getInventory(inventoryPage);
        },
        error: function (error) {
            alert("error " + error);
        }
    })
}

function displayCreate(data) {
    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/list-category',
        success: function (categories) {
            $.ajax({
                type: 'get',
                url: 'http://localhost:8080/list-brand',
                success: function (brands) {
                    var strHtml = '';
                    strHtml +=
                        '<div class="modal" id="createModalChild" style="display:block;">\n' +
                        '    <div class="modal-content">\n' +
                        '        <span class="modal-title">Toy Information</span>\n' +
                        '        <span class="close" onclick="closeCreate()">&times;</span>\n' +
                        '        <form id="createForm" method="post" action="/create-toy">\n' +
                        '            <table>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Name</b></td>\n' +
                        '                    <td><input type="text" id="name" name="name" placeholder="Input toy\'s name"></td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Image</b></td>\n' +
                        '                    <td><input type="text" id="image" name="image" placeholder="Input image\'s link"></td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Price</b></td>\n' +
                        '                    <td><input type="number" id="price" name="price" placeholder="Input toy\'s price"> </td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Available Toys</b></td>\n' +
                        '                    <td><input type="number" id="quantityInStock" name="quantityInStock" placeholder="Number of toys in your store"></td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Manufacturing Date</b></td>\n' +
                        '                    <td>\n' +
                        '                        <input id="manufacturingDate" name="manufacturingDate" class="js-datepicker" type="text">\n' +
                        '                        <i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>\n' +
                        '                    </td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Description</b></td>\n' +
                        '                    <td><input type="text" id="description" name="description" placeholder="type something"></td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Information</b></td>\n' +
                        '                    <td><input type="text" id="information" name="information" placeholder="type something"></td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Old price</b></td>\n' +
                        '                    <td><input type="number" id="oldPrice" name="oldPrice" placeholder="Input toy\'s old price"> </td>\n' +
                        '                </tr>\n' +
                        '                <tr>\n' +
                        '                    <td><b>Is it on sale?</b></td>\n' +
                        '                    <td>\n' +
                        '                        <input type="radio" id="onSale" name="onSale" value="true">Yes\n' +
                        '                        <input type="radio" id="onSale1" name="onSale" value="false">No\n' +
                        '                    </td>\n' +
                        '                </tr>';
                    strHtml += '         <tr>\n' +
                        '                    <td><b>Brand Name</b></td>\n' +
                        '                    <td>\n' +
                        '                        <select id="brandName" name="brandName">\n';
                    for (var j = 0; j < brands.length; ++j) {
                        strHtml += '<option value="' + brands[j].brandName + '">' + brands[j].brandName + '</option>'
                    }
                    strHtml += '                 </select>\n' +
                        '                    </td>\n' +
                        '               </tr>\n' +
                        '               <tr>\n' +
                        '                    <td></td>\n' +
                        '                    <td>if your brand doesn\'t exist,<a href="#">create new one</a></td>\n' +
                        '               </tr>';
                    strHtml += '        <tr>\n' +
                        '                    <td><b>Category Name</b></td>\n' +
                        '                    <td>\n' +
                        '                        <select id="categoryName" name="categoryName">\n';
                    for (var i = 0; i < categories.length; ++i) {
                        strHtml += '<option value="' + categories[i].name + '">' + categories[i].name + '</option>'
                    }
                    strHtml += '                 </select>\n' +
                        '                    </td>\n' +
                        '               </tr>\n' +
                        '               <tr>\n' +
                        '                    <td></td>\n' +
                        '                    <td>if your category doesn\'t exist,<a href="#">create new one</a></td>\n' +
                        '               </tr>\n' +
                        '            </table>' +
                        '            <button type="submit" class="btn btn--pill btn--green" onclick="createNewToy(this)">Add</button>\n' +
                        '        </form>' +
                        '    </div>' +
                        '</div>';
                    $('#createModal').html(strHtml);
                }
            })
        }
    })
}

function closeCreate() {
    document.getElementById('createModalChild').style.display = 'none';
}
function createNewToy(){
    $('#createForm').submit(function (event) {
        event.preventDefault();
    });
    var toyJSON = new Object();
    toyJSON.name = $('#name').val();
    toyJSON.image = $('#image').val();
    toyJSON.price = $('#price').val();
    toyJSON.quantityInStock = $('#quantityInStock').val();
    toyJSON.manufacturingDate = $('#manufacturingDate').val();
    toyJSON.description = $('#description').val();
    toyJSON.information = $('#information').val();
    toyJSON.oldPrice = $('#oldPrice').val();
    var check = $('#onSale').checked;
    if (check){
        toyJSON.onSale = check;
    } else{
        toyJSON.onSale = $('#onSale1').val();
    }
    toyJSON.brandName = $('#brandName').val();
    toyJSON.categoryName = $('#categoryName').val();
    $.ajax({
        type: 'post',
        url: 'http://localhost:8080/create-toy',
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(toyJSON),
        success: function () {
            alert('you have added successfully');
            getInventory(inventoryPage);
        },
        error: function () {
            alert('error');

        }
    })
}