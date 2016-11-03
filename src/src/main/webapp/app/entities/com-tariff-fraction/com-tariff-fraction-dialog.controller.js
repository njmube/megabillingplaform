(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tariff_fractionDialogController', Com_tariff_fractionDialogController);

    Com_tariff_fractionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_tariff_fraction'];

    function Com_tariff_fractionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_tariff_fraction) {
        var vm = this;
        vm.com_tariff_fraction = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_tariff_fractionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_tariff_fraction.id !== null) {
                Com_tariff_fraction.update(vm.com_tariff_fraction, onSaveSuccess, onSaveError);
            } else {
                Com_tariff_fraction.save(vm.com_tariff_fraction, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
