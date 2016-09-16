(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_transactionDialogController', Type_transactionDialogController);

    Type_transactionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_transaction'];

    function Type_transactionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Type_transaction) {
        var vm = this;

        vm.type_transaction = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.type_transaction.id !== null) {
                Type_transaction.update(vm.type_transaction, onSaveSuccess, onSaveError);
            } else {
                Type_transaction.save(vm.type_transaction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:type_transactionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
