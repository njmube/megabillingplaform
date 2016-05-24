(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_stateDialogController', Voucher_stateDialogController);

    Voucher_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Voucher_state'];

    function Voucher_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, Voucher_state) {
        var vm = this;
        vm.voucher_state = entity;
        vm.load = function(id) {
            Voucher_state.get({id : id}, function(result) {
                vm.voucher_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:voucher_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.voucher_state.id !== null) {
                Voucher_state.update(vm.voucher_state, onSaveSuccess, onSaveError);
            } else {
                Voucher_state.save(vm.voucher_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
