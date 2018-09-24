/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	*/
var icons = {
    header:"ui-icon-circle-plus",
    activeHeader:"ui-icon-circle-minus"
};

$(".date").datepicker({
    nextText: "Next",
    prevText:"Prev",
    buttonText: "Pick Date",
    showOn: "both",
    navigationAsDateFormat: true,
    buttonImage: "/row/js/calendar.gif"
});


$(function() {
    $(".need_focus").focus();
});
$("#company_name").autocomplete({
    source: APPLICATION_URL + "CompanyService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#company_id").val(ui.item.id);
            $("#action_id").val('Submit');
            $("#form_id").submit();
        }
    }
});
$("#company_name2").autocomplete({
    source: APPLICATION_URL + "CompanyService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#company_id").val(ui.item.id);
        }
    }
});

$("#contact_name").autocomplete({
    source: APPLICATION_URL + "ContactService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#contact_id").val(ui.item.id);
        }
    }
});
$(".contact_name2").autocomplete({
    source: APPLICATION_URL + "ContactService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#contact_id").val(ui.item.id);
            $("#action_id").val('Submit');
            $("#form_id").submit();			
        }
    }
});
$("#permit_lookup").autocomplete({
    source: APPLICATION_URL + "PermitService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#permit_id").val(ui.item.id);
            $("#permit_num").val(ui.item.permit_num);			
        }
    }
});
jQuery(function ($) {
    var launcherClick = function(e)  {
            var openMenus   = $('.menuLinks.open'),
                menu        = $(e.target).siblings('.menuLinks');
            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);

            menu.removeClass('closed');
            menu.   addClass('open');
            e.stopPropagation();
        },
        documentClick = function(e) {
            var openMenus   = $('.menuLinks.open');

            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);
        };
    $('.menuLauncher').click(launcherClick);
    $(document       ).click(documentClick);
});
$("#select_id").change(function(){
	$('#action_id').val('Submit');
	$("#form_id").submit();
})




