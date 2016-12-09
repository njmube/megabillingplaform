(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_stateDialogController', Type_stateDialogController);

    Type_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_state'];

    function Type_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, Type_state) {
        var vm = this;
        vm.type_state = entity;
        vm.load = function(id) {
            Type_state.get({id : id}, function(result) {
                vm.type_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:type_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.type_state.id !== null) {
                Type_state.update(vm.type_state, onSaveSuccess, onSaveError);
            } else {
                Type_state.save(vm.type_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
