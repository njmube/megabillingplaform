(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_exchangeDialogController', Freecom_foreign_exchangeDialogController);

    Freecom_foreign_exchangeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_foreign_exchange', 'C_type_operation', 'Free_cfdi'];

    function Freecom_foreign_exchangeDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_foreign_exchange, C_type_operation, Free_cfdi) {
        var vm = this;
        vm.freecom_foreign_exchange = entity;
        vm.c_type_operations = C_type_operation.query();
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_foreign_exchange.get({id : id}, function(result) {
                vm.freecom_foreign_exchange = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_foreign_exchangeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_foreign_exchange.id !== null) {
                Freecom_foreign_exchange.update(vm.freecom_foreign_exchange, onSaveSuccess, onSaveError);
            } else {
                Freecom_foreign_exchange.save(vm.freecom_foreign_exchange, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
