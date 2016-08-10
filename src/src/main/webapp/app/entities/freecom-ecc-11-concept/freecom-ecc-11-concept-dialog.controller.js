(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_conceptDialogController', Freecom_ecc11_conceptDialogController);

    Freecom_ecc11_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_ecc11_concept', 'Freecom_ecc11', 'Freecom_product_key', 'C_tar'];

    function Freecom_ecc11_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_ecc11_concept, Freecom_ecc11, Freecom_product_key, C_tar) {
        var vm = this;

        vm.freecom_ecc11_concept = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.freecom_ecc11s = Freecom_ecc11.query();
        vm.freecom_product_keys = Freecom_product_key.query();
        vm.c_tars = C_tar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_ecc11_concept.id !== null) {
                Freecom_ecc11_concept.update(vm.freecom_ecc11_concept, onSaveSuccess, onSaveError);
            } else {
                Freecom_ecc11_concept.save(vm.freecom_ecc11_concept, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_ecc11_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
