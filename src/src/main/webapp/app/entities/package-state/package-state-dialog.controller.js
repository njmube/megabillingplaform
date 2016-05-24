(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_stateDialogController', Package_stateDialogController);

    Package_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Package_state', 'Package_transactions'];

    function Package_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, Package_state, Package_transactions) {
        var vm = this;
        vm.package_state = entity;
        vm.package_transactionss = Package_transactions.query();
        vm.load = function(id) {
            Package_state.get({id : id}, function(result) {
                vm.package_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:package_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.package_state.id !== null) {
                Package_state.update(vm.package_state, onSaveSuccess, onSaveError);
            } else {
                Package_state.save(vm.package_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
