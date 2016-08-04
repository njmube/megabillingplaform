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

        getAccount();

		$scope.$on('authenticationSuccess', function() {
            getAccount();
        });

		function getAccount() {

			Principal.identity().then(function(account) {
                vm.account = account;

                if(vm.account != null){
                    vm.isNoAdmin = vm.account.authorities.indexOf('ROLE_ADMIN') == -1;

                    if(!vm.isNoAdmin){
                        $('#sidebar').attr('class','sidebar responsive sidebar-fixed sidebar-scroll');
                        $('#sidebar').attr('data-sidebar','true');
                        $('#sidebar').attr('data-sidebar-scroll','true');
                        $('#sidebar').attr('data-sidebar-hover','true');
                        $('.nav-wrap, .scroll-content').css('max-height',($(window).height() - 55) + 'px');
                        $('.scroll-track.scroll-active').css('height',($(window).height() - 55) + 'px');
                        $('#sidebar-shortcuts').attr('style','');
                        $('#sidebar-options').attr('style','');
                    }
                }
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
