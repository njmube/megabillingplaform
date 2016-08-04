(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_storeroom_paybillDialogController', Freecom_storeroom_paybillDialogController);

    Freecom_storeroom_paybillDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_storeroom_paybill', 'Free_cfdi'];

    function Freecom_storeroom_paybillDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_storeroom_paybill, Free_cfdi) {
        var vm = this;
        vm.freecom_storeroom_paybill = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_storeroom_paybill.get({id : id}, function(result) {
                vm.freecom_storeroom_paybill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_storeroom_paybillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_storeroom_paybill.id !== null) {
                Freecom_storeroom_paybill.update(vm.freecom_storeroom_paybill, onSaveSuccess, onSaveError);
            } else {
                Freecom_storeroom_paybill.save(vm.freecom_storeroom_paybill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
