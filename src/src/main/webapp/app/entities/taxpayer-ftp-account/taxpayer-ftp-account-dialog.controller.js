(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_ftp_accountDialogController', Taxpayer_ftp_accountDialogController);

    Taxpayer_ftp_accountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_ftp_account', 'Taxpayer_account'];

    function Taxpayer_ftp_accountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_ftp_account, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_ftp_account = entity;
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
            if (vm.taxpayer_ftp_account.id !== null) {
                Taxpayer_ftp_account.update(vm.taxpayer_ftp_account, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_ftp_account.save(vm.taxpayer_ftp_account, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_ftp_accountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
