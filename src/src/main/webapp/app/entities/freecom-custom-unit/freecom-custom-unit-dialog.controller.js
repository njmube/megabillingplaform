(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_custom_unitDialogController', Freecom_custom_unitDialogController);

    Freecom_custom_unitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_custom_unit'];

    function Freecom_custom_unitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_custom_unit) {
        var vm = this;

        vm.freecom_custom_unit = entity;
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
            if (vm.freecom_custom_unit.id !== null) {
                Freecom_custom_unit.update(vm.freecom_custom_unit, onSaveSuccess, onSaveError);
            } else {
                Freecom_custom_unit.save(vm.freecom_custom_unit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_custom_unitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
