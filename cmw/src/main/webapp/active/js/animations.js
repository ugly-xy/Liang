/* ======= Animations ======= */
jQuery(document).ready(function($) {

      
        
        /* Animate elements in #Features */
        $('#task ul li:odd').css('opacity', 0).one('inview', function(event, isInView) {
            if (isInView) {$(this).addClass('animated fadeInUp delayp1');}
        });
        

});