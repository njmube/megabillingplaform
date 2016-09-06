(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_taxesDialogController', Freecom_local_taxesDialogController);

    Freecom_local_taxesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_local_taxes', 'Free_cfdi'];

    function Freecom_local_taxesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_local_taxes, Free_cfdi) {
        var vm = this;

        vm.freecom_local_taxes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.free_cfdis = Free_cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_local_taxes.id !== null) {
                Freecom_local_taxes.update(vm.freecom_local_taxes, onSaveSuccess, onSaveError);
            } else {
                Freecom_local_taxes.save(vm.freecom_local_taxes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_local_taxesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
