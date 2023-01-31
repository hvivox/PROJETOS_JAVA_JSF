//função para escutar clicks
$(document).ready(function(){

	
	/*quando houver clique no elemento js-toggle chame a função*/
	$('.js-toggle').bind('click', function(){
		/*se o sidebar não tiver uma classe css istoggle ele irá colocar, e se tiver ele retira
		 * toda vez que a função for chamada*/
		$('.js-sidebar').toggleClass('is-toggled');
		$('.js-content').toggleClass('is-toggled');
		$('.js-footer').toggleClass('is-toggled');
	});
	
});