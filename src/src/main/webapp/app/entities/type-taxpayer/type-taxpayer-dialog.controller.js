(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_taxpayerDialogController', Type_taxpayerDialogController);

    Type_taxpayerDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_taxpayer'];

    function Type_taxpayerDialogController ($scope, $stateParams, $uibModalInstance, entity, Type_taxpayer) {
        var vm = this;
        vm.type_taxpayer = entity;
        vm.load = function(id) {
            Type_taxpayer.get({id : id}, function(result) {
                vm.type_taxpayer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:type_taxpayerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.type_taxpayer.id !== null) {
                Type_taxpayer.update(vm.type_taxpayer, onSaveSuccess, onSaveError);
            } else {
                Type_taxpayer.save(vm.type_taxpayer, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
