(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_transferedDialogController', Freecom_local_transferedDialogController);

    Freecom_local_transferedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_local_transfered', 'Freecom_local_taxes'];

    function Freecom_local_transferedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_local_transfered, Freecom_local_taxes) {
        var vm = this;

        vm.freecom_local_transfered = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_local_taxes = Freecom_local_taxes.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_local_transfered.id !== null) {
                Freecom_local_transfered.update(vm.freecom_local_transfered, onSaveSuccess, onSaveError);
            } else {
                Freecom_local_transfered.save(vm.freecom_local_transfered, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_local_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
