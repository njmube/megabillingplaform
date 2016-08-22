(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operation_ceDialogController', C_type_operation_ceDialogController);

    C_type_operation_ceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_type_operation_ce'];

    function C_type_operation_ceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_type_operation_ce) {
        var vm = this;

        vm.c_type_operation_ce = entity;
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
            if (vm.c_type_operation_ce.id !== null) {
                C_type_operation_ce.update(vm.c_type_operation_ce, onSaveSuccess, onSaveError);
            } else {
                C_type_operation_ce.save(vm.c_type_operation_ce, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_type_operation_ceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
