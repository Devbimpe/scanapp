function user_creation() {
    var left = [];
    $.each($("#bootstrap-duallistbox-selected-list_stafflist option"), function(){
        left.push($(this).val());
    });



    var postdata = {
        firstname : $('#input-firstname').val(),
        lastname : $('#input-lastname').val(),
        email : $('#input-email').val()

    }


    $.ajax({
        url: '/users',
        type: 'POST',
        data: JSON.stringify(postdata),
        contentType:'application/json',
        async: false,
        success:function (data) {
            console.log('document uploaded successfully')
            uploadUpdateForm(data.id)
            bootbox.alert("Task created !!!!!")

            location.href = '/users'


        },
        error : function (data) {
            console.log('error occured')
            console.log(data)
            bootbox.alert(data)
        },
        cache: false,
        processData: false
    });
}