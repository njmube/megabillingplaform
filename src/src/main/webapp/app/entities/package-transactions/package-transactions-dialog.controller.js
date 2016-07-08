(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_transactionsDialogController', Package_transactionsDialogController);

    Package_transactionsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Package_transactions', 'Package_state'];

    function Package_transactionsDialogController ($scope, $stateParams, $uibModalInstance, entity, Package_transactions, Package_state) {
        var vm = this;
        vm.package_transactions = entity;
        vm.package_states = Package_state.query({filtername:" "});
        vm.load = function(id) {
            Package_transactions.get({id : id}, function(result) {
                vm.package_transactions = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:package_transactionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.package_transactions.id !== null) {
                Package_transactions.update(vm.package_transactions, onSaveSuccess, onSaveError);
            } else {
                Package_transactions.save(vm.package_transactions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
