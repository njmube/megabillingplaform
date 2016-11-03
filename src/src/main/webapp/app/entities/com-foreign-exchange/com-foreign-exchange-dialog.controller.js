(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_exchangeDialogController', Com_foreign_exchangeDialogController);

    Com_foreign_exchangeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_foreign_exchange', 'C_type_operation', 'Cfdi'];

    function Com_foreign_exchangeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_foreign_exchange, C_type_operation, Cfdi) {
        var vm = this;
        vm.com_foreign_exchange = entity;
        vm.c_type_operations = C_type_operation.query();
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_foreign_exchangeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_foreign_exchange.id !== null) {
                Com_foreign_exchange.update(vm.com_foreign_exchange, onSaveSuccess, onSaveError);
            } else {
                Com_foreign_exchange.save(vm.com_foreign_exchange, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
