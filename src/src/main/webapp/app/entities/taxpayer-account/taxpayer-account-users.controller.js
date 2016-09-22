(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountUsersController', Taxpayer_accountUsersController);

    Taxpayer_accountUsersController.$inject = ['$scope', '$uibModal','Principal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountUsersController($scope, $uibModal, Principal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.add = add;
        vm.deleteUser = deleteUser;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function deleteUser(user){
            var list = [];
            var cont = 0;
            for(var i = 0; i<vm.taxpayer_account.users.length; i++){
                if(user.login != vm.taxpayer_account.users[i].login){
                    list[cont] = vm.taxpayer_account.users[i];
                    cont++;
                }
            }
            vm.taxpayer_account.users = list;
            Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_account =  result;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        function add(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/add-user.html',
                controller: 'AddUserController',
                controllerAs: 'vm',
                backdrop: true,
                size: '',
                resolve: {
                    entity: function () {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function(result) {
                    vm.taxpayer_account = result.taxpayer_account;
                }, function() {
                });
        }
    }
})();
