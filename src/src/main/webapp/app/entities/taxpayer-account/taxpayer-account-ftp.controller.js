(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountFtpController', Taxpayer_accountFtpController);

    Taxpayer_accountFtpController.$inject = ['$scope', 'AlertService', 'Taxpayer_ftp_account', '$uibModal','Principal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountFtpController($scope, AlertService, Taxpayer_ftp_account, $uibModal, Principal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.save = save;
        vm.clear = clear;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;
        loadAll();

        function loadAll () {
            Taxpayer_ftp_account.query({
                page: 0,
                size: 10,
                taxpayer_account: vm.taxpayer_account.id
            }, onSuccess, onError);

            function onSuccess(data) {
                vm.taxpayer_ftp_account = data[0];
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
            loadAll();
        }

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function save(){
            vm.isSaving = true;
            vm.taxpayer_ftp_account.taxpayer_account = vm.taxpayer_account;
            if (vm.taxpayer_ftp_account.id !== null) {
                Taxpayer_ftp_account.update(vm.taxpayer_ftp_account, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_ftp_account.save(vm.taxpayer_ftp_account, onSaveSuccess, onSaveError);
            }
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_ftp_account =  result;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
    }
})();


