(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_destruction_certificateDialogController', Freecom_destruction_certificateDialogController);

    Freecom_destruction_certificateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_destruction_certificate', 'Free_cfdi', 'C_type_series'];

    function Freecom_destruction_certificateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_destruction_certificate, Free_cfdi, C_type_series) {
        var vm = this;

        vm.freecom_destruction_certificate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.free_cfdis = Free_cfdi.query();
        vm.c_type_series = C_type_series.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_destruction_certificate.id !== null) {
                Freecom_destruction_certificate.update(vm.freecom_destruction_certificate, onSaveSuccess, onSaveError);
            } else {
                Freecom_destruction_certificate.save(vm.freecom_destruction_certificate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_destruction_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
