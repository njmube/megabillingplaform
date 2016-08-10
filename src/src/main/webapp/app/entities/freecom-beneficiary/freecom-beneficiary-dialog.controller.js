(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_beneficiaryDialogController', Freecom_beneficiaryDialogController);

    Freecom_beneficiaryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_beneficiary'];

    function Freecom_beneficiaryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_beneficiary) {
        var vm = this;

        vm.freecom_beneficiary = entity;
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
            if (vm.freecom_beneficiary.id !== null) {
                Freecom_beneficiary.update(vm.freecom_beneficiary, onSaveSuccess, onSaveError);
            } else {
                Freecom_beneficiary.save(vm.freecom_beneficiary, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_beneficiaryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
