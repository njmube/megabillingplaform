(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountIndexController', Taxpayer_accountIndexController);

    Taxpayer_accountIndexController.$inject = ['$scope', '$uibModal', 'AlertService', 'Taxpayer_transactions', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountIndexController($scope, $uibModal, AlertService, Taxpayer_transactions, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;

        function messtransaction(){
            Taxpayer_transactions.query({
                idaccount:vm.taxpayer_account.id
            }, onSuccess, onError);
        }

        function onSuccess(data) {
            vm.taxpayer_transactions = data;
            vm.taxpayer_transaction = vm.taxpayer_transactions[0];

            if (vm.taxpayer_transaction.transactions_available == 0){
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-account/messtransactions.html',
                    controller: 'MessTransactionsController',
                    controllerAs: 'vm',
                    backdrop: true,
                    size: ''
                }).result.then(function () {}, function () {});
            }
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }

        messtransaction();

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });

        $scope.$on('$destroy', unsubscribe);

        function changeAccount(){
            window.location.assign(getAbsolutePath()+vm.taxpayer_account.id);
        }

        function getAbsolutePath() {
            var loc = window.location.href;
            var pathName = loc.substring(0, loc.lastIndexOf('/') + 1);
            return pathName;
        }
    }
})();
