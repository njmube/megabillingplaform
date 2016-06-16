(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('User_normalController', User_normalController);

    User_normalController.$inject = ['$scope', 'Principal', 'LoginService'];

    function User_normalController ($scope, Principal, LoginService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
