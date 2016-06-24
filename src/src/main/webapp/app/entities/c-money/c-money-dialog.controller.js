(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_moneyDialogController', C_moneyDialogController);

    C_moneyDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_money'];

    function C_moneyDialogController ($scope, $stateParams, $uibModalInstance, entity, C_money) {
        var vm = this;
        vm.c_money = entity;
        vm.load = function(id) {
            C_money.get({id : id}, function(result) {
                vm.c_money = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_moneyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_money.id !== null) {
                C_money.update(vm.c_money, onSaveSuccess, onSaveError);
            } else {
                C_money.save(vm.c_money, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
