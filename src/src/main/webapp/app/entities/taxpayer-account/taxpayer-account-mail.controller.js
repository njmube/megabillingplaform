(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountMailController', Taxpayer_accountMailController);

    Taxpayer_accountMailController.$inject = ['$scope', 'AlertService', 'Taxpayer_mail_accounts', '$uibModal','Principal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountMailController($scope, AlertService, Taxpayer_mail_accounts, $uibModal, Principal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.save = save;
        vm.clear = clear;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;
        loadAll();

        function loadAll () {
            Taxpayer_mail_accounts.query({
                page: 0,
                size: 10,
                taxpayer_account: vm.taxpayer_account.id
            }, onSuccess, onError);

            function onSuccess(data) {
                vm.taxpayer_mail_accounts = data[0];
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

        function save(){
            vm.isSaving = true;
            vm.taxpayer_mail_accounts.taxpayer_account = vm.taxpayer_account;
            if (vm.taxpayer_mail_accounts.id !== null) {
                Taxpayer_mail_accounts.update(vm.taxpayer_mail_accounts, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_mail_accounts.save(vm.taxpayer_mail_accounts, onSaveSuccess, onSaveError);
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

