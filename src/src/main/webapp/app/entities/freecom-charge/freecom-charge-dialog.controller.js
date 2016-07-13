(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_chargeDialogController', Freecom_chargeDialogController);

    Freecom_chargeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_charge', 'Freecom_airline'];

    function Freecom_chargeDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_charge, Freecom_airline) {
        var vm = this;
        vm.freecom_charge = entity;
        vm.freecom_airlines = Freecom_airline.query();
        vm.load = function(id) {
            Freecom_charge.get({id : id}, function(result) {
                vm.freecom_charge = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_chargeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_charge.id !== null) {
                Freecom_charge.update(vm.freecom_charge, onSaveSuccess, onSaveError);
            } else {
                Freecom_charge.save(vm.freecom_charge, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
