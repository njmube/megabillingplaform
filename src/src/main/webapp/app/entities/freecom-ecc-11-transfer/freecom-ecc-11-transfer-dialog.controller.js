(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_transferDialogController', Freecom_ecc11_transferDialogController);

    Freecom_ecc11_transferDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_ecc11_transfer', 'Freecom_ecc11_concept'];

    function Freecom_ecc11_transferDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_ecc11_transfer, Freecom_ecc11_concept) {
        var vm = this;

        vm.freecom_ecc11_transfer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_ecc11_concepts = Freecom_ecc11_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_ecc11_transfer.id !== null) {
                Freecom_ecc11_transfer.update(vm.freecom_ecc11_transfer, onSaveSuccess, onSaveError);
            } else {
                Freecom_ecc11_transfer.save(vm.freecom_ecc11_transfer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_ecc11_transferUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
