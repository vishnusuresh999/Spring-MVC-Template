/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function ()
{
    loginvalidate();
});

function loginvalidate()
{
    $("#login").validate(
    {
        submitHandler: function (form) 
        {
            transight.checkLogin();
        },
        rules:
        {
            company_name:
            {
                required: true
            },
            username:
            {
                required: true
            },
            password:
            {
                required: true
            }
        },
        messages:
        {
            company_name:
            {
                required: "Please enter Company name"
            },
            username:
            {
                required: "Please enter a User Name"
            },
            password:
            {
                required: "Please enter a Password"
            }
        },
        errorElement: "label",
        errorPlacement: function(error, element)
        {
            $("#invalid-"+element.attr('id')).html('');
            error.appendTo('#invalid-' + element.attr('id'));
            error.css("color","red");
            error.css("font-size","12px");
            error.css("padding","0");
        }});
}

