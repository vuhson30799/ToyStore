$(document).ready(function () {
    province();
});

function chooseDistrict(obj) {

    var val = obj.value;

    if (val != 0) {

        $.ajax({
            type: 'get',
            url: 'http://localhost:8080/villages/' + val,
            dataType: 'json',
            success: function (data) {

                var strHtml = "";
                strHtml += '<option value="0">Village</option>';

                for (var i = 0; i < data.length; ++i) {
                    strHtml += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }

                $('#village').html(strHtml);

            }
        });
    } else {
        $('#village').html('<option value="0">Village</option>');
    }

}

function chooseProvince(obj) {

    var val = obj.value;

    if (val != 0) {

        $.ajax({
            type: 'get',
            url: 'http://localhost:8080/districts/' + val,
            dataType: 'json',
            success: function (data) {

                var strHtml = "";
                strHtml += '<option value="0">District</option>';

                for (var i = 0; i < data.length; ++i) {
                    strHtml += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }

                $('#district').html(strHtml);
                $('#village').html('<option value="0">Village</option>');

            }
        });

    } else {

        $('#district').html('<option value="0">District</option>');
        $('#village').html('<option value="0">Village</option>');

    }

}

function province() {

    var prv = $("input[name='prv']").val();
    var dst = $("input[name='dst']").val();
    var vlg = $("input[name='vlg']").val();

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/provinces/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";
            strHtml += '<option value="0">Province</option>';

            for (var i = 0; i < data.length; ++i) {

                if (data[i].id == prv) {
                    strHtml += '<option selected value="' + data[i].id + '">' + data[i].name + '</option>';
                } else {
                    strHtml += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }

            }

            $('#province').html(strHtml);

        }
    });

    if (prv != 0) {

        $.ajax({
            type: 'get',
            url: 'http://localhost:8080/districts/' + prv,
            dataType: 'json',
            success: function (data) {

                var strHtml = "";
                strHtml += '<option value="0">District</option>';

                for (var i = 0; i < data.length; ++i) {

                    if (data[i].id == dst) {
                        strHtml += '<option selected value="' + data[i].id + '">' + data[i].name + '</option>';
                    } else {
                        strHtml += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }

                }

                $('#district').html(strHtml);
                $('#village').html('<option value="0">Village</option>');

            }
        });

        if (dst != 0) {

            $.ajax({
                type: 'get',
                url: 'http://localhost:8080/villages/' + dst,
                dataType: 'json',
                success: function (data) {

                    var strHtml = "";
                    strHtml += '<option value="0">Village</option>';

                    for (var i = 0; i < data.length; ++i) {

                        if (data[i].id == vlg) {
                            strHtml += '<option selected value="' + data[i].id + '">' + data[i].name + '</option>';
                        } else {
                            strHtml += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                        }
                    }

                    $('#village').html(strHtml);

                }
            });

        } else {
            $('#village').html('<option value="0">Village</option>');
        }

    } else {
        $('#district').html('<option value="0">District</option>');
        $('#village').html('<option value="0">Village</option>');
    }
}

function denySignUpB() {
    var button = document.getElementById('sign-up');
    button.disabled = 'disabled';
    button.style.backgroundColor = '#7f8c8d';
    alert("We can't associate with you since you don't have enough business certificate!")
}

function denySignUpA() {
    var button = document.getElementById('sign-up');
    button.removeAttribute('disabled');
    button.removeAttribute('style');
}