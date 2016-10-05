(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountTransactionController', Taxpayer_accountTransactionController);

    Taxpayer_accountTransactionController.$inject = ['$scope', 'Type_transaction', 'AlertService', 'Taxpayer_transactions', '$uibModal','Principal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountTransactionController($scope, Type_transaction, AlertService, Taxpayer_transactions, $uibModal, Principal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.type_transactions = Type_transaction.query();
        vm.accountdestiny = {};
        vm.counttransfer = 0;
        vm.countbuy = 0;
        vm.showaccountmess = null;
        vm.showcounttransfmess = null;
        vm.buy = buy;
        vm.transfer = transfer;
        vm.clear = clear;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;

        loadAll();

        function loadAll () {
            Taxpayer_transactions.query({
                page: 0,
                size: 20,
                idaccount:vm.taxpayer_account.id
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.taxpayer_transactions = data;
                vm.taxpayer_transaction = vm.taxpayer_transactions[0];
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function changeAccount(){
            window.location.assign(getAbsolutePath()+vm.taxpayer_account.id);
        }

        function getAbsolutePath() {
            var loc = window.location.href;
            var pathName = loc.substring(0, loc.lastIndexOf('/') + 1);
            return pathName;
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function buy(){

        }

        function transfer(){
            vm.showcounttransfmess = null;
            vm.showaccountmess = null;
            if(vm.taxpayer_transaction != null){
                if(vm.accountdestine != null) {
                    if (vm.accountdestine.id != vm.taxpayer_account.id) {
                        if (vm.counttransfer <= vm.taxpayer_transaction.transactions_available) {
                            $uibModal.open({
                                templateUrl: 'app/entities/taxpayer-account/confirmation-transfer.html',
                                controller: 'Confirmation_transferController',
                                controllerAs: 'vm',
                                backdrop: true,
                                size: ''
                            }).result.then(function (result) {

                                    Taxpayer_transactions.query({
                                        page: 0,
                                        size: 20,
                                        idaccount: vm.accountdestine.id
                                    }, onSuccess1, onError1);
                                    function onSuccess1(data, headers) {
                                        vm.taxpayer_transactions1 = data;
                                        vm.accountdestiny = vm.taxpayer_transactions1[0];
                                    }
                                    function onError1(error) {
                                        AlertService.error(error.data.message);
                                    }
                                    var counttemp = vm.counttransfer;
                                    vm.taxpayer_transaction.transactions_available -= vm.counttransfer;

                                    Taxpayer_transactions.update(vm.taxpayer_transaction, onSaveSuccessTrans, onSaveErrorTras);
                                    function onSaveSuccessTrans(resulttrans){
                                        vm.taxpayer_transaction = resulttrans;
                                        var temp = parseInt(vm.accountdestiny.transactions_available);
                                        var sum = 0;
                                        sum = temp + parseInt(counttemp);
                                        vm.accountdestiny.transactions_available = sum;
                                        Taxpayer_transactions.update(vm.accountdestiny, onSaveSuccessTrans1, onSaveErrorTras1);
                                        function onSaveSuccessTrans1(resulttrans1){
                                            vm.counttransfer = 0;
                                            vm.accountdestiny = resulttrans1;
                                        }
                                        function onSaveErrorTras1(){

                                        }
                                    }
                                    function onSaveErrorTras(){

                                    }

                                }, function () {
                                });
                        } else {
                            //Cantidad insuficiente
                            vm.showcounttransfmess = 'OK';
                        }
                    } else {
                        //tranfiriendo para la misma cuenta
                        vm.showaccountmess = 'OK';
                    }
                }else{
                    //tranfiriendo para la misma cuenta
                    vm.showaccountmess = 'OK';
                }
            }
        }
        var onSaveSuccess = function (result) {
            vm.taxpayer_mail_accounts =  result;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
    }
})();


