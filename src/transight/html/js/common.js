/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function afterLoad()
{
    $('#nav li').hover(function()
    {
        var child = $(this).children(0)[0];
        if(child.nodeName!="A")
        {
            $(this).attr("class","span_hover");
        }
        else
        {
            console.log($(child).parent().parent());
            $("#nav li").css("height","");
        }
        $('ul', this).fadeIn(100);
    },
    function()
    {
        $('ul', this).fadeOut(10);
        $(this).attr("class","");
    });
    
    $(".logout").click(function ()
    {
       dashboard.logout();
       $.cookie('username','',{expires:-1});
       $.cookie('company_name','',{expires:-1})
    });
}

