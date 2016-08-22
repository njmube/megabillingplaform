(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tariff_fractionDialogController', Freecom_tariff_fractionDialogController);

    Freecom_tariff_fractionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_tariff_fraction'];

    function Freecom_tariff_fractionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_tariff_fraction) {
        var vm = this;

        vm.freecom_tariff_fraction = entity;
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
            if (vm.freecom_tariff_fraction.id !== null) {
                Freecom_tariff_fraction.update(vm.freecom_tariff_fraction, onSaveSuccess, onSaveError);
            } else {
                Freecom_tariff_fraction.save(vm.freecom_tariff_fraction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_tariff_fractionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
