(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_transactionsDialogController', Taxpayer_transactionsDialogController);

    Taxpayer_transactionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_transactions', 'Taxpayer_account'];

    function Taxpayer_transactionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_transactions, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_transactions = entity;
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
            if (vm.taxpayer_transactions.id !== null) {
                Taxpayer_transactions.update(vm.taxpayer_transactions, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_transactions.save(vm.taxpayer_transactions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_transactionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
