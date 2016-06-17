(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$location', '$state', 'Auth', 'Principal', 'ENV', 'LoginService'];

    function FooterController ($location, $state, Auth, Principal, ENV, LoginService) {
        var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;
        vm.$state = $state;
		
		vm.login = login;
		
		function login () {
            LoginService.open();
        }
    }
	
})();
