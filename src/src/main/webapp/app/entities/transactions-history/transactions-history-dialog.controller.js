(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Transactions_historyDialogController', Transactions_historyDialogController);

    Transactions_historyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Transactions_history', 'C_system', 'User', 'Type_transaction', 'Taxpayer_account', 'Ring_pack'];

    function Transactions_historyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Transactions_history, C_system, User, Type_transaction, Taxpayer_account, Ring_pack) {
        var vm = this;

        vm.transactions_history = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.c_systems = C_system.query();
        vm.users = User.query();
        vm.type_transactions = Type_transaction.query();
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.ring_packs = Ring_pack.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transactions_history.id !== null) {
                Transactions_history.update(vm.transactions_history, onSaveSuccess, onSaveError);
            } else {
                Transactions_history.save(vm.transactions_history, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:transactions_historyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_transaction = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
