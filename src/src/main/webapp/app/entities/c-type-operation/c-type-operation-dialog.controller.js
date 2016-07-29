(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operationDialogController', C_type_operationDialogController);

    C_type_operationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_type_operation'];

    function C_type_operationDialogController ($scope, $stateParams, $uibModalInstance, entity, C_type_operation) {
        var vm = this;
        vm.c_type_operation = entity;
        vm.load = function(id) {
            C_type_operation.get({id : id}, function(result) {
                vm.c_type_operation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_type_operationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_type_operation.id !== null) {
                C_type_operation.update(vm.c_type_operation, onSaveSuccess, onSaveError);
            } else {
                C_type_operation.save(vm.c_type_operation, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
