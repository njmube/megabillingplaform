(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountTransactionController', Taxpayer_accountTransactionController);

    Taxpayer_accountTransactionController.$inject = ['$scope', 'Ring_pack', 'Type_transaction', 'AlertService', 'Taxpayer_transactions', '$uibModal','Principal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountTransactionController($scope, Ring_pack, Type_transaction, AlertService, Taxpayer_transactions, $uibModal, Principal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.type_transactions = Type_transaction.query();
        vm.taxpayer_transaction = {};
        vm.ring_packs = Ring_pack.query();
        vm.accountdestiny = {};
        vm.accountbuy = {};
        vm.accountsource = {};
        vm.ring_pack = {};
        vm.counttransfer = 0;
        vm.showaccountmess = null;
        vm.showcounttransfmess = null;
        vm.showaccountsource = null;
        vm.showaccountbuy = null;
        vm.buy = buy;
        vm.transfer = transfer;
        vm.clear = clear;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;

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
            if(vm.ring_pack != null && vm.accountbuy != null){
                Ring_pack.buytransactions({
                    idaccount: vm.accountbuy.id,
                    idring_pack: vm.ring_pack.id
                }, onSuccessbuy, onErrorbuy);
            }else{
                vm.showaccountbuy = 'OK';
            }
        }

        function onSuccessbuy() {
            Principal.identity().then(function(account) {

                if(account != null){
                    User.get({login: account.login}, function(result) {

                        window.location.assign("http://payu-prod.megacfdi.com/content/common/payu/integrationRequest.jsf"+
                        "idUser="+ result.id + "&idAccount=" + vm.accountbuy.id + "&idRingPackage=" + vm.ring_pack.id);

                    });
                }
            });

        }
        function onErrorbuy(error) {
            AlertService.error(error.data.message);
        }
        function transfer(){
            vm.showcounttransfmess = null;
            vm.showaccountmess = null;
            vm.showaccountsource = null;

            if(vm.accountsource != null){
                if(vm.accountdestine != null) {
                    if (vm.accountdestine.id != vm.accountsource.id) {

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
                                        idaccount:vm.accountsource.id
                                    }, onSuccess, onError);

                                    function onSuccess(data, headers) {
                                        vm.taxpayer_transactions = data;
                                        vm.taxpayer_transaction = vm.taxpayer_transactions[0];

                                        if (vm.counttransfer <= vm.taxpayer_transaction.transactions_available){
                                            Taxpayer_transactions.query({
                                                page: 0,
                                                size: 20,
                                                idaccount: vm.accountdestine.id
                                            }, onSuccess1, onError1);

                                        }else {
                                            //Cantidad insuficiente
                                            vm.showcounttransfmess = 'OK';
                                        }
                                    }
                                    function onError(error) {
                                        AlertService.error(error.data.message);
                                    }

                                    function onSuccess1(data, headers) {
                                        vm.taxpayer_transactions1 = data;
                                        vm.accountdestiny = vm.taxpayer_transactions1[0];

                                        vm.taxpayer_transaction.transactions_available -= vm.counttransfer;

                                        Taxpayer_transactions.update(vm.taxpayer_transaction, onSaveSuccessTrans, onSaveErrorTras);

                                    }
                                    function onError1(error) {
                                        AlertService.error(error.data.message);
                                    }
                                    function onSaveSuccessTrans(resulttrans){
                                        vm.taxpayer_transaction = resulttrans;

                                        var counttemp = vm.counttransfer;
                                        var temp = parseInt(vm.accountdestiny.transactions_available);
                                        var sum = 0;
                                        sum = temp + parseInt(counttemp);
                                        vm.accountdestiny.transactions_available = sum;
                                        Taxpayer_transactions.update(vm.accountdestiny, onSaveSuccessTrans1, onSaveErrorTras1);
                                        function onSaveSuccessTrans1(resulttrans1){
                                            vm.counttransfer = 0;
                                            vm.accountdestiny = resulttrans1;
                                            Taxpayer_transactions.history_email({
                                                idsource: vm.taxpayer_transaction.id,
                                                iddestiny: vm.accountdestiny.id,
                                                amount: counttemp
                                            }, onSuccessHistoty, onErrorHistory);
                                            function onSuccessHistoty(){

                                            }
                                            function onErrorHistory(){

                                            }
                                        }
                                        function onSaveErrorTras1(){

                                        }
                                    }
                                    function onSaveErrorTras(){

                                    }

                                }, function () {
                                });

                    } else {
                        //tranfiriendo para la misma cuenta
                        vm.showaccountmess = 'OK';
                    }
                }else{
                    //tranfiriendo para la misma cuenta
                    vm.showaccountmess = 'OK';
                }
            }else{
                //tranfiriendo para la misma cuenta
                vm.showaccountsource = 'OK';
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


