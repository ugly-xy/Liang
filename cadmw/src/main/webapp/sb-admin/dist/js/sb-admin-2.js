$(function() {

    $('#side-menu').metisMenu();

});

$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var turl = $("#nav_id").val(); 
    var element = $('ul.nav a').filter(function() {
    	//console.log(this.getAttribute("data"));
    	if(this.getAttribute("data")==turl){
    		console.log("OK");
    		return true;
    	}
    }).addClass('active').parent().parent().addClass('in').parent();
    
    if (element.is('li')) {
        element.addClass('active');
    }
});
