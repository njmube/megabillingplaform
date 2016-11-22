(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$rootScope', 'AlertService','$scope', '$uibModal','$location', '$state', 'Auth', 'Principal', 'ENV', 'LoginService'];

    function FooterController ($rootScope,AlertService, $scope, $uibModal, $location, $state, Auth, Principal, ENV, LoginService) {
        var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;
        vm.$state = $state;

        vm.login = login;

		function login () {
            LoginService.open();
        }

        vm.asnSAT = asnSAT;

        function asnSAT(){
            Principal.identity().then(function() {
                $uibModal.open({
                    templateUrl: 'app/home/asnSAT.html',
                    controller: 'AsnSATController',
                    controllerAs: 'vm',
                    backdrop: true,
                    size: '',
                    resolve: {
                        entity: function () {
                            return 0;
                        }
                    }
                }).result.then(function () {

                }, function () {
                });
            });
        }
    }

})();
