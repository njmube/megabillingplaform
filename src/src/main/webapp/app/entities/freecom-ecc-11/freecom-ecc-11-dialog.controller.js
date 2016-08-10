(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11DialogController', Freecom_ecc11DialogController);

    Freecom_ecc11DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_ecc11', 'Free_cfdi'];

    function Freecom_ecc11DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_ecc11, Free_cfdi) {
        var vm = this;

        vm.freecom_ecc11 = entity;
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
            if (vm.freecom_ecc11.id !== null) {
                Freecom_ecc11.update(vm.freecom_ecc11, onSaveSuccess, onSaveError);
            } else {
                Freecom_ecc11.save(vm.freecom_ecc11, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_ecc11Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
