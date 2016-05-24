(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_typeDialogController', Voucher_typeDialogController);

    Voucher_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Voucher_type'];

    function Voucher_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Voucher_type) {
        var vm = this;
        vm.voucher_type = entity;
        vm.load = function(id) {
            Voucher_type.get({id : id}, function(result) {
                vm.voucher_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:voucher_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.voucher_type.id !== null) {
                Voucher_type.update(vm.voucher_type, onSaveSuccess, onSaveError);
            } else {
                Voucher_type.save(vm.voucher_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
