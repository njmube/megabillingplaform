(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$location', '$state', 'Auth', 'Principal', 'ENV', 'LoginService'];

    function NavbarController ($scope, $location, $state, Auth, Principal, ENV, LoginService) {
        var vm = this;

        vm.navCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.inProduction = ENV === 'prod';
        vm.login = login;
		vm.account = null;
        vm.logout = logout;
        vm.$state = $state;

        function login () {
            LoginService.open();
        }

		$scope.$on('authenticationSuccess', function() {
            getAccount();
        });

		function getAccount() {

			Principal.identity().then(function(account) {
                vm.account = account;
            });
        }

        function logout () {
            Auth.logout();

			$('#sidebar').attr('class','sidebar h-sidebar navbar-collapse collapse');
			$('#sidebar-shortcuts').attr('style','display:none');
			$('#sidebar-options').attr('style','display:none');

			$state.go('home');
        }
    }
})();
