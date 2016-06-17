(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService'];

    function HomeController ($scope, Principal, LoginService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.isNoAdmin = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
			
			$('#sidebar').attr('class','sidebar h-sidebar navbar-collapse collapse');
			
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
				if(vm.account != null){
					vm.isNoAdmin = vm.account.authorities.indexOf('ROLE_ADMIN') == -1;
					
					if(!vm.isNoAdmin)
					{
						$('#sidebar').attr('class','sidebar responsive');
						$('#sidebar-shortcuts').attr('style','');
						$('#sidebar-options').attr('style','');
					}
				}
            });
        }
    }
})();
