(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_mail_accountsDialogController', Taxpayer_mail_accountsDialogController);

    Taxpayer_mail_accountsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_mail_accounts', 'Taxpayer_account'];

    function Taxpayer_mail_accountsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_mail_accounts, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_mail_accounts = entity;
        vm.clear = clear;
        vm.save = save;
        vm.taxpayer_accounts = Taxpayer_account.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_mail_accounts.id !== null) {
                Taxpayer_mail_accounts.update(vm.taxpayer_mail_accounts, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_mail_accounts.save(vm.taxpayer_mail_accounts, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_mail_accountsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
