$(document).ready(function () {
    findAll();
});

function cancelForm(obj) {
    
    var ratingId = obj.getAttribute("id");
    var repdiv = '#repdiv' + ratingId;
    
    $(repdiv).html("");
}

function hideForm(id) {
    
    var repdiv = '#repdiv' + id;
    $(repdiv).html("");
    
}

function showReplies(id) {
    
    var toyId = document.getElementsByName('toyId')[0].value;
    var ratingId = id;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/comments/' + ratingId + '/' + toyId,
        dataType: 'json',
        success: function (data) {
            
            var strHtml = "";
            
            for (var i = 0; i < data.length; ++i) {
                
                strHtml += '<span class="account-name">';
                strHtml += '<img src="/resource/rating/images/user.png" width="15px" height="15px">';
                strHtml += '<span>';
                strHtml += '<b>' + data[i].nameUser + '</b>';
                strHtml += '<span> (' + data[i].timeAgo + ')</span>';
                strHtml += '</span>';
                strHtml += '<span class="rate-span">';
                strHtml += '<form>';
                strHtml += '<div class="rate">';
                
                if (data[i].ratingStar == 5) {
                    strHtml += '<input type="radio" checked disabled id="star5" name="rate" value="5" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star5" name="rate" value="5" />';
                }
                
                strHtml += '<label for="star5"></label>';
                
                if (data[i].ratingStar == 4) {
                    strHtml += '<input type="radio" checked disabled id="star4" name="rate" value="4" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star4" name="rate" value="4" />';
                }
                
                strHtml += '<label for="star4"></label>';
                
                if (data[i].ratingStar == 3) {
                    strHtml += '<input type="radio" checked disabled id="star3" name="rate" value="3" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star3" name="rate" value="3" />';
                }
                
                strHtml += '<label for="star3"></label>';
                
                if (data[i].ratingStar == 2) {
                    strHtml += '<input type="radio" checked disabled id="star2" name="rate" value="2" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star2" name="rate" value="2" />';
                }
                
                strHtml += '<label for="star2"></label>';
                
                if (data[i].ratingStar == 1) {
                    strHtml += '<input type="radio" checked disabled id="star1" name="rate" value="1" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star1" name="rate" value="1" />';
                }
                
                strHtml += '<label for="star1"></label>';
                strHtml += '</div>';
                strHtml += '</form>';
                strHtml += '</span>';
                strHtml += '<div class="container-comments">';
                strHtml += '<div class="dialogbox">';
                strHtml += '<div class="body-comments">';
                strHtml += '<span class="tip tip-up"></span>';
                strHtml += '<div class="message-comments">';
                strHtml += '<span>';
                
                if (data[i].parentId != ratingId) {
                    strHtml += '<span class="comment-tag">' + data[i].parentName + '</span>';
                }
                
                strHtml += data[i].comment + '</span>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '<div class="reply">';
                strHtml += '<a class="reply-text" name="' + ratingId + '" id="' + data[i].ratingId + '" onclick="replyForm(this)">Reply</a>';
                strHtml += '</div>';
                strHtml += '<div class="reply-input" id="repdiv' + data[i].ratingId + '"></div>';
                strHtml += '</span>';
                
            }
            
            var cmtId = '#cmt-' + ratingId;
            var spanId = '#span' + ratingId;

            $(cmtId).html(strHtml);
            $(spanId).html('<a class="reply-text" id="' + ratingId + '" onclick="hideReplies(this)">Hide replies</a>');
        }})
}


function createReply(obj) {

    var ratingId = obj.getAttribute("id");
    var rootId = obj.getAttribute("name");
    var textName = 'reply' + ratingId;
    var rateName = 'rate' + ratingId;

    var cm = document.getElementsByName(textName)[0].value.trim();

    if (cm == "") {
        alert("Please input your comment!");
        return;
    }

    if (cm.length < 5 || cm.length > 500) {
        alert("Comment length must be from 5 to 500 characters!");
        return;
    }


    var username = $("input[name='username']").val().trim();

    if (username == "") {
        alert("You are not logged in!");
        return;
    }

    var comment = new Object();

    comment.parentId = ratingId;
    comment.ratingStar = $("input[name='myrate']:checked").val();
    comment.comment = cm;
    comment.toyId = $("input[name='toyId']").val();
    comment.username = username;

    $.ajax({
        type: "post",
        url: "http://localhost:8080/comments/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(comment),
        success: function(status){
            showReplies(rootId);
            hideForm(rootId);
        },
        error:function(error){
            alert("error " + error);
        }
    });
}

function replyForm(obj) {
    var ratingId = obj.getAttribute("id");
    var rootId = obj.getAttribute("name");
    var repdiv = '#repdiv' + ratingId;

    var strHtml = '<textarea name="reply' + ratingId + '" class="reply-txt" rows="5" placeholder="Add your public answer..."></textarea>';
    strHtml += '<div class="btn-div">';
    strHtml += '<input class="small-btn" type="button" name="' + rootId + '" id="' + ratingId + '" onclick="createReply(this)" value="Reply">';
    strHtml += '<input class="small-btn" type="button" id="' + ratingId + '" onclick="cancelForm(this)" value="Cancel">';
    strHtml += '</div>';

    $(repdiv).html(strHtml);
}

function findAll() {

    var toyId = document.getElementsByName('toyId')[0].value;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/comments/0/' + toyId,
        dataType: 'json',
        success: function (data) {

            var strHtml = "";

            for (var i = 0; i < data.length; ++i) {

                strHtml += '<span class="account-name">';
                strHtml += '<img src="/resource/rating/images/user.png" width="15px" height="15px">';
                strHtml += '<span>';
                strHtml += '<b>' + data[i].nameUser + '</b>';
                strHtml += '<span> (' + data[i].timeAgo + ')</span>';
                strHtml += '</span>';
                strHtml += '<span class="rate-span">';
                strHtml += '<form>';
                strHtml += '<div class="rate">';

                if (data[i].ratingStar == 5) {
                    strHtml += '<input type="radio" checked disabled id="star5" name="rate' + data[i].ratingId + '" value="5" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star5" name="rate' + data[i].ratingId + '" value="5" />';
                }

                strHtml += '<label for="star5"></label>';

                if (data[i].ratingStar == 4) {
                    strHtml += '<input type="radio" checked disabled id="star4" name="rate' + data[i].ratingId + '" value="4" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star4" name="rate' + data[i].ratingId + '" value="4" />';
                }

                strHtml += '<label for="star4"></label>';

                if (data[i].ratingStar == 3) {
                    strHtml += '<input type="radio" checked disabled id="star3" name="rate' + data[i].ratingId + '" value="3" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star3" name="rate' + data[i].ratingId + '" value="3" />';
                }

                strHtml += '<label for="star3"></label>';

                if (data[i].ratingStar == 2) {
                    strHtml += '<input type="radio" checked disabled id="star2" name="rate' + data[i].ratingId + '" value="2" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star2" name="rate' + data[i].ratingId + '" value="2" />';
                }

                strHtml += '<label for="star2"></label>';

                if (data[i].ratingStar == 1) {
                    strHtml += '<input type="radio" checked disabled id="star1" name="rate' + data[i].ratingId + '" value="1" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star1" name="rate' + data[i].ratingId + '" value="1" />';
                }

                strHtml += '<label for="star1"></label>';

                strHtml += '</div>';
                strHtml += '</form>';
                strHtml += '</span>';
                strHtml += '<div class="container-comments">';
                strHtml += '<div class="dialogbox">';
                strHtml += '<div class="body-comments">';
                strHtml += '<span class="tip tip-up"></span>';
                strHtml += '<div class="message-comments">';
                strHtml += '<span>' + data[i].comment + '</span>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '<div class="reply">';
                strHtml += '<a class="reply-text" name="' + data[i].rootId + '" id="' + data[i].ratingId + '" onclick="replyForm(this)">Reply</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                strHtml += '<span id="span' + data[i].ratingId + '">';

                if (data[i].replyQty != 0) {

                    if (data[i].replyQty == 1) {
                        strHtml += '<a class="reply-text" id="' + data[i].ratingId + '" onclick="findReplies(this)">View more 1 reply</a>';
                    } else {
                        strHtml += '<a class="reply-text" id="' + data[i].ratingId + '" onclick="findReplies(this)">View more ' + data[i].replyQty + ' replies</a>';
                    }

                }

                strHtml += '</span>';
                strHtml += '</div>';
                strHtml += '<div class="reply-input" id="repdiv' + data[i].ratingId + '"></div>';
                strHtml += '<div class="m-g-l-100" id="cmt-' + data[i].ratingId + '"></div>';
                strHtml += '</span>';
            }
            $('#m-g-l').html(strHtml);
        }
    });
}

function hideReplies(obj) {

    var toyId = document.getElementsByName('toyId')[0].value;
    var ratingId = obj.getAttribute("id");
    var spanId = '#span' + ratingId;
    var cmtId = '#cmt-' + ratingId;


    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/comments/' + ratingId + '/' + toyId,
        dataType: 'json',
        success: function (data) {

            if (data.length == 1) {
                $(spanId).html('<a class="reply-text" id="' + ratingId + '" onclick="findReplies(this)">View more 1 reply</a>');
            } else if (data.length > 1) {
                $(spanId).html('<a class="reply-text" id="' + ratingId + '" onclick="findReplies(this)">View more ' + data.length + ' replies</a>');
            }

            $(cmtId).html("");

        }})
}

function findReplies(obj) {

    var toyId = document.getElementsByName('toyId')[0].value;
    var ratingId = obj.getAttribute("id");

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/comments/' + ratingId + '/' + toyId,
        dataType: 'json',
        success: function (data) {

            var strHtml = "";

            for (var i = 0; i < data.length; ++i) {
                strHtml += '<span class="account-name">';
                strHtml += '<img src="/resource/rating/images/user.png" width="15px" height="15px">';
                strHtml += '<span>';
                strHtml += '<b>' + data[i].nameUser + '</b>';
                strHtml += '<span> (' + data[i].timeAgo + ')</span>';
                strHtml += '</span>';
                strHtml += '<span class="rate-span">';
                strHtml += '<form>';
                strHtml += '<div class="rate">';

                if (data[i].ratingStar == 5) {
                    strHtml += '<input type="radio" checked disabled id="star5" name="rate" value="5" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star5" name="rate" value="5" />';
                }

                strHtml += '<label for="star5"></label>';

                if (data[i].ratingStar == 4) {
                    strHtml += '<input type="radio" checked disabled id="star4" name="rate" value="4" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star4" name="rate" value="4" />';
                }

                strHtml += '<label for="star4"></label>';

                if (data[i].ratingStar == 3) {
                    strHtml += '<input type="radio" checked disabled id="star3" name="rate" value="3" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star3" name="rate" value="3" />';
                }

                strHtml += '<label for="star3"></label>';

                if (data[i].ratingStar == 2) {
                    strHtml += '<input type="radio" checked disabled id="star2" name="rate" value="2" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star2" name="rate" value="2" />';
                }

                strHtml += '<label for="star2"></label>';

                if (data[i].ratingStar == 1) {
                    strHtml += '<input type="radio" checked disabled id="star1" name="rate" value="1" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star1" name="rate" value="1" />';
                }

                strHtml += '<label for="star1"></label>';

                strHtml += '</div>';
                strHtml += '</form>';
                strHtml += '</span>';
                strHtml += '<div class="container-comments">';
                strHtml += '<div class="dialogbox">';
                strHtml += '<div class="body-comments">';
                strHtml += '<span class="tip tip-up"></span>';
                strHtml += '<div class="message-comments">';
                strHtml += '<span>';

                if (data[i].parentId != ratingId) {
                    strHtml += '<span class="comment-tag">' + data[i].parentName + '</span>';
                }

                strHtml += data[i].comment + '</span>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '<div class="reply">';
                strHtml += '<a class="reply-text" name="' + data[i].rootId + '" id="' + data[i].ratingId + '" onclick="replyForm(this)">Reply</a>';
                strHtml += '</div>';
                strHtml += '<div class="reply-input" id="repdiv' + data[i].ratingId + '"></div>';
                strHtml += '</span>';

            }

            var cmtId = '#cmt-' + ratingId;
            var spanId = '#span' + ratingId;

            $(cmtId).html(strHtml);
            $(spanId).html('<a class="reply-text" id="' + ratingId + '" onclick="hideReplies(this)">Hide replies</a>');

        }})
}

function createComment2() {

    var cm = $("textarea[name='comment']").val().trim();

    if (cm == "") {
        alert("Please input your comment!");
        return;
    }

    if (cm.length < 5 || cm.length > 500) {
        alert("Comment length must be from 5 to 500 characters!");
        return;
    }

    var comment = new Object();

    comment.parentId = 0;
    comment.ratingStar = $("input[name='myrate']:checked").val();
    comment.comment = cm;
    comment.toyId = $("input[name='toyId']").val();
    comment.username = $("input[name='username']").val();

    $.ajax({
        type: "post",
        url: "http://localhost:8080/comments/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(comment),
        success: function(status){
            findAll();
        },
        error:function(error){
            alert("Error ");
        }
    });

}

function addFavorite(obj) {

    var username = $("input[name='username']").val().trim();

    if (username == "") {
        alert("You are not logged in!");
        return;
    }

    var favorite = new Object();
    favorite.toyId = document.getElementsByName('toyId')[0].value;

    $.ajax({
        type: "post",
        url: "http://localhost:8080/favorites/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(favorite),
        success: function(status){
            alert("Added to favorite list success");
        },
        error:function(jqXHR, textStatus, errorThrown){

            if (jqXHR.status == 403) {
                alert("Error");
            }

            if (jqXHR.status == 406) {
                alert("This toy was added to favirote list");
            }

        }
    });

}