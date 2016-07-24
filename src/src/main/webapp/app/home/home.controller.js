(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'AuditsService','LoginService','$filter'];

    function HomeController ($scope, Principal,AuditsService,  LoginService, $filter) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.isNoAdmin = null;
        var today = new Date();
        vm.page = 1;
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        var dateFormat = 'yyyy-MM-dd';
        var fromDate = $filter('date')("0000-01-01", dateFormat);
        var toDate = $filter('date')(vm.toDate, dateFormat);
        var principal = " ";
        var auditEventType = " ";
        var ip = " ";
        vm.audits = AuditsService.query({
            page: vm.page -1,
            size:15,
            fromDate: fromDate,
            toDate: toDate,
            principal: principal,
            auditEventType: auditEventType,
            ip:ip});

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
